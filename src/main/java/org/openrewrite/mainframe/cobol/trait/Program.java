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
import org.openrewrite.mainframe.cobol.CobolParser;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.trait.SimpleTraitMatcher;
import org.openrewrite.trait.Trait;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.requireNonNull;

/**
 * The program a statement belongs to.
 * <p>
 * Every row a portfolio analysis writes is attributed to a program, so this is the question asked
 * most often and from the deepest part of the tree. Read from the cursor rather than tracked as
 * visitor state: a nested program's statements answer with the nested program, and a lambda handed a
 * cursor can ask without being a visitor of its own.
 */
@Value
public class Program implements Trait<Cobol.ProgramUnit> {

    Cursor cursor;

    /**
     * The name from {@code PROGRAM-ID}, or empty where the paragraph carries none.
     */
    public String getName() {
        String name = Names.of(getTree().getIdentificationDivision().getProgramIdParagraph().getProgramName());
        return name == null ? "" : name;
    }

    /**
     * The EXEC interface block the CICS translator inserts into a program it translates, as a DATA
     * DIVISION whose LINKAGE SECTION declares DFHEIBLK. The division hangs under this program's
     * cursor, so a definition in it belongs to this program.
     * <p>
     * Null where this program declares a DFHEIBLK itself, since the translator then inserts none.
     * Null for a nested program too, since the integrated translator declares the block GLOBAL in
     * the outermost program only. A program is nested only if its cursor reaches the program
     * containing it.
     */
    public @Nullable Cursor getExecInterfaceBlock() {
        return outermost() != this || declaresExecInterfaceBlock() ? null : new Cursor(cursor, Translated.EXEC_INTERFACE_BLOCK);
    }

    /**
     * The DL/I interface block the translator inserts into a program with EXEC DLI commands, as a
     * DATA DIVISION whose WORKING-STORAGE SECTION declares DLZDIB. The division hangs under this
     * program's cursor. Null where this program's own PROCEDURE DIVISION has no EXEC DLI, so a
     * nested program issuing none does not see the block of a program containing it.
     */
    public @Nullable Cursor getDliInterfaceBlock() {
        return issuesExecDli() ? new Cursor(cursor, Translated.DLI_INTERFACE_BLOCK) : null;
    }

    /**
     * The outermost program on this program's cursor, or this program itself where none contains it.
     */
    Program outermost() {
        Cursor outermost = cursor;
        for (Iterator<Cursor> path = cursor.getPathAsCursors(); path.hasNext(); ) {
            Cursor enclosing = path.next();
            if (enclosing.getValue() instanceof Cobol.ProgramUnit) {
                outermost = enclosing;
            }
        }
        return outermost == cursor ? this : new Program(outermost);
    }

    private boolean declaresExecInterfaceBlock() {
        Cobol.DataDivision division = getTree().getDataDivision();
        if (division == null) {
            return false;
        }
        AtomicBoolean declared = new AtomicBoolean();
        new CopybookSkippingVisitor<AtomicBoolean>() {
            @Override
            public Cobol.DataDescriptionEntry visitDataDescriptionEntry(Cobol.DataDescriptionEntry entry, AtomicBoolean found) {
                if ("DFHEIBLK".equals(Names.upperOf(entry.getName()))) {
                    found.set(true);
                }
                return entry;
            }
        }.visit(division, declared, cursor);
        return declared.get();
    }

    private boolean issuesExecDli() {
        Cobol.ProcedureDivision division = getTree().getProcedureDivision();
        if (division == null) {
            return false;
        }
        AtomicBoolean issued = new AtomicBoolean();
        new CopybookSkippingVisitor<AtomicBoolean>() {
            @Override
            public Cobol.ExecDliStatement visitExecDliStatement(Cobol.ExecDliStatement exec, AtomicBoolean found) {
                found.set(true);
                return exec;
            }
        }.visit(division, issued, cursor);
        return issued.get();
    }

    public static class Matcher extends SimpleTraitMatcher<Program> {

        @Override
        protected @Nullable Program test(Cursor cursor) {
            return cursor.getValue() instanceof Cobol.ProgramUnit ? new Program(cursor) : null;
        }
    }

    /**
     * The program {@code cursor} is inside, innermost first.
     * <p>
     * At the compilation unit itself — where an analysis that reports once per file asks — there is
     * no program above to find, so the file's first program answers instead. A file holding several
     * is rare and the first is the one it is named for.
     */
    public static @Nullable Program of(Cursor cursor) {
        for (Iterator<Cursor> path = cursor.getPathAsCursors(); path.hasNext(); ) {
            Cursor enclosing = path.next();
            Object value = enclosing.getValue();
            if (value instanceof Cobol.ProgramUnit) {
                return new Program(enclosing);
            }
            if (value instanceof Cobol.CompilationUnit) {
                List<Cobol.ProgramUnit> units = ((Cobol.CompilationUnit) value).getProgramUnits();
                return units.isEmpty() ? null : new Program(new Cursor(enclosing, units.get(0)));
            }
        }
        return null;
    }

    /**
     * The name of the program {@code cursor} is inside. {@code UNKNOWN} where there is none to read,
     * which keeps a row attributable to something rather than dropping it.
     */
    public static String nameOf(Cursor cursor) {
        return nameOf(of(cursor));
    }

    /**
     * The name of the program a file declares, for a caller holding the file rather than a cursor
     * into it.
     */
    public static String nameOf(Cobol.CompilationUnit cu) {
        List<Cobol.ProgramUnit> units = cu.getProgramUnits();
        return units.isEmpty() ? "UNKNOWN" :
                nameOf(new Program(new Cursor(new Cursor(null, Cursor.ROOT_VALUE), units.get(0))));
    }

    private static String nameOf(@Nullable Program program) {
        if (program == null) {
            return "UNKNOWN";
        }
        String name = program.getName();
        return name.isEmpty() ? "UNKNOWN" : name;
    }

    /**
     * No program's source contains the blocks the translator inserts, so each is parsed once.
     */
    private static final class Translated {

        private static final Cobol.DataDivision EXEC_INTERFACE_BLOCK = parse(
                "       IDENTIFICATION DIVISION.\n" +
                "       PROGRAM-ID. DFHEIBLK.\n" +
                "       DATA DIVISION.\n" +
                "       LINKAGE SECTION.\n" +
                "       COPY DFHEIBLK.\n");

        private static final Cobol.DataDivision DLI_INTERFACE_BLOCK = parse(
                "       IDENTIFICATION DIVISION.\n" +
                "       PROGRAM-ID. DLZDIB.\n" +
                "       DATA DIVISION.\n" +
                "       WORKING-STORAGE SECTION.\n" +
                "       COPY DLZDIB.\n");

        private static Cobol.DataDivision parse(String source) {
            Cobol.CompilationUnit unit = (Cobol.CompilationUnit) CobolParser.builder().build().parse(source)
                    .findFirst().orElseThrow(IllegalStateException::new);
            return requireNonNull(unit.getProgramUnits().get(0).getDataDivision());
        }
    }

    @Override
    public String toString() {
        return "PROGRAM-ID. " + getName();
    }
}
