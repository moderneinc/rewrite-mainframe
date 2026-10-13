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

import org.jspecify.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.MULTILINE;

public enum SqlDialect {

    TRANSACT_SQL("Transact-SQL",
            "^[ \t]*GO[ \t]*$" +
            "|\\b(CREATE|ALTER|DROP)\\s+PROC\\b" +
            "|\\bSET\\s+(NOCOUNT|ANSI_NULLS|ANSI_PADDING|ANSI_WARNINGS|ARITHABORT|QUOTED_IDENTIFIER)\\b" +
            "|\\bDECLARE\\s+@" +
            "|\\bRAISERROR\\b" +
            "|\\bOBJECT_ID\\s*\\(" +
            "|\\b(GETDATE|ISNULL)\\s*\\(" +
            "|\\b(NVARCHAR|DATETIME2|UNIQUEIDENTIFIER)\\b" +
            "|\\bdbo\\s*\\." +
            "|\\[\\w+]\\s*[.(]" +
            "|\\.\\s*\\[\\w+]"),

    ORACLE_PL_SQL("Oracle PL/SQL",
            "\\bDBMS_\\w" +
            "|\\bVARCHAR2\\b" +
            "|\\bSYSDATE\\b" +
            "|\\bNVL\\s*\\(" +
            "|\\bFROM\\s+DUAL\\b" +
            "|\\bPRAGMA\\b"),

    MY_SQL_OR_POSTGRESQL("MySQL or PostgreSQL",
            "^[ \t]*DELIMITER\\b" +
            "|\\bENGINE\\s*=\\s*(INNODB|MYISAM)\\b" +
            "|\\bLANGUAGE\\s+PLPGSQL\\b" +
            "|\\bAUTO_INCREMENT\\b"),

    TERADATA("Teradata",
            "\\bMULTISET\\s+TABLE\\b" +
            "|\\bNO\\s+(BEFORE|AFTER)\\s+JOURNAL\\b");

    private final String displayName;
    private final Pattern markers;

    SqlDialect(String displayName, String markers) {
        this.displayName = displayName;
        this.markers = Pattern.compile(markers, CASE_INSENSITIVE | MULTILINE);
    }

    public static @Nullable SqlDialect of(String source) {
        for (SqlDialect dialect : values()) {
            if (dialect.markers.matcher(source).find()) {
                return dialect;
            }
        }
        return null;
    }

    public String describe(String source) {
        Matcher matcher = markers.matcher(source);
        return matcher.find() ? "'" + matcher.group().trim() + "' is " + displayName : displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
