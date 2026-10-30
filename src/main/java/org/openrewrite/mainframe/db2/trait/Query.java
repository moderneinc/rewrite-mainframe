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
package org.openrewrite.mainframe.db2.trait;

import lombok.Value;
import org.jspecify.annotations.Nullable;
import org.openrewrite.Cursor;
import org.openrewrite.mainframe.db2.tree.Db2;
import org.openrewrite.trait.SimpleTraitMatcher;
import org.openrewrite.trait.Trait;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * A query, and the tables it reads. A view or a materialized query table is built from one, and an
 * {@code INSERT ... SELECT} copies what one reads. The subqueries of an {@code UPDATE} or a
 * {@code DELETE} are queries, and so is each query in a SQL PL body.
 * <p>
 * The tree keeps a query as its words, with every qualified name in it a {@link Db2.Name}, so a
 * table, a column and a function are each a name. Which one a name is depends on where it stands:
 * a table is in FROM-list position.
 */
@Value
public class Query implements Trait<Db2.Query> {

    /**
     * The words that end a FROM list.
     */
    private static final Set<String> AFTER_FROM = new HashSet<>(Arrays.asList(
            "WHERE", "GROUP", "HAVING", "ORDER", "UNION", "EXCEPT", "INTERSECT", "FETCH", "FOR", "WITH",
            "OPTIMIZE"));

    Cursor cursor;

    /**
     * The names the query reads as tables, in the order written, a subquery's included: each name
     * after {@code FROM} or {@code JOIN}, or after a comma that separates a FROM list, unless it is
     * called like a function. A name in the select list, a join's {@code ON} or a {@code WHERE} is a
     * column or a function, and an alias is no table. Nor is a name the {@code WITH} clause gives a common table expression, whose
     * query's tables are read instead.
     */
    public List<Db2.Name> getTables() {
        List<Db2.Name> tables = new ArrayList<>();
        // A level per query, innermost first, with the parenthesis depth its own words are at.
        Deque<Level> levels = new ArrayDeque<>();
        levels.push(new Level(0));
        int depth = 0;
        List<Db2> parts = getTree().getParts();
        Set<String> commonTables = new HashSet<>();
        boolean defining = false;
        String previous = "";
        for (int i = 0; i < parts.size(); i++) {
            Db2 part = parts.get(i);
            Level level = levels.peek();
            if (part instanceof Db2.Name) {
                // A SQL PL body's query keeps the name of a common table expression as a name.
                if (depth == 0 && defining && ("WITH".equals(previous) || ",".equals(previous))) {
                    commonTables.add(((Db2.Name) part).getSimpleName().toUpperCase(Locale.ROOT));
                }
                if (level.table && depth == level.depth) {
                    // A name called like a function, XMLTABLE say, is a table function.
                    if (!isWord(parts, i + 1, "(")) {
                        tables.add((Db2.Name) part);
                    }
                    // What follows is its alias, or the end of the table's reference.
                    level.table = false;
                }
                previous = "";
                continue;
            }
            String word = part instanceof Db2.Word ? ((Db2.Word) part).getText().toUpperCase(Locale.ROOT) : "";
            if (depth == 0) {
                // A WITH clause opens the query, and its commas separate the expressions it defines.
                if (i == 0 && "WITH".equals(word)) {
                    defining = true;
                } else if ("SELECT".equals(word)) {
                    defining = false;
                } else if (defining && ("WITH".equals(previous) || ",".equals(previous))) {
                    commonTables.add(word);
                }
            }
            previous = word;
            if ("(".equals(word)) {
                depth++;
                if (isWord(parts, i + 1, "SELECT")) {
                    // A subquery in a FROM list is that table reference itself, with tables of its own.
                    level.table = false;
                    levels.push(new Level(depth));
                }
            } else if (")".equals(word)) {
                if (levels.size() > 1 && level.depth == depth) {
                    levels.pop();
                }
                depth--;
            } else if (depth == level.depth) {
                if ("FROM".equals(word)) {
                    level.from = true;
                    level.table = true;
                } else if ("JOIN".equals(word) || ",".equals(word)) {
                    level.table = level.from;
                } else if (AFTER_FROM.contains(word)) {
                    level.from = false;
                    level.table = false;
                } else {
                    // Every other word ends a table position. ON starts a join condition. A
                    // reference that starts with a word, such as TABLE (...) or a table named by the
                    // reserved word VIEW, names no table, and the alias after it names none either.
                    level.table = false;
                }
            }
        }
        tables.removeIf(table -> table.getQualifier() == null &&
                                 commonTables.contains(table.getSimpleName().toUpperCase(Locale.ROOT)));
        return tables;
    }

    private static boolean isWord(List<Db2> parts, int i, String text) {
        return i < parts.size() && parts.get(i) instanceof Db2.Word &&
               text.equalsIgnoreCase(((Db2.Word) parts.get(i)).getText());
    }

    /**
     * Where one query's words are: whether they are in its FROM list, and whether the next name is a
     * table.
     */
    private static class Level {
        final int depth;
        boolean from;
        boolean table;

        Level(int depth) {
            this.depth = depth;
        }
    }

    public static class Matcher extends SimpleTraitMatcher<Query> {

        @Override
        protected @Nullable Query test(Cursor cursor) {
            return cursor.getValue() instanceof Db2.Query ? new Query(cursor) : null;
        }
    }

    @Override
    public String toString() {
        List<String> names = new ArrayList<>();
        for (Db2.Name table : getTables()) {
            names.add(table.getFullName());
        }
        return "QUERY " + names;
    }
}
