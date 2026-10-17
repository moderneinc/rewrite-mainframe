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

import java.util.List;

/**
 * A view: its name, the columns it declares, and the query it is.
 * <p>
 * A program reads a view as it reads a table, so what reaches the tables behind it is this query.
 */
@Value
public class View implements Trait<Db2.CreateView> {

    Cursor cursor;

    /**
     * The schema, or null for a view created unqualified.
     */
    public @Nullable String getSchema() {
        return getTree().getName().getQualifier();
    }

    public String getName() {
        return getTree().getName().getSimpleName();
    }

    /**
     * Schema and name, upper cased, as the COBOL that reads the view writes it.
     */
    public String getQualifiedName() {
        return getTree().getName().getFullName();
    }

    /**
     * The column names the view declares, in order, or empty for one whose columns are its query's.
     */
    public List<String> getColumns() {
        return Constraints.columnNames(getTree().getColumns());
    }

    public Query getQuery() {
        return new Query(new Cursor(cursor, getTree().getQuery().getElement()));
    }

    public static class Matcher extends SimpleTraitMatcher<View> {

        @Override
        protected @Nullable View test(Cursor cursor) {
            return cursor.getValue() instanceof Db2.CreateView ? new View(cursor) : null;
        }
    }

    @Override
    public String toString() {
        return "VIEW " + getQualifiedName();
    }
}
