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
package org.openrewrite.mainframe.db2;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.ParseExceptionResult;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.db2.internal.grammar.DB2Parser;
import org.openrewrite.mainframe.db2.marker.Semicolon;
import org.openrewrite.mainframe.db2.tree.Db2;
import org.openrewrite.mainframe.db2.tree.Db2Container;
import org.openrewrite.tree.ParseError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.InstanceOfAssertFactories.STRING;

/**
 * What the grammar reads, and what it refuses to read.
 * <p>
 * Every case here asserts the file prints back byte for byte as well as what was read from it.
 * Nothing but printing proves a statement was read whole.
 */
class Db2ParserTest {

    @Test
    void aTableIsColumnsAndAPrimaryKey() {
        Db2.Ddl ddl = parse(
          """
            CREATE TABLE CARDDEMO.TRANSACTION_TYPE
            (   TR_TYPE                        CHAR(2) NOT NULL,
                TR_DESCRIPTION                 VARCHAR(50) NOT NULL,
                PRIMARY KEY(TR_TYPE));
            """);

        assertThat(ddl.getStatements()).hasSize(1);
        Db2.CreateTable table = (Db2.CreateTable) ddl.getStatements().get(0);
        assertThat(table.getName().getFullName()).isEqualTo("CARDDEMO.TRANSACTION_TYPE");
        assertThat(table.getName().getQualifier()).isEqualTo("CARDDEMO");
        assertThat(table.getName().getSimpleName()).isEqualTo("TRANSACTION_TYPE");

        assertThat(columns(table)).hasSize(2);
        Db2.ColumnDefinition trType = columns(table).get(0);
        assertThat(trType.getName().getSimpleName()).isEqualTo("TR_TYPE");
        assertThat(trType.getType().getName().getSimpleName()).isEqualTo("CHAR");
        assertThat(isNotNull(trType)).isTrue();

        assertThat(constraints(table)).hasSize(1);
        Db2.Constraint primaryKey = constraints(table).get(0);
        assertThat(Db2.has(primaryKey.getKeywords(), Db2.Keyword.Type.Primary)).isTrue();
        assertThat(names(primaryKey.getColumns())).containsExactly("TR_TYPE");
    }

    /**
     * A nullable column has no {@code NOT NULL} among its attributes rather than a {@code NULL} of
     * its own, so nullability is the absence of something. CardDemo pads its DDL out into columns,
     * which is where a parser reading attributes positionally would go wrong.
     */
    @Test
    void aColumnWithoutNotNullIsNullable() {
        Db2.Ddl ddl = parse(
          """
            CREATE TABLE CARDDEMO.AUTHFRDS
            (CARD_NUM              CHAR(16)    NOT NULL,
                AUTH_TS                TIMESTAMP   NOT NULL,
                TRANSACTION_AMT        DECIMAL(12,2)       ,
                POS_ENTRY_MODE         SMALLINT            ,
                FRAUD_RPT_DATE         DATE                ,
                PRIMARY KEY(CARD_NUM,AUTH_TS )             );
            """);

        Db2.CreateTable table = (Db2.CreateTable) ddl.getStatements().get(0);
        assertThat(columns(table)).extracting(c -> c.getName().getSimpleName())
          .containsExactly("CARD_NUM", "AUTH_TS", "TRANSACTION_AMT", "POS_ENTRY_MODE", "FRAUD_RPT_DATE");
        assertThat(columns(table)).extracting(Db2ParserTest::isNotNull)
          .containsExactly(true, true, false, false, false);
        // The parentheses and the comma belong to the container, so DECIMAL(12,2) is two arguments.
        Db2Container<Db2.Word> arguments = columns(table).get(2).getType().getArguments();
        assertThat(arguments).isNotNull();
        assertThat(arguments.getElements()).extracting(Db2.Word::getText).containsExactly("12", "2");
        assertThat(names(constraints(table).get(0).getColumns()))
          .containsExactly("CARD_NUM", "AUTH_TS");
    }

    /**
     * DB2 lets a foreign key carry a name between {@code KEY} and its columns, which CardDemo writes
     * and genapp does not. Reading that name as the constrained column would make the table point at
     * itself.
     */
    @Test
    void aForeignKeyPointsAtAnotherTable() {
        Db2.Ddl ddl = parse(
          """
            CREATE TABLE CARDDEMO.TRANSACTION_TYPE_CATEGORY
            (   TRC_TYPE_CODE                  CHAR(2) NOT NULL,
                TRC_TYPE_CATEGORY              CHAR(4) NOT NULL,
                PRIMARY KEY(TRC_TYPE_CODE,TRC_TYPE_CATEGORY),
                FOREIGN KEY TRC_TYPE_CODE (TRC_TYPE_CODE)
                REFERENCES CARDDEMO.TRANSACTION_TYPE (TR_TYPE) ON DELETE RESTRICT);
            """);

        Db2.CreateTable table = (Db2.CreateTable) ddl.getStatements().get(0);
        assertThat(constraints(table)).hasSize(2);

        Db2.Constraint foreignKey = constraints(table).get(1);
        assertThat(Db2.has(foreignKey.getKeywords(), Db2.Keyword.Type.Foreign)).isTrue();
        assertThat(foreignKey.getKeyName()).isNotNull();
        assertThat(foreignKey.getKeyName().getSimpleName()).isEqualTo("TRC_TYPE_CODE");
        assertThat(names(foreignKey.getColumns())).containsExactly("TRC_TYPE_CODE");
        assertThat(foreignKey.getReferencedTable()).isNotNull();
        assertThat(foreignKey.getReferencedTable().getFullName())
          .isEqualTo("CARDDEMO.TRANSACTION_TYPE");
        assertThat(names(foreignKey.getReferencedColumns())).containsExactly("TR_TYPE");
    }

    @Test
    void anIndexNamesItsTableAndItsKeys() {
        Db2.Ddl ddl = parse(
          """
            CREATE UNIQUE INDEX CARDDEMO.XAUTHFRD
                ON CARDDEMO.AUTHFRDS
                (CARD_NUM ASC, AUTH_TS DESC)
                COPY YES;
            """);

        Db2.CreateIndex index = (Db2.CreateIndex) ddl.getStatements().get(0);
        assertThat(Db2.has(index.getKeywords(), Db2.Keyword.Type.Unique)).isTrue();
        assertThat(index.getName().getFullName()).isEqualTo("CARDDEMO.XAUTHFRD");
        assertThat(index.getTable().getFullName()).isEqualTo("CARDDEMO.AUTHFRDS");
        assertThat(index.getKeys().getElements()).extracting(k -> k.getName().getSimpleName())
          .containsExactly("CARD_NUM", "AUTH_TS");
        assertThat(index.getKeys().getElements()).extracting(
          k -> k.getDirection() == null ? null : k.getDirection().getType())
          .containsExactly(Db2.Keyword.Type.Asc, Db2.Keyword.Type.Desc);
    }

    /**
     * An index with no {@code UNIQUE} is not a unique index, and a key with no direction takes DB2's
     * ascending default rather than being unread.
     */
    @Test
    void anIndexNeedNotBeUniqueOrDirected() {
        Db2.Ddl ddl = parse(
          """
            CREATE INDEX BANKZ.ACCTCUST
               ON BANKZ.ACCOUNT(ACCOUNT_SORTCODE,ACCOUNT_CUSTOMER_NUMBER)
               ;
            """);

        Db2.CreateIndex index = (Db2.CreateIndex) ddl.getStatements().get(0);
        assertThat(Db2.has(index.getKeywords(), Db2.Keyword.Type.Unique)).isFalse();
        assertThat(index.getKeys().getElements()).extracting(Db2.IndexKey::getDirection)
          .containsOnlyNulls();
    }

    /**
     * CardDemo adds its foreign key after the fact, so an estate's referential integrity is not all
     * in the {@code CREATE TABLE}s.
     */
    @Test
    void anAlterTableCarriesAForeignKey() {
        Db2.Ddl ddl = parse(
          """
            ALTER TABLE CARDDEMO.TRANSACTION_TYPE_CATEGORY
              FOREIGN KEY (TRC_TYPE_CODE)
                REFERENCES CARDDEMO.TRANSACTION_TYPE (TR_TYPE)
            ON DELETE RESTRICT;
            """);

        Db2.AlterTable alter = (Db2.AlterTable) ddl.getStatements().get(0);
        assertThat(alter.getName().getFullName()).isEqualTo("CARDDEMO.TRANSACTION_TYPE_CATEGORY");
        List<Db2.Constraint> constraints = Db2.elementsOf(alter.getActions(), Db2.Constraint.class);
        assertThat(constraints).hasSize(1);
        assertThat(constraints.get(0).getReferencedTable()).isNotNull();
        assertThat(constraints.get(0).getReferencedTable().getFullName())
          .isEqualTo("CARDDEMO.TRANSACTION_TYPE");
    }

    /**
     * The form DB2 documents writes {@code ADD}, and a name after {@code CONSTRAINT}. What is added
     * is the same constraint a {@code CREATE TABLE} would have held, so a key added this way is read
     * as a key rather than as words.
     */
    @Test
    void anAlterTableAddsAConstraintOfEachKind() {
        Db2.Ddl ddl = parse(
          """
            ALTER TABLE CLM.CLAIM_HIST
              ADD CONSTRAINT FK_HIST_POLICY FOREIGN KEY (POLICY_NO)
                  REFERENCES CLM.POLICY (POLICY_NO) ON DELETE RESTRICT;
            ALTER TABLE CLM.CLAIM_HIST ADD FOREIGN KEY (CLAIM_NO) REFERENCES CLM.CLAIM;
            ALTER TABLE CLM.CLAIM ADD CONSTRAINT PK_CLAIM PRIMARY KEY (CLAIM_NO);
            ALTER TABLE CLM.CLAIM ADD UNIQUE (CLAIM_REF);
            ALTER TABLE CLM.CLAIM ADD CONSTRAINT CK_AMOUNT CHECK (CLAIM_AMT >= 0);
            """);

        List<Db2.Constraint> added = new ArrayList<>();
        for (Db2.AlterTable alter : Db2.elementsOf(ddl.getStatements(), Db2.AlterTable.class)) {
            assertThat(alter.getActions()).singleElement().isInstanceOfSatisfying(Db2.Option.class, add -> {
                assertThat(add.getKeywords()).extracting(Db2.Keyword::getType).containsExactly(Db2.Keyword.Type.Add);
                added.addAll(Db2.elementsOf(add.getValues(), Db2.Constraint.class));
            });
        }
        assertThat(added).hasSize(5);
        assertThat(added).extracting(c -> c.getConstraintName() == null ? null : c.getConstraintName().getSimpleName())
          .containsExactly("FK_HIST_POLICY", null, "PK_CLAIM", null, "CK_AMOUNT");

        Db2.Constraint foreignKey = added.get(0);
        assertThat(Db2.has(foreignKey.getKeywords(), Db2.Keyword.Type.Foreign)).isTrue();
        assertThat(names(foreignKey.getColumns())).containsExactly("POLICY_NO");
        assertThat(foreignKey.getReferencedTable()).isNotNull();
        assertThat(foreignKey.getReferencedTable().getFullName()).isEqualTo("CLM.POLICY");
        assertThat(names(foreignKey.getReferencedColumns())).containsExactly("POLICY_NO");

        assertThat(added.get(1).getReferencedTable()).isNotNull();
        assertThat(added.get(1).getReferencedTable().getFullName()).isEqualTo("CLM.CLAIM");
        assertThat(Db2.has(added.get(2).getKeywords(), Db2.Keyword.Type.Primary)).isTrue();
        assertThat(names(added.get(2).getColumns())).containsExactly("CLAIM_NO");
        assertThat(Db2.has(added.get(3).getKeywords(), Db2.Keyword.Type.Unique)).isTrue();
        assertThat(names(added.get(3).getColumns())).containsExactly("CLAIM_REF");
        assertThat(Db2.has(added.get(4).getKeywords(), Db2.Keyword.Type.Check)).isTrue();
    }

    /**
     * A column added afterwards is a column definition like any other, whether or not the
     * {@code COLUMN} that may introduce it is written. The words in front of it are keywords of the
     * {@code ADD}, not white space in front of the column.
     */
    @Test
    void anAlterTableAddsAColumn() {
        Db2.Ddl ddl = parse(
          """
            ALTER TABLE CLM.CLAIM ADD COLUMN CLAIM_NOTE VARCHAR(40);
            ALTER TABLE CLM.CLAIM ADD CLAIM_CLOSED DATE;
            """);

        List<Db2.Option> adds = new ArrayList<>();
        for (Db2.AlterTable alter : Db2.elementsOf(ddl.getStatements(), Db2.AlterTable.class)) {
            adds.addAll(Db2.elementsOf(alter.getActions(), Db2.Option.class));
        }
        assertThat(adds).extracting(add -> add.getKeywords().stream().map(Db2.Keyword::getType).collect(Collectors.toList()))
          .containsExactly(Arrays.asList(Db2.Keyword.Type.Add, Db2.Keyword.Type.Column), singletonList(Db2.Keyword.Type.Add));
        List<Db2.ColumnDefinition> columns = new ArrayList<>();
        adds.forEach(add -> columns.addAll(Db2.elementsOf(add.getValues(), Db2.ColumnDefinition.class)));
        assertThat(columns).extracting(c -> c.getName().getSimpleName()).containsExactly("CLAIM_NOTE", "CLAIM_CLOSED");
        assertThat(columns).extracting(c -> c.getPrefix().getWhitespace()).containsOnly(" ");
    }

    /**
     * {@code VALUES} is written between the columns and the rows, so it prints after the table it
     * fills rather than in front of it.
     */
    @Test
    void anInsertNamesItsTableBeforeItsValues() {
        Db2.Ddl ddl = parse(
          """
            INSERT INTO CARDDEMO.TRANSACTION_TYPE VALUES ('01','PURCHASE');
            INSERT INTO CARDDEMO.TRANSACTION_TYPE
                   (TR_TYPE,TR_DESCRIPTION)
            VALUES ('02','PAYMENT'), ('03','CREDIT');
            insert into carddemo.transaction_type values ('04','AUTHORIZATION');
            """);

        List<Db2.Insert> inserts = Db2.elementsOf(ddl.getStatements(), Db2.Insert.class);
        assertThat(inserts).extracting(insert -> insert.getTable().getFullName())
          .containsExactly("CARDDEMO.TRANSACTION_TYPE", "CARDDEMO.TRANSACTION_TYPE", "CARDDEMO.TRANSACTION_TYPE");
        Db2.Insert rows = inserts.get(1);
        assertThat(names(rows.getColumns())).containsExactly("TR_TYPE", "TR_DESCRIPTION");
        assertThat(rows.getValues().getElements()).hasSize(2);
        assertThat(rows.getKeywords()).extracting(Db2.Keyword::getType)
          .containsExactly(Db2.Keyword.Type.Insert, Db2.Keyword.Type.Into, Db2.Keyword.Type.Values);
    }

    /**
     * {@code FOR}, and the {@code TABLE} or {@code SEQUENCE} that may follow it, sit between an alias
     * and what it names, and print there.
     */
    @Test
    void anAliasNamesWhatItStandsForAfterFor() {
        Db2.Ddl ddl = parse(
          """
            CREATE ALIAS CLM.POL FOR CLM.POLICY;
            CREATE ALIAS CLM.HIST FOR TABLE CLM.CLAIM_HIST;
            CREATE PUBLIC ALIAS CLMSEQ FOR SEQUENCE CLM.CLAIM_SEQ;
            CREATE SYNONYM POLICY FOR CLM.POLICY;
            create alias clm.pol2 for clm.policy;
            """);

        assertThat(Db2.elementsOf(ddl.getStatements(), Db2.CreateAlias.class))
          .extracting(alias -> alias.getName().getFullName(), alias -> alias.getTarget().getElement().getFullName())
          .containsExactly(tuple("CLM.POL", "CLM.POLICY"), tuple("CLM.HIST", "CLM.CLAIM_HIST"),
            tuple("CLMSEQ", "CLM.CLAIM_SEQ"), tuple("CLM.POL2", "CLM.POLICY"));
        assertThat(Db2.elementsOf(ddl.getStatements(), Db2.CreateSynonym.class))
          .extracting(synonym -> synonym.getName().getFullName(), synonym -> synonym.getTarget().getElement().getFullName())
          .containsExactly(tuple("POLICY", "CLM.POLICY"));
    }

    @Test
    void aMaskOrAPermissionPrintsEachKeywordBesideWhatItIntroduces() {
        Db2.Ddl ddl = parse(
          """
            CREATE MASK CLM.SSN_MASK ON CLM.POLICY FOR COLUMN SSN RETURN CASE WHEN 1 = 1 THEN SSN ELSE 'X' END ENABLE;
            CREATE MASK CLM.RATE_MASK ON CLM.POLICY AS P
              FOR COLUMN RATE RETURN CASE WHEN P.RATE > 0 THEN P.RATE ELSE 0 END;
            CREATE PERMISSION CLM.OWN_ROWS ON CLM.CLAIM AS C
              FOR ROW ACCESS CONTROL WHEN (C.OWNER = SESSION_USER) ENFORCED FOR ALL ACCESS ENABLE;
            CREATE PERMISSION CLM.ALL_ROWS ON CLM.POLICY FOR ROW ACCESS CONTROL WHEN (1 = 1);
            """);

        assertThat(Db2.elementsOf(ddl.getStatements(), Db2.CreateMask.class))
          .extracting(mask -> mask.getName().getFullName(), mask -> mask.getTable().getFullName(),
            mask -> Db2.elementsOf(mask.getOptions(), Db2.Name.class).stream().map(Db2.Name::getFullName).collect(Collectors.toList()))
          .containsExactly(tuple("CLM.SSN_MASK", "CLM.POLICY", List.of("SSN")),
            tuple("CLM.RATE_MASK", "CLM.POLICY", List.of("P", "RATE")));
        assertThat(Db2.elementsOf(ddl.getStatements(), Db2.CreatePermission.class))
          .extracting(permission -> permission.getName().getFullName(), permission -> permission.getTable().getFullName(),
            permission -> Db2.elementsOf(permission.getOptions(), Db2.Name.class).stream().map(Db2.Name::getFullName).collect(Collectors.toList()))
          .containsExactly(tuple("CLM.OWN_ROWS", "CLM.CLAIM", List.of("C")),
            tuple("CLM.ALL_ROWS", "CLM.POLICY", List.of()));
    }

    @Test
    void aTriggerPrintsEachKeywordBesideWhatItIntroduces() {
        Db2.Ddl ddl = parse(
          """
            CREATE TRIGGER CLM.STAMP NO CASCADE BEFORE INSERT OR UPDATE OF STATUS ON CLM.CLAIM
              REFERENCING NEW AS N
              FOR EACH ROW MODE DB2SQL
              BEGIN ATOMIC
                SET N.CHANGED = CURRENT TIMESTAMP;
              END;
            """);

        Db2.CreateTrigger trigger = (Db2.CreateTrigger) ddl.getStatements().get(0);
        assertThat(trigger.getName().getFullName()).isEqualTo("CLM.STAMP");
        assertThat(trigger.getTable().getFullName()).isEqualTo("CLM.CLAIM");
        assertThat(trigger.getKeywords()).extracting(Db2.Keyword::getType)
          .contains(Db2.Keyword.Type.No, Db2.Keyword.Type.Cascade, Db2.Keyword.Type.Before);
        assertThat(Db2.elementsOf(trigger.getEvents(), Db2.Option.class))
          .extracting(event -> Db2.has(event.getKeywords(), Db2.Keyword.Type.Insert),
            event -> Db2.has(event.getKeywords(), Db2.Keyword.Type.Update))
          .containsExactly(tuple(true, false), tuple(false, true));
        assertThat(trigger.getBody()).isNotNull();
        assertThat(trigger.getBody().getKeywords()).extracting(Db2.Keyword::getType)
          .containsExactly(Db2.Keyword.Type.Begin, Db2.Keyword.Type.Atomic);
        assertThat(trigger.getBody().getEnd()).isNotNull();
    }

    @Test
    void aSingleStatementTriggerEndsAtItsSemicolon() {
        Db2.Ddl ddl = parse(
          """
            CREATE TRIGGER CLM.RATED AFTER UPDATE ON CLM.POLICY
              FOR EACH ROW UPDATE CLM.CLAIM SET STATUS = CASE WHEN STATUS = 'O' THEN 'A' ELSE 'P' END;
            COMMIT;
            """);

        assertThat(ddl.getStatements()).extracting(s -> s.getClass().getSimpleName())
          .containsExactly("CreateTrigger", "Commit");
        Db2.CreateTrigger trigger = (Db2.CreateTrigger) ddl.getStatements().get(0);
        assertThat(trigger.getName().getFullName()).isEqualTo("CLM.RATED");
        assertThat(trigger.getTable().getFullName()).isEqualTo("CLM.POLICY");
        assertThat(Db2.elementsOf(trigger.getEvents(), Db2.Option.class)).singleElement()
          .satisfies(event -> assertThat(event.getKeywords()).extracting(Db2.Keyword::getType)
            .containsExactly(Db2.Keyword.Type.Update));
        assertThat(trigger.getBody()).isNotNull();
        assertThat(trigger.getBody().getBody()).extracting(word -> ((Db2.Word) word).getText())
          .startsWith("UPDATE", "CLM", ".", "CLAIM", "SET").endsWith("ELSE", "'P'", "END");
        assertThat(trigger.getBody().getEnd()).isNull();
    }

    /**
     * Each of these writes keywords after the name it acts on — the {@code TO} of a rename, the mode
     * a table is locked in, the rule a drop is made under, what an {@code ALTER VIEW} does — and each
     * prints them there.
     */
    @Test
    void aKeywordWrittenAfterTheNamePrintsAfterIt() {
        Db2.Ddl ddl = parse(
          """
            RENAME TABLE CLM.POLICY TO POLICY_OLD;
            RENAME INDEX CLM.XPOLICY TO XPOLICY_OLD;
            LOCK TABLE CLM.POLICY IN EXCLUSIVE MODE;
            DROP PROCEDURE CLM.PURGE_CLAIMS RESTRICT;
            DROP FUNCTION CLM.CLAIM_AGE (DATE) RESTRICT;
            ALTER VIEW CLM.OPEN_CLAIMS REGENERATE;
            """);

        assertThat(Db2.elementsOf(ddl.getStatements(), Db2.Rename.class))
          .extracting(rename -> rename.getName().getFullName(), rename -> rename.getNewName().getElement().getFullName())
          .containsExactly(tuple("CLM.POLICY", "POLICY_OLD"), tuple("CLM.XPOLICY", "XPOLICY_OLD"));
        assertThat(Db2.elementsOf(ddl.getStatements(), Db2.LockTable.class))
          .extracting(lock -> lock.getName().getFullName()).containsExactly("CLM.POLICY");
        assertThat(Db2.elementsOf(ddl.getStatements(), Db2.Drop.class))
          .extracting(drop -> drop.getName().getFullName()).containsExactly("CLM.PURGE_CLAIMS", "CLM.CLAIM_AGE");
        assertThat(Db2.elementsOf(ddl.getStatements(), Db2.AlterView.class))
          .extracting(alter -> alter.getName().getFullName()).containsExactly("CLM.OPEN_CLAIMS");
    }

    /**
     * What a {@code GRANT} lets its grantees pass on, and whose grants a {@code REVOKE} takes back,
     * are written after the grantees, and print there.
     */
    @Test
    void aGrantOrARevokeEndsWithItsOption() {
        Db2.Ddl ddl = parse(
          """
            GRANT SELECT, UPDATE ON TABLE CLM.POLICY TO CLMUSER, CLMADMIN WITH GRANT OPTION;
            GRANT DBADM TO CLMADMIN WITH ADMIN OPTION;
            REVOKE SELECT ON CLM.POLICY FROM CLMUSER BY ALL RESTRICT;
            revoke dbadm from clmadmin by all;
            """);

        Db2.Grant grant = (Db2.Grant) ddl.getStatements().get(0);
        assertThat(grant.getObjects().getElements()).extracting(Db2.Name::getFullName).containsExactly("CLM.POLICY");
        assertThat(grant.getGrantees().getElements()).hasSize(2);
        assertThat(grant.getKeywords()).extracting(Db2.Keyword::getType).containsExactly(
          Db2.Keyword.Type.Grant, Db2.Keyword.Type.With, Db2.Keyword.Type.Grant, Db2.Keyword.Type.Option);
        Db2.Revoke revoke = (Db2.Revoke) ddl.getStatements().get(2);
        assertThat(revoke.getObjects().getElements()).extracting(Db2.Name::getFullName).containsExactly("CLM.POLICY");
        assertThat(revoke.getGrantees().getElements()).hasSize(1);
    }

    /**
     * The {@code AS} of a view, a sequence or a type and the {@code IS} of a comment or a label are
     * keywords, so they print in the case the file wrote them in, as every other keyword does.
     */
    @Test
    void aWordBetweenTwoPartsKeepsItsCase() {
        Db2.Ddl ddl = parse(
          """
            create view clm.open_claims as select claim_no from clm.claim;
            create sequence clm.claim_seq as integer;
            create distinct type clm.money as decimal(11,2) with comparisons;
            comment on table clm.claim is 'one row per claim';
            label on table clm.claim is 'Claims';
            """);

        Db2.CreateView view = (Db2.CreateView) ddl.getStatements().get(0);
        assertThat(view.getKeywords()).extracting(Db2.Keyword::getText).containsExactly("create", "view", "as");
        assertThat(Db2.elementsOf(view.getQuery().getElement().getParts(), Db2.Name.class))
          .extracting(Db2.Name::getFullName).contains("CLM.CLAIM");
        Db2.Comment comment = (Db2.Comment) ddl.getStatements().get(3);
        assertThat(comment.getTarget().getFullName()).isEqualTo("CLM.CLAIM");
        assertThat(comment.getText().getElement().getText()).isEqualTo("'one row per claim'");
    }

    /**
     * {@code COMMENT ON COLUMN} names the column through its table. The target is that one
     * qualified name, so the column is its simple name and the table is what qualifies it — and a
     * comment on a column no longer turns the whole file into a parse error.
     */
    @Test
    void aCommentOnAColumnNamesItThroughItsTable() {
        Db2.Ddl ddl = parse(
          """
            CREATE TABLE CLM.POLICY (POLICY_NO CHAR(10) NOT NULL, PRIMARY KEY (POLICY_NO));
            COMMENT ON TABLE CLM.POLICY IS 'One row per policy';
            COMMENT ON COLUMN CLM.POLICY.POLICY_NO IS 'The policy number';
            LABEL ON COLUMN CLM.POLICY.POLICY_NO IS 'Policy';
            COMMENT ON COLUMN POLICY.POLICY_NO IS 'Unqualified';
            """);

        assertThat(ddl.getStatements()).extracting(s -> s.getClass().getSimpleName())
          .containsExactly("CreateTable", "Comment", "Comment", "Label", "Comment");

        Db2.Comment onColumn = (Db2.Comment) ddl.getStatements().get(2);
        assertThat(Db2.has(onColumn.getKeywords(), Db2.Keyword.Type.Column)).isTrue();
        assertThat(onColumn.getTarget().getSimpleName()).isEqualTo("POLICY_NO");
        assertThat(onColumn.getTarget().getParts()).extracting(Db2.Word::getText)
          .containsExactly("CLM", ".", "POLICY", ".", "POLICY_NO");
        assertThat(onColumn.getText().getElement().getText()).isEqualTo("'The policy number'");

        Db2.Label label = (Db2.Label) ddl.getStatements().get(3);
        assertThat(Db2.has(label.getKeywords(), Db2.Keyword.Type.Column)).isTrue();
        assertThat(label.getTarget().getSimpleName()).isEqualTo("POLICY_NO");

        Db2.Comment unqualified = (Db2.Comment) ddl.getStatements().get(4);
        assertThat(unqualified.getTarget().getQualifier()).isEqualTo("POLICY");
        assertThat(unqualified.getTarget().getSimpleName()).isEqualTo("POLICY_NO");
    }

    /**
     * A variable's default and a distinct type's comparisons are options of their statement. Reading
     * either used to fail the visit, which turned every table in the file into a parse error with it.
     */
    @Test
    void aVariableDefaultAndATypeComparisonAreOptions() {
        Db2.Ddl ddl = parse(
          """
            CREATE VARIABLE CLM.BATCH_FLAG CHAR(1) DEFAULT 'N';
            CREATE VARIABLE CLM.RUN_NO INTEGER DEFAULT 0;
            CREATE DISTINCT TYPE CLM.MONEY AS DECIMAL(11,2) WITH COMPARISONS;
            """);

        Db2.CreateVariable runNo = (Db2.CreateVariable) ddl.getStatements().get(1);
        assertThat(runNo.getOptions()).singleElement().isInstanceOfSatisfying(Db2.Option.class, option ->
          assertThat(option.getKeywords()).extracting(Db2.Keyword::getType).containsExactly(Db2.Keyword.Type.Default));
        Db2.CreateType money = (Db2.CreateType) ddl.getStatements().get(2);
        assertThat(money.getOptions()).singleElement().isInstanceOfSatisfying(Db2.Option.class, option ->
          assertThat(option.getKeywords()).extracting(Db2.Keyword::getType)
            .containsExactly(Db2.Keyword.Type.With, Db2.Keyword.Type.Comparisons));
    }

    @Test
    void aCaseExpressionsEndDoesNotCloseTheBlock() {
        Db2.Ddl ddl = parse(
          """
            CREATE PROCEDURE CLM.RATE (INOUT X INT) LANGUAGE SQL
            BEGIN
              SET X = CASE WHEN X = 1 THEN 2 ELSE 3 END;
              SET X = CASE X WHEN 1 THEN CASE WHEN X > 0 THEN 1 END END;
              CASE X
                WHEN 1 THEN SET X = CASE WHEN X = 1 THEN 2 END;
                ELSE SET X = 0;
              END CASE;
              RETURN X;
            END;
            """);

        assertThat(ddl.getStatements()).hasSize(1);
        Db2.CreateProcedure procedure = (Db2.CreateProcedure) ddl.getStatements().get(0);
        assertThat(procedure.getBody().getEnd()).isNotNull();
        assertThat(procedure.getBody().getBody()).extracting(word -> ((Db2.Word) word).getText())
          .endsWith("END", "CASE", ";", "RETURN", "X", ";");
    }

    /**
     * Every statement DB2 documents is a node of its own. None of these says anything the
     * relationship graph joins on, and all of them are still read as what they are rather than
     * demoted to something that says nothing.
     */
    @Test
    void everyStatementIsItsOwnNode() {
        Db2.Ddl ddl = parse(
          """
            SET CURRENT SQLID = 'SYSADM';
            CREATE DATABASE CARDDEMO
                   STOGROUP AWST1STG
                   BUFFERPOOL BP0
                   CCSID EBCDIC;
            COMMIT ;
            CREATE TABLESPACE CARDSPC1
              IN CARDDEMO
              SEGSIZE 4
              LOCKSIZE TABLE;
            GRANT DELETE, INSERT, SELECT, UPDATE
                  ON TABLE CARDDEMO.TRANSACTION_TYPE
                  TO PUBLIC;
            """);

        assertThat(ddl.getStatements()).hasSize(5);
        assertThat(ddl.getStatements()).extracting(s -> s.getClass().getSimpleName())
          .containsExactly("Set", "CreateDatabase", "Commit", "CreateTablespace", "Grant");

        Db2.CreateTablespace tablespace = (Db2.CreateTablespace) ddl.getStatements().get(3);
        assertThat(tablespace.getName().getSimpleName()).isEqualTo("CARDSPC1");
        assertThat(tablespace.getDatabase()).isNotNull();
        assertThat(tablespace.getDatabase().getSimpleName()).isEqualTo("CARDDEMO");

        Db2.Grant grant = (Db2.Grant) ddl.getStatements().get(4);
        assertThat(grant.getObjects().getElements()).extracting(Db2.Name::getFullName)
          .containsExactly("CARDDEMO.TRANSACTION_TYPE");
    }

    /**
     * {@code CREATE TABLESPACE} begins with the two words a {@code CREATE TABLE} does. The lexer
     * takes the longest match, so TABLESPACE is one word and never half of one.
     */
    @Test
    void createTablespaceIsNotCreateTable() {
        Db2.Ddl ddl = parse("CREATE TABLESPACE ACCOUNT IN BANKZ ;\n");
        assertThat(ddl.getStatements().get(0)).isInstanceOf(Db2.CreateTablespace.class);
    }

    /**
     * A table defined by another table, and one defined by a query. Neither has a column list to
     * read, so the shape of the contents is what says where the columns came from.
     */
    @Test
    void aTableNeedNotListItsColumns() {
        Db2.Ddl like = parse("CREATE TABLE CARDDEMO.ARCHIVE LIKE CARDDEMO.ACCOUNT;\n");
        Db2.TableLike contents = (Db2.TableLike) ((Db2.CreateTable) like.getStatements().get(0)).getContents();
        assertThat(contents.getTable().getFullName()).isEqualTo("CARDDEMO.ACCOUNT");

        Db2.Ddl asQuery = parse(
          """
            CREATE TABLE CARDDEMO.ACCOUNT_SUMMARY AS
              (SELECT ACCT_ID, ACCT_STATUS FROM CARDDEMO.ACCOUNT) WITH NO DATA;
            """);
        assertThat(((Db2.CreateTable) asQuery.getStatements().get(0)).getContents())
          .isInstanceOf(Db2.TableAsQuery.class);
    }

    @Test
    void theTablespaceATableIsCreatedIn() {
        Db2.Ddl ddl = parse(
          """
            CREATE TABLE  CARDDEMO.TRANSACTION_TYPE
             (       TR_TYPE   CHAR(2)      NOT NULL,
                       PRIMARY KEY(TR_TYPE))
               IN CARDDEMO.CARDSPC1
               CCSID EBCDIC;
            """);

        Db2.CreateTable table = (Db2.CreateTable) ddl.getStatements().get(0);
        Db2.Option in = Db2.elementsOf(table.getOptions(), Db2.Option.class).get(0);
        assertThat(Db2.has(in.getKeywords(), Db2.Keyword.Type.In)).isTrue();
        assertThat(Db2.elementsOf(in.getValues(), Db2.Name.class).get(0).getFullName())
          .isEqualTo("CARDDEMO.CARDSPC1");
    }

    /**
     * genapp ships its DDL as a template. A placeholder stands where a schema belongs, and lexing it
     * as anything but a name would leave the whole statement unread.
     */
    @Test
    void aTemplatedSchemaIsStillAName() {
        Db2.Ddl ddl = parse(
          """
            CREATE TABLE <DB2DBID>.house (
                 policyNumber   INTEGER NOT NULL,
                 postcode       CHAR(8),
               PRIMARY KEY(policyNumber),
               FOREIGN KEY(policyNumber)
                      REFERENCES <DB2DBID>.policy (policyNumber) ON DELETE CASCADE)
               CCSID EBCDIC
               IN <DB2DBID>.GENATS04;
            """);

        Db2.CreateTable table = (Db2.CreateTable) ddl.getStatements().get(0);
        assertThat(table.getName().getQualifier()).isEqualTo("<DB2DBID>");
        assertThat(table.getName().getSimpleName()).isEqualTo("house");
        // The DDL writes the table in lower case and the COBOL that reads it in upper.
        assertThat(table.getName().getFullName()).isEqualTo("<DB2DBID>.HOUSE");
        assertThat(columns(table)).hasSize(2);
        Db2.Name referenced = constraints(table).get(1).getReferencedTable();
        assertThat(referenced).isNotNull();
        assertThat(referenced.getSimpleName()).isEqualTo("policy");
    }

    /**
     * GenevaERS writes its schema as a JCL symbolic, so the same placeholder can arrive with an
     * ampersand rather than in angle brackets.
     */
    @Test
    void aJclSymbolicIsAlsoAName() {
        Db2.Ddl ddl = parse("CREATE TABLE &$DBSCH..LOOKUP (ENVIRONID INTEGER NOT NULL);\n");
        Db2.CreateTable table = (Db2.CreateTable) ddl.getStatements().get(0);
        assertThat(table.getName().getQualifier()).isEqualTo("&$DBSCH.");
        assertThat(table.getName().getSimpleName()).isEqualTo("LOOKUP");
    }

    /**
     * A comment is not a node. It arrives in the prefix of whatever follows it, which is the only
     * reason a file of copyright banners prints back unchanged.
     */
    @Test
    void commentsRideInTheWhitespace() {
        String source =
          """
            /* Copyright Amazon.com, Inc. or its affiliates.                   */
            /* All Rights Reserved.                                            */
            -- the transaction type reference table
            CREATE TABLE CARDDEMO.TRANSACTION_TYPE
            (   TR_TYPE  CHAR(2) NOT NULL,  -- a two character code
                PRIMARY KEY(TR_TYPE));
            """;

        Db2.Ddl ddl = parse(source);
        assertThat(ddl.getStatements()).hasSize(1);
        assertThat(ddl.printAll()).isEqualTo(source);
    }

    /**
     * The contract that matters most: what this grammar cannot read is a syntax error, never a node
     * that says nothing. There is no catch-all to fall back on, so a schema can never be silently
     * missing a table.
     */
    @Test
    void whatTheGrammarCannotReadIsAnError() {
        // Neither is DDL. A DML UPDATE or DELETE changes rows, which is a program's business.
        assertThat(errorsIn("UPDATE CARDDEMO.ACCOUNT SET ACCT_STATUS = 'N';\n")).isNotEmpty();
        assertThat(errorsIn("DELETE FROM CARDDEMO.ACCOUNT WHERE ACCT_ID = 1;\n")).isNotEmpty();
        assertThat(errorsIn("CREATE TABLE CARDDEMO.ACCOUNT (ACCT_ID INTEGER;\n")).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {
      "CREATE TABLE T(COMMENT VARCHAR(10));\n",
      "CREATE TABLE T(DATA VARCHAR(10));\n",
      "CREATE TABLE T(CONSTRAINT VARCHAR(10));\n",
      "ALTER TABLE T ADD COLUMN DATA VARCHAR(10);\n",
      "CREATE TABLE T(ID);\n"})
    void aTableElementThatCannotBeReadDoesNotFailTheWholeFile(String source) {
        SourceFile parsed = parseIgnoringErrors(source);

        assertThat(parsed).isInstanceOf(Db2.Ddl.class);
        assertThat(parsed.getMarkers().findFirst(ParseExceptionResult.class)).isEmpty();
        assertThat(errorsIn(source)).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {
      "CREATE TABLE T(COMMENT VARCHAR(10));\n",
      "CREATE TABLE T(DATA VARCHAR(10));\n",
      "CREATE TABLE T(ID);\n"})
    void anUnreadableColumnIsKeptAsLooseWords(String source) {
        Db2.Ddl ddl = (Db2.Ddl) parseIgnoringErrors(source);
        assertThat(ddl.printAll()).isEqualTo(source);

        Db2.CreateTable table = (Db2.CreateTable) ddl.getStatements().get(0);
        assertThat(elements(table)).hasSize(1);
        assertThat(columns(table)).isEmpty();
    }

    /**
     * A statement that cannot be assembled at all fails in a way that says nothing, so the file is
     * reported as the syntax error that led to it rather than as the failure itself.
     */
    @ParameterizedTest
    @ValueSource(strings = {
      "DROP WIDGET CARDDEMO.ACCOUNT;\n",
      "GRANT EXEC ON CARDDEMO.ACCOUNT TO PUBLIC\n"})
    void aStatementThatCannotBeAssembledIsReportedAsItsSyntaxError(String source) {
        SourceFile parsed = parseIgnoringErrors(source);

        assertThat(parsed).isInstanceOf(ParseError.class);
        assertThat(((ParseError) parsed).getText()).isEqualTo(source);
        assertThat(parsed.getMarkers().findFirst(ParseExceptionResult.class))
          .get().extracting(ParseExceptionResult::getMessage, as(STRING))
          .contains("Db2ParsingException").contains("Syntax error in");
        assertThat(errorsIn(source)).isNotEmpty();
    }

    /**
     * A member a build cannot read is still taken, since typing a file happens outside the one
     * place that can report a failure against the file it came from.
     */
    @Test
    void aFileThatCannotBeReadIsStillTaken(@TempDir Path directory) throws IOException {
        Path unreadable = directory.resolve("unreadable.sql");
        Files.write(unreadable, "CREATE TABLE CARDDEMO.CARD (ID INTEGER NOT NULL);\n".getBytes(StandardCharsets.UTF_8));
        assumeTrue(unreadable.toFile().setReadable(false), "the file system kept the file readable");

        try {
            assertThat(Db2Parser.builder().build().accept(unreadable)).isTrue();
        } finally {
            unreadable.toFile().setReadable(true);
        }
    }

    /**
     * A {@code .sql} file is as likely to be another vendor's SQL as it is DDL, so one that is goes
     * unclaimed and reaches the build step that keeps a file as text, where a DDL file is taken.
     */
    @Test
    void aFileOfAnotherDialectIsNotTakenForDdl(@TempDir Path directory) throws IOException {
        Path sybase = directory.resolve("AccountReport.sql");
        Files.write(sybase, "drop proc dbo.usp_AccountReport\ngo\n".getBytes(StandardCharsets.UTF_8));
        Path db2 = directory.resolve("account.sql");
        Files.write(db2, "CREATE TABLE CARDDEMO.ACCOUNT (ID INTEGER NOT NULL);\n".getBytes(StandardCharsets.UTF_8));

        Db2Parser parser = Db2Parser.builder().build();
        assertThat(parser.accept(sybase)).isFalse();
        assertThat(parser.accept(db2)).isTrue();
        assertThat(parser.parse(singletonList(sybase), directory, new InMemoryExecutionContext(t -> {
        })).collect(Collectors.toList())).isEmpty();
    }

    /**
     * A character the lexer has no token for is reported like any other syntax error. A console
     * write per character fills a build log when a {@code .sql} file is in another dialect.
     */
    @Test
    void aCharacterWithNoTokenIsAnErrorAndNotConsoleOutput() {
        PrintStream stderr = System.err;
        ByteArrayOutputStream console = new ByteArrayOutputStream();
        List<String> errors;
        try {
            System.setErr(new PrintStream(console, true, StandardCharsets.UTF_8));
            errors = errorsIn("CREATE TABLESPACE ACCOUNT IN BANKZ;\n\\\n\\\n");
        } finally {
            System.setErr(stderr);
        }

        assertThat(console.toString(StandardCharsets.UTF_8)).doesNotContain("token recognition error");
        assertThat(errors).anySatisfy(e -> assertThat(e).contains("line 2:0 token recognition error at: '\\'"));
    }

    @Nested
    class RangePartition {

        @Test
        void aPartitionNeedNotBeNumbered() {
            Db2.Ddl ddl = parse(
              """
                CREATE TABLE SUT.ARCHIVED_EXTENDED_ALLOCATION_LINE_ITEM (
                    ALLOCATION_LINE_ITEM_ID     BIGINT      NOT NULL,
                    POSTING_YEAR                INTEGER     NOT NULL,
                    PRIMARY KEY(ALLOCATION_LINE_ITEM_ID)
                ) PARTITION BY RANGE (POSTING_YEAR) (
                       STARTING FROM (2020) ENDING AT (2099)
                );
                """);

            Db2.CreateTable table = (Db2.CreateTable) ddl.getStatements().get(0);
            assertThat(table.getName().getFullName())
              .isEqualTo("SUT.ARCHIVED_EXTENDED_ALLOCATION_LINE_ITEM");
            assertThat(columns(table)).extracting(c -> c.getName().getSimpleName())
              .containsExactly("ALLOCATION_LINE_ITEM_ID", "POSTING_YEAR");

            Db2.Option partitioning = partitioningOf(ddl);
            assertThat(partitioning.getKeywords()).extracting(Db2.Keyword::getType)
              .containsExactly(Db2.Keyword.Type.Partition, Db2.Keyword.Type.By, Db2.Keyword.Type.Range);
            assertThat(keyOf(partitioning)).containsExactly("POSTING_YEAR");
            assertThat(wordsOf(partitioning))
              .containsSequence("STARTING", "FROM", "(", "2020", ")", "ENDING", "AT", "(", "2099", ")");
        }

        /**
         * The form DB2 for z/OS writes.
         */
        @Test
        void aNumberedPartitionEndsAtItsLimit() {
            Db2.Option partitioning = partitioningOf(parse(
              """
                CREATE TABLE SUT.ALLOCATION (POSTING_YEAR INTEGER NOT NULL)
                  PARTITION BY RANGE (POSTING_YEAR)
                  (PARTITION 1 ENDING AT (2019) INCLUSIVE);
                """));

            assertThat(wordsOf(partitioning))
              .containsSequence("PARTITION", "1", "ENDING", "AT", "(", "2019", ")", "INCLUSIVE");
        }

        @Test
        void aRangeLeftOpenRepeatsEveryInterval() {
            Db2.Option partitioning = partitioningOf(parse(
              """
                CREATE TABLE SUT.ALLOCATION (POSTING_YEAR INTEGER NOT NULL)
                  PARTITION BY RANGE (POSTING_YEAR)
                  (STARTING FROM (2020) EXCLUSIVE ENDING AT (MAXVALUE) EVERY (1));
                """));

            assertThat(wordsOf(partitioning))
              .containsSequence("STARTING", "FROM", "(", "2020", ")", "EXCLUSIVE")
              .containsSequence("ENDING", "AT", "(", "MAXVALUE", ")", "EVERY", "(", "1", ")");
        }

        @Test
        void aKeyOfSeveralColumnsBoundsEachInKeyOrder() {
            Db2.Option partitioning = partitioningOf(parse(
              """
                CREATE TABLE SUT.LEDGER (
                    POSTING_YEAR  INTEGER NOT NULL,
                    POSTING_MONTH INTEGER NOT NULL
                ) PARTITION BY RANGE (POSTING_YEAR, POSTING_MONTH)
                  (PARTITION 1 ENDING AT (2019, 12),
                   PARTITION 2 ENDING AT (MAXVALUE, MAXVALUE));
                """));

            assertThat(keyOf(partitioning)).containsExactly("POSTING_YEAR", "POSTING_MONTH");
            assertThat(wordsOf(partitioning))
              .containsSequence("PARTITION", "1", "ENDING", "AT", "(", "2019", ",", "12", ")")
              .containsSequence("PARTITION", "2", "ENDING", "AT", "(", "MAXVALUE", ",", "MAXVALUE", ")");
        }

        private Db2.Option partitioningOf(Db2.Ddl ddl) {
            Db2.CreateTable table = (Db2.CreateTable) ddl.getStatements().get(0);
            assertThat(table.getOptions()).hasSize(1);
            return (Db2.Option) table.getOptions().get(0);
        }

        private List<String> keyOf(Db2.Option partitioning) {
            return Db2.elementsOf(partitioning.getValues(), Db2.Name.class).stream()
              .map(Db2.Name::getFullName)
              .collect(Collectors.toList());
        }

        private List<String> wordsOf(Db2.Option partitioning) {
            return Db2.elementsOf(partitioning.getValues(), Db2.Word.class).stream()
              .map(Db2.Word::getText)
              .collect(Collectors.toList());
        }
    }

    /**
     * A catalog query is what the .sql files beside a schema usually hold, and the names it reads
     * are what a lineage graph joins on.
     */
    @Test
    void aQueryIsAStatementOfItsOwn() {
        Db2.Ddl ddl = parse(
          """
            SELECT TBNAME, COLNO, NAME, COLTYPE, NULLS
              FROM SYSIBM.SYSCOLUMNS
             WHERE TBCREATOR = 'CLM'
             ORDER BY TBNAME, COLNO;
            """);

        Db2.Query query = (Db2.Query) ddl.getStatements().get(0);
        assertThat(Db2.elementsOf(query.getParts(), Db2.Name.class))
          .extracting(Db2.Name::getFullName).contains("SYSIBM.SYSCOLUMNS");
    }

    /**
     * Whether a statement was terminated is a marker on its padding rather than a node, so a file
     * that ends without a semicolon prints back without one.
     */
    @Test
    void theSemicolonIsAMarkerNotANode() {
        Db2.Ddl terminated = parse("COMMIT;\n");
        assertThat(terminated.getPadding().getStatements().get(0).getMarkers()
          .findFirst(Semicolon.class)).isPresent();

        Db2.Ddl bare = parse("COMMIT\n");
        assertThat(bare.getPadding().getStatements().get(0).getMarkers()
          .findFirst(Semicolon.class)).isEmpty();
    }

    @Test
    void aStraySemicolonIsAnEmptyStatement() {
        Db2.Ddl ddl = parse(";\nCOMMENT ON TABLE T IS 'x';;\n  ;\nCOMMENT ON TABLE U IS 'y';\n");

        assertThat(ddl.getStatements()).extracting(s -> s.getClass().getSimpleName())
          .containsExactly("Empty", "Comment", "Empty", "Empty", "Comment");
        assertThat(ddl.getPadding().getStatements())
          .allSatisfy(s -> assertThat(s.getMarkers().findFirst(Semicolon.class)).isPresent());
    }

    /**
     * A statement written without its terminating semicolon must not swallow the one after it.
     */
    @Test
    void aMissingSemicolonDoesNotRunTwoStatementsTogether() {
        Db2.Ddl ddl = parse(
          """
            COMMIT
            CREATE TABLE BANKZ.CONTROL (
                                CONTROL_NAME                   CHAR(32)
                               )
            """);

        assertThat(ddl.getStatements()).hasSize(2);
        assertThat(ddl.getStatements().get(0)).isInstanceOf(Db2.Commit.class);
        assertThat(ddl.getStatements().get(1)).isInstanceOf(Db2.CreateTable.class);
    }

    /**
     * A check constraint's expression is kept as words rather than modelled, but it is full of the
     * commas and parentheses that separate one table element from the next, and being read as
     * several would lose the columns after it.
     */
    @Test
    void aCheckConstraintIsKeptWhole() {
        Db2.Ddl ddl = parse(
          """
            CREATE TABLE CARDDEMO.ACCOUNT
            (   ACCT_ID     DECIMAL(11) NOT NULL,
                ACCT_STATUS CHAR(1) NOT NULL,
                CONSTRAINT STATUS_OK CHECK (ACCT_STATUS IN ('Y', 'N')),
                ACCT_GROUP  CHAR(10),
                PRIMARY KEY(ACCT_ID));
            """);

        Db2.CreateTable table = (Db2.CreateTable) ddl.getStatements().get(0);
        assertThat(columns(table)).extracting(c -> c.getName().getSimpleName())
          .containsExactly("ACCT_ID", "ACCT_STATUS", "ACCT_GROUP");
        assertThat(constraints(table)).extracting(c -> Db2.subjectOf(c.getKeywords()))
          .containsExactly(null, Db2.Keyword.Type.Key);

        Db2.Constraint check = constraints(table).get(0);
        assertThat(check.getConstraintName()).isNotNull();
        assertThat(check.getConstraintName().getSimpleName()).isEqualTo("STATUS_OK");
        assertThat(Db2.has(check.getKeywords(), Db2.Keyword.Type.Check)).isTrue();
    }

    /**
     * {@code CREATE UNIQUE WHERE NOT NULL INDEX} is one index, not a syntax error with an index in
     * it.
     */
    @Test
    void theFourWordUniqueIndex() {
        Db2.Ddl ddl = parse(
          """
            CREATE UNIQUE WHERE NOT NULL INDEX CARDDEMO.XACCTALT
                ON CARDDEMO.ACCOUNT (ACCT_GROUP);
            """);

        Db2.CreateIndex index = (Db2.CreateIndex) ddl.getStatements().get(0);
        assertThat(Db2.has(index.getKeywords(), Db2.Keyword.Type.Unique)).isTrue();
        assertThat(index.getTable().getFullName()).isEqualTo("CARDDEMO.ACCOUNT");
    }

    /**
     * A delimited name keeps its case and its spaces in DB2, so the quotes are punctuation rather
     * than part of what the column is called.
     */
    @Test
    void aDelimitedName() {
        Db2.Ddl ddl = parse(
          """
            CREATE TABLE "My Schema"."Account" (
                "Account Id" INTEGER NOT NULL,
                PRIMARY KEY("Account Id"));
            """);

        Db2.CreateTable table = (Db2.CreateTable) ddl.getStatements().get(0);
        assertThat(table.getName().getSimpleName()).isEqualTo("Account");
        assertThat(table.getName().getQualifier()).isEqualTo("My Schema");
        assertThat(columns(table).get(0).getName().getSimpleName()).isEqualTo("Account Id");
    }

    /**
     * DB2 DDL is case insensitive and the corpus is not consistent about it. A keyword matches on
     * its type whichever case the file used, and prints back in the case the file used.
     */
    @Test
    void aKeywordKeepsTheCaseTheFileWroteIt() {
        Db2.Ddl ddl = parse("create table bankz.control (control_name char(32));\n");
        Db2.CreateTable table = (Db2.CreateTable) ddl.getStatements().get(0);
        assertThat(table.getName().getFullName()).isEqualTo("BANKZ.CONTROL");
        assertThat(columns(table)).hasSize(1);
        assertThat(table.getKeywords()).extracting(Db2.Keyword::getType)
          .containsExactly(Db2.Keyword.Type.Create, Db2.Keyword.Type.Table);
        assertThat(table.getKeywords()).extracting(Db2.Keyword::getText)
          .containsExactly("create", "table");
    }

    /**
     * The space in front of a keyword belongs to the keyword, so DDL that lines its words up prints
     * back lined up.
     */
    @Test
    void theSpaceBetweenTwoKeywordsSurvives() {
        parse("CREATE   TABLESPACE   GENATS04   IN   <DB2DBID>;\n");
    }

    /**
     * A word the lexer learns without a keyword type to match reads as a plain word, and nothing
     * else would notice.
     */
    @Test
    void everyWordTheLexerKnowsIsAKeywordType() {
        Set<String> types = Arrays.stream(Db2.Keyword.Type.values())
          .map(type -> type.name().toUpperCase(Locale.ROOT))
          .collect(Collectors.toSet());
        List<String> words = new ArrayList<>();
        for (int token = 1; token <= DB2Parser.VOCABULARY.getMaxTokenType(); token++) {
            String literal = DB2Parser.VOCABULARY.getLiteralName(token);
            if (literal != null && literal.matches("'[A-Z_]+'")) {
                words.add(DB2Parser.VOCABULARY.getSymbolicName(token));
            }
        }

        assertThat(words).isNotEmpty()
          .allSatisfy(word -> assertThat(types).contains(word.replace("_", "")));
    }

    private static List<Db2> elements(Db2.CreateTable table) {
        return ((Db2.TableElements) table.getContents()).getElements().getElements();
    }

    private static List<Db2.ColumnDefinition> columns(Db2.CreateTable table) {
        return Db2.elementsOf(elements(table), Db2.ColumnDefinition.class);
    }

    private static List<Db2.Constraint> constraints(Db2.CreateTable table) {
        return Db2.elementsOf(elements(table), Db2.Constraint.class);
    }

    private static boolean isNotNull(Db2.ColumnDefinition column) {
        for (Db2.Option option : Db2.elementsOf(column.getAttributes(), Db2.Option.class)) {
            if (Db2.has(option.getKeywords(), Db2.Keyword.Type.Not)
                && Db2.has(option.getKeywords(), Db2.Keyword.Type.Null)) {
                return true;
            }
        }
        return false;
    }

    private static List<String> names(Db2Container<Db2.Name> columns) {
        assertThat(columns).isNotNull();
        return columns.getElements().stream()
          .map(Db2.Name::getSimpleName)
          .collect(Collectors.toList());
    }

    private static List<String> errorsIn(String source) {
        List<String> errors = new ArrayList<>();
        Db2Parser.builder().build()
          .parse(new InMemoryExecutionContext(t -> errors.add(String.valueOf(t.getMessage()))), source)
          .forEach(cu -> {
          });
        return errors;
    }

    private static SourceFile parseIgnoringErrors(String source) {
        return Db2Parser.builder().build()
          .parse(new InMemoryExecutionContext(t -> {
          }), source)
          .findFirst().orElseThrow();
    }

    private static Db2.Ddl parse(String source) {
        List<SourceFile> parsed = Db2Parser.builder().build()
          .parse(new InMemoryExecutionContext(t -> {
              throw new IllegalStateException(t);
          }), source)
          .collect(Collectors.toList());
        assertThat(parsed).hasSize(1);
        assertThat(parsed.get(0)).isInstanceOf(Db2.Ddl.class);
        Db2.Ddl ddl = (Db2.Ddl) parsed.get(0);
        assertThat(ddl.printAll()).as("prints back byte for byte").isEqualTo(source);
        return ddl;
    }
}
