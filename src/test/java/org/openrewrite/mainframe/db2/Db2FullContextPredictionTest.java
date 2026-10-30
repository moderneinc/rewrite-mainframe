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

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import org.openrewrite.mainframe.FullContextPredictions;
import org.openrewrite.mainframe.db2.internal.grammar.DB2Lexer;
import org.openrewrite.mainframe.db2.internal.grammar.DB2Parser;

import static org.assertj.core.api.Assertions.assertThat;

class Db2FullContextPredictionTest {

    @Test
    void compoundStatementEndsAtItsOwnEnd() {
        String ddl = """
          CREATE PROCEDURE CLM.NEXT_POLICY (INOUT X INT)
            LANGUAGE SQL
          BEGIN
            IF X = 1 THEN
              WHILE X < 10 DO
                SET X = X + 1;
              END WHILE;
            END IF;
            BEGIN
              SET X = 2;
            END;
            RETURN X;
          END;
          """;
        assertThat(fallbacks(ddl, "compoundStatement", "bodyItem")).isZero();
    }

    @Test
    void aCaseExpressionsEndIsNotTheBlocks() {
        String ddl = """
          CREATE PROCEDURE CLM.RATE (INOUT X INT)
            LANGUAGE SQL
          BEGIN
            SET X = CASE WHEN X = 1 THEN 2 ELSE 3 END;
            CASE X
              WHEN 1 THEN SET X = CASE X WHEN 1 THEN CASE WHEN X > 0 THEN 1 END ELSE 0 END;
              ELSE SET X = 0;
            END CASE;
            RETURN X;
          END;
          """;
        assertThat(fallbacks(ddl, "compoundStatement", "bodyItem")).isZero();
    }

    @Test
    void anAtSignAfterABlockEndsTheStatement() {
        String ddl = """
          --#SET TERMINATOR @
          CREATE PROCEDURE CLM.OPEN_CLAIM (INOUT X INT)
          BEGIN
            SET X = 1;
          END @
          CREATE PROCEDURE CLM.CLOSE_CLAIM (INOUT X INT)
          BEGIN
            IF X = 1 THEN
              IF X = 2 THEN
                SET X = 3;
              END IF;
            END IF;
          END @
          """;
        assertThat(fallbacks(ddl, "compoundStatement", "bodyItem", "end")).isZero();
    }

    @Test
    void qualifiedNamesColumnTargetsAndIndexKeys() {
        String ddl = """
          CREATE TABLE CLM.POLICY (POLICY_NO CHAR(10) NOT NULL);
          COMMENT ON COLUMN CLM.POLICY.POLICY_NO IS 'The policy number';
          LABEL ON COLUMN POLICY.POLICY_NO IS 'Policy';
          CREATE VIEW CLM.V AS SELECT P.*, P.POLICY_NO FROM CLM.POLICY P;
          CREATE INDEX CLM.IX ON CLM.POLICY (POLICY_NO ASC, UPPER(POLICY_NO));
          """;
        assertThat(fallbacks(ddl, "qualifiedName", "indexKey")).isZero();
    }

    @Test
    void optionValuesDefaultsAndSpecialRegisters() {
        String ddl = """
          CREATE TABLE CLM.CLAIM (
            CLAIM_NO CHAR(10) NOT NULL,
            NOTE VARCHAR(80) DEFAULT NULL,
            OWNER CHAR(8) WITH DEFAULT CURRENT SQLID,
            STAMP TIMESTAMP NOT NULL WITH DEFAULT);
          CREATE TABLESPACE CLMTS IN CLMDB USING STOGROUP CLMSG01 COMPRESS YES GBPCACHE CHANGED;
          CREATE INDEX CLM.CX ON CLM.CLAIM (CLAIM_NO) COPY YES GBPCACHE CHANGED CLOSE NO;
          SET CURRENT SQLID = 'CLMADM';
          """;
        assertThat(fallbacks(ddl, "columnAttribute", "tablespaceOption", "indexOption", "storageOption",
          "specialRegister", "defaultValue")).isZero();
    }

    @Test
    void trustedContextOptions() {
        String ddl = """
          CREATE TRUSTED CONTEXT CLMCTX BASED UPON CONNECTION USING SYSTEM AUTHID CLMADM
            ATTRIBUTES (ADDRESS '10.0.0.1', JOBNAME 'CLMBAT*') DEFAULT ROLE CLMROLE ENABLE
          ALTER TRUSTED CONTEXT CLMCTX ALTER ATTRIBUTES (ENCRYPTION 'LOW') DROP ATTRIBUTES (ADDRESS '10.0.0.1')
            ALTER SYSTEM AUTHID CLMADM2 ALTER NO DEFAULT ROLE ALTER WITHOUT ROLE AS OBJECT OWNER ALTER DISABLE
            ALTER DEFAULT SECURITY LABEL CLMLBL DROP USE FOR JOE, PUBLIC
            ADD USE FOR BILL ROLE CLMROLE SECURITY LABEL CLMLBL WITHOUT AUTHENTICATION, EXTERNAL SECURITY PROFILE CLMPROF
            REPLACE USE FOR JOE WITH AUTHENTICATION
          ALTER TABLE CLM.POLICY ADD COLUMN NOTE CHAR(8);
          """;
        assertThat(fallbacks(ddl, "createTrustedContext", "alterTrustedContext", "trustedContextOption",
          "namedTrustedContextOption", "trustedAttribute", "trustedUser")).isZero();
    }

    @Test
    void aSemicolonAfterAStatementIsItsEnd() {
        String ddl = """
          ;
          COMMENT ON TABLE T IS 'x';;
            ;
          COMMENT ON TABLE U IS 'y';
          """;
        assertThat(fallbacks(ddl, "end", "compilationUnit", "statement")).isZero();
    }

    private static long fallbacks(String ddl, String... rules) {
        DB2Parser parser = new DB2Parser(new CommonTokenStream(new DB2Lexer(CharStreams.fromString(ddl))));
        return FullContextPredictions.fallbacks(parser, parser::compilationUnit, rules);
    }
}
