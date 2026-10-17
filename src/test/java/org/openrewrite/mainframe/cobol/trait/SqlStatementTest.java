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

import org.junit.jupiter.api.Test;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.cobol.CobolIsoVisitor;
import org.openrewrite.mainframe.cobol.CobolPreprocessorIsoVisitor;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.CobolPreprocessor;
import org.openrewrite.test.RewriteTest;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.openrewrite.mainframe.cobol.Assertions.cobol;
import static org.openrewrite.mainframe.cobol.Assertions.copybook;

class SqlStatementTest implements RewriteTest {

    /**
     * The shape genapp's DB2 modules are written in. The host variable in the WHERE clause is a
     * predicate rather than a result, and a row claiming a column flows into it would be wrong.
     */
    @Test
    void bindsEachColumnToTheHostVariableItLandsIn() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. BINDPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  CA-FIRST-NAME      PIC X(10).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          SELECT FIRSTNAME, LASTNAME
              000000          INTO :CA-FIRST-NAME, :CA-LAST-NAME
              000000          FROM CUSTOMER
              000000          WHERE CUSTOMERNUMBER = :CA-CUSTOMER-NUM
              000000     END-EXEC.
              000000     EXEC SQL
              000000          INSERT INTO POLICY (ISSUEDATE, BROKERID, PAYMENT)
              000000          VALUES (:DB2-ISSUEDATE, 0, :DB2-PAYMENT)
              000000     END-EXEC.
              000000     EXEC SQL
              000000          SELECT * INTO :WS-REC FROM ACCOUNT
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                List<SqlStatement> statements = statementsIn(cu);
                assertThat(statements).hasSize(3);

                assertThat(SqlStatements.bind(statements.get(0), List.of()))
                  .extracting(SqlStatement.Binding::getColumn, SqlStatement.Binding::getHostVariable)
                  .containsExactly(tuple("FIRSTNAME", "CA-FIRST-NAME"), tuple("LASTNAME", "CA-LAST-NAME"));

                // The literal takes a column position without a host variable, and the one after it
                // still has to line up.
                assertThat(SqlStatements.bind(statements.get(1), List.of()))
                  .extracting(SqlStatement.Binding::getColumn, SqlStatement.Binding::getHostVariable)
                  .containsExactly(tuple("ISSUEDATE", "DB2-ISSUEDATE"), tuple("PAYMENT", "DB2-PAYMENT"));

                // SELECT * names no column, and guessing at the record's shape would put a name on
                // the row that the database does not use.
                assertThat(SqlStatements.bind(statements.get(2), List.of())).isEmpty();
            })
          )
        );
    }

    @Test
    void readsVerbTablesAndAccess() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. SQLPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  WS-ID              PIC X(11).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          SELECT ACCT-ID INTO :WS-ID FROM ACCOUNT
              000000     END-EXEC.
              000000     EXEC SQL
              000000          INSERT INTO CUSTOMER VALUES (:WS-ID)
              000000     END-EXEC.
              000000     EXEC SQL
              000000          DELETE FROM AUDIT-LOG
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                List<SqlStatement> statements = statementsIn(cu);
                assertThat(statements).hasSize(3);

                assertThat(statements.get(0).getVerb()).isEqualTo("SELECT");
                assertThat(statements.get(0).getAccess()).isEqualTo(SqlStatement.Access.READ);
                // The INTO here names a host variable, not a table.
                assertThat(statements.get(0).getTables()).containsExactly("ACCOUNT");

                assertThat(statements.get(1).getVerb()).isEqualTo("INSERT");
                assertThat(statements.get(1).getAccess()).isEqualTo(SqlStatement.Access.CREATE);
                assertThat(statements.get(1).getTables()).containsExactly("CUSTOMER");

                assertThat(statements.get(2).getVerb()).isEqualTo("DELETE");
                assertThat(statements.get(2).getAccess()).isEqualTo(SqlStatement.Access.DELETE);
                assertThat(statements.get(2).getTables()).containsExactly("AUDIT-LOG");
                // No SQL parser takes a hyphen in a name, and the word it is read off is still the one written.
                assertThat(statements.get(2).getTableAccesses()).singleElement().satisfies(table -> {
                    assertThat(table.getAccess()).isEqualTo(SqlStatement.Access.DELETE);
                    assertThat(spelled(table)).isEqualTo("AUDIT-LOG");
                });
            })
          )
        );
    }

    @Test
    void readsCursorStatements() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. CURSPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  WS-ID              PIC X(11).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          DECLARE ACCTCSR CURSOR FOR
              000000          SELECT ACCT-ID FROM ACCOUNT
              000000     END-EXEC.
              000000     EXEC SQL OPEN ACCTCSR END-EXEC.
              000000     EXEC SQL FETCH ACCTCSR INTO :WS-ID END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                List<SqlStatement> statements = statementsIn(cu);
                assertThat(statements).hasSize(3);

                assertThat(statements.get(0).getCursorName()).isEqualTo("ACCTCSR");
                assertThat(statements.get(0).getTables()).containsExactly("ACCOUNT");
                assertThat(statements.get(0).getAccess()).isEqualTo(SqlStatement.Access.DECLARE);

                assertThat(statements.get(1).getCursorName()).isEqualTo("ACCTCSR");
                assertThat(statements.get(2).getCursorName()).isEqualTo("ACCTCSR");
                assertThat(statements.get(2).getAccess()).isEqualTo(SqlStatement.Access.READ);
            })
          )
        );
    }

    /**
     * Real SQL is written a column to a line, so the comma separating two of them is the last thing
     * on the card. Bank of Z and genapp both write it this way, and the separator is kept somewhere
     * different from one written mid-line. Lost, {@code SELECT A, B} becomes {@code SELECT A B},
     * which is not a parse failure but a column with an alias — INQCUST read as one column where it
     * names seventeen, and reported no column at all rather than the wrong one.
     */
    @Test
    void bindsColumnsSeparatedAcrossLines() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. WRAPPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  HV-SORTCODE        PIC X(6).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000        SELECT CUSTOMER_EYECATCHER,
              000000               CUSTOMER_SORTCODE,
              000000               CUSTOMER_NUMBER
              000000          INTO :HV-EYECATCHER,
              000000               :HV-SORTCODE,
              000000               :HV-NUMBER
              000000          FROM CUSTOMER
              000000         WHERE CUSTOMER_SORTCODE = :HV-SORTCODE
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> assertThat(SqlStatements.bind(statementsIn(cu).get(0), List.of()))
              .extracting(SqlStatement.Binding::getColumn, SqlStatement.Binding::getHostVariable)
              .containsExactly(tuple("CUSTOMER_EYECATCHER", "HV-EYECATCHER"),
                tuple("CUSTOMER_SORTCODE", "HV-SORTCODE"),
                tuple("CUSTOMER_NUMBER", "HV-NUMBER")))
          )
        );
    }

    /**
     * CardDemo qualifies its tables with a schema. SQL writes the name as one word and COBOL reads it
     * as two, so read as words it is the table CARDDEMO.TRANSACTION — a name no database has, and one
     * the CRUD matrix reported for every DB2 access in the program.
     */
    @Test
    void keepsAQualifiedTableWhole() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. QUALPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  WS-TYPE            PIC X(2).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          SELECT TR_TYPE INTO :WS-TYPE
              000000          FROM CARDDEMO.TRANSACTION_TYPE
              000000     END-EXEC.
              000000     EXEC SQL
              000000          DELETE FROM CARDDEMO.TRANSACTION_TYPE
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                List<SqlStatement> statements = statementsIn(cu);
                assertThat(statements).hasSize(2).allSatisfy(statement -> {
                    assertThat(statement.getTables()).containsExactly("CARDDEMO.TRANSACTION_TYPE");
                    assertThat(statement.getTable()).isEqualTo("CARDDEMO.TRANSACTION_TYPE");
                });
            })
          )
        );
    }

    /**
     * A fetch names no columns of its own, so the only way it says which column it read is the
     * cursor it was declared against. The indicator variable rides with the column before it rather
     * than taking a position of its own.
     */
    @Test
    void bindsAFetchThroughTheCursorItWasDeclaredAgainst() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. FETCHPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  DB2-ISSUEDATE      PIC X(10).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          DECLARE POLICY_CURSOR CURSOR WITH HOLD FOR
              000000            SELECT ISSUEDATE, EXPIRYDATE, BROKERID
              000000            FROM POLICY
              000000            WHERE CUSTOMERNUMBER = :DB2-CUSTOMERNUM
              000000            FOR UPDATE OF ISSUEDATE, EXPIRYDATE
              000000     END-EXEC.
              000000     EXEC SQL
              000000          FETCH POLICY_CURSOR
              000000          INTO :DB2-ISSUEDATE,
              000000               :DB2-EXPIRYDATE,
              000000               :DB2-BROKERID INDICATOR :IND-BROKERID
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                List<SqlStatement> statements = statementsIn(cu);
                assertThat(statements).hasSize(2);

                SqlStatement declare = statements.get(0);
                assertThat(declare.getColumns())
                  .containsExactly("ISSUEDATE", "EXPIRYDATE", "BROKERID");

                assertThat(SqlStatements.bind(statements.get(1), declare.getColumns()))
                  .extracting(SqlStatement.Binding::getColumn, SqlStatement.Binding::getHostVariable)
                  .containsExactly(tuple("ISSUEDATE", "DB2-ISSUEDATE"),
                    tuple("EXPIRYDATE", "DB2-EXPIRYDATE"),
                    tuple("BROKERID", "DB2-BROKERID"));

                // Without the declaration there is nothing to name, and no row is better than a guess.
                assertThat(SqlStatements.bind(statements.get(1), List.of())).isEmpty();
            })
          )
        );
    }

    /**
     * DB2 writes a fetch {@code FETCH FROM <cursor>}, and read as a table introducer that
     * {@code FROM} makes the cursor a table: Bank of Z reported {@code ACC-CURSOR} beside
     * {@code ACCOUNT} as a store of its own. A cursor is not a resource, and the tables a fetch
     * reaches are the ones its declaration named.
     */
    @Test
    void readsAFetchAsAnAccessToTheTableAndNeverToTheCursor() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. FROMPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  WS-ID              PIC X(11).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          DECLARE ACC-CURSOR INSENSITIVE SCROLL CURSOR FOR
              000000          SELECT ACCOUNT_NUMBER FROM ACCOUNT
              000000     END-EXEC.
              000000     EXEC SQL OPEN ACC-CURSOR END-EXEC.
              000000     EXEC SQL FETCH FROM ACC-CURSOR INTO :WS-ID END-EXEC.
              000000     EXEC SQL CLOSE ACC-CURSOR END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                List<SqlStatement> statements = statementsIn(cu);
                assertThat(statements).hasSize(4);
                var cursors = SqlStatements.cursorsIn(cu);
                // The attributes between the name and CURSOR are the shape genapp writes.
                assertThat(cursors).containsOnlyKeys("ACC-CURSOR");

                assertThat(statements).allSatisfy(statement -> {
                    assertThat(statement.getCursorName()).isEqualTo("ACC-CURSOR");
                    assertThat(statement.getTables()).doesNotContain("ACC-CURSOR");
                });
                // The declaration names the table, the OPEN and the FETCH reach it through the
                // cursor, and the CLOSE names the cursor and touches nothing.
                for (SqlStatement statement : statements.subList(0, 3)) {
                    assertThat(SqlStatements.tablesOf(statement, cursors)).containsExactly("ACCOUNT");
                }
                assertThat(SqlStatements.tablesOf(statements.get(3), cursors)).isEmpty();
            })
          )
        );
    }

    /**
     * A join names two tables and the program reaches both. Read off the words only the first is
     * found, and genapp writes {@code FROM POLICY,COMMERCIAL} with no space, which the words hand
     * back as one table nothing is called.
     */
    @Test
    void namesEveryTableOfAJoin() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. JOINPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  WS-ID              PIC X(11).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          SELECT ISSUEDATE INTO :WS-ID
              000000          FROM  POLICY,COMMERCIAL
              000000          WHERE POLICY.POLICYNUMBER = COMMERCIAL.POLICYNUMBER
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                SqlStatement statement = statementsIn(cu).get(0);
                assertThat(statement.getTables()).containsExactlyInAnyOrder("POLICY", "COMMERCIAL");
                // Two tables give no way to say which column came from where.
                assertThat(statement.getTable()).isNull();
                // Each is written in a word of its own, though no blank separates them.
                assertThat(statement.getTableAccesses())
                  .extracting(SqlTableAccess::getName, SqlStatementTest::spelled)
                  .containsExactly(tuple("POLICY", "POLICY"), tuple("COMMERCIAL", "COMMERCIAL"));
            })
          )
        );
    }

    /**
     * A DCLGEN declares its table ahead of the keyword, which is the one place a table is not
     * introduced by the word in front of it, and the statement is not one any SQL parser takes.
     */
    @Test
    void readsTheTableADclgenDeclares() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. DCLPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000     EXEC SQL DECLARE CLM.POLICY TABLE
              000000          ( POLICY_NO CHAR(12) NOT NULL )
              000000     END-EXEC.
              000000 PROCEDURE DIVISION.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                SqlStatement statement = statementsIn(cu).get(0);
                assertThat(statement.getTables()).containsExactly("CLM.POLICY");
                assertThat(statement.getAccess()).isEqualTo(SqlStatement.Access.DECLARE);
                // A table declaration is not a cursor declaration.
                assertThat(statement.getCursorName()).isNull();
                assertThat(statement.getCursorWords()).isEmpty();
                assertThat(SqlStatements.cursorsIn(cu)).isEmpty();
                assertThat(statement.getTableAccesses()).singleElement().satisfies(table -> {
                    assertThat(table.getAccess()).isEqualTo(SqlStatement.Access.DECLARE);
                    assertThat(spelled(table)).isEqualTo("CLM.POLICY");
                });
            })
          )
        );
    }

    /**
     * The fixture's CLMD020 writes its indicator with no {@code INDICATOR} keyword, which is the
     * other spelling DB2 takes. Counted as a position of its own it leaves the fetch one host
     * variable longer than the cursor's select list, and then nothing pairs at all.
     */
    @Test
    void ridesABareIndicatorWithTheColumnBeforeIt() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. INDPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  POLICY-NO          PIC X(12).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          DECLARE POLCSR CURSOR WITH HOLD FOR
              000000          SELECT POLICY_NO,
              000000                 LAST_CLAIM_DATE
              000000            FROM CLM.POLICY
              000000     END-EXEC.
              000000     EXEC SQL
              000000          FETCH POLCSR
              000000           INTO :POLICY-NO,
              000000                :LAST-CLAIM-DATE :WS-LAST-CLAIM-IND
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                List<SqlStatement> statements = statementsIn(cu);
                SqlStatement fetch = statements.get(1);
                assertThat(fetch.getHostVariables()).containsExactly("POLICY-NO", "LAST-CLAIM-DATE");
                assertThat(fetch.getIndicatorVariables()).containsExactly("WS-LAST-CLAIM-IND");
                assertThat(SqlStatements.bind(fetch, statements.get(0).getColumns()))
                  .extracting(SqlStatement.Binding::getColumn, SqlStatement.Binding::getHostVariable)
                  .containsExactly(tuple("POLICY_NO", "POLICY-NO"),
                    tuple("LAST_CLAIM_DATE", "LAST-CLAIM-DATE"));
                // DB2 fills the indicator, so the program is still using it.
                assertThat(SqlStatements.variablesOf(fetch)).contains("WS-LAST-CLAIM-IND");
            })
          )
        );
    }

    /**
     * SQL's one name reaches COBOL as more than one word, so the name is spelled by all of them:
     * the first alone, as a search anchored on it would place it, is the table CLM.CLAIM.
     */
    @Test
    void spellsAQualifiedNameWithEveryWordItReachedCobolAs() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. HISTPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  WS-CLAIM           PIC X(10).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          SELECT CLAIM_NO INTO :WS-CLAIM
              000000            FROM CLM.CLAIM_HIST
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> assertThat(statementsIn(cu).get(0).getTableAccesses())
              .singleElement().satisfies(table -> {
                  assertThat(table.getName()).isEqualTo("CLM.CLAIM_HIST");
                  assertThat(table.getLocation()).isNull();
                  assertThat(table.getSchema()).isEqualTo("CLM");
                  assertThat(table.getSimpleName()).isEqualTo("CLAIM_HIST");
                  assertThat(table.getAccess()).isEqualTo(SqlStatement.Access.READ);
                  assertThat(table.getWords()).extracting(Cobol.Word::getWord).hasSizeGreaterThan(1);
                  assertThat(spelled(table)).isEqualTo("CLM.CLAIM_HIST");
              }))
          )
        );
    }

    /**
     * What a statement writes is the one table that takes its access. Every other table it names,
     * in the query an insert copies from or in a subquery, it only reads.
     */
    @Test
    void writesTheTargetAndReadsTheRest() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. ARCHPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  WS-CUTOFF          PIC X(10).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          INSERT INTO CLM.CLAIM_ARCH
              000000            SELECT * FROM CLM.CLAIM_HIST
              000000             WHERE CLOSED_DATE < :WS-CUTOFF
              000000     END-EXEC.
              000000     EXEC SQL
              000000          UPDATE CLM.POLICY SET STATUS = 'C'
              000000           WHERE POLICY_NO IN
              000000             (SELECT POLICY_NO FROM CLM.CLAIM_ARCH)
              000000     END-EXEC.
              000000     EXEC SQL
              000000          DELETE FROM CLM.CLAIM_HIST
              000000           WHERE CLOSED_DATE < :WS-CUTOFF
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                List<SqlStatement> statements = statementsIn(cu);
                assertThat(statements.get(0).getTableAccesses())
                  .extracting(SqlTableAccess::getName, SqlTableAccess::getAccess)
                  .containsExactly(tuple("CLM.CLAIM_ARCH", SqlStatement.Access.CREATE),
                    tuple("CLM.CLAIM_HIST", SqlStatement.Access.READ));
                assertThat(statements.get(1).getTableAccesses())
                  .extracting(SqlTableAccess::getName, SqlTableAccess::getAccess)
                  .containsExactly(tuple("CLM.POLICY", SqlStatement.Access.UPDATE),
                    tuple("CLM.CLAIM_ARCH", SqlStatement.Access.READ));
                assertThat(statements.get(2).getTableAccesses())
                  .extracting(SqlTableAccess::getName, SqlTableAccess::getAccess)
                  .containsExactly(tuple("CLM.CLAIM_HIST", SqlStatement.Access.DELETE));
            })
          )
        );
    }

    /**
     * The parser hands the tables of a statement back as a set. The accesses are in the order they
     * were written, so the first of them is the first table a reader meets.
     */
    @Test
    void putsTheTablesInTheOrderTheyWereWritten() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. ORDRPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  WS-ID              PIC X(11).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          SELECT Z.ID INTO :WS-ID
              000000            FROM ZETA Z, ALPHA A, MIDDLE M
              000000           WHERE Z.ID = A.ID AND A.ID = M.ID
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> assertThat(statementsIn(cu).get(0).getTableAccesses())
              .extracting(SqlTableAccess::getName)
              .containsExactly("ZETA", "ALPHA", "MIDDLE"))
          )
        );
    }

    /**
     * A column can share its table's name. The name is looked for after a word that introduces a
     * table first, so the column ahead of it is not where the table is said to be written.
     */
    @Test
    void takesNoColumnForTheTableItIsNamedAfter() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. STATPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  WS-STATUS          PIC X(1).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          SELECT STATUS INTO :WS-STATUS
              000000            FROM STATUS
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                SqlStatement statement = statementsIn(cu).get(0);
                List<Cobol.Word> words = wordsOf(statement.getExec());
                Cobol.Word written = statement.getTableAccesses().get(0).getWords().get(0);
                assertThat(words.get(words.indexOf(written) - 1).getWord()).isEqualTo("FROM");
            })
          )
        );
    }

    /**
     * DB2 folds a name to upper case and a delimited name is taken as it is, so the table is named
     * the way the catalog names it. The tables keep the spelling the statement used.
     */
    @Test
    void namesADelimitedTableAsDb2FoldsIt() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. DELIMPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  WS-ID              PIC X(11).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          SELECT ADJ_NO INTO :WS-ID
              000000            FROM clm."ADJUSTER"
              000000     END-EXEC.
              000000     EXEC SQL
              000000          SELECT POLICY_NO INTO :WS-ID
              000000            FROM DB2P.CLM.POLICY
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                List<SqlStatement> statements = statementsIn(cu);
                assertThat(statements.get(0).getTables()).containsExactly("CLM.\"ADJUSTER\"");
                assertThat(statements.get(0).getTableAccesses()).singleElement().satisfies(table -> {
                    assertThat(table.getName()).isEqualTo("CLM.ADJUSTER");
                    assertThat(table.getSchema()).isEqualTo("CLM");
                    assertThat(table.getSimpleName()).isEqualTo("ADJUSTER");
                    assertThat(spelled(table)).isEqualTo("clm.\"ADJUSTER\"");
                });
                assertThat(statements.get(1).getTableAccesses()).singleElement()
                  .extracting(SqlTableAccess::getLocation, SqlTableAccess::getSchema, SqlTableAccess::getSimpleName)
                  .containsExactly("DB2P", "CLM", "POLICY");
            })
          )
        );
    }

    /**
     * Every cursor statement says which words name its cursor, wherever the statement puts the name.
     */
    @Test
    void namesTheWordsOfEachCursorStatementsCursor() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. CSRPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  WS-ID              PIC X(11).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          DECLARE POL_CSR CURSOR FOR
              000000          SELECT POLICY_NO FROM CLM.POLICY
              000000     END-EXEC.
              000000     EXEC SQL OPEN POL_CSR END-EXEC.
              000000     EXEC SQL FETCH NEXT FROM POL_CSR INTO :WS-ID END-EXEC.
              000000     EXEC SQL FETCH ABSOLUTE 5 FROM POL_CSR INTO :WS-ID END-EXEC.
              000000     EXEC SQL CLOSE POL_CSR END-EXEC.
              000000     EXEC SQL COMMIT END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                List<SqlStatement> statements = statementsIn(cu);
                assertThat(statements).hasSize(6);
                assertThat(statements.subList(0, 5)).allSatisfy(statement -> {
                    assertThat(statement.getCursorName()).isEqualTo("POL_CSR");
                    assertThat(statement.getCursorWords().stream().map(Cobol.Word::getWord).collect(joining()))
                      .isEqualTo("POL_CSR");
                });
                assertThat(statements.get(5).getCursorName()).isNull();
                assertThat(statements.get(5).getCursorWords()).isEmpty();
            })
          )
        );
    }

    /**
     * The words of a block hand the visitor back to the COBOL tree, so a matcher asking every word
     * reports one block many times. Each is reported once, wherever it was written, and
     * {@link SqlStatements#find} still reads the word a block hangs off.
     */
    @Test
    void reportsEachBlockOnce() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. ONCEPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000     EXEC SQL DECLARE CLM.POLICY TABLE
              000000          ( POLICY_NO CHAR(12) NOT NULL )
              000000     END-EXEC.
              000000 01  WS-ID              PIC X(12).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          SELECT POLICY_NO INTO :WS-ID
              000000            FROM CLM.POLICY
              000000     END-EXEC.
              000000     EXEC CICS RETURN END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                List<SqlStatement> statements = statementsIn(cu);
                assertThat(statements).extracting(SqlStatement::getVerb).containsExactly("DECLARE", "SELECT");
                assertThat(statements).allSatisfy(statement ->
                  assertThat(statement.getEnclosingWord()).isNotNull());

                List<SqlStatement> found = new ArrayList<>();
                new CobolIsoVisitor<Integer>() {
                    @Override
                    public Cobol.Word visitWord(Cobol.Word word, Integer p) {
                        found.addAll(SqlStatements.find(word));
                        return super.visitWord(word, p);
                    }
                }.visit(cu, 0);
                assertThat(found).extracting(SqlStatement::getExec)
                  .containsExactlyElementsOf(statements.stream().map(SqlStatement::getExec).collect(toList()));
            })
          )
        );
    }

    /**
     * A DCLGEN copybook is a source of its own, and the table it declares is read there, with no
     * word of a program around it.
     */
    @Test
    void readsTheBlocksOfACopybook() {
        rewriteRun(
          copybook(
            """
              000000******************************************************************
              000000* DCLGEN TABLE(CLM.POLICY)                                       *
              000000******************************************************************
              000000     EXEC SQL DECLARE CLM.POLICY TABLE
              000000     ( POLICY_NO                      CHAR(12) NOT NULL,
              000000       HOLDER_NAME                    CHAR(40) NOT NULL
              000000     ) END-EXEC.
              000000 01  DCLPOLICY.
              000000     10 POLICY-NO            PIC X(12).
              000000     10 HOLDER-NAME          PIC X(40).
              """,
            spec -> spec.path("CLMPOLD.cpy").afterRecipe(copybook -> {
                List<SqlStatement> statements = statementsIn(copybook);
                assertThat(statements).singleElement().satisfies(statement -> {
                    assertThat(statement.getEnclosingWord()).isNull();
                    assertThat(statement.getTableAccesses()).singleElement()
                      .extracting(SqlTableAccess::getName, SqlTableAccess::getAccess)
                      .containsExactly("CLM.POLICY", SqlStatement.Access.DECLARE);
                });
            })
          )
        );
    }

    /**
     * A copybook's blocks stay in the copybook, under the statement copying it, rather than being
     * left on a word of the program, so they are the copybook's to report. Read in the program as
     * well, a DCLGEN's table would be declared once by the copybook and again by every program
     * including it.
     */
    @Test
    void leavesTheBlocksOfACopybookToTheCopybook() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. INCLPGM.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000     EXEC SQL INCLUDE MGSACATG END-EXEC.
              000000     EXEC SQL INCLUDE SQL_TBL END-EXEC.
              000000 01  WS-ID              PIC X(12).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQL
              000000          SELECT POLICY_NO INTO :WS-ID FROM CLM.POLICY
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> assertThat(statementsIn(cu))
              .flatExtracting(SqlStatement::getTables)
              .containsExactly("CLM.POLICY"))
          )
        );
    }

    /**
     * {@code EXEC SQLIMS} is SQL for IMS, read by another precompiler against databases that are
     * not DB2's, and none of its statements are this trait's.
     */
    @Test
    void leavesSqlImsAlone() {
        rewriteRun(
          cobol(
            """
              000000 IDENTIFICATION DIVISION.
              000000 PROGRAM-ID. IMSSQL.
              000000 DATA DIVISION.
              000000 WORKING-STORAGE SECTION.
              000000 01  WS-ID              PIC X(12).
              000000 PROCEDURE DIVISION.
              000000 MAIN-PARA.
              000000     EXEC SQLIMS
              000000          SELECT POLICY_NO INTO :WS-ID FROM POLSEG
              000000     END-EXEC.
              000000     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> assertThat(statementsIn(cu)).isEmpty())
          )
        );
    }

    private static List<SqlStatement> statementsIn(SourceFile sourceFile) {
        return new SqlStatement.Matcher().lower(sourceFile).collect(toList());
    }

    private static String spelled(SqlTableAccess table) {
        return table.getWords().stream().map(Cobol.Word::getWord).collect(joining());
    }

    private static List<Cobol.Word> wordsOf(CobolPreprocessor.ExecStatement exec) {
        List<Cobol.Word> words = new ArrayList<>();
        new CobolPreprocessorIsoVisitor<Integer>() {
            @Override
            public CobolPreprocessor.Word visitWord(CobolPreprocessor.Word word, Integer p) {
                words.add(word.getCobolWord());
                return word;
            }
        }.visit(exec.getCobol(), 0);
        return words;
    }
}
