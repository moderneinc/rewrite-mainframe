/*
 * Copyright 2026 the original author or authors.
 * <p>
 * Licensed under the Moderne Source Available License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://docs.moderne.io/licensing/moderne-source-available-license
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.mainframe.cobol.trait;

import lombok.Value;
import org.jspecify.annotations.Nullable;
import org.openrewrite.Cursor;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.Identifier;
import org.openrewrite.mainframe.cobol.tree.Name;
import org.openrewrite.trait.SimpleTraitMatcher;
import org.openrewrite.trait.Trait;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Collections.emptyList;
import static java.util.Collections.nCopies;
import static java.util.Collections.singleton;

/**
 * The point at which a program is handed its PCBs.
 * <p>
 * An IMS program receives one PCB per database or destination its PSB declares, and the binding is
 * positional: the PSB's first PCB arrives as the first argument, and nothing in the COBOL names the
 * database. The order is what lets a DL/I call be attributed to a database rather than to a
 * variable, which is why an IMS analysis has to read the entry point before it reads any call.
 * <p>
 * Mapping a position onto a database name additionally needs the PSB, which lives outside the COBOL.
 */
@Value
public class ProgramEntry implements Trait<Cobol> {

    Cursor cursor;

    /**
     * PCB names in the order this program receives them, which is the PSB's order in a program that
     * no other contains.
     */
    public List<String> getPcbNames() {
        List<String> names = new ArrayList<>();
        Cobol tree = getTree();
        if (tree instanceof Cobol.Entry) {
            List<Identifier> identifiers = ((Cobol.Entry) tree).getIdentifiers();
            if (identifiers != null) {
                for (Identifier identifier : identifiers) {
                    add(names, identifier);
                }
            }
        } else {
            for (Cobol phrase : ((Cobol.ProcedureDivisionUsingClause) tree).getProcedureDivisionUsingParameter()) {
                if (phrase instanceof Cobol.ProcedureDivisionByReferencePhrase) {
                    for (Cobol.ProcedureDivisionByReference parameter :
                            ((Cobol.ProcedureDivisionByReferencePhrase) phrase).getProcedureDivisionByReference()) {
                        add(names, parameter.getReference());
                    }
                } else if (phrase instanceof Cobol.ProcedureDivisionByValuePhrase) {
                    for (Name parameter : ((Cobol.ProcedureDivisionByValuePhrase) phrase).getPhrases()) {
                        add(names, parameter);
                    }
                }
            }
        }
        return names;
    }

    /**
     * The zero based position of {@code pcbName} in the PSB, or -1 when the name is not a PCB the
     * program was handed or its position cannot be told. The position is what a PSB listing has to
     * be joined on to name the database. A nested program is handed its parameters by a call in the
     * program containing it, so a parameter takes the position of the argument passed in its place.
     */
    public int indexOf(@Nullable String pcbName) {
        List<String> names = getPcbNames();
        int parameter = pcbName == null ? -1 : names.indexOf(pcbName.toUpperCase(Locale.ROOT));
        if (parameter < 0) {
            return -1;
        }
        Cursor program = enclosing(cursor, Cobol.ProgramUnit.class);
        Cursor above = program == null ? null : program.getParent();
        Cursor containing = above == null ? null : enclosing(above, Cobol.ProgramUnit.class);
        return program == null || containing == null ? parameter :
                psbPositions(program, containing, names.size()).get(parameter);
    }

    /**
     * Whether {@code pcbName} is the I/O PCB, meaning calls against it read and write the message
     * queue rather than a database.
     * <p>
     * Message driven programs receive the I/O PCB first, which is the signal used here alongside the
     * naming conventions the field has settled on. A batch DL/I program with no message queue has no
     * I/O PCB at all, so a first position match is only trusted when the name does not look like a
     * database PCB.
     */
    public boolean isIoPcb(@Nullable String pcbName) {
        if (pcbName == null) {
            return false;
        }
        String upper = pcbName.toUpperCase(Locale.ROOT);
        if (upper.contains("IO-PCB") || upper.contains("IOPCB") ||
            upper.contains("TP-PCB") || upper.contains("TPPCB")) {
            return true;
        }
        return indexOf(upper) == 0 && !namesDatabasePcb(upper) && !namesDatabasePcb(firstPsbPcb());
    }

    private static boolean namesDatabasePcb(@Nullable String name) {
        return name != null && (name.contains("DB-PCB") || name.contains("DBPCB"));
    }

    /**
     * The name the outermost program's entry gives the PSB's first PCB, which a nested program may
     * know by another name.
     */
    private @Nullable String firstPsbPcb() {
        Program program = Program.of(cursor);
        ProgramEntry entry = program == null ? null : of(program.outermost().getCursor());
        List<String> names = entry == null ? emptyList() : entry.getPcbNames();
        return names.isEmpty() ? null : names.get(0);
    }

    /**
     * Whether {@code pcbName} names a message destination rather than a database: the I/O PCB the
     * reply goes back on, or an alternate PCB, which sends to some other logical terminal — a branch
     * printer, say. Both are TP PCBs, and what an {@code ISRT} against one names in its fourth
     * argument is a MOD and not a segment search argument.
     * <p>
     * Only the PSB says which a mask really is, so an alternate PCB is recognised the way the I/O PCB
     * is, by the naming conventions the field has settled on.
     */
    public boolean isMessagePcb(@Nullable String pcbName) {
        if (isIoPcb(pcbName)) {
            return true;
        }
        String upper = pcbName == null ? "" : pcbName.toUpperCase(Locale.ROOT);
        return upper.contains("ALT-PCB") || upper.contains("ALTPCB");
    }

    /**
     * The PSB position each of a nested program's parameters receives, or -1 for one where its callers
     * do not agree on a PCB. A COMMON program's siblings can call it too, so all of its positions are
     * unknown. A dynamic call whose target cannot be read might be calling it, which leaves them
     * unknown too. Kept on the nested program's cursor, since every DL/I call in it asks.
     */
    private static List<Integer> psbPositions(Cursor nested, Cursor containing, int parameters) {
        return nested.computeMessageIfAbsent("cobol.psbPositions", k -> {
            List<Integer> positions = new ArrayList<>(nCopies(parameters, -1));
            Cobol.ProgramIdParagraph id = nested.<Cobol.ProgramUnit>getValue().getIdentificationDivision()
                    .getProgramIdParagraph();
            List<Cobol.Word> attributes = id.getProgramAttributes();
            Cobol.ProcedureDivision division = containing.<Cobol.ProgramUnit>getValue().getProcedureDivision();
            if (division == null ||
                attributes != null && attributes.stream().anyMatch(word -> "COMMON".equalsIgnoreCase(word.getWord()))) {
                return positions;
            }
            ProgramEntry entry = of(containing);
            List<List<@Nullable String>> calls = callsTo(Literals.unquote(new Program(nested).getName()),
                    new Cursor(containing, division));
            if (entry != null && calls != null) {
                for (int parameter = 0; parameter < parameters; parameter++) {
                    positions.set(parameter, agreed(calls, parameter, entry));
                }
            }
            return positions;
        });
    }

    /**
     * The arguments of each call in {@code division} that can reach {@code program}, or null where a
     * call's targets cannot all be read.
     */
    private static @Nullable List<List<@Nullable String>> callsTo(String program, Cursor division) {
        List<List<@Nullable String>> calls = new ArrayList<>();
        AtomicBoolean unreadable = new AtomicBoolean();
        new CopybookSkippingVisitor<Integer>() {
            @Override
            public Cobol.Call visitCall(Cobol.Call call, Integer p) {
                String target = Names.of(call.getIdentifier());
                String literal = target == null ? null : Literals.valueOf(target);
                Set<String> names = literal != null ? singleton(literal) :
                        LiteralAssignment.candidates(getCursor(), target);
                if (names == null) {
                    unreadable.set(true);
                } else if (names.stream().anyMatch(name -> name.trim().equalsIgnoreCase(program))) {
                    calls.add(DliCall.argumentsByPosition(call));
                }
                return super.visitCall(call, p);
            }
        }.visit(division.getValue(), 0, division.getParentOrThrow());
        return unreadable.get() ? null : calls;
    }

    private static int agreed(List<List<@Nullable String>> calls, int parameter, ProgramEntry caller) {
        int agreed = -1;
        for (List<@Nullable String> arguments : calls) {
            int position = parameter < arguments.size() ? caller.indexOf(arguments.get(parameter)) : -1;
            if (position < 0 || agreed >= 0 && position != agreed) {
                return -1;
            }
            agreed = position;
        }
        return agreed;
    }

    private static void add(List<String> names, Cobol tree) {
        String pcb = Names.upperOf(tree);
        if (pcb != null) {
            names.add(pcb);
        }
    }

    public static class Matcher extends SimpleTraitMatcher<ProgramEntry> {

        @Override
        protected @Nullable ProgramEntry test(Cursor cursor) {
            Object value = cursor.getValue();
            return value instanceof Cobol.Entry || value instanceof Cobol.ProcedureDivisionUsingClause ?
                    new ProgramEntry(cursor) : null;
        }
    }

    /**
     * The entry point of the program {@code cursor} is in or at, or null for one that takes nothing.
     * <p>
     * {@code PROCEDURE DIVISION USING} is the entry point where a program has one, so no search is
     * needed to reach it. Only an IMS batch program, which is entered at {@code ENTRY 'DLITCBL' USING}
     * instead, has to be looked through — and the answer is kept on the program's cursor, because
     * every DL/I call in the program asks for it.
     */
    public static @Nullable ProgramEntry of(Cursor cursor) {
        Cursor program = enclosing(cursor, Cobol.ProgramUnit.class);
        Cobol.ProcedureDivision procedureDivision = program == null ? null :
                program.<Cobol.ProgramUnit>getValue().getProcedureDivision();
        if (program == null || procedureDivision == null) {
            return null;
        }
        Cursor division = new Cursor(program, procedureDivision);
        if (procedureDivision.getProcedureDivisionUsingClause() != null) {
            return new ProgramEntry(new Cursor(division,
                    procedureDivision.getProcedureDivisionUsingClause()));
        }
        Optional<ProgramEntry> entry = program.computeMessageIfAbsent("cobol.programEntry",
                k -> new Matcher().lower(division).findFirst());
        return entry.orElse(null);
    }

    private static @Nullable Cursor enclosing(Cursor cursor, Class<? extends Cobol> type) {
        for (Iterator<Cursor> path = cursor.getPathAsCursors(); path.hasNext(); ) {
            Cursor enclosing = path.next();
            if (type.isInstance(enclosing.getValue())) {
                return enclosing;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ENTRY " + getPcbNames();
    }
}
