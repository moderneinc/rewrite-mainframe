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
package org.openrewrite.mainframe;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openrewrite.ExecutionContext;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.Parser;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.assembler.AssemblerParser;
import org.openrewrite.mainframe.bms.BmsParser;
import org.openrewrite.mainframe.cobol.CobolParser;
import org.openrewrite.mainframe.cobol.CopybookParser;
import org.openrewrite.mainframe.controlcard.idcams.IdcamsParser;
import org.openrewrite.mainframe.controlcard.sort.SortParser;
import org.openrewrite.mainframe.controlcard.utility.UtilityParser;
import org.openrewrite.mainframe.controlm.ControlMParser;
import org.openrewrite.mainframe.db2.Db2Parser;
import org.openrewrite.mainframe.db2.bind.BindParser;
import org.openrewrite.mainframe.ims.ImsParser;
import org.openrewrite.mainframe.jcl.JclParser;
import org.openrewrite.mainframe.linkedit.LinkEditParser;
import org.openrewrite.tree.ParseError;
import org.openrewrite.tree.ParsingEventListener;
import org.openrewrite.tree.ParsingExecutionContextView;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Named.named;

class PrintEqualsInputTest {

    static Stream<Arguments> parsers() {
        return Stream.of(
          Arguments.of(named("COBOL", CobolParser.builder().build()),
            "       IDENTIFICATION DIVISION.\n       PROGRAM-ID. A.\n       PROCEDURE DIVISION.\n           STOP RUN.\n"),
          Arguments.of(named("copybook", CopybookParser.builder().build()), "       01  A PIC X.\n"),
          Arguments.of(named("JCL", JclParser.builder().build()), "//A JOB\n//S EXEC PGM=IEFBR14\n"),
          Arguments.of(named("Db2", Db2Parser.builder().build()), "COMMIT;\n"),
          Arguments.of(named("bind", BindParser.builder().build()), "DSN SYSTEM(DB2P)\nBIND PACKAGE(P) MEMBER(M)\nEND\n"),
          Arguments.of(named("BMS", BmsParser.builder().build()), "COSGN00 DFHMSD LANG=COBOL\n"),
          Arguments.of(named("IMS", ImsParser.builder().build()), "      DBD   NAME=CUSTOMER,ACCESS=(HDAM,OSAM)\n"),
          Arguments.of(named("IDCAMS", IdcamsParser.builder().build()), "  DELETE CLM.PROD.CLMMAST CLUSTER PURGE\n"),
          Arguments.of(named("sort", SortParser.builder().build()), "  SORT FIELDS=(1,10,CH,A)\n"),
          Arguments.of(named("utility", UtilityParser.builder().build()), "  UNLOAD TABLESPACE DB.TS\n"),
          Arguments.of(named("link-edit", LinkEditParser.builder().build()), "  INCLUDE SYSLIB(DFHECI)\n  NAME A(R)\n"),
          Arguments.of(named("Control-M", ControlMParser.builder().build()), ""),
          Arguments.of(named("assembler", AssemblerParser.builder().build()), "CLMU030  CSECT\n         END   CLMU030\n")
        );
    }

    @ParameterizedTest
    @MethodSource("parsers")
    void anLstThatDoesNotPrintTheInputBackIsAParseErrorUnlessTheCallerOptsOut(Parser parser, String source) {
        assertThat(parseWhileTheInputChanges(parser, source, true)).isInstanceOf(ParseError.class);
        assertThat(parseWhileTheInputChanges(parser, source, false)).isNotInstanceOf(ParseError.class);
    }

    /**
     * Parses a source that reads back with one character more once the parser has built its LST, so that the LST
     * cannot print what the input then holds.
     */
    private static SourceFile parseWhileTheInputChanges(Parser parser, String source, boolean requirePrintEqualsInput) {
        AtomicBoolean parsed = new AtomicBoolean();
        ExecutionContext ctx = new InMemoryExecutionContext();
        ctx.putMessage(ExecutionContext.REQUIRE_PRINT_EQUALS_INPUT, requirePrintEqualsInput);
        ParsingExecutionContextView.view(ctx).setParsingListener(new ParsingEventListener() {
            @Override
            public void parsed(Parser.Input input, SourceFile sourceFile) {
                parsed.set(true);
            }
        });
        Parser.Input input = new Parser.Input(Paths.get("MEMBER"), null,
          () -> new ByteArrayInputStream((parsed.get() ? source + "X" : source).getBytes(UTF_8)), true);
        List<SourceFile> results = parser.parseInputs(singletonList(input), null, ctx).collect(toList());
        assertThat(results).hasSize(1);
        return results.get(0);
    }
}
