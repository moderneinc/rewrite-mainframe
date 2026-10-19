/*
 * Copyright 2025 the original author or authors.
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

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RewriteTest;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.mainframe.db2.Assertions.db2;

class TableTest implements RewriteTest {

    @Test
    void whatAColumnHolds() {
        rewriteRun(
          db2(
            """
              CREATE TABLE CARDDEMO.AUTHFRDS
              (CARD_NUM              CHAR(16)    NOT NULL,
                  TRANSACTION_AMT        DECIMAL(12,2)       ,
                  MERCHANT_NAME          VARCHAR(22)         ,
                  FRAUD_RPT_DATE         DATE                ,
                  PRIMARY KEY(CARD_NUM)                      );
              """,
            spec -> spec.afterRecipe(cu -> {
                Table table = new Table.Matcher().lower(cu).findFirst().orElseThrow();
                assertThat(table.getSchema()).isEqualTo("CARDDEMO");
                assertThat(table.getName()).isEqualTo("AUTHFRDS");
                assertThat(table.getTablespace()).isNull();

                Column cardNum = table.getColumn("card_num");
                assertThat(cardNum).isNotNull();
                assertThat(cardNum.getTypeName()).isEqualTo("CHAR");
                assertThat(cardNum.getLength()).isEqualTo(16);
                assertThat(cardNum.getScale()).isNull();
                assertThat(cardNum.getOrdinal()).isEqualTo(1);

                Column amount = table.getColumn("TRANSACTION_AMT");
                assertThat(amount.getTypeName()).isEqualTo("DECIMAL");
                assertThat(amount.getLength()).isEqualTo(12);
                assertThat(amount.getScale()).isEqualTo(2);
                assertThat(amount.getOrdinal()).isEqualTo(2);

                Column reported = table.getColumn("FRAUD_RPT_DATE");
                assertThat(reported.getTypeName()).isEqualTo("DATE");
                assertThat(reported.getLength()).isNull();
                assertThat(reported.getTable().getName()).isEqualTo("AUTHFRDS");
            })
          )
        );
    }

    /**
     * DB2 supplies {@code NOT NULL} for a primary key column whether or not the DDL writes it, so a
     * key column is never nullable even when nothing in its own definition says so.
     */
    @Test
    void aPrimaryKeyColumnIsNotNullable() {
        rewriteRun(
          db2(
            """
              CREATE TABLE BANKZ.CONTROL (
                                  CONTROL_NAME                   CHAR(32),
                                  CONTROL_VALUE_NUM              INTEGER,
                                  CONTROL_VALUE_STR              CHAR(40) NOT NULL,
                                PRIMARY KEY(CONTROL_NAME)
                                 );
              """,
            spec -> spec.afterRecipe(cu -> {
                Table table = new Table.Matcher().lower(cu).findFirst().orElseThrow();
                assertThat(table.getPrimaryKey()).containsExactly("CONTROL_NAME");
                assertThat(table.getColumns()).extracting(Column::isPrimaryKey)
                  .containsExactly(true, false, false);
                assertThat(table.getColumns()).extracting(Column::isNullable)
                  .containsExactly(false, true, false);
            })
          )
        );
    }

    /**
     * A foreign key added afterwards belongs to the table the {@code ALTER} names, not to the one
     * the file happens to create first.
     */
    @Test
    void aForeignKeyAddedByAnAlter() {
        rewriteRun(
          db2(
            """
              CREATE TABLE CARDDEMO.TRANSACTION_TYPE
              (   TR_TYPE  CHAR(2) NOT NULL,
                  PRIMARY KEY(TR_TYPE));

              CREATE TABLE CARDDEMO.TRANSACTION_TYPE_CATEGORY
              (   TRC_TYPE_CODE  CHAR(2) NOT NULL,
                  PRIMARY KEY(TRC_TYPE_CODE));

              ALTER TABLE CARDDEMO.TRANSACTION_TYPE_CATEGORY
                FOREIGN KEY (TRC_TYPE_CODE)
                  REFERENCES CARDDEMO.TRANSACTION_TYPE (TR_TYPE)
              ON DELETE RESTRICT;
              """,
            spec -> spec.afterRecipe(cu -> {
                // The CREATE TABLE declares none, so reaching foreign keys only through Table would
                // report an estate with no referential integrity at all.
                List<Table> tables = new Table.Matcher().lower(cu).collect(toList());
                assertThat(tables).hasSize(2);
                assertThat(tables).allMatch(table -> table.getForeignKeys().isEmpty());

                List<ForeignKey> foreignKeys = new ForeignKey.Matcher().lower(cu).collect(toList());
                assertThat(foreignKeys).hasSize(1);

                ForeignKey foreignKey = foreignKeys.get(0);
                assertThat(foreignKey.getTable()).isEqualTo("CARDDEMO.TRANSACTION_TYPE_CATEGORY");
                assertThat(foreignKey.getName()).isNull();
                assertThat(foreignKey.getColumns()).containsExactly("TRC_TYPE_CODE");
                assertThat(foreignKey.getReferencedTable()).isEqualTo("CARDDEMO.TRANSACTION_TYPE");
                assertThat(foreignKey.getReferencedColumns()).containsExactly("TR_TYPE");
                assertThat(foreignKey.getDeleteRule()).isEqualTo("RESTRICT");
            })
          )
        );
    }

    /**
     * The form DB2 documents adds a key with {@code ADD} and names it after {@code CONSTRAINT}. It is
     * the same edge CardDemo's {@code ALTER} draws, and its name is the one DB2 catalogs it under —
     * as is the name CardDemo's {@code CREATE TABLE} writes between {@code FOREIGN KEY} and the
     * columns.
     */
    @Test
    void aForeignKeyAddedUnderAName() {
        rewriteRun(
          db2(
            """
              CREATE TABLE CARDDEMO.TRANSACTION_TYPE_CATEGORY
              (   TRC_TYPE_CODE  CHAR(2) NOT NULL,
                  PRIMARY KEY(TRC_TYPE_CODE),
                  FOREIGN KEY TRC_TYPE_CODE (TRC_TYPE_CODE)
                  REFERENCES CARDDEMO.TRANSACTION_TYPE (TR_TYPE));

              ALTER TABLE CLM.CLAIM_HIST
                ADD CONSTRAINT FK_HIST_POLICY FOREIGN KEY (POLICY_NO)
                    REFERENCES CLM.POLICY (POLICY_NO)
                ON DELETE CASCADE;

              ALTER TABLE CLM.CLAIM_HIST ADD FOREIGN KEY (CLAIM_NO) REFERENCES CLM.CLAIM;
              """,
            spec -> spec.afterRecipe(cu -> {
                List<ForeignKey> foreignKeys = new ForeignKey.Matcher().lower(cu).collect(toList());
                assertThat(foreignKeys).hasSize(3);
                assertThat(foreignKeys.get(0).getName()).isEqualTo("TRC_TYPE_CODE");

                ForeignKey named = foreignKeys.get(1);
                assertThat(named.getTable()).isEqualTo("CLM.CLAIM_HIST");
                assertThat(named.getName()).isEqualTo("FK_HIST_POLICY");
                assertThat(named.getColumns()).containsExactly("POLICY_NO");
                assertThat(named.getReferencedTable()).isEqualTo("CLM.POLICY");
                assertThat(named.getReferencedColumns()).containsExactly("POLICY_NO");
                assertThat(named.getDeleteRule()).isEqualTo("CASCADE");

                // Given no name, and left to the primary key of the table it references.
                ForeignKey unnamed = foreignKeys.get(2);
                assertThat(unnamed.getTable()).isEqualTo("CLM.CLAIM_HIST");
                assertThat(unnamed.getName()).isNull();
                assertThat(unnamed.getColumns()).containsExactly("CLAIM_NO");
                assertThat(unnamed.getReferencedTable()).isEqualTo("CLM.CLAIM");
                assertThat(unnamed.getReferencedColumns()).isEmpty();
            })
          )
        );
    }

    /**
     * {@code SET NULL} is the one delete rule written as two words, and reading only the first would
     * report a rule DB2 does not have.
     */
    @Test
    void theTwoWordDeleteRule() {
        rewriteRun(
          db2(
            """
              CREATE TABLE GENASA1.claim (
                   claimNumber   INTEGER NOT NULL,
                   policyNumber  INTEGER,
                 PRIMARY KEY(claimNumber),
                 FOREIGN KEY(policyNumber)
                        REFERENCES GENASA1.policy (policyNumber) ON DELETE SET NULL);
              """,
            spec -> spec.afterRecipe(cu -> {
                ForeignKey foreignKey = new ForeignKey.Matcher().lower(cu).findFirst().orElseThrow();
                assertThat(foreignKey.getDeleteRule()).isEqualTo("SET NULL");
            })
          )
        );
    }

    @Test
    void whatAnIndexCovers() {
        rewriteRun(
          db2(
            """
              CREATE UNIQUE INDEX CARDDEMO.XAUTHFRD
                  ON CARDDEMO.AUTHFRDS
                  (CARD_NUM ASC, AUTH_TS DESC)
                  COPY YES;
              """,
            spec -> spec.afterRecipe(cu -> {
                Index index = new Index.Matcher().lower(cu).findFirst().orElseThrow();
                assertThat(index.getName()).isEqualTo("XAUTHFRD");
                assertThat(index.getQualifiedName()).isEqualTo("CARDDEMO.XAUTHFRD");
                assertThat(index.getTable()).isEqualTo("CARDDEMO.AUTHFRDS");
                assertThat(index.getKeyColumns()).containsExactly("CARD_NUM", "AUTH_TS");
                assertThat(index.getKeys()).extracting(Index.Key::getDirection)
                  .containsExactly("ASC", "DESC");
            })
          )
        );
    }
}
