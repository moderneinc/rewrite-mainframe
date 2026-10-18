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
import org.openrewrite.mainframe.cobol.tree.Cobol;

import java.util.List;

/**
 * One table an {@code EXEC SQL} statement names, what the statement does to it, and where the name
 * was written.
 */
@Value
public class SqlTableAccess {

    /**
     * The name as DB2 folds it, with delimiters stripped and in upper case: {@code CLM.POLICY} for
     * {@code clm."POLICY"} as much as for {@code CLM.POLICY}.
     */
    String name;

    /**
     * The location of a three-part name, or null.
     */
    @Nullable
    String location;

    /**
     * The schema the name is qualified with, or null where it is not qualified.
     */
    @Nullable
    String schema;

    /**
     * The table's own name, without the schema or location qualifying it.
     */
    String simpleName;

    /**
     * What the statement does to this table. The table an {@code INSERT}, {@code UPDATE},
     * {@code DELETE} or {@code MERGE} writes takes the statement's access, and the tables of a
     * {@code DECLARE} take {@link SqlStatement.Access#DECLARE}. Every other table is read, as the
     * table of a subquery is.
     */
    SqlStatement.Access access;

    /**
     * The words the name was written in, first to last, which is more than one where SQL's one name
     * reaches COBOL as several: {@code CLM.CLAIM_HIST} arrives as {@code CLM.CLAIM}, {@code _} and
     * {@code HIST}. Empty for a name no word of the statement spells.
     */
    List<Cobol.Word> words;
}
