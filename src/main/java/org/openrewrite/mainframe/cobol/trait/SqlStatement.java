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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.jspecify.annotations.Nullable;
import org.openrewrite.Cursor;
import org.openrewrite.Tree;
import org.openrewrite.TreeVisitor;
import org.openrewrite.internal.ListUtils;
import org.openrewrite.mainframe.cobol.CobolPreprocessorIsoVisitor;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.CobolPreprocessor;
import org.openrewrite.mainframe.cobol.tree.CommentArea;
import org.openrewrite.mainframe.db2.EmbeddedSql;
import org.openrewrite.trait.SimpleTraitMatcher;
import org.openrewrite.trait.Trait;
import org.openrewrite.trait.VisitFunction2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;

/**
 * An {@code EXEC SQL} block, read only as far as a resource matrix and column lineage need.
 * <p>
 * This is deliberately an island: which tables a statement touches, what it does to them, and which
 * columns it moves into or out of which host variables. Nothing about joins, predicates or
 * expressions. A full SQL grammar would be a project of its own and would answer no question this
 * is asked.
 * <p>
 * The columns are the one place a lineage answer can name a real database column rather than the
 * COBOL field standing in for it, which is why the island reaches this far and no further.
 * <p>
 * The trait is over the block rather than over the word it hangs off, because the {@code DECLARE
 * TABLE} of a DCLGEN copybook hangs off no word at all.
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlStatement implements Trait<CobolPreprocessor.ExecStatement> {

    /**
     * The block. In a program its parent is the word the block hangs off, and in a copybook it is
     * the copybook's statement holding it.
     */
    Cursor cursor;

    /**
     * The leading keyword, upper cased: {@code SELECT}, {@code INSERT}, {@code UPDATE},
     * {@code DELETE}, {@code DECLARE}, {@code OPEN}, {@code FETCH}, {@code CLOSE}, {@code INCLUDE}.
     */
    String verb;

    /**
     * The tables and views named, upper cased and with any schema qualifier kept as written. A
     * cursor declaration reports the tables of the query it declares; a cursor's {@code OPEN},
     * {@code FETCH} and {@code CLOSE} report none, since only the declaration names one.
     */
    List<String> tables;

    Access access;

    /**
     * The cursor a {@code DECLARE}, {@code OPEN}, {@code FETCH} or {@code CLOSE} names, or null.
     * Cursor statements are what make a DB2 read span four statements, and joining them by name is
     * the only way {@code FETCH} says which table it reads.
     */
    @Nullable
    String cursorName;

    /**
     * The words the cursor's name was written in, first to last, or none where the statement names
     * no cursor.
     */
    List<Cobol.Word> cursorWords;

    /**
     * The columns the statement's own query names, in the order it names them. Empty for a
     * {@code FETCH}, which borrows its cursor's, and for {@code SELECT *}, which names none this can
     * pair a host variable with.
     */
    List<String> columns;

    /**
     * The host variables the statement moves values through, positionally against {@link #columns}.
     * An entry is empty where a literal sits in that position.
     */
    List<String> hostVariables;

    /**
     * The null indicators, which DB2 sets alongside the host variable they were written with rather
     * than in a column position of their own. Kept apart from {@link #hostVariables} so the pairing
     * stays positional, and reported so a program's indicator does not look like a field nothing
     * fills.
     */
    List<String> indicatorVariables;

    /**
     * The one table the query names, where the parser read exactly one. Null for a join, which
     * gives no way to say which column came from where, and for a statement no SQL parser takes.
     */
    @Nullable
    String table;

    /**
     * One entry for each distinct table the statement names, in the order it names them, saying
     * what the statement does to it and where it was written.
     */
    List<SqlTableAccess> tableAccesses;

    public CobolPreprocessor.ExecStatement getExec() {
        return getTree();
    }

    /**
     * Whether the statement leaves an outcome behind at all. A {@code DECLARE} does not, since
     * nothing runs where it is written, and neither does an {@code INCLUDE}.
     */
    public boolean isExecuted() {
        return access != Access.DECLARE && !"INCLUDE".equals(verb);
    }

    /**
     * The word the block hangs off, which is the program text around it. Null for a block in a
     * copybook read as a source of its own.
     */
    public Cobol.@Nullable Word getEnclosingWord() {
        return cursor.firstEnclosing(Cobol.Word.class);
    }

    /**
     * Whether {@code exec} is an {@code EXEC SQL} block. {@code EXEC SQLIMS} is the IMS variant and
     * is not one.
     */
    public static boolean isSql(CobolPreprocessor.ExecStatement exec) {
        List<CobolPreprocessor.Word> words = exec.getWords();
        return words.size() >= 2 && "SQL".equalsIgnoreCase(words.get(1).getCobolWord().getWord());
    }

    public static class Matcher extends SimpleTraitMatcher<SqlStatement> {

        @Override
        protected @Nullable SqlStatement test(Cursor cursor) {
            Object value = cursor.getValue();
            return value instanceof CobolPreprocessor.ExecStatement && isSql((CobolPreprocessor.ExecStatement) value) ?
                    parse(cursor, (CobolPreprocessor.ExecStatement) value) : null;
        }

        /**
         * One statement per block. In a program that is the block each stand-in word carries — see
         * {@link Execs#isStandIn} — which are the blocks the program writes itself. A copybook's
         * blocks stay in the copybook, under the statement that copies it, so they are read where
         * the copybook is read as a source of its own: there it is each block the copybook holds. A
         * copybook copying another is not descended into, as the other is a source of its own.
         */
        @Override
        public <P> TreeVisitor<? extends Tree, P> asVisitor(VisitFunction2<SqlStatement, P> visitor) {
            return new TreeVisitor<Tree, P>() {
                @Override
                public Tree preVisit(Tree tree, P p) {
                    stopAfterPreVisit();
                    if (tree instanceof Cobol) {
                        return inProgram(visitor).visitNonNull(tree, p, getCursor().getParentOrThrow());
                    }
                    if (tree instanceof CobolPreprocessor) {
                        return inCopybook(visitor).visitNonNull(tree, p, getCursor().getParentOrThrow());
                    }
                    return tree;
                }
            };
        }

        /**
         * Each block is parsed once. Left to the default, it is parsed again to be handed back.
         */
        @Override
        public Stream<SqlStatement> lower(Cursor cursor) {
            Stream.Builder<SqlStatement> statements = Stream.builder();
            this.<Stream.Builder<SqlStatement>>asVisitor((statement, found) -> {
                found.add(statement);
                return statement.getTree();
            }).visit(cursor.getValue(), statements, cursor.getParentOrThrow());
            return statements.build();
        }

        private static <P> TreeVisitor<?, P> inProgram(VisitFunction2<SqlStatement, P> visitor) {
            return new CopybookSkippingVisitor<P>() {
                @Override
                public Cobol.Word visitWord(Cobol.Word word, P p) {
                    Cobol.Word w = super.visitWord(word, p);
                    if (w.getPreprocessorStatements().isEmpty() || !Execs.isStandIn(getCursor(), word)) {
                        return w;
                    }
                    Cursor standIn = getCursor();
                    return w.withPreprocessorStatements(ListUtils.map(w.getPreprocessorStatements(), ps ->
                            ps instanceof CobolPreprocessor.ExecStatement && isSql((CobolPreprocessor.ExecStatement) ps) ?
                                    (CobolPreprocessor) visitor.visit(
                                            parse(new Cursor(standIn, ps), (CobolPreprocessor.ExecStatement) ps), p) :
                                    ps));
                }
            };
        }

        private static <P> TreeVisitor<?, P> inCopybook(VisitFunction2<SqlStatement, P> visitor) {
            return new CobolPreprocessorIsoVisitor<P>() {
                @Override
                public CobolPreprocessor.Copybook visitCopybook(CobolPreprocessor.Copybook copybook, P p) {
                    return getCursor().getParentTreeCursor().getValue() instanceof Tree ?
                            copybook : super.visitCopybook(copybook, p);
                }

                @Override
                public CobolPreprocessor.ExecStatement visitExecStatement(CobolPreprocessor.ExecStatement exec, P p) {
                    return isSql(exec) ?
                            (CobolPreprocessor.ExecStatement) visitor.visit(parse(getCursor(), exec), p) :
                            exec;
                }
            };
        }
    }

    /**
     * Keywords that introduce a table name. {@code FROM} and {@code JOIN} appear in queries,
     * {@code INTO} in inserts, {@code UPDATE} and {@code TABLE} name one directly.
     */
    private static final Set<String> TABLE_INTRODUCERS =
            new HashSet<>(Arrays.asList("FROM", "JOIN", "INTO", "UPDATE", "TABLE"));

    /**
     * Where a table's name is looked for first: after a table introducer, or after the {@code USING}
     * naming what a {@code MERGE} reads. Reading tables off the words leaves {@code USING} out, as
     * {@code OPEN ... USING :HV} names none, but a name looked for here is already known to be one.
     */
    private static final Set<String> NAME_INTRODUCERS =
            new HashSet<>(Arrays.asList("FROM", "JOIN", "INTO", "UPDATE", "TABLE", "USING"));

    static SqlStatement parse(Cursor cursor, CobolPreprocessor.ExecStatement exec) {
        List<Token> tokens = tokens(exec);
        if (tokens.isEmpty()) {
            return new SqlStatement(cursor, "", emptyList(), Access.OTHER, null, emptyList(),
                    emptyList(), emptyList(), emptyList(), null, emptyList());
        }

        List<String> texts = new ArrayList<>(tokens.size());
        for (Token token : tokens) {
            texts.add(token.text);
        }
        String verb = texts.get(0);
        int named = cursorOf(verb, texts);
        Access access = accessOf(verb);
        EmbeddedSql embedded = EmbeddedSql.read(text(exec));
        // The parsed query answers this where there is one. The words are what is left when DB2
        // wrote something no SQL parser takes, DECLARE ... TABLE being the one that names a table.
        boolean read = !embedded.getTables().isEmpty();
        List<String> tables = read ? embedded.getTables() : tablesOf(verb, texts);
        // What a statement writes is the first table it names, which is the one written after its verb.
        String target = read ? embedded.getTarget() : writes(verb) && !tables.isEmpty() ? tables.get(0) : null;
        boolean cursorDeclaration = "DECLARE".equals(verb) && named > 0;
        return new SqlStatement(cursor, verb, tables, access, named < 0 ? null : texts.get(named),
                named < 0 ? emptyList() : unmodifiableList(tokens.get(named).words),
                embedded.getColumns(), embedded.getHostVariables(), embedded.getIndicatorVariables(),
                embedded.getTable(), accessesOf(verb, access, tables, target, tokens, cursorDeclaration));
    }

    /**
     * Words written with nothing between them, which SQL reads as one. A separator followed by a
     * blank is kept in the whitespace before the next word, but one written hard against both its
     * neighbours is a word of its own, so COBOL hands {@code POLICY,COMMERCIAL} back as three words,
     * and {@code CLM.CLAIM_HIST} as {@code CLM.CLAIM}, {@code _} and {@code HIST}.
     */
    private static final class Token {
        final List<Cobol.Word> words = new ArrayList<>(1);
        String text = "";

        void add(Cobol.Word word) {
            words.add(word);
            text += word.getWord().toUpperCase(Locale.ROOT);
        }
    }

    /**
     * The block body as it was written. A separator is not a word: it is kept in the whitespace
     * around one, so {@code SELECT A, B} read as words is {@code SELECT A B}, which is not a parse
     * failure but a column with an alias — wrong quietly rather than loudly.
     * <p>
     * Where the separator is kept depends on where the card ends. Written mid-line it joins the
     * prefix of the word after it; written last on the card, as real SQL a column to a line is, it
     * goes to the run of blanks between the word and column 73. Both have to be picked up. What
     * follows column 73 is the identification area and is not part of the statement.
     */
    private static String text(CobolPreprocessor.ExecStatement exec) {
        StringBuilder sql = new StringBuilder();
        new CobolPreprocessorIsoVisitor<Integer>() {
            @Override
            public CobolPreprocessor.Word visitWord(CobolPreprocessor.Word word, Integer p) {
                sql.append(word.getPrefix().getWhitespace()).append(word.getCobolWord().getWord());
                CommentArea trailing = word.getCobolWord().getCommentArea();
                if (trailing != null) {
                    sql.append(trailing.getPrefix().getWhitespace());
                }
                return word;
            }
        }.visit(exec.getCobol(), 0);
        return sql.toString();
    }

    /**
     * The words of the block body, put back together where a name SQL writes as one word reached
     * COBOL as several. Read word by word, {@code CARDDEMO.TRANSACTION_TYPE} is the table
     * {@code CARDDEMO.TRANSACTION} — a name no database has. What separates two names from one
     * broken in two is the space that is not between them.
     * <p>
     * An {@code EXEC SQL} body is a {@code CharDataSql} rather than the {@code CharData} a CICS body
     * uses, so the words are collected by walking rather than by shape.
     */
    private static List<Token> tokens(CobolPreprocessor.ExecStatement exec) {
        List<Token> tokens = new ArrayList<>();
        new CobolPreprocessorIsoVisitor<Integer>() {
            // What follows a word up to column 73 separates it from the next just as a space before
            // that one would, and is where a card-ending separator is kept.
            private boolean trailingSpace = true;

            @Override
            public CobolPreprocessor.Word visitWord(CobolPreprocessor.Word word, Integer p) {
                if (tokens.isEmpty() || trailingSpace || !word.getPrefix().getWhitespace().isEmpty()) {
                    tokens.add(new Token());
                }
                tokens.get(tokens.size() - 1).add(word.getCobolWord());
                CommentArea trailing = word.getCobolWord().getCommentArea();
                trailingSpace = trailing != null && !trailing.getPrefix().getWhitespace().isEmpty();
                return word;
            }
        }.visit(exec.getCobol(), 0);
        return tokens;
    }

    /**
     * Which token names the cursor of a cursor statement, or -1. {@code DECLARE} puts the name
     * first, {@code OPEN} and {@code CLOSE} immediately after the verb, and {@code FETCH} after its
     * {@code FROM} or, written without one, after the words saying which row it moves to.
     */
    private static int cursorOf(String verb, List<String> tokens) {
        switch (verb) {
            case "DECLARE":
                // DECLARE <name> [WITH HOLD|INSENSITIVE SCROLL|...] CURSOR FOR ..., as against
                // DECLARE <name> TABLE ... — genapp writes every one of its cursors with attributes.
                for (int i = 2; i < tokens.size(); i++) {
                    if ("CURSOR".equals(tokens.get(i))) {
                        return 1;
                    }
                    if ("TABLE".equals(tokens.get(i)) || "FOR".equals(tokens.get(i))) {
                        return -1;
                    }
                }
                return -1;
            case "OPEN":
            case "CLOSE":
                return tokens.size() >= 2 ? 1 : -1;
            case "FETCH":
                // FETCH [NEXT|PRIOR|ABSOLUTE n|...] FROM <name>, or FETCH <name>.
                for (int i = 1; i < tokens.size() && !"INTO".equals(tokens.get(i)); i++) {
                    if ("FROM".equals(tokens.get(i))) {
                        return i + 1 < tokens.size() ? i + 1 : -1;
                    }
                }
                for (int i = 1; i < tokens.size(); i++) {
                    if (!isPositionKeyword(tokens.get(i))) {
                        return i;
                    }
                }
                return -1;
            default:
                return -1;
        }
    }

    private static boolean isPositionKeyword(String token) {
        switch (token) {
            case "NEXT":
            case "PRIOR":
            case "FIRST":
            case "LAST":
            case "CURRENT":
            case "ABSOLUTE":
            case "RELATIVE":
            case "ROWSET":
            case "SENSITIVE":
            case "INSENSITIVE":
            case "BEFORE":
            case "AFTER":
                return true;
            default:
                return false;
        }
    }

    /**
     * Words that follow a table introducer without being a table: {@code INSERT INTO} is a table but
     * {@code SELECT ... INTO :HOST-VAR} is a host variable, and {@code DELETE FROM} is a table but
     * {@code FETCH ... INTO} is not.
     */
    private static boolean isTableName(String token) {
        return !token.isEmpty() && token.charAt(0) != ':' && token.charAt(0) != '(' &&
               !"SELECT".equals(token) && !"(".equals(token);
    }

    private static List<String> tablesOf(String verb, List<String> tokens) {
        // DECLARE <table> TABLE, the DCLGEN form, names the table ahead of the keyword, which is the
        // one place a table is not introduced by the word in front of it. DECLARE <cursor> CURSOR
        // FOR <query> falls through, and the query's own FROM is what the loop below reads.
        if ("DECLARE".equals(verb) && tokens.size() >= 3 && "TABLE".equals(tokens.get(2))) {
            return singletonList(tokens.get(1));
        }
        // A cursor statement names its cursor and no table. DB2 writes FETCH FROM <cursor>, and read
        // as a table introducer that FROM makes the cursor a table of its own.
        if ("OPEN".equals(verb) || "FETCH".equals(verb) || "CLOSE".equals(verb)) {
            return emptyList();
        }
        List<String> tables = new ArrayList<>(1);
        for (int i = 0; i < tokens.size() - 1; i++) {
            if (!TABLE_INTRODUCERS.contains(tokens.get(i))) {
                continue;
            }
            // DECLARE <cursor> CURSOR FOR SELECT: the FOR is not a table introducer, so the SELECT's
            // own FROM is what gets read, which is what we want.
            String candidate = tokens.get(i + 1);
            if (isTableName(candidate) && !tables.contains(candidate)) {
                tables.add(candidate);
            }
        }
        return tables;
    }

    private static Access accessOf(String verb) {
        switch (verb) {
            case "SELECT":
            case "FETCH":
            case "OPEN":
                return Access.READ;
            case "INSERT":
                return Access.CREATE;
            case "UPDATE":
                return Access.UPDATE;
            case "DELETE":
                return Access.DELETE;
            case "MERGE":
                // A MERGE both inserts and updates; reporting the stronger of the two would hide half
                // of what it does, so it is reported as an update and the verb is kept on the row.
                return Access.UPDATE;
            case "DECLARE":
                return Access.DECLARE;
            default:
                return Access.OTHER;
        }
    }

    private static boolean writes(String verb) {
        return "INSERT".equals(verb) || "UPDATE".equals(verb) || "DELETE".equals(verb) || "MERGE".equals(verb);
    }

    private static List<SqlTableAccess> accessesOf(String verb, Access access, List<String> tables,
                                                   @Nullable String target, List<Token> tokens,
                                                   boolean cursorDeclaration) {
        if (tables.isEmpty()) {
            return emptyList();
        }
        // A cursor can share a table's name, and its declaration names it ahead of the query.
        int from = 0;
        if (cursorDeclaration) {
            for (int i = 0; i < tokens.size(); i++) {
                if ("FOR".equals(tokens.get(i).text)) {
                    from = i + 1;
                    break;
                }
            }
        }
        // Two spellings of one name are one table.
        Map<String, List<String>> named = new LinkedHashMap<>();
        for (String table : tables) {
            List<String> parts = partsOf(table);
            named.putIfAbsent(String.join(".", parts), parts);
        }
        String written = target == null ? null : String.join(".", partsOf(target));
        Set<Cobol.Word> used = Collections.newSetFromMap(new IdentityHashMap<>());
        List<SqlTableAccess> accesses = new ArrayList<>(named.size());
        List<Spelling> spellings = new ArrayList<>(named.size());
        for (Map.Entry<String, List<String>> table : named.entrySet()) {
            String name = table.getKey();
            List<String> parts = table.getValue();
            Spelling spelling = spelling(name, tokens, from, used);
            used.addAll(spelling.words);
            spellings.add(spelling);
            int n = parts.size();
            accesses.add(new SqlTableAccess(name, n >= 3 ? parts.get(n - 3) : null, n >= 2 ? parts.get(n - 2) : null,
                    parts.get(n - 1), "DECLARE".equals(verb) ? Access.DECLARE : name.equals(written) ? access : Access.READ,
                    unmodifiableList(spelling.words)));
        }
        // The parser reports a statement's tables as a set, so they are put in the order they were written.
        List<Integer> order = new ArrayList<>(accesses.size());
        for (int i = 0; i < accesses.size(); i++) {
            order.add(i);
        }
        order.sort(Comparator.<Integer>comparingInt(i -> spellings.get(i).token).thenComparingInt(i -> spellings.get(i).word));
        List<SqlTableAccess> ordered = new ArrayList<>(accesses.size());
        for (int i : order) {
            ordered.add(accesses.get(i));
        }
        return unmodifiableList(ordered);
    }

    /**
     * Where one table's name was written: the token and the word within it that the name starts
     * at, or past the last token for a name no words spell.
     */
    private static final class Spelling {
        final int token;
        final int word;
        final List<Cobol.Word> words;

        Spelling(int token, int word, List<Cobol.Word> words) {
            this.token = token;
            this.word = word;
            this.words = words;
        }
    }

    /**
     * The words that spell {@code name}, preferring a spelling that follows a word introducing a
     * table, so that a column sharing the table's name is not taken for it, and otherwise the first.
     * A spelling starts and ends at a word, and never part way through a longer name, so neither
     * {@code POLICY} in {@code POLICY.HOLDER} nor {@code CLM.POLICY} in {@code CLM.POLICY_HIST}
     * counts. A word another table's name already took is not taken again.
     */
    private static Spelling spelling(String name, List<Token> tokens, int from, Set<Cobol.Word> used) {
        Spelling first = null;
        for (int t = from; t < tokens.size(); t++) {
            List<Cobol.Word> words = tokens.get(t).words;
            for (int i = 0; i < words.size(); i++) {
                if (i > 0 && continuesName(words.get(i - 1).getWord(), false)) {
                    continue;
                }
                StringBuilder spelled = new StringBuilder();
                for (int j = i; j < words.size(); j++) {
                    spelled.append(words.get(j).getWord());
                    String folded = String.join(".", partsOf(spelled.toString()));
                    if (!name.startsWith(folded)) {
                        break;
                    }
                    if (!folded.equals(name) ||
                        j + 1 < words.size() && continuesName(words.get(j + 1).getWord(), true)) {
                        continue;
                    }
                    List<Cobol.Word> spelling = words.subList(i, j + 1);
                    if (anyUsed(spelling, used)) {
                        break;
                    }
                    if (i == 0 && t > 0 && NAME_INTRODUCERS.contains(tokens.get(t - 1).text)) {
                        return new Spelling(t, i, spelling);
                    }
                    if (first == null) {
                        first = new Spelling(t, i, spelling);
                    }
                    break;
                }
            }
        }
        return first == null ? new Spelling(tokens.size(), 0, emptyList()) : first;
    }

    private static boolean anyUsed(List<Cobol.Word> words, Set<Cobol.Word> used) {
        for (Cobol.Word word : words) {
            if (used.contains(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Whether a word written hard against a name carries the name on: its first character for a
     * word after the name, its last for one before it.
     */
    private static boolean continuesName(String word, boolean after) {
        if (word.isEmpty()) {
            return false;
        }
        char c = word.charAt(after ? 0 : word.length() - 1);
        return Character.isLetterOrDigit(c) || "_#@$.\"-".indexOf(c) >= 0;
    }

    /**
     * The parts of a qualified name as DB2 folds them, with delimiters stripped and in upper case. A
     * period inside a delimited part is the part's own.
     */
    private static List<String> partsOf(String name) {
        List<String> parts = new ArrayList<>(3);
        StringBuilder part = new StringBuilder();
        boolean delimited = false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '"') {
                delimited = !delimited;
            } else if (c == '.' && !delimited) {
                parts.add(part.toString().toUpperCase(Locale.ROOT));
                part.setLength(0);
            } else {
                part.append(c);
            }
        }
        parts.add(part.toString().toUpperCase(Locale.ROOT));
        return parts;
    }

    /**
     * One column bound to one host variable — the pairing that lets a lineage answer name a real
     * database column rather than the COBOL field standing in for it.
     */
    @Value
    public static class Binding {
        String column;
        String hostVariable;
    }

    public enum Access {
        READ,
        CREATE,
        UPDATE,
        DELETE,
        /**
         * A statement that names a table but does not itself touch data — {@code DECLARE CURSOR} and
         * {@code DECLARE TABLE}. Reporting these as reads would overstate what the program does.
         */
        DECLARE,
        OTHER
    }

    @Override
    public String toString() {
        return "EXEC SQL " + verb + (tables.isEmpty() ? "" : " " + String.join(", ", tables));
    }
}
