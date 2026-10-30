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

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The unwrapping, against the text of real statements rather than through a COBOL program, so a
 * failure says which clause broke rather than which program.
 */
class EmbeddedSqlTest {

    /**
     * Bank of Z's INQCUST, the widest singleton select in the corpus.
     */
    @Test
    void readsASeventeenColumnSelectInto() {
        EmbeddedSql read = EmbeddedSql.read(
          "SELECT CUSTOMER_EYECATCHER, CUSTOMER_SORTCODE, CUSTOMER_NUMBER, CUSTOMER_TITLE " +
          "INTO :HV-CUSTOMER-EYECATCHER, :HV-CUSTOMER-SORTCODE, :HV-CUSTOMER-NUMBER, :HV-CUSTOMER-TITLE " +
          "FROM CUSTOMER " +
          "WHERE CUSTOMER_SORTCODE = :HV-CUSTOMER-SORTCODE AND CUSTOMER_NUMBER = :HV-CUSTOMER-NUMBER");

        assertThat(read.getColumns()).containsExactly(
          "CUSTOMER_EYECATCHER", "CUSTOMER_SORTCODE", "CUSTOMER_NUMBER", "CUSTOMER_TITLE");
        assertThat(read.getHostVariables()).containsExactly(
          "HV-CUSTOMER-EYECATCHER", "HV-CUSTOMER-SORTCODE", "HV-CUSTOMER-NUMBER", "HV-CUSTOMER-TITLE");
    }

    /**
     * CardDemo qualifies its tables with a schema. Read off the COBOL words the name comes back as
     * CARDDEMO.TRANSACTION, because SQL's one name is more than one COBOL word — which is a dataset
     * a catalog would file separately from the table that actually exists.
     */
    @Test
    void keepsAQualifiedTableWhole() {
        assertThat(EmbeddedSql.read(
          "SELECT TR_TYPE, TR_DESCRIPTION FROM CARDDEMO.TRANSACTION_TYPE WHERE TR_TYPE >= :WS-START-KEY")
          .getTable()).isEqualTo("CARDDEMO.TRANSACTION_TYPE");
    }

    /**
     * The table a statement writes, named as its tables name it, so the one can be found among the
     * others. A query writes none.
     */
    @Test
    void namesTheTableAStatementWrites() {
        assertThat(EmbeddedSql.read("INSERT INTO clm.claim_arch SELECT * FROM CLM.CLAIM_HIST").getTarget())
          .isEqualTo("CLM.CLAIM_ARCH")
          .isIn(EmbeddedSql.read("INSERT INTO clm.claim_arch SELECT * FROM CLM.CLAIM_HIST").getTables());
        assertThat(EmbeddedSql.read("UPDATE CLM.POLICY SET STATUS = 'C' WHERE POLICY_NO = :WS-NO").getTarget())
          .isEqualTo("CLM.POLICY");
        assertThat(EmbeddedSql.read("DELETE FROM CLM.CLAIM_HIST WHERE CLOSED < :WS-CUTOFF").getTarget())
          .isEqualTo("CLM.CLAIM_HIST");
        assertThat(EmbeddedSql.read(
          "MERGE INTO CLM.POLICY P USING CLM.POLICY_STAGE S ON P.POLICY_NO = S.POLICY_NO " +
          "WHEN MATCHED THEN UPDATE SET P.STATUS = S.STATUS").getTarget())
          .isEqualTo("CLM.POLICY");
        assertThat(EmbeddedSql.read("SELECT POLICY_NO INTO :WS-NO FROM CLM.POLICY").getTarget()).isNull();
        assertThat(EmbeddedSql.read("DECLARE C1 CURSOR FOR SELECT POLICY_NO FROM CLM.POLICY").getTarget()).isNull();
    }

    @Test
    void readsAnInsertWhoseColumnsAQueryFills() {
        EmbeddedSql read = EmbeddedSql.read("INSERT INTO CLM.LOG (ID, LOGGED) SELECT ID, :WS-NOW FROM CLM.CLAIM");
        assertThat(read.getColumns()).containsExactly("ID", "LOGGED");
        // A query fills the listed columns by position, as VALUES does.
        assertThat(read.getHostVariables()).containsExactly("", "WS-NOW");

        assertThat(EmbeddedSql.read("INSERT INTO CLM.LOG (ID, LOGGED) (SELECT ID, :WS-NOW FROM CLM.CLAIM)")
          .getHostVariables()).containsExactly("", "WS-NOW");

        assertThat(EmbeddedSql.read(
          "INSERT INTO CLM.LOG (ID) WITH T AS (SELECT ID FROM CLM.CLAIM) SELECT ID FROM T").getTables())
          .contains("CLM.CLAIM", "CLM.LOG");
    }

    @Test
    void readsAQueryThatIsNotAPlainSelect() {
        assertThat(EmbeddedSql.read(
          "DECLARE C1 CURSOR FOR SELECT ID, STATUS FROM CLM.CLAIM UNION ALL SELECT CLAIM_ID, STATE FROM CLM.CLAIM_HIST")
          .getColumns()).containsExactly("ID", "STATUS");

        assertThat(EmbeddedSql.read("VALUES CURRENT TIMESTAMP INTO :WS-NOW").getHostVariables())
          .containsExactly("WS-NOW");
    }

    /**
     * Every statement is parsed on one pool of daemon threads rather than on an executor built for
     * it, so reading an estate neither starts a thread a statement nor holds the JVM open.
     */
    @Test
    void parsesOnOneSharedPoolOfDaemonThreads() {
        for (int i = 0; i < 50; i++) {
            EmbeddedSql.read("SELECT POLICY_NO INTO :WS-NO FROM CLM.POLICY WHERE POLICY_NO = " + i);
        }
        List<Thread> parsers = Thread.getAllStackTraces().keySet().stream()
          .filter(thread -> "rewrite-mainframe-embedded-sql".equals(thread.getName()))
          .collect(toList());
        assertThat(parsers).isNotEmpty().hasSizeLessThan(50).allMatch(Thread::isDaemon);
    }
}
