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
import lombok.NoArgsConstructor;
import org.openrewrite.Cursor;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.CobolPreprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

/**
 * Reading what the {@code EXEC SQL} statements of a program say together: which table a cursor
 * statement reaches, and which column each host variable carries.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlStatements {

    /**
     * The SQLCA fields DB2 leaves the outcome of a statement in. Nothing in the COBOL assigns them,
     * and a program that writes {@code EXEC SQL INCLUDE SQLCA} does not declare them anywhere the
     * COBOL parser can see, so anything reasoning about where their values come from has to be told.
     */
    public static final List<String> RESPONSE_FIELDS = Arrays.asList("SQLCODE", "SQLSTATE");

    /**
     * The statements of the blocks {@code word} carries. A caller asking every word hears about a
     * block once for each word carrying it, and the statement's cursor reaches no further than the
     * word; {@link SqlStatement.Matcher} reports each block once, with the path to it.
     */
    public static List<SqlStatement> find(Cobol.Word word) {
        List<SqlStatement> statements = null;
        for (CobolPreprocessor ps : word.getPreprocessorStatements()) {
            if (ps instanceof CobolPreprocessor.ExecStatement) {
                CobolPreprocessor.ExecStatement exec = (CobolPreprocessor.ExecStatement) ps;
                if (SqlStatement.isSql(exec)) {
                    if (statements == null) {
                        statements = new ArrayList<>(1);
                    }
                    statements.add(SqlStatement.parse(
                            new Cursor(new Cursor(new Cursor(null, Cursor.ROOT_VALUE), word), exec), exec));
                }
            }
        }
        return statements == null ? emptyList() : statements;
    }

    /**
     * The cursor declarations of a program, by name. A fetch says which table and columns it read
     * only through the cursor it names, and a declaration sits wherever the program put it — often
     * in WORKING-STORAGE, sometimes in a paragraph after the fetch — so they are collected up front
     * rather than as they are met.
     */
    public static Map<String, SqlStatement> cursorsIn(Cobol.CompilationUnit cu) {
        Map<String, SqlStatement> cursors = new LinkedHashMap<>();
        new SqlStatement.Matcher().lower(cu).forEach(statement -> {
            if (statement.getAccess() == SqlStatement.Access.DECLARE && statement.getCursorName() != null) {
                cursors.put(statement.getCursorName(), statement);
            }
        });
        return cursors;
    }

    /**
     * The tables a statement touches. An {@code OPEN} or a {@code FETCH} names only its cursor, so
     * it takes the tables that cursor was declared against — a fetch reads the table and never the
     * cursor. A {@code CLOSE} names its cursor as well and touches nothing, so it takes none, and
     * neither do {@code COMMIT} and {@code ROLLBACK}, which name nothing at all.
     */
    public static List<String> tablesOf(SqlStatement statement, Map<String, SqlStatement> cursors) {
        if (statement.getTables().isEmpty() && statement.getCursorName() != null &&
            statement.getAccess() != SqlStatement.Access.OTHER) {
            SqlStatement declared = cursors.get(statement.getCursorName());
            if (declared != null) {
                return declared.getTables();
            }
        }
        return statement.getTables();
    }

    /**
     * Every field a statement names: the host variables it moves values through and the indicators
     * riding with them. DB2 sets an indicator, so a program that declares one is using it, but it
     * carries no column of its own.
     */
    public static List<String> variablesOf(SqlStatement statement) {
        if (statement.getIndicatorVariables().isEmpty()) {
            return statement.getHostVariables();
        }
        List<String> variables = new ArrayList<>(statement.getHostVariables());
        variables.addAll(statement.getIndicatorVariables());
        return variables;
    }

    /**
     * Which column each host variable carries. A {@code FETCH} names no columns of its own, so it
     * borrows the ones its cursor was declared for — joining the two by cursor name is the only way
     * a fetch says which column it read.
     * <p>
     * The two lists are paired by position, and only when they are the same length. A query whose
     * select list and {@code INTO} clause disagree has been read wrongly by one side or the other,
     * and pairing a prefix of it would put a confident column name on a row that has not earned one.
     *
     * @param declaredColumns the columns the statement's cursor was declared for, empty when it has
     *                        no cursor or the declaration was not found.
     */
    public static List<SqlStatement.Binding> bind(SqlStatement statement, List<String> declaredColumns) {
        List<String> columns = statement.getColumns().isEmpty() ?
                declaredColumns : statement.getColumns();
        List<String> hostVariables = statement.getHostVariables();
        if (columns.isEmpty() || columns.size() != hostVariables.size()) {
            return emptyList();
        }
        List<SqlStatement.Binding> bindings = new ArrayList<>(columns.size());
        for (int i = 0; i < columns.size(); i++) {
            if (!hostVariables.get(i).isEmpty()) {
                bindings.add(new SqlStatement.Binding(columns.get(i), hostVariables.get(i)));
            }
        }
        return bindings;
    }
}
