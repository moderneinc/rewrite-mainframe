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
import org.openrewrite.mainframe.cobol.CobolIsoVisitor;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.CobolPreprocessor;
import org.openrewrite.mainframe.cobol.tree.DataDivisionSection;
import org.openrewrite.mainframe.cobol.tree.Statement;
import org.openrewrite.trait.SimpleTraitMatcher;
import org.openrewrite.trait.Trait;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.*;

/**
 * A literal landing in a data item: a {@code VALUE} clause, or a {@code MOVE} of a literal.
 * <p>
 * Mainframe programs name their resources at one remove. {@code MOVE 'PAYROLL' TO WS-PGM} precedes
 * {@code EXEC CICS LINK PROGRAM(WS-PGM)}, and {@code MOVE 'GU  ' TO DLI-FUNC} precedes a DL/I call,
 * so an operand is only as resolvable as the literals that reach it. This is the statement that put
 * a value there; {@link #resolve} is the question a call site asks.
 */
@Value
public class LiteralAssignment implements Trait<Cobol> {

    Cursor cursor;

    /**
     * The data items the literal lands in. A {@code MOVE} names more than one.
     */
    public List<String> getTargets() {
        Cobol tree = getTree();
        if (tree instanceof Cobol.MoveToStatement) {
            List<String> targets = new ArrayList<>(1);
            for (Cobol to : ((Cobol.MoveToStatement) tree).getNames()) {
                String name = Names.of(to);
                if (name != null) {
                    targets.add(name);
                }
            }
            return targets;
        }
        // A VALUE clause names nothing. The item it belongs to is the one it hangs off, which is
        // what the cursor is for.
        Object entry = cursor.getParentTreeCursor().getValue();
        String name = entry instanceof Cobol.DataDescriptionEntry &&
                      ((Cobol.DataDescriptionEntry) entry).getName() != null ?
                ((Cobol.DataDescriptionEntry) entry).getName().getWord() : null;
        return name == null ? emptyList() : singletonList(name);
    }

    /**
     * The literals, unquoted. A condition name is written {@code VALUE 'Y' 'N'} and holds either.
     * Figurative constants are absent: {@code VALUE ZERO} says nothing a resource name can be
     * resolved to.
     */
    public List<String> getValues() {
        List<String> values = new ArrayList<>(1);
        Cobol tree = getTree();
        if (tree instanceof Cobol.MoveToStatement) {
            add(values, Names.of(((Cobol.MoveToStatement) tree).getFrom()));
        } else {
            for (Cobol value : ((Cobol.DataValueClause) tree).getCobols()) {
                add(values, Names.of(value));
            }
        }
        return values;
    }

    private static void add(List<String> values, @Nullable String word) {
        String literal = word == null ? null : Literals.valueOf(word);
        if (literal != null) {
            values.add(literal);
        }
    }

    public static class Matcher extends SimpleTraitMatcher<LiteralAssignment> {

        /**
         * A {@code MOVE} only says what a field holds when it moves a literal: {@code MOVE A TO B}
         * says nothing about B that can be read off the source.
         */
        @Override
        protected @Nullable LiteralAssignment test(Cursor cursor) {
            Object value = cursor.getValue();
            if (value instanceof Cobol.DataValueClause) {
                return new LiteralAssignment(cursor);
            }
            if (value instanceof Cobol.MoveToStatement) {
                String from = Names.of(((Cobol.MoveToStatement) value).getFrom());
                return from != null && Literals.isLiteral(from) ? new LiteralAssignment(cursor) : null;
            }
            return null;
        }
    }

    /**
     * The single literal {@code dataName} can hold, or null when it can hold none, more than one, or
     * anything other than a literal. See {@link #candidates} for what that takes.
     */
    public static @Nullable String resolve(Cursor cursor, @Nullable String dataName) {
        Set<String> candidates = candidates(cursor, dataName);
        return candidates != null && candidates.size() == 1 ? candidates.iterator().next() : null;
    }

    /**
     * Every literal {@code dataName} can hold, or null when the program does not show them all.
     * <p>
     * The set is complete when the item is declared in working or local storage and is not
     * {@code EXTERNAL}. Every statement naming it must read it, move a literal to it, or clear it
     * with a figurative constant or {@code INITIALIZE}. Every statement naming an item sharing its
     * storage must read or clear that item. Any other statement is taken to write what it names.
     * <p>
     * It is flow insensitive by choice. It collects what is assigned anywhere, not what holds at a
     * point, and leaves out what an item without a {@code VALUE} holds before its first assignment.
     */
    public static @Nullable Set<String> candidates(Cursor cursor, @Nullable String dataName) {
        Cursor program = enclosing(cursor);
        return dataName == null || program == null ? null :
                Storage.of(program).candidates(dataName.toUpperCase(Locale.ROOT));
    }

    private static @Nullable Cursor enclosing(Cursor cursor) {
        for (Iterator<Cursor> path = cursor.getPathAsCursors(); path.hasNext(); ) {
            Cursor enclosing = path.next();
            if (enclosing.getValue() instanceof Cobol.ProgramUnit) {
                return enclosing;
            }
        }
        return null;
    }

    /**
     * The literals a program assigns each data name, and the items something else can write.
     */
    private static class Storage {

        private static final Set<String> SQL_READS = new HashSet<>(Arrays.asList(
                "INSERT", "UPDATE", "DELETE", "MERGE", "DECLARE", "OPEN"));

        private static final Set<String> SQL_QUERIES = new HashSet<>(Arrays.asList("SELECT", "VALUES", "WITH"));

        /**
         * Options whose operand a CICS or DL/I command only reads. Whether {@code COMMAREA},
         * {@code LENGTH} and {@code RIDFLD} are read depends on the command.
         */
        private static final Set<String> INPUT_OPTIONS = new HashSet<>(Arrays.asList(
                "PROGRAM", "TRANSID", "FILE", "DATASET", "QUEUE", "QNAME", "MAP", "MAPSET", "FROM",
                "KEYLENGTH", "SYSID", "CURSOR", "CHANNEL", "CONTAINER", "SEGMENT", "PCB", "WHERE"));

        private static final Set<String> SENDING_LENGTH = new HashSet<>(Arrays.asList(
                "XCTL", "LINK", "RETURN", "SEND", "WRITE", "REWRITE", "WRITEQ", "START"));

        private static final Set<String> KEYED_RIDFLD = new HashSet<>(Arrays.asList(
                "READ", "WRITE", "DELETE", "STARTBR", "RESETBR"));

        private static final Set<String> RETURNING_COMMANDS = new HashSet<>(Arrays.asList(
                "ASSIGN", "INQUIRE", "EXTRACT", "ADDRESS", "RETRIEVE", "GETNEXT"));

        final Map<String, Set<String>> literals = new HashMap<>();
        final Map<String, List<Item>> items = new HashMap<>();
        final List<Item> records = new ArrayList<>();
        final Set<Item> open = newSetFromMap(new IdentityHashMap<>());

        /**
         * Kept on the program's cursor, so that the program is walked once for every operand any
         * call site in it resolves. A nested program's entries and statements belong to its own storage.
         */
        static Storage of(Cursor program) {
            return program.computeMessageIfAbsent("cobol.literalAssignments", k -> {
                Storage storage = new Storage();
                storage.declare(program);
                new Matcher().lower(program).forEach(assignment -> {
                    if (!belongsTo(assignment.getCursor(), program)) {
                        return;
                    }
                    List<String> literals = assignment.getValues();
                    for (String target : assignment.getTargets()) {
                        storage.literals.computeIfAbsent(target.toUpperCase(Locale.ROOT), t -> new HashSet<>(2))
                                .addAll(literals);
                    }
                });
                storage.write(program);
                return storage;
            });
        }

        private static boolean belongsTo(Cursor at, Cursor program) {
            Program enclosing = Program.of(at);
            return enclosing != null && enclosing.getTree() == program.getValue();
        }

        @Nullable Set<String> candidates(String name) {
            List<Item> named = items.get(name);
            if (named == null) {
                return null;
            }
            for (Item item : named) {
                if (open.contains(item)) {
                    return null;
                }
            }
            Set<String> values = literals.get(name);
            return values == null ? emptySet() : unmodifiableSet(values);
        }

        /**
         * Builds the item hierarchy the way {@link DataName} does. An item the program does not own
         * is open from the start. That is anything outside working and local storage, anything
         * {@code EXTERNAL}, and a {@code RENAMES}, which spans items it does not nest.
         */
        private void declare(Cursor program) {
            Deque<Item> nesting = new ArrayDeque<>();
            new CopybookSkippingVisitor<Integer>() {
                @Override
                public Cobol.ProgramUnit visitProgramUnit(Cobol.ProgramUnit unit, Integer p) {
                    return unit == program.getValue() ? super.visitProgramUnit(unit, p) : unit;
                }

                @Override
                public Cobol.DataDescriptionEntry visitDataDescriptionEntry(Cobol.DataDescriptionEntry entry, Integer p) {
                    Object section = getCursor().firstEnclosing(DataDivisionSection.class);
                    boolean owned = section instanceof Cobol.WorkingStorageSection ||
                                    section instanceof Cobol.LocalStorageSection;
                    declare(entry, owned, nesting);
                    return super.visitDataDescriptionEntry(entry, p);
                }
            }.visit(program.getValue(), 0, program.getParentOrThrow());
        }

        private void declare(Cobol.DataDescriptionEntry entry, boolean owned, Deque<Item> nesting) {
            int level = DliCall.levelOf(entry);
            Item parent;
            if (level == 88) {
                parent = nesting.peek();
            } else if (level == 66) {
                parent = nesting.peekLast();
            } else {
                if (level == 1 || level == 77) {
                    nesting.clear();
                }
                while (!nesting.isEmpty() && nesting.peek().level >= level) {
                    nesting.pop();
                }
                parent = nesting.peek();
            }
            List<Item> siblings = parent == null ? records : parent.children;
            Item item = new Item(level, parent);
            boolean external = false;
            for (Cobol clause : entry.getClauses() == null ? Collections.<Cobol>emptyList() : entry.getClauses()) {
                if (clause instanceof Cobol.DataRedefinesClause) {
                    Item redefined = last(siblings, Names.upperOf(((Cobol.DataRedefinesClause) clause).getDataName()));
                    if (redefined != null) {
                        item.aliases = redefined.aliases;
                        item.aliases.add(item);
                    }
                } else if (clause instanceof Cobol.DataExternalClause) {
                    external = true;
                }
            }
            if (!owned || external || level == 66 || parent != null && open.contains(parent)) {
                open.add(item);
            }
            if (level != 88 && level != 66) {
                siblings.add(item);
                nesting.push(item);
            }
            Cobol.Word name = entry.getName();
            if (name != null && !"FILLER".equalsIgnoreCase(name.getWord())) {
                item.name = Names.upperOf(name);
                items.computeIfAbsent(item.name, k -> new ArrayList<>(1)).add(item);
            }
        }

        private static @Nullable Item last(List<Item> siblings, @Nullable String name) {
            for (int i = siblings.size() - 1; i >= 0; i--) {
                if (siblings.get(i).name != null && siblings.get(i).name.equals(name)) {
                    return siblings.get(i);
                }
            }
            return null;
        }

        /**
         * A restart call writes the areas after its I/O area. They count as written unless the first
         * argument holds a function code other than {@code XRST}, since one holding no literal may be
         * the optional parameter count that moves the I/O area along. Writing them can open another
         * call's function code in turn, hence the loop.
         */
        private void write(Cursor program) {
            Map<String, List<String>> restorable = new HashMap<>();
            new DataName.Matcher().lower(program).forEach(usage -> {
                if (usage.isDefinition() || !belongsTo(usage.getCursor(), program)) {
                    return;
                }
                String name = Names.upperOf(usage.getTree());
                switch (accessOf(usage)) {
                    case LITERAL:
                        write(name, true);
                        break;
                    case WRITE:
                        write(name, false);
                        break;
                    case RESTORED:
                        Cobol.Call call = usage.getCursor().firstEnclosing(Cobol.Call.class);
                        restorable.computeIfAbsent(DliCall.arguments(call).get(0), k -> new ArrayList<>()).add(name);
                        break;
                    default:
                }
            });
            for (boolean changed = true; changed; ) {
                changed = false;
                for (Iterator<Map.Entry<String, List<String>>> calls = restorable.entrySet().iterator(); calls.hasNext(); ) {
                    Map.Entry<String, List<String>> call = calls.next();
                    if (canRestore(call.getKey())) {
                        call.getValue().forEach(name -> write(name, false));
                        calls.remove();
                        changed = true;
                    }
                }
            }
        }

        private boolean canRestore(String function) {
            String literal = Literals.valueOf(function);
            if (literal != null) {
                return "XRST".equalsIgnoreCase(literal);
            }
            Set<String> candidates = candidates(function);
            return candidates == null || candidates.isEmpty() || candidates.contains("XRST");
        }

        /**
         * A write through a condition name is to the item it is declared under, and one through a
         * {@code RENAMES} is to the whole record it renames.
         */
        private void write(@Nullable String name, boolean literal) {
            List<Item> named = name == null ? null : items.get(name);
            if (named == null) {
                return;
            }
            for (Item item : named) {
                Item written = item.level == 88 || item.level == 66 ? item.parent : item;
                if (written == null) {
                    continue;
                }
                boolean whole = literal && written == item;
                for (Item overlapping : written.overlapping()) {
                    if (!whole || overlapping != written) {
                        open.add(overlapping);
                    }
                }
            }
        }

        private enum Access {
            READ,
            LITERAL,
            CONSTANT,
            WRITE,
            RESTORED
        }

        private static Access accessOf(DataName usage) {
            Cursor at = usage.getCursor();
            Object parent = at.getParentTreeCursor().getValue();
            if (parent instanceof Cobol.InData || at.firstEnclosing(Cobol.DataDivision.class) != null) {
                return Access.READ;
            }
            if (at.firstEnclosing(Cobol.ProcedureDivision.class) == null) {
                return Access.WRITE;
            }
            if (parent instanceof Cobol.Word) {
                CobolPreprocessor.ExecStatement exec = Execs.blockOn((Cobol.Word) parent);
                return exec == null ? Access.WRITE : accessIn(exec, usage.getTree());
            }
            Object child = at.getValue();
            for (Cursor up = at.getParentTreeCursor(); ; child = up.getValue(), up = up.getParentTreeCursor()) {
                Object value = up.getValue();
                if (value instanceof Cobol.SpecialRegister && isAddress((Cobol.SpecialRegister) value)) {
                    return Access.WRITE;
                }
                if (value instanceof Cobol.TableCall && child != ((Cobol.TableCall) value).getQualifiedDataName() ||
                    value instanceof Cobol.ConditionNameReference && child != ((Cobol.ConditionNameReference) value).getName() ||
                    value instanceof Cobol.Condition || value instanceof Cobol.Display ||
                    value instanceof Cobol.Evaluate || value instanceof Cobol.EvaluateWhenPhrase ||
                    value instanceof Cobol.GoTo) {
                    return Access.READ;
                }
                if (value instanceof Cobol.MoveToStatement) {
                    return moved((Cobol.MoveToStatement) value, child);
                }
                if (value instanceof Cobol.MoveCorrespondingToStatement) {
                    return child == ((Cobol.MoveCorrespondingToStatement) value).getMoveCorrespondingToSendingArea() ?
                            Access.READ : Access.WRITE;
                }
                if (value instanceof Cobol.Initialize) {
                    return ((Cobol.Initialize) value).getInitializeReplacingPhrase() == null ?
                            Access.CONSTANT : Access.WRITE;
                }
                if (value instanceof Cobol.CallBy) {
                    return passed(up, (Cobol.CallBy) value);
                }
                if (value instanceof Cobol.Call) {
                    return child == ((Cobol.Call) value).getIdentifier() ? Access.READ : Access.WRITE;
                }
                if (value instanceof Statement || !(value instanceof Cobol) || value instanceof Cobol.ProcedureDivision) {
                    return Access.WRITE;
                }
            }
        }

        /**
         * Whoever holds an item's address can write it.
         */
        private static boolean isAddress(Cobol.SpecialRegister register) {
            for (Cobol word : register.getWords()) {
                if (word instanceof Cobol.Word && "ADDRESS".equalsIgnoreCase(((Cobol.Word) word).getWord())) {
                    return true;
                }
            }
            return false;
        }

        private static Access moved(Cobol.MoveToStatement move, Object child) {
            if (child == move.getFrom()) {
                return Access.READ;
            }
            Cobol from = move.getFrom();
            if (from instanceof Cobol.FigurativeConstant && ((Cobol.FigurativeConstant) from).getLiteral() == null) {
                return Access.CONSTANT;
            }
            boolean literal = from instanceof Cobol.Word && Literals.isLiteral(((Cobol.Word) from).getWord());
            boolean partial = child instanceof Cobol.TableCall && ((Cobol.TableCall) child).getReferenceModifier() != null;
            return literal && !partial ? Access.LITERAL : Access.WRITE;
        }

        /**
         * A {@code CALL} argument is passed by reference unless the phrase it is in says otherwise.
         * A DL/I call follows the convention {@link DliCall} reads it by. It reads the function code
         * and writes the PCB and the I/O area. What follows is {@link Access#RESTORED}.
         */
        private static Access passed(Cursor at, Cobol.CallBy callBy) {
            if (callBy.getWords() != null) {
                for (Cobol.Word word : callBy.getWords()) {
                    if ("ADDRESS".equalsIgnoreCase(word.getWord())) {
                        return Access.WRITE;
                    }
                }
            }
            Object phrase = at.getParentTreeCursor().getValue();
            if (phrase instanceof Cobol.CallPhrase && ((Cobol.CallPhrase) phrase).getWords() != null) {
                for (Cobol.Word word : ((Cobol.CallPhrase) phrase).getWords()) {
                    if ("CONTENT".equalsIgnoreCase(word.getWord()) || "VALUE".equalsIgnoreCase(word.getWord())) {
                        return Access.READ;
                    }
                }
            }
            Cobol.Call call = at.firstEnclosing(Cobol.Call.class);
            String target = call == null ? null : Names.of(call.getIdentifier());
            String iface = target == null ? null : Literals.valueOf(target);
            if (iface == null || !DliCall.INTERFACES.contains(iface.toUpperCase(Locale.ROOT))) {
                return Access.WRITE;
            }
            int position = DliCall.arguments(call).indexOf(Names.upperOf(callBy.getIdentifier()));
            return position == 0 ? Access.READ : position <= 2 ? Access.WRITE : Access.RESTORED;
        }

        /**
         * How an {@code EXEC} block uses a data name it holds. A SQL host variable is read outside
         * the {@code INTO} of a query and by the verbs known to only read it. A CICS or DL/I operand
         * is read when its option is one the command only reads.
         */
        private static Access accessIn(CobolPreprocessor.ExecStatement exec, Cobol.Word word) {
            List<Cobol.Word> words = Execs.wordsOf(exec);
            if (words.isEmpty()) {
                return Access.WRITE;
            }
            String verb = words.get(0).getWord().toUpperCase(Locale.ROOT);
            List<CobolPreprocessor.Word> header = exec.getWords();
            if (header.size() > 1 && header.get(1).getCobolWord().getWord().regionMatches(true, 0, "SQL", 0, 3)) {
                boolean into = false;
                for (Cobol.Word each : words) {
                    if (each.getId().equals(word.getId())) {
                        return SQL_READS.contains(verb) || SQL_QUERIES.contains(verb) && !into ?
                                Access.READ : Access.WRITE;
                    }
                    if ("INTO".equalsIgnoreCase(each.getWord())) {
                        into = true;
                    } else if ("FROM".equalsIgnoreCase(each.getWord())) {
                        into = false;
                    }
                }
                return Access.WRITE;
            }
            Set<String> options = new HashSet<>();
            int depth = 0;
            for (Cobol.Word each : words) {
                String text = each.getWord();
                depth += "(".equals(text) ? 1 : ")".equals(text) ? -1 : 0;
                if (depth == 0) {
                    options.add(text.toUpperCase(Locale.ROOT));
                }
            }
            String option = null;
            String last = null;
            depth = 0;
            for (Cobol.Word each : words) {
                String text = each.getWord();
                if ("(".equals(text)) {
                    if (depth++ == 0) {
                        option = last;
                    }
                } else if (")".equals(text)) {
                    depth--;
                } else if (depth == 0) {
                    last = text.toUpperCase(Locale.ROOT);
                } else if (each.getId().equals(word.getId())) {
                    return option != null && reads(verb, option, options) ? Access.READ : Access.WRITE;
                }
            }
            return Access.WRITE;
        }

        private static boolean reads(String verb, String option, Set<String> options) {
            if (RETURNING_COMMANDS.contains(verb)) {
                return false;
            }
            switch (option) {
                case "COMMAREA":
                    return !"LINK".equals(verb);
                case "LENGTH":
                    return SENDING_LENGTH.contains(verb);
                case "RIDFLD":
                    return KEYED_RIDFLD.contains(verb) && !options.contains("GTEQ") && !options.contains("GENERIC");
                default:
                    return INPUT_OPTIONS.contains(option);
            }
        }
    }

    /**
     * A data item and where its storage lies among the others'. Identity is the declaration, since
     * two items of the same name are two items.
     */
    private static class Item {

        final int level;

        final @Nullable Item parent;

        final List<Item> children = new ArrayList<>();

        /**
         * This item and every item redefining the same storage, shared among all of them.
         */
        List<Item> aliases = new ArrayList<>(singletonList(this));

        @Nullable
        String name;

        Item(int level, @Nullable Item parent) {
            this.level = level;
            this.parent = parent;
        }

        /**
         * This item, everything under it, every group above it, and every item redefining the same
         * storage as one of those along with everything under that. Without byte offsets two items
         * redefining the same storage are taken to overlap entirely.
         */
        Set<Item> overlapping() {
            Set<Item> overlapping = newSetFromMap(new IdentityHashMap<>());
            addTree(overlapping);
            for (Item above = this; above != null; above = above.parent) {
                overlapping.add(above);
                for (Item alias : above.aliases) {
                    if (alias != above) {
                        alias.addTree(overlapping);
                    }
                }
            }
            return overlapping;
        }

        private void addTree(Set<Item> items) {
            items.add(this);
            for (Item child : children) {
                child.addTree(items);
            }
        }
    }

    @Override
    public String toString() {
        return getTargets() + " = " + getValues();
    }
}
