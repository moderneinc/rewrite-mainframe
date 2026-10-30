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
import org.openrewrite.mainframe.controlcard.utility.UtilityParser;
import org.openrewrite.mainframe.db2.bind.BindParser;
import org.openrewrite.mainframe.db2.trait.Query;
import org.openrewrite.mainframe.db2.trait.Table;
import org.openrewrite.mainframe.db2.tree.Db2;
import org.openrewrite.mainframe.linkedit.LinkEditParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * SQL a shop keeps with its control cards, as CardDemo keeps the deck its {@code DSNTIAD} step runs
 * in {@code ctl/DB2CREAT.ctl} and the rows its {@code DSNTEP2} steps load in {@code ctl/DB2LTCAT.ctl}
 * and {@code ctl/DB2LTTYP.ctl}.
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

    private static final String DB2LTCAT = """
      /* Copyright Amazon.com, Inc. or its affiliates.                   */
      /* All Rights Reserved.                                            */
       INSERT INTO CARDDEMO.TRANSACTION_TYPE_CATEGORY
            (TRC_TYPE_CODE    ,
             TRC_TYPE_CATEGORY,
             TRC_CAT_DATA     )
       WITH DMY AS (SELECT * FROM SYSIBM.SYSDUMMY1)
       SELECT '01','0001','REGULAR SALES DRAFT'           FROM DMY UNION ALL
       SELECT '01','0002','REGULAR CASH ADVANCE'          FROM DMY UNION ALL
       SELECT '07','0001','SALES DRAFT CREDIT ADJUSTMENT' FROM DMY

       COMMIT;
      """;

    private static final String DB2LTTYP = """
      /* Copyright Amazon.com, Inc. or its affiliates.                   */
      /* All Rights Reserved.                                            */
       INSERT INTO CARDDEMO.TRANSACTION_TYPE
              (TR_TYPE,TR_DESCRIPTION)
       SELECT '01','PURCHASE'      FROM SYSIBM.SYSDUMMY1 UNION ALL
       SELECT '02','PAYMENT'       FROM SYSIBM.SYSDUMMY1 UNION ALL
       SELECT '07','ADJUSTMENT'    FROM SYSIBM.SYSDUMMY1
       COMMIT;
      """;

    @Test
    void takesAMemberWhoseFirstStatementIsDdl(@TempDir Path directory) throws IOException {
        Db2Parser parser = Db2Parser.builder().build();

        assertThat(parser.accept(write(directory, "DB2CREAT.ctl", DB2CREAT))).isTrue();
        assertThat(parser.accept(write(directory, "ALTERPOL.ctl",
          "-- Widen the policy holder's name\n  ALTER TABLE CLM.POLICY\n    ALTER COLUMN HOLDER SET DATA TYPE VARCHAR(80);\n"))).isTrue();
        assertThat(parser.accept(write(directory, "CLMDDL", "CREATE TABLE CLM.X (A CHAR(1));\n"))).isTrue();
    }

    @Test
    void takesAMemberThatOpensWithDmlItReads(@TempDir Path directory) throws IOException {
        Db2Parser parser = Db2Parser.builder().build();

        assertThat(parser.accept(write(directory, "DB2LTCAT.ctl", DB2LTCAT))).isTrue();
        assertThat(parser.accept(write(directory, "DB2LTTYP.ctl", DB2LTTYP))).isTrue();
        assertThat(parser.accept(write(directory, "CLMRELOD.ctl",
          "  DELETE FROM CLM.POLICY;\n  INSERT INTO CLM.POLICY SELECT * FROM CLM.POLICY_STAGE;\n"))).isTrue();
        assertThat(parser.accept(write(directory, "CLMCLOSE.ctl",
          "  UPDATE CLM.POLICY SET STATUS = 'C' WHERE EXPIRY < '2026-01-01';\n"))).isTrue();
        assertThat(parser.accept(write(directory, "CLMTABS",
          "  SELECT NAME FROM SYSIBM.SYSTABLES WHERE CREATOR = 'CLM';\n"))).isTrue();
        assertThat(parser.accept(write(directory, "CLMCOLS",
          "  WITH T AS (SELECT NAME FROM SYSIBM.SYSTABLES) SELECT * FROM T;\n"))).isTrue();
    }

    @Test
    void leavesAControlCardThatIsNoDb2SqlAlone(@TempDir Path directory) throws IOException {
        Db2Parser parser = Db2Parser.builder().build();

        assertThat(parser.accept(write(directory, "DELCLU.ctl",
          "  DELETE CLM.PROD.CLMMAST PURGE\n"))).isFalse();
        assertThat(parser.accept(write(directory, "TOOLIN.ctl",
          "  SELECT FROM(IN) TO(OUT) ON(1,8,CH) FIRST\n"))).isFalse();
        assertThat(parser.accept(write(directory, "NOTES",
          "Insert the new rate rows before the month-end batch runs.\n"))).isFalse();
        assertThat(parser.accept(write(directory, "ALTERCLU.ctl",
          "  ALTER CLM.PROD.CLMMAST -\n    NEWNAME(CLM.PROD.CLMMAST.OLD)\n"))).isFalse();
        assertThat(parser.accept(write(directory, "SRTCLM01.ctl", "  SORT FIELDS=(1,8,CH,A)\n"))).isFalse();
        assertThat(parser.accept(write(directory, "DB2TIAD1.ctl",
          "  DSN SYSTEM(DAZ1)\n  RUN PROGRAM(DSNTIAD) -\n      PLAN(DSNTIAD)\n  END\n"))).isFalse();
    }

    /**
     * The readers offered a control card before the Db2 reader keep declining a member that opens
     * with Db2 SQL, so it reaches this one.
     */
    @Test
    void theOtherControlCardReadersLeaveDb2SqlToThisOne(@TempDir Path directory) throws IOException {
        for (Path sql : asList(write(directory, "DB2CREAT.ctl", DB2CREAT), write(directory, "DB2LTCAT.ctl", DB2LTCAT))) {
            assertThat(SortParser.builder().build().accept(sql)).as(sql.toString()).isFalse();
            assertThat(IdcamsParser.builder().build().accept(sql)).as(sql.toString()).isFalse();
            assertThat(UtilityParser.builder().build().accept(sql)).as(sql.toString()).isFalse();
            assertThat(LinkEditParser.builder().build().accept(sql)).as(sql.toString()).isFalse();
            assertThat(BindParser.builder().build().accept(sql)).as(sql.toString()).isFalse();
        }
    }

    @Test
    void readsTheTablesTheDeckCreates(@TempDir Path directory) throws IOException {
        SourceFile parsed = parse(directory, "DB2CREAT.ctl", DB2CREAT);

        assertThat(new Table.Matcher().lower(parsed).map(Table::getQualifiedName).collect(toList()))
          .containsExactly("CARDDEMO.TRANSACTION_TYPE", "CARDDEMO.TRANSACTION_TYPE_CATEGORY");
    }

    @Test
    void readsTheTableADeckInsertsIntoAndTheTablesItSelectsFrom(@TempDir Path directory) throws IOException {
        Db2.Ddl categories = parse(directory, "DB2LTCAT.ctl", DB2LTCAT);
        Db2.Ddl types = parse(directory, "DB2LTTYP.ctl", DB2LTTYP);

        Db2.Insert category = (Db2.Insert) categories.getStatements().get(0);
        assertThat(category.getTable().getFullName()).isEqualTo("CARDDEMO.TRANSACTION_TYPE_CATEGORY");
        assertThat(category.getValues().getElements()).singleElement().isInstanceOf(Db2.Query.class);
        assertThat(categories.getStatements().get(1)).isInstanceOf(Db2.Commit.class);
        assertThat(tables(categories)).containsExactly("SYSIBM.SYSDUMMY1");

        Db2.Insert type = (Db2.Insert) types.getStatements().get(0);
        assertThat(type.getTable().getFullName()).isEqualTo("CARDDEMO.TRANSACTION_TYPE");
        assertThat(type.getValues().getElements()).singleElement().isInstanceOf(Db2.Query.class);
        // Each branch of the union names the table again.
        assertThat(tables(types)).containsExactly("SYSIBM.SYSDUMMY1", "SYSIBM.SYSDUMMY1", "SYSIBM.SYSDUMMY1");
    }

    @Test
    void readsTheFirstStatementPastItsComments() {
        assertThat(Db2Parser.opensWithDdl("/* a\n   b */\n-- c\n  SET CURRENT SQLID='SYSADM';")).isTrue();
        assertThat(Db2Parser.opensWithDdl("  COMMENT ON TABLE CLM.POLICY IS 'policies';")).isTrue();
        assertThat(Db2Parser.opensWithDdl("  SET MAXCC = 0")).isFalse();
        assertThat(Db2Parser.opensWithDdl("")).isFalse();
    }

    private static Db2.Ddl parse(Path directory, String name, String source) throws IOException {
        Parser.Input input = new Parser.Input(write(directory, name, source),
          () -> new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8)));
        SourceFile parsed = Db2Parser.builder().build()
          .parseInputs(singletonList(input), null, new InMemoryExecutionContext(t -> {
              throw new IllegalStateException(t);
          })).findFirst().orElseThrow();
        assertThat(parsed).isInstanceOf(Db2.Ddl.class);
        assertThat(parsed.printAll()).as("prints back byte for byte").isEqualTo(source);
        return (Db2.Ddl) parsed;
    }

    private static List<String> tables(Db2.Ddl ddl) {
        return new Query.Matcher().lower(ddl)
          .flatMap(query -> query.getTables().stream())
          .map(Db2.Name::getFullName)
          .collect(toList());
    }

    private static Path write(Path directory, String name, String content) throws IOException {
        Path path = directory.resolve(name);
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        return path;
    }
}
