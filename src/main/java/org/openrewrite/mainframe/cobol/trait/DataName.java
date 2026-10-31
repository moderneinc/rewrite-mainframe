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
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.mainframe.cobol.marker.CopiedWord;
import org.openrewrite.mainframe.cobol.marker.SystemCopybook;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.CobolPreprocessor;
import org.openrewrite.trait.SimpleTraitMatcher;
import org.openrewrite.trait.Trait;
import org.openrewrite.trait.VisitFunction2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

/**
 * A data name where it is written: as its definition, the data description entry in the program or
 * in a copybook it copies, or as a usage.
 * <p>
 * This is the compiler's cross-reference listing, read off the tree. A name index has to tell the
 * definition from the usages and leave out an item nothing names; a search wants a subscripted or
 * reference-modified occurrence to reach the item; and any use of a level-88 condition name counts
 * as a usage of the item the condition is declared under, since a test of the condition reads that
 * item and a {@code SET} of it assigns it.
 * <p>
 * Within an {@code EXEC} block only a name the program declares is an occurrence — a {@code :HOST}
 * variable, an operand in a CICS or DLI option — because the block's words are not parsed, and an
 * option name or a segment field is written the same way as a data name. {@code DFHRESP(NORMAL)} is
 * a literal, and neither word of it is an occurrence.
 */
@Value
public class DataName implements Trait<Cobol.Word> {

    Cursor cursor;

    /**
     * The name as written.
     */
    public String getName() {
        return getTree().getWord();
    }

    public boolean isDefinition() {
        return isDefinition(cursor);
    }

    /**
     * The entry declaring a definition, or null for a usage.
     */
    public Cobol.@Nullable DataDescriptionEntry getEntry() {
        return isDefinition() ? (Cobol.DataDescriptionEntry) cursor.getParentTreeCursor().getValue() : null;
    }

    /**
     * The copybook the word was copied from, or null where the program wrote it itself.
     */
    public @Nullable String getCopybook() {
        return getTree().getMarkers().findFirst(CopiedWord.class).map(CopiedWord::getCopybook).orElse(null);
    }

    /**
     * Whether the word was copied from a member the CICS translator or the Db2 precompiler supplies.
     * Read it on a usage's definition to ask where the usage's declaration came from.
     */
    public boolean isSystemSupplied() {
        return getTree().getMarkers().findFirst(SystemCopybook.class).isPresent();
    }

    /**
     * The level number of the definition, or of the definition a usage resolves to; 0 where it
     * resolves to none.
     */
    public int getLevel() {
        Definition definition = definition(index());
        return definition == null ? 0 : definition.getLevel();
    }

    public boolean isConditionName() {
        return getLevel() == 88;
    }

    /**
     * Whether a program nested in the one declaring the definition can name it, read through the
     * definition for a usage. An entry is GLOBAL when it says so, when an entry it is under does, or
     * when the FD it is a record of does. False for an occurrence resolving to nothing.
     */
    public boolean isGlobal() {
        Definition definition = definition(index());
        return definition != null && definition.isGlobal();
    }

    /**
     * The definition this occurrence resolves to. A definition answers itself. A name the program
     * does not declare, such as EIBCALEN, resolves to its field in the DFHEIBLK the CICS translator
     * inserts. Failing that, it resolves to a GLOBAL item of the nearest program containing it that
     * declares one. Null where the name is declared nowhere the program can see, such as a copybook
     * that was not found, or where qualification leaves more than one declaration.
     */
    public @Nullable DataName getDefinition() {
        if (isDefinition()) {
            return this;
        }
        Definition definition = definition(index());
        return definition == null ? null : definition.trait();
    }

    /**
     * The item the definition is declared under — the group of an elementary item, the conditional
     * variable of a level-88 — read through the definition for a usage. Null for a level 01 or 77
     * and for an occurrence resolving to nothing.
     */
    public @Nullable DataName getParent() {
        Definition definition = definition(index());
        Definition parent = definition == null ? null : definition.namedParent();
        return parent == null ? null : parent.trait();
    }

    /**
     * For a condition name, the item it is declared under, which a test of the name reads and a
     * {@code SET} of it assigns. Null for a data name, for an occurrence resolving to nothing, and for
     * a condition declared under a {@code FILLER}, which nothing else can name.
     */
    public @Nullable DataName getConditionalVariable() {
        if (!isConditionName()) {
            return null;
        }
        Definition definition = definition(index());
        Definition item = definition == null ? null : definition.getParent();
        return item == null ? null : item.trait();
    }

    /**
     * Whether the occurrence is a target of {@code SET}. For a condition name that is an assignment
     * to its conditional variable.
     */
    public boolean isSet() {
        Cursor identifier = identifier();
        if (identifier == null) {
            return false;
        }
        Object statement = identifier.getParentTreeCursor().getValue();
        List<Cobol> targets = statement instanceof Cobol.SetTo ? ((Cobol.SetTo) statement).getIdentifiers() :
                statement instanceof Cobol.SetUpDown ? ((Cobol.SetUpDown) statement).getTo() : null;
        return targets != null && any(targets, (Cobol) identifier.getValue());
    }

    /**
     * Whether anything in the program or a program nested in it names a definition: a usage
     * resolving to it, or a use of a condition name declared under it. False for a usage.
     */
    public boolean isReferenced() {
        return isDefinition() && index().referenced.contains(getTree().getId());
    }

    /**
     * The names the occurrence is qualified by, innermost first and upper cased: {@code CUST-ID OF
     * CUST-REC} is qualified by {@code CUST-REC}. A qualifier is an occurrence of its own, qualified
     * by the names after it.
     */
    public List<String> getQualifiers() {
        Cursor parent = cursor.getParentTreeCursor();
        Cobol.Word from = null;
        if (parent.getValue() instanceof Cobol.InData) {
            from = getTree();
            parent = parent.getParentTreeCursor();
            if (parent.getValue() instanceof Cobol.QualifiedDataName) {
                parent = parent.getParentTreeCursor();
            }
        }
        Object value = parent.getValue();
        List<? extends Cobol> ins = value instanceof Cobol.QualifiedDataNameFormat1 ?
                ((Cobol.QualifiedDataNameFormat1) value).getQualifiedInData() :
                value instanceof Cobol.ConditionNameReference ? ((Cobol.ConditionNameReference) value).getInDatas() : null;
        if (ins == null) {
            return emptyList();
        }
        List<String> qualifiers = new ArrayList<>(ins.size());
        boolean after = from == null;
        for (Cobol in : ins) {
            if (in instanceof Cobol.QualifiedDataName) {
                in = ((Cobol.QualifiedDataName) in).getDataName();
            }
            String name = in instanceof Cobol.InData ? Names.upperOf(((Cobol.InData) in).getName()) :
                    in instanceof Cobol.InTable ? Names.upperOf(((Cobol.InTable) in).getTableCall().getQualifiedDataName()) :
                            null;
            if (after && name != null) {
                qualifiers.add(name);
            } else if (in instanceof Cobol.InData && is(((Cobol.InData) in).getName(), from)) {
                after = true;
            }
        }
        return qualifiers;
    }

    /**
     * Whether the occurrence names one element of the item, as {@code WS-EACH-CARD (1)} does.
     */
    public boolean isSubscripted() {
        Cursor identifier = identifier();
        Object value = identifier == null ? null : identifier.getValue();
        if (value instanceof Cobol.TableCall) {
            return !((Cobol.TableCall) value).getSubscripts().isEmpty();
        }
        return value instanceof Cobol.ConditionNameReference &&
               ((Cobol.ConditionNameReference) value).getReferences() != null &&
               !((Cobol.ConditionNameReference) value).getReferences().isEmpty();
    }

    /**
     * Whether the occurrence names part of the item, as {@code DFHCOMMAREA(1:EIBCALEN)} does.
     */
    public boolean isReferenceModified() {
        Cursor identifier = identifier();
        Object value = identifier == null ? null : identifier.getValue();
        return value instanceof Cobol.TableCall && ((Cobol.TableCall) value).getReferenceModifier() != null;
    }

    /**
     * The identifier the name is written as: the {@code QualifiedDataName}, or the {@code TableCall}
     * around it when subscripted or reference modified, or the {@code ConditionNameReference}. Null
     * for a definition, a qualifier and a word in an {@code EXEC} block.
     */
    private @Nullable Cursor identifier() {
        Cursor parent = cursor.getParentTreeCursor();
        Object value = parent.getValue();
        if (value instanceof Cobol.ConditionNameReference) {
            return parent;
        }
        if (!(value instanceof Cobol.QualifiedDataNameFormat1)) {
            return null;
        }
        Cursor qualified = parent.getParentTreeCursor();
        if (!(qualified.getValue() instanceof Cobol.QualifiedDataName)) {
            return parent;
        }
        Cursor above = qualified.getParentTreeCursor();
        return above.getValue() instanceof Cobol.TableCall ? above : qualified;
    }

    private @Nullable Definition definition(Index index) {
        if (isDefinition()) {
            return index.byWord.get(getTree().getId());
        }
        List<Definition> candidates = candidates(index);
        return candidates.size() == 1 ? candidates.get(0) : null;
    }

    private List<Definition> candidates(Index index) {
        String name = Names.upperOf(getTree());
        List<String> qualifiers = getQualifiers();
        List<Definition> matching = index.matching(name, qualifiers, false);
        for (Index outer = index.container; matching.isEmpty() && outer != null; outer = outer.container) {
            matching = outer.matching(name, qualifiers, true);
        }
        return matching;
    }

    private Index index() {
        Program program = Program.of(cursor);
        if (program == null) {
            return Index.EMPTY;
        }
        // Kept on the root rather than the program's cursor: a definition handed back out of the
        // index sits on a cursor chain of the index's own, and would build the index again from it.
        Map<UUID, Index> byProgram = cursor.getRoot().computeMessageIfAbsent("cobol.dataNames", k -> new HashMap<>());
        UUID id = program.getTree().getId();
        if (!byProgram.containsKey(id)) {
            Index.of(program.outermost().getCursor(), null, new HashSet<>(), byProgram);
        }
        return byProgram.get(id);
    }

    private static boolean isDefinition(Cursor at) {
        Object parent = at.getParentTreeCursor().getValue();
        if (!(parent instanceof Cobol.DataDescriptionEntry)) {
            return false;
        }
        Cobol.Word name = ((Cobol.DataDescriptionEntry) parent).getName();
        return is(name, at.getValue()) && !"FILLER".equalsIgnoreCase(name.getWord());
    }

    /**
     * Whether the word at {@code at} is written where a data name is used. A word in an {@code EXEC}
     * block passes on position alone; whether the program declares it is checked on resolving.
     */
    private static boolean isUsage(Cursor at) {
        Cobol.Word word = at.getValue();
        Object parent = at.getParentTreeCursor().getValue();
        if (parent instanceof Cobol.QualifiedDataNameFormat1) {
            return is(((Cobol.QualifiedDataNameFormat1) parent).getName(), word) &&
                   !inCicsLiteral(at.getParentTreeCursor());
        }
        if (parent instanceof Cobol.ConditionNameReference) {
            return is(((Cobol.ConditionNameReference) parent).getName(), word) && !Literals.isCicsLiteral(parent);
        }
        if (parent instanceof Cobol.InData) {
            return is(((Cobol.InData) parent).getName(), word);
        }
        if (parent instanceof Cobol.DataRedefinesClause) {
            return is(((Cobol.DataRedefinesClause) parent).getDataName(), word);
        }
        if (parent instanceof Cobol.DataRecordsClause) {
            return any(((Cobol.DataRecordsClause) parent).getDataName(), word);
        }
        if (parent instanceof Cobol.Word) {
            CobolPreprocessor.ExecStatement exec = Execs.blockOn((Cobol.Word) parent);
            return exec != null && isOperand(exec, word);
        }
        return false;
    }

    /**
     * Whether the qualified name at {@code format1} is the keyword of {@code DFHRESP(NORMAL)} or the
     * condition in its parentheses.
     */
    private static boolean inCicsLiteral(Cursor format1) {
        Cursor qualified = format1.getParentTreeCursor();
        if (!(qualified.getValue() instanceof Cobol.QualifiedDataName)) {
            return false;
        }
        Cursor above = qualified.getParentTreeCursor();
        if (above.getValue() instanceof Cobol.Subscript) {
            above = above.getParentTreeCursor().getParentTreeCursor();
        }
        return Literals.isCicsLiteral(above.getValue());
    }

    /**
     * Whether {@code word} sits where a data name goes in the block: after a colon in SQL, within
     * an option's parentheses in CICS and DLI.
     */
    private static boolean isOperand(CobolPreprocessor.ExecStatement exec, Cobol.Word word) {
        List<CobolPreprocessor.Word> header = exec.getWords();
        boolean sql = header.size() > 1 && header.get(1).getCobolWord().getWord().regionMatches(true, 0, "SQL", 0, 3);
        int depth = 0;
        String previous = "";
        for (Cobol.Word each : Execs.wordsOf(exec)) {
            if (each.getId().equals(word.getId())) {
                return sql ? ":".equals(previous) : depth > 0;
            }
            String text = each.getWord();
            if ("(".equals(text)) {
                depth++;
            } else if (")".equals(text)) {
                depth--;
            }
            previous = text;
        }
        return false;
    }

    private static boolean is(@Nullable Cobol node, @Nullable Cobol word) {
        return node != null && word != null && node.getId().equals(word.getId());
    }

    private static boolean any(List<? extends Cobol> nodes, Cobol node) {
        for (Cobol each : nodes) {
            if (is(each, node)) {
                return true;
            }
        }
        return false;
    }

    @Value
    private static class Definition {

        /**
         * Null for a FILLER or a nameless entry, which nests what follows it and names nothing.
         */
        @Nullable
        Cursor word;

        int level;

        @Nullable
        Definition parent;

        boolean global;

        @Nullable String getName() {
            return word == null ? null : Names.upperOf(word.getValue());
        }

        @Nullable Definition namedParent() {
            Definition above = parent;
            while (above != null && above.word == null) {
                above = above.parent;
            }
            return above;
        }

        @Nullable DataName trait() {
            return word == null ? null : new DataName(word);
        }

        /**
         * Whether each qualifier names an item above this one, in order going up.
         */
        boolean isQualifiedBy(List<String> qualifiers) {
            Definition above = parent;
            for (String qualifier : qualifiers) {
                while (above != null && !qualifier.equals(above.getName())) {
                    above = above.parent;
                }
                if (above == null) {
                    return false;
                }
                above = above.parent;
            }
            return true;
        }
    }

    /**
     * Every definition in a program, and which definitions anything names. A usage in a nested
     * program can name an outer program's GLOBAL entry. So the outermost program and every program
     * nested in it are indexed together, the first time any occurrence in them asks, and share one
     * {@code referenced} set.
     */
    private static class Index {

        static final Index EMPTY = new Index(null, emptySet());

        final @Nullable Index container;

        final Map<String, List<Definition>> byName = new HashMap<>();
        final Map<UUID, Definition> byWord = new HashMap<>();
        final Set<UUID> referenced;

        private Index(@Nullable Index container, Set<UUID> referenced) {
            this.container = container;
            this.referenced = referenced;
        }

        /**
         * Indexes {@code program}, then each program nested in it, so that a nested program's
         * usages resolve against the complete index of every program containing it.
         */
        static void of(Cursor program, @Nullable Index container, Set<UUID> referenced, Map<UUID, Index> byProgram) {
            Cobol.ProgramUnit unit = program.getValue();
            Index index = new Index(container, referenced);
            byProgram.put(unit.getId(), index);
            List<Cursor> usages = index.walk(program, false);

            Cursor block = new Program(program).getExecInterfaceBlock();
            if (block != null) {
                // The translator declares it GLOBAL.
                index.walk(block, true);
            }

            // Resolved after the walk: REDEFINES and DEPENDING ON name items before and after them.
            for (Cursor at : usages) {
                DataName usage = new DataName(at);
                for (Definition definition : usage.candidates(index)) {
                    index.reference(definition);
                    // A condition name reads its item where it is tested and assigns it where it is SET.
                    // A FILLER item has no name to be credited by, so the group above it is not.
                    if (definition.getLevel() == 88) {
                        index.reference(definition.getParent());
                    }
                }
            }

            if (unit.getProgramUnits() != null) {
                for (Cobol.ProgramUnit nested : unit.getProgramUnits()) {
                    of(new Cursor(program, nested), index, referenced, byProgram);
                }
            }
        }

        List<Definition> matching(String name, List<String> qualifiers, boolean globalOnly) {
            List<Definition> named = byName.get(name);
            if (named == null) {
                return emptyList();
            }
            if (qualifiers.isEmpty() && !globalOnly) {
                return named;
            }
            List<Definition> matching = new ArrayList<>(1);
            for (Definition definition : named) {
                if ((definition.isGlobal() || !globalOnly) && definition.isQualifiedBy(qualifiers)) {
                    matching.add(definition);
                }
            }
            return matching;
        }

        /**
         * Defines every entry under {@code tree} and returns the words written where a data name is used.
         */
        private List<Cursor> walk(Cursor tree, boolean global) {
            Index index = this;
            List<Cursor> usages = new ArrayList<>();
            new CopybookSkippingVisitor<Deque<Definition>>() {
                @Override
                public Cobol.ProgramUnit visitProgramUnit(Cobol.ProgramUnit unit, Deque<Definition> open) {
                    // A nested program's names are its own, indexed from its own cursor.
                    return unit == tree.getValue() ? super.visitProgramUnit(unit, open) : unit;
                }

                @Override
                public Cobol.DataDescriptionEntry visitDataDescriptionEntry(Cobol.DataDescriptionEntry entry,
                                                                            Deque<Definition> open) {
                    index.define(getCursor(), open, global);
                    return super.visitDataDescriptionEntry(entry, open);
                }

                @Override
                public Cobol.Word visitWord(Cobol.Word word, Deque<Definition> open) {
                    Cobol.Word visited = super.visitWord(word, open);
                    Cursor at = new Cursor(getCursor().getParentOrThrow(), visited);
                    if (isUsage(at)) {
                        usages.add(at);
                    }
                    return visited;
                }
            }.visit(tree.getValue(), new ArrayDeque<>(), tree.getParentOrThrow());
            return usages;
        }

        private void reference(@Nullable Definition definition) {
            if (definition != null && definition.getWord() != null) {
                referenced.add(((Cobol.Word) definition.getWord().getValue()).getId());
            }
        }

        /**
         * A level 88 and a level 66 sit under the item before them and nest nothing; 01 and 77
         * start over; anything else is under the nearest entry of a lower level.
         */
        private void define(Cursor at, Deque<Definition> open, boolean global) {
            Cobol.DataDescriptionEntry entry = at.getValue();
            int level = levelOf(entry);
            Definition parent;
            if (level == 88) {
                parent = open.peek();
            } else if (level == 66) {
                parent = open.peekLast();
            } else {
                if (level == 1 || level == 77) {
                    open.clear();
                }
                while (!open.isEmpty() && open.peek().getLevel() >= level) {
                    open.pop();
                }
                parent = open.peek();
            }
            Cobol.Word name = entry.getName();
            boolean named = name != null && !"FILLER".equalsIgnoreCase(name.getWord());
            Definition definition = new Definition(named ? new Cursor(at, name) : null, level, parent,
                    parent == null ? global || declaresGlobal(at) : parent.isGlobal());
            if (level != 88 && level != 66) {
                open.push(definition);
            }
            if (named) {
                byWord.put(name.getId(), definition);
                byName.computeIfAbsent(Names.upperOf(name), k -> new ArrayList<>(1)).add(definition);
            }
        }

        /**
         * Whether the entry says GLOBAL, or the FD it describes a record of does.
         */
        private static boolean declaresGlobal(Cursor at) {
            Cobol.DataDescriptionEntry entry = at.getValue();
            Cobol.FileDescriptionEntry file = at.firstEnclosing(Cobol.FileDescriptionEntry.class);
            return anyOf(entry.getClauses(), Cobol.DataGlobalClause.class) ||
                   file != null && anyOf(file.getClauses(), Cobol.GlobalClause.class);
        }

        private static boolean anyOf(@Nullable List<Cobol> clauses, Class<? extends Cobol> type) {
            if (clauses != null) {
                for (Cobol clause : clauses) {
                    if (type.isInstance(clause)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private static int levelOf(Cobol.DataDescriptionEntry entry) {
            try {
                return entry.getWords().isEmpty() ? 0 : Integer.parseInt(entry.getWords().get(0).getWord());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

    public static class Matcher extends SimpleTraitMatcher<DataName> {

        @Override
        protected @Nullable DataName test(Cursor cursor) {
            if (!(cursor.getValue() instanceof Cobol.Word)) {
                return null;
            }
            if (isDefinition(cursor)) {
                return new DataName(cursor);
            }
            if (!isUsage(cursor)) {
                return null;
            }
            DataName usage = new DataName(cursor);
            // An option keyword inside a CICS parenthesis is written like a data name and is not one.
            return cursor.getParentTreeCursor().getValue() instanceof Cobol.Word &&
                   usage.candidates(usage.index()).isEmpty() ? null : usage;
        }

        @Override
        public <P> TreeVisitor<? extends Tree, P> asVisitor(VisitFunction2<DataName, P> visitor) {
            return new CopybookSkippingVisitor<P>() {
                @Override
                public Cobol.Word visitWord(Cobol.Word word, P p) {
                    Cobol.Word visited = super.visitWord(word, p);
                    DataName name = test(new Cursor(getCursor().getParentOrThrow(), visited));
                    return name == null ? visited : (Cobol.Word) visitor.visit(name, p);
                }
            };
        }
    }

    @Override
    public String toString() {
        if (isDefinition()) {
            String copybook = getCopybook();
            return getLevel() + " " + getName() + (copybook == null ? "" : " (COPY " + copybook + ")");
        }
        DataName definition = getDefinition();
        return getName() + " -> " + (definition == null ? "?" : definition.toString());
    }
}
