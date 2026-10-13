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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.ParseExceptionResult;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.db2.tree.Db2;
import org.openrewrite.tree.ParseError;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class SqlDialectTest {

    @ParameterizedTest
    @ValueSource(strings = {
      "drop proc dbo.usp_AccountReport\n",
      "CREATE PROC dbo.usp_AccountReport AS SELECT 1\n",
      "SET NOCOUNT ON\n",
      "DECLARE @id INT\n",
      "RAISERROR('no account', 16, 1)\n",
      "IF OBJECT_ID('dbo.ACCOUNT') IS NOT NULL DROP TABLE dbo.ACCOUNT\n",
      "CREATE TABLE [dbo].[ACCOUNT] (ID INT)\n",
      "SELECT ISNULL(ACCT_ID, 0) FROM dbo.ACCOUNT\n",
      "CREATE PROCEDURE dbo.usp_PutAccount (@ACCT_ID DECIMAL(11) = NULL) AS SELECT 1\n",
      "CREATE TABLE ACCOUNT (ID INT)\nGO\n",
    })
    void transactSql(String source) {
        assertThat(SqlDialect.of(source)).isEqualTo(SqlDialect.TRANSACT_SQL);
    }

    @ParameterizedTest
    @ValueSource(strings = {
      "EXECUTE DBMS_STATS.GATHER_TABLE_STATS('CARDDEMO', 'ACCOUNT');\n",
      "CREATE TABLE ACCOUNT (NAME VARCHAR2(30));\n",
      "BEGIN DBMS_OUTPUT.PUT_LINE('x'); END;\n",
      "SELECT SYSDATE FROM DUAL;\n",
      "CREATE TABLE ACCOUNT (R NUMBER) AS SELECT NVL(R, 0) FROM DUAL;\n",
    })
    void oracle(String source) {
        assertThat(SqlDialect.of(source)).isEqualTo(SqlDialect.ORACLE_PL_SQL);
    }

    @ParameterizedTest
    @ValueSource(strings = {
      "CREATE MULTISET TABLE STAGE.ACCOUNT (ID INTEGER) PRIMARY INDEX (ID);\n",
      "CREATE TABLE STAGE.ACCOUNT, FALLBACK, NO BEFORE JOURNAL (ID INTEGER);\n",
    })
    void teradata(String source) {
        assertThat(SqlDialect.of(source)).isEqualTo(SqlDialect.TERADATA);
    }

    @ParameterizedTest
    @ValueSource(strings = {
      "CREATE TABLE ACCOUNT (ID INT) ENGINE=InnoDB;\n",
      "DELIMITER //\n",
      "CREATE TABLE ACCOUNT (ID INT AUTO_INCREMENT);\n",
      "CREATE FUNCTION ACCOUNT() RETURNS INT AS $$ BEGIN RETURN 1; END $$ LANGUAGE plpgsql;\n",
    })
    void mySqlAndPostgres(String source) {
        assertThat(SqlDialect.of(source)).isEqualTo(SqlDialect.MY_SQL_OR_POSTGRESQL);
    }

    @ParameterizedTest
    @ValueSource(strings = {
      "drop proc dbo.usp_AccountReport\ngo\n",
      "IF OBJECT_ID('dbo.ACCOUNT') IS NOT NULL DROP TABLE dbo.ACCOUNT\n",
      "CREATE TABLE [dbo].[ACCOUNT] (ID INT)\n",
      "BEGIN DBMS_OUTPUT.PUT_LINE('x'); END;\n",
      "DELIMITER //\n",
      "CREATE MULTISET TABLE { $db }.Stg{$job}Src, FALLBACK (ID INTEGER);\n",
    })
    void aFileOfAnotherDialectIsLeftUnclaimed(String source, @TempDir Path directory) throws IOException {
        assertThat(claims(source, directory)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {
      "CREATE TABLE CARDDEMO.ACCOUNT (ID INTEGER AUTO_INCREMENT);\n",
      "CREATE TABLE CARDDEMO.ACCOUNT (NAME VARCHAR2(30));\n",
      "-- migrated from dbo.ACCOUNT in Sybase\nCREATE TABLE CARDDEMO.ACCOUNT (ID INTEGER);\n",
    })
    void ddlTheGrammarReadsIsClaimedAnyway(String source, @TempDir Path directory) throws IOException {
        assertThat(SqlDialect.of(source)).isNotNull();
        assertThat(claims(source, directory)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
      "-- see [ACCOUNT]; the `acct_id` column, then GO on\n/* $$ := */\nDROP WIDGET CARDDEMO.ACCOUNT;\n",
      "-- rebuild the primary index after the load\nDROP WIDGET CARDDEMO.ACCOUNT;\n",
      "/* the primary index on ACCT_ID is unique */\nDROP WIDGET CARDDEMO.ACCOUNT;\n",
      "-- begin transaction boundary is here\nDROP WIDGET CARDDEMO.ACCOUNT;\n",
      "-- no fallback if the load fails\nDROP WIDGET CARDDEMO.ACCOUNT;\n",
      "-- see sp_helptext output for details\nDROP WIDGET CARDDEMO.ACCOUNT;\n",
    })
    void aCommentDoesNotMakeAFileAnotherDialect(String source) {
        assertThat(SqlDialect.of(source)).isNull();
        assertThat(refusalOf(source)).contains("Db2ParsingException").contains("Syntax error in");
    }

    @ParameterizedTest
    @ValueSource(strings = {
      "CREATE TABLE CARDDEMO.ACCOUNT (ID INTEGER AUTO_INCREMENT);\n",
      "CREATE OR REPLACE PROCEDURE CARDDEMO.POST_TRANSACTION LANGUAGE SQL;\n",
      "CREATE TABLE CARDDEMO.ACCOUNT (NAME VARCHAR2(30));\n",
      "CREATE TABLE CARDDEMO.ACCOUNT (@ID INTEGER NOT NULL, PRIMARY KEY(@ID));\n",
      "CREATE TABLE CARDDEMO.ACCOUNT (ID CARDDEMO.ACCT_TYPE NOT NULL);\n",
      "SELECT NAME FROM SYSIBM.SYSTABLES WHERE CREATOR = 'CARDDEMO';\n",
      "COMMENT ON TABLE CARDDEMO.ACCOUNT IS 'see [ACCOUNT], then GO on';\n",
    })
    void whatTheGrammarReadsIsNeverRefused(String source) {
        List<String> errors = new ArrayList<>();
        assertThat(parse(source, errors)).singleElement().isInstanceOf(Db2.Ddl.class);
        assertThat(errors).isEmpty();
    }

    @Test
    void aGrammarGapIsReportedAndNotBlamedOnADialect() {
        List<String> errors = new ArrayList<>();
        List<SourceFile> parsed = parse("DROP WIDGET CARDDEMO.ACCOUNT;\n", errors);

        assertThat(parsed).singleElement().isInstanceOf(ParseError.class);
        assertThat(errors).anySatisfy(e -> assertThat(e).contains("Syntax error in"));
        assertThat(refusalOf("DROP WIDGET CARDDEMO.ACCOUNT;\n")).contains("Db2ParsingException");
    }

    @Test
    void theMarkerAndTheDialectAreBothNamed() {
        assertThat(SqlDialect.TRANSACT_SQL.describe("drop proc dbo.ACCOUNT\n"))
          .isEqualTo("'drop proc' is Transact-SQL");
    }

    @Test
    void theFirstDialectDeclaredWins() {
        assertThat(SqlDialect.of("drop proc dbo.ACCOUNT -- unlike VARCHAR2 in Oracle\n"))
          .isEqualTo(SqlDialect.TRANSACT_SQL);
        assertThat(SqlDialect.values()).containsExactly(SqlDialect.TRANSACT_SQL,
          SqlDialect.ORACLE_PL_SQL, SqlDialect.MY_SQL_OR_POSTGRESQL, SqlDialect.TERADATA);
    }

    private static boolean claims(String source, Path directory) throws IOException {
        Path file = directory.resolve("candidate.sql");
        Files.write(file, source.getBytes(StandardCharsets.UTF_8));
        return Db2Parser.builder().build().accept(file);
    }

    private static List<SourceFile> parse(String source, List<String> errors) {
        return Db2Parser.builder().build()
          .parse(new InMemoryExecutionContext(t -> errors.add(String.valueOf(t.getMessage()))), source)
          .collect(Collectors.toList());
    }

    private static String refusalOf(String source) {
        List<SourceFile> parsed = parse(source, new ArrayList<>());
        assertThat(parsed).singleElement().isInstanceOf(ParseError.class);
        return parsed.get(0).getMarkers().findFirst(ParseExceptionResult.class)
          .map(ParseExceptionResult::getMessage).orElse("");
    }
}
