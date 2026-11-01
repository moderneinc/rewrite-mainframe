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
package org.openrewrite.mainframe.db2;

import lombok.Value;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.Values;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.DOTALL;

/**
 * The columns an {@code EXEC SQL} block moves, and the host variables it moves them through.
 * <p>
 * The query itself is read by JSQLParser, the same parser rewrite-sql uses — a select list, a join
 * or a qualified name is not worth scanning for a second time. What JSQLParser will not read is the
 * envelope DB2 embeds a query in, and that is what this unwraps: the {@code INTO} clause naming host
 * variables, {@code DECLARE ... CURSOR FOR}, {@code FETCH ... INTO}, and the two clause tails the
 * parser rejects. Measured against the corpus, the rest of what DB2 writes — {@code WITH HOLD},
 * {@code WITH UR}, {@code OPTIMIZE FOR}, {@code FETCH FIRST n ROWS} — it accepts as it stands.
 * <p>
 * This works on the block's text rather than on its words, because the COBOL tokenizer keeps a
 * separator in the whitespace before the next word rather than as a word of its own. Read as words,
 * {@code SELECT A, B} is {@code SELECT A B}, and no SQL parser will take that.
 * <p>
 * {@link #getColumns()} and {@link #getHostVariables()} are positional: entry <i>i</i> of one pairs
 * with entry <i>i</i> of the other. A host variable is null where a literal sits in that position,
 * which is what keeps the two aligned through {@code VALUES (:A, 5, :B)}, and a null indicator is
 * reported apart from them for the same reason.
 * <p>
 * Nothing here is particular to COBOL, so the SQL a Java program hands JDBC reads the same way.
 */
@Value
public class EmbeddedSql {

    private static final EmbeddedSql NONE =
            new EmbeddedSql(emptyList(), emptyList(), emptyList(), emptyList(), null);

    /**
     * JSQLParser parses on an executor so that it can give up on a statement that takes too long, and
     * {@link CCJSqlParserUtil#parse(String)} builds a new one for every statement it reads. An estate
     * has many thousands of statements, so they share one pool, of daemon threads so that it never
     * holds a JVM open.
     */
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool(runnable -> {
        Thread thread = new Thread(runnable, "rewrite-mainframe-embedded-sql");
        thread.setDaemon(true);
        return thread;
    });

    /**
     * The {@code INTO} clause of a singleton {@code SELECT} or a {@code FETCH}, which is where the
     * host variables are. {@code INSERT INTO} names a table instead, and the colon is what tells the
     * two apart.
     */
    private static final Pattern INTO =
            Pattern.compile("\\bINTO\\s+(:.*?)(?=\\bFROM\\b|\\bWHERE\\b|$)", CASE_INSENSITIVE | DOTALL);

    private static final Pattern HOST_VARIABLE = Pattern.compile(":(?:([\\w$#@-]+)\\.)?([\\w$#@-]+)");

    /**
     * The query a cursor is declared for. What sits between the name and {@code CURSOR} is the
     * cursor's attributes — {@code WITH HOLD}, {@code INSENSITIVE SCROLL} — and genapp writes them
     * on every one of its cursors.
     */
    private static final Pattern DECLARED = Pattern.compile(
            "\\bDECLARE\\s+\\S+\\s+(?:\\w+\\s+)*?CURSOR\\b.*?\\bFOR\\b(.*)", CASE_INSENSITIVE | DOTALL);

    /**
     * The tails JSQLParser rejects: a multi-column {@code FOR UPDATE OF} stops it, and so does
     * {@code FOR FETCH ONLY}. Neither names a column the select list has not already.
     */
    private static final Pattern TAIL =
            Pattern.compile("\\bFOR\\s+(UPDATE|FETCH|READ)\\b.*", CASE_INSENSITIVE | DOTALL);

    private static final Pattern VERB = Pattern.compile("\\s*(\\w+)");

    List<String> columns;
    List<@Nullable HostVariable> hostVariables;
    List<HostVariable> indicatorVariables;

    /**
     * Every table the query names, qualified as written. Empty where the query named none or could
     * not be read.
     */
    List<String> tables;

    /**
     * The table an {@code INSERT}, {@code UPDATE}, {@code DELETE} or {@code MERGE} writes, named as
     * {@link #getTables()} names it. Null for a query, which writes none, and for a statement the
     * parser could not read.
     */
    @Nullable
    String target;

    public List<String> getColumns() {
        return unmodifiableList(columns);
    }

    public List<@Nullable HostVariable> getHostVariables() {
        return unmodifiableList(hostVariables);
    }

    public List<HostVariable> getIndicatorVariables() {
        return unmodifiableList(indicatorVariables);
    }

    public List<String> getTables() {
        return unmodifiableList(tables);
    }

    /**
     * The one table the query names, or null where it named none, could not be read, or named more
     * than one — a join gives no way to say which column came from where.
     */
    public @Nullable String getTable() {
        return tables.size() == 1 ? tables.get(0) : null;
    }

    public static EmbeddedSql read(String sql) {
        Matcher verb = VERB.matcher(sql);
        if (!verb.lookingAt()) {
            return NONE;
        }

        List<HostVariable> indicators = new ArrayList<>();
        // A FETCH names no columns and no table of its own. Which ones it reads is on the cursor's
        // declaration, and joining the two is the caller's to do because only it sees the program.
        if ("FETCH".equalsIgnoreCase(verb.group(1))) {
            return new EmbeddedSql(emptyList(), hostVariablesOf(sql, indicators), indicators, emptyList(), null);
        }

        List<@Nullable HostVariable> hostVariables = emptyList();
        String query = sql;
        Matcher declared = DECLARED.matcher(sql);
        if (declared.find()) {
            query = declared.group(1);
        } else {
            Matcher into = INTO.matcher(sql);
            if (into.find()) {
                hostVariables = hostVariablesOf(into.group(1), indicators);
                query = sql.substring(0, into.start()) + " " + sql.substring(into.end());
            }
        }

        Map<String, HostVariable> names = new HashMap<>();
        Statement parsed = parse(withPlaceholders(TAIL.matcher(query).replaceFirst(""), names));
        List<String> tables = tablesIn(parsed);
        String target = targetOf(parsed);
        if (parsed instanceof Select) {
            return new EmbeddedSql(selected((Select) parsed), hostVariables, indicators, tables, target);
        }
        if (parsed instanceof Insert) {
            return inserted((Insert) parsed, names, tables, target);
        }
        if (parsed instanceof Update) {
            return updated((Update) parsed, names, tables, target);
        }
        return new EmbeddedSql(emptyList(), hostVariables, indicators, tables, target);
    }

    /**
     * Every table the parsed statement names. Read off the query rather than the words for two
     * reasons: a join names more than one table and the words find only the first, and a qualified
     * name is one name to SQL and more than one word to COBOL, so
     * {@code CARDDEMO.TRANSACTION_TYPE} comes back from the words as {@code CARDDEMO.TRANSACTION}.
     * Where the words are all there is, the COBOL trait falls back to them.
     */
    private static List<String> tablesIn(@Nullable Statement parsed) {
        if (parsed == null) {
            return emptyList();
        }
        List<String> tables = new ArrayList<>(1);
        try {
            // DB2 folds an undelimited name, so two spellings of one table are one table.
            for (String table : new TablesNamesFinder<Void>().getTables(parsed)) {
                tables.add(table.toUpperCase());
            }
        } catch (Exception e) {
            // Not every statement the parser accepts is one it will walk.
            return emptyList();
        }
        return tables;
    }

    private static @Nullable String targetOf(@Nullable Statement parsed) {
        Table table = parsed instanceof Insert ? ((Insert) parsed).getTable() :
                parsed instanceof Update ? ((Update) parsed).getTable() :
                parsed instanceof Delete ? ((Delete) parsed).getTable() :
                parsed instanceof Merge ? ((Merge) parsed).getTable() :
                null;
        return table == null ? null : table.getFullyQualifiedName().toUpperCase();
    }

    /**
     * COBOL names a field {@code WS-ACCT-ID} and SQL reads the hyphens as subtraction, so
     * {@code :DB2-ISSUEDATE} parses — into {@code :DB2 - ISSUEDATE}, which is worse than failing.
     * Each host variable is renamed to a numbered placeholder the parser keeps whole, and named back
     * on the way out. Numbered, so two structures' fields of the same name stay apart.
     */
    private static String withPlaceholders(String sql, Map<String, HostVariable> names) {
        Matcher host = HOST_VARIABLE.matcher(sql);
        StringBuffer renamed = new StringBuffer();
        while (host.find()) {
            String placeholder = "H" + names.size();
            names.put(placeholder, hostVariable(host));
            host.appendReplacement(renamed, ":" + placeholder);
        }
        host.appendTail(renamed);
        return renamed.toString();
    }

    private static @Nullable Statement parse(String sql) {
        try {
            return CCJSqlParserUtil.parse(sql, EXECUTOR, null);
        } catch (Exception e) {
            // A query the parser cannot read leaves the statement with no columns, and the caller
            // reports the table without one rather than reporting nothing at all.
            return null;
        }
    }

    /**
     * The host variables named in a clause, without the colon SQL introduces them with. Each
     * comma-separated position contributes one, and anything further in the same position is a null
     * indicator riding with it — written {@code :VALUE INDICATOR :IND} or, as often, {@code :VALUE
     * :IND}. Counting an indicator as a position of its own leaves the clause one longer than the
     * select list, and then nothing pairs at all.
     */
    private static List<@Nullable HostVariable> hostVariablesOf(String clause, List<HostVariable> indicators) {
        List<@Nullable HostVariable> variables = new ArrayList<>();
        for (String position : clause.split(",")) {
            Matcher host = HOST_VARIABLE.matcher(position);
            if (host.find()) {
                variables.add(hostVariable(host));
            }
            while (host.find()) {
                indicators.add(hostVariable(host));
            }
        }
        return variables;
    }

    private static HostVariable hostVariable(Matcher host) {
        String structure = host.group(1);
        return new HostVariable(host.group(2), structure == null ? emptyList() : singletonList(structure));
    }

    private static List<String> selected(Select select) {
        // A VALUES row reads no column, so a name in it is a special register such as USER.
        if (select instanceof Values) {
            return emptyList();
        }
        List<String> columns = new ArrayList<>();
        for (Expression selected : firstRow(select)) {
            // SELECT * names no column this can pair a host variable with, and guessing at the
            // record's shape would put a name on the row that the database does not use.
            if (!(selected instanceof Column)) {
                return emptyList();
            }
            columns.add(((Column) selected).getColumnName());
        }
        return columns;
    }

    /**
     * The expression in each position of a query's first row, where SQL takes a union's column
     * names from. JSQLParser's {@link Select#getPlainSelect()} and {@link Select#getValues()} are
     * unchecked casts that throw on any other shape.
     */
    private static List<Expression> firstRow(Select select) {
        if (select instanceof ParenthesedSelect) {
            return firstRow(((ParenthesedSelect) select).getSelect());
        }
        if (select instanceof SetOperationList && !((SetOperationList) select).getSelects().isEmpty()) {
            return firstRow(((SetOperationList) select).getSelects().get(0));
        }
        List<Expression> row = new ArrayList<>();
        if (select instanceof PlainSelect && ((PlainSelect) select).getSelectItems() != null) {
            for (SelectItem<?> item : ((PlainSelect) select).getSelectItems()) {
                row.add(item.getExpression());
            }
        } else if (select instanceof Values && ((Values) select).getExpressions() != null) {
            ExpressionList<?> values = ((Values) select).getExpressions();
            // Several rows parse to a list of parenthesized rows.
            if (!values.isEmpty() && values.get(0) instanceof ParenthesedExpressionList) {
                values = (ExpressionList<?>) values.get(0);
            }
            row.addAll(values);
        }
        return row;
    }

    private static EmbeddedSql inserted(Insert insert, Map<String, HostVariable> names, List<String> tables,
                                        @Nullable String target) {
        if (insert.getColumns() == null) {
            return new EmbeddedSql(emptyList(), emptyList(), emptyList(), tables, target);
        }
        List<String> columns = new ArrayList<>();
        for (Column column : insert.getColumns()) {
            columns.add(column.getColumnName());
        }
        List<@Nullable HostVariable> variables = inEachPosition(firstRow(insert.getSelect()), columns.size(), names);
        return new EmbeddedSql(columns, variables, emptyList(), tables, target);
    }

    private static EmbeddedSql updated(Update update, Map<String, HostVariable> names, List<String> tables,
                                       @Nullable String target) {
        List<String> columns = new ArrayList<>();
        List<Expression> values = new ArrayList<>();
        for (UpdateSet set : update.getUpdateSets()) {
            for (int i = 0; i < set.getColumns().size(); i++) {
                columns.add(set.getColumns().get(i).getColumnName());
                values.add(i < set.getValues().size() ? set.getValues().get(i) : null);
            }
        }
        return new EmbeddedSql(columns, inEachPosition(values, columns.size(), names), emptyList(), tables, target);
    }

    /**
     * The host variable in each value position, null where a literal or an expression sits there.
     */
    private static List<@Nullable HostVariable> inEachPosition(List<@Nullable Expression> values, int columns,
                                                               Map<String, HostVariable> names) {
        List<@Nullable HostVariable> variables = new ArrayList<>(columns);
        for (int i = 0; i < columns; i++) {
            Expression value = i >= values.size() ? null : values.get(i);
            variables.add(value instanceof JdbcNamedParameter ? names.get(((JdbcNamedParameter) value).getName()) : null);
        }
        return variables;
    }

    /**
     * A host variable as the statement writes it, with the names that qualify it.
     * {@code :DCLPOL.POLICY-NO} is {@code POLICY-NO} qualified by {@code DCLPOL}.
     */
    @Value
    public static class HostVariable {
        String name;

        /**
         * Innermost first, and as written, like the name.
         */
        List<String> qualifiers;
    }
}
