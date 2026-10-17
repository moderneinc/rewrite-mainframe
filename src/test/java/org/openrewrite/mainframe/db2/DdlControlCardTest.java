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
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.Parser;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.controlcard.idcams.IdcamsParser;
import org.openrewrite.mainframe.controlcard.sort.SortParser;
import org.openrewrite.mainframe.db2.bind.BindParser;
import org.openrewrite.mainframe.db2.trait.Table;
import org.openrewrite.mainframe.db2.tree.Db2;
import org.openrewrite.mainframe.linkedit.LinkEditParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * DDL a shop keeps with its control cards, as CardDemo keeps the deck its {@code DSNTIAD} step runs
 * in {@code ctl/DB2CREAT.ctl}.
 */
class DdlControlCardTest {

    private static final String DB2CREAT =
      "/* Copyright Amazon.com, Inc. or its affiliates.                   */\n" +
      "/* All Rights Reserved.                                            */\n" +
      "  SET CURRENT SQLID = 'SYSADM';\n" +
      "\n" +
      "      CREATE DATABASE CARDDEMO\n" +
      "             STOGROUP AWST1STG\n" +
      "             BUFFERPOOL BP0\n" +
      "             CCSID EBCDIC;\n" +
      "\n" +
      "      COMMIT ;\n" +
      "\n" +
      "      CREATE TABLE  CARDDEMO.TRANSACTION_TYPE\n" +
      "       (       TR_TYPE   CHAR(2)      NOT NULL,\n" +
      "        TR_DESCRIPTION   VARCHAR(50)  NOT NULL,\n" +
      "                 PRIMARY KEY(TR_TYPE))\n" +
      "         IN CARDDEMO.CARDSPC1\n" +
      "         CCSID EBCDIC;\n" +
      "\n" +
      "   CREATE UNIQUE INDEX CARDDEMO.XTRAN_TYPE\n" +
      "                    ON CARDDEMO.TRANSACTION_TYPE\n" +
      "                        (TR_TYPE   ASC)\n" +
      "                    USING STOGROUP AWST1STG\n" +
      "                    CLOSE NO;\n" +
      "\n" +
      "      GRANT DELETE, INSERT, SELECT, UPDATE\n" +
      "            ON TABLE CARDDEMO.TRANSACTION_TYPE\n" +
      "            TO PUBLIC;\n" +
      "\n" +
      "  CREATE TABLE CARDDEMO.TRANSACTION_TYPE_CATEGORY\n" +
      "       (TRC_TYPE_CODE     CHAR(2)        NOT NULL,\n" +
      "        TRC_TYPE_CATEGORY CHAR(4)        NOT NULL,\n" +
      "  PRIMARY KEY(TRC_TYPE_CODE, TRC_TYPE_CATEGORY))\n" +
      "     IN CARDDEMO.CARDSTTC\n" +
      "     CCSID EBCDIC;\n" +
      "\n" +
      "  ALTER TABLE CARDDEMO.TRANSACTION_TYPE_CATEGORY\n" +
      "    FOREIGN KEY (TRC_TYPE_CODE)\n" +
      "      REFERENCES CARDDEMO.TRANSACTION_TYPE (TR_TYPE)\n" +
      "  ON DELETE RESTRICT;\n";

    @Test
    void takesAMemberWhoseFirstStatementIsDdl(@TempDir Path directory) throws IOException {
        Db2Parser parser = Db2Parser.builder().build();

        assertThat(parser.accept(write(directory, "DB2CREAT.ctl", DB2CREAT))).isTrue();
        assertThat(parser.accept(write(directory, "ALTERPOL.ctl",
          "-- Widen the policy holder's name\n  ALTER TABLE CLM.POLICY\n    ALTER COLUMN HOLDER SET DATA TYPE VARCHAR(80);\n"))).isTrue();
        assertThat(parser.accept(write(directory, "CLMDDL", "CREATE TABLE CLM.X (A CHAR(1));\n"))).isTrue();
    }

    /**
     * A control card library holds the cards of every utility side by side, and the rows a deck
     * inserts, the IDCAMS {@code ALTER} of a cluster and a sort card are none of them DDL.
     */
    @Test
    void leavesAControlCardThatIsNoDdlAlone(@TempDir Path directory) throws IOException {
        Db2Parser parser = Db2Parser.builder().build();

        assertThat(parser.accept(write(directory, "DB2LTTYP.ctl",
          "INSERT INTO CARDDEMO.TRANSACTION_TYPE\n (TR_TYPE,TR_DESCRIPTION)\n"))).isFalse();
        assertThat(parser.accept(write(directory, "ALTERCLU.ctl",
          "  ALTER CLM.PROD.CLMMAST -\n    NEWNAME(CLM.PROD.CLMMAST.OLD)\n"))).isFalse();
        assertThat(parser.accept(write(directory, "SRTCLM01.ctl", "  SORT FIELDS=(1,8,CH,A)\n"))).isFalse();
        assertThat(parser.accept(write(directory, "DB2TIAD1.ctl",
          "  DSN SYSTEM(DAZ1)\n  RUN PROGRAM(DSNTIAD) -\n      PLAN(DSNTIAD)\n  END\n"))).isFalse();
    }

    /**
     * The readers offered a control card before the DDL reader keep declining a member that opens
     * with DDL, so it reaches this one.
     */
    @Test
    void theOtherControlCardReadersLeaveDdlToThisOne(@TempDir Path directory) throws IOException {
        Path ddl = write(directory, "DB2CREAT.ctl", DB2CREAT);

        assertThat(SortParser.builder().build().accept(ddl)).isFalse();
        assertThat(IdcamsParser.builder().build().accept(ddl)).isFalse();
        assertThat(LinkEditParser.builder().build().accept(ddl)).isFalse();
        assertThat(BindParser.builder().build().accept(ddl)).isFalse();
    }

    @Test
    void readsTheTablesTheDeckCreates(@TempDir Path directory) throws IOException {
        Path deck = write(directory, "DB2CREAT.ctl", DB2CREAT);
        Parser.Input input = new Parser.Input(deck,
          () -> new ByteArrayInputStream(DB2CREAT.getBytes(StandardCharsets.UTF_8)));
        SourceFile parsed = Db2Parser.builder().build()
          .parseInputs(singletonList(input), null, new InMemoryExecutionContext()).findFirst().orElseThrow();

        assertThat(parsed).isInstanceOf(Db2.Ddl.class);
        assertThat(new Table.Matcher().lower(parsed).map(Table::getQualifiedName).collect(toList()))
          .containsExactly("CARDDEMO.TRANSACTION_TYPE", "CARDDEMO.TRANSACTION_TYPE_CATEGORY");
    }

    @Test
    void readsTheFirstStatementPastItsComments() {
        assertThat(Db2Parser.opensWithDdl("/* a\n   b */\n-- c\n  SET CURRENT SQLID='SYSADM';")).isTrue();
        assertThat(Db2Parser.opensWithDdl("  COMMENT ON TABLE CLM.POLICY IS 'policies';")).isTrue();
        assertThat(Db2Parser.opensWithDdl("  SET MAXCC = 0")).isFalse();
        assertThat(Db2Parser.opensWithDdl("")).isFalse();
    }

    private static Path write(Path directory, String name, String content) throws IOException {
        Path path = directory.resolve(name);
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        return path;
    }
}
