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
package org.openrewrite.mainframe.cobol;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.openrewrite.ExecutionContext;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.Parser;
import org.openrewrite.SourceFile;
import org.openrewrite.internal.ObjectMappers;
import org.openrewrite.mainframe.cobol.marker.CopiedWord;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.CobolPreprocessor;
import org.openrewrite.mainframe.cobol.tree.Space;
import org.openrewrite.marker.Markers;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.Tree.randomId;

class CopybookSharingTest {

    private static final TypeReference<List<CobolPreprocessor>> STATEMENTS = new TypeReference<>() {
    };

    // Reads and writes fields, as an LST is serialized.
    private static final ObjectMapper MAPPER = ObjectMappers.propertyBasedMapper(null)
      .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    static {
        MAPPER.setVisibility(MAPPER.getSerializationConfig().getDefaultVisibilityChecker()
          .withCreatorVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY)
          .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
          .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
          .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
    }

    private final ExecutionContext ctx = new InMemoryExecutionContext(t -> {
        throw new AssertionError(t);
    });

    @Test
    void programsCopyingOneCopybookAttachOneTreeThatIsNotItsSourceFile() {
        List<SourceFile> copybooks = CopybookParser.builder().build().parseInputs(List.of(
          Parser.Input.fromString(Paths.get("LEAF.cpy"), """
            000100     05  LEAF-ID              PIC X(6).
            000200     05  LEAF-NAME            PIC X(20).
            """),
          Parser.Input.fromString(Paths.get("OUTER.cpy"), """
            000100     COPY LEAF.
            000200     COPY NOWHERE.
            """)), null, ctx).collect(Collectors.toList());
        List<SourceFile> programs = CobolParser.builder().copybooks(copybooks).build().parseInputs(List.of(
          Parser.Input.fromString(Paths.get("PGMONE.cbl"), program("PGMONE")),
          Parser.Input.fromString(Paths.get("PGMTWO.cbl"), program("PGMTWO"))), null, ctx).collect(Collectors.toList());

        CobolPreprocessor.Copybook outer = copybook(programs.get(0), "OUTER");
        assertThat(copybook(programs.get(1), "OUTER")).isSameAs(outer);
        assertThat(copiedFrom(programs.get(1))).containsEntry("LEAF-NAME", "LEAF");

        assertThat(copybook(outer, "LEAF")).isNotSameAs(copybooks.get(0));
    }

    @Test
    void aCopybookTwoStatementsCarryIsReadBackOnce() throws Exception {
        CobolPreprocessor.Copybook copybook = emptyCopybook();
        String json = MAPPER.writerFor(STATEMENTS).writeValueAsString(List.of(copying(copybook), copying(copybook)));

        List<CobolPreprocessor> read = MAPPER.readValue(json, STATEMENTS);

        assertThat(((CobolPreprocessor.CopyStatement) read.get(1)).getCopybook())
          .isSameAs(((CobolPreprocessor.CopyStatement) read.get(0)).getCopybook());
    }

    @Test
    void aCopybookWrittenWithoutAnIdStillReads() throws Exception {
        CobolPreprocessor.Copybook copybook = emptyCopybook();
        ArrayNode json = (ArrayNode) MAPPER.readTree(MAPPER.writerFor(STATEMENTS).writeValueAsString(List.of(copying(copybook))));
        ((ObjectNode) json.get(0).get("copybook")).remove("@ref");

        List<CobolPreprocessor> read = MAPPER.readValue(MAPPER.treeAsTokens(json), STATEMENTS);

        assertThat(((CobolPreprocessor.CopyStatement) read.get(0)).getCopybook().getId()).isEqualTo(copybook.getId());
    }

    private static String program(String name) {
        return """
          000100 IDENTIFICATION DIVISION.
          000200 PROGRAM-ID. %s.
          000300 DATA DIVISION.
          000400 WORKING-STORAGE SECTION.
          000500 01  RECORD-AREA.
          000600     COPY OUTER.
          000700 PROCEDURE DIVISION.
          000800     GOBACK.
          """.formatted(name);
    }

    private static CobolPreprocessor.Copybook copybook(SourceFile program, String name) {
        return new CobolIsoVisitor<List<CobolPreprocessor.Copybook>>() {
            @Override
            public Cobol.Word visitWord(Cobol.Word word, List<CobolPreprocessor.Copybook> found) {
                for (CobolPreprocessor statement : word.getPreprocessorStatements()) {
                    if (statement instanceof CobolPreprocessor.CopyStatement copy && named(copy, name)) {
                        found.add(copy.getCopybook());
                    }
                }
                return super.visitWord(word, found);
            }
        }.reduce(program, new ArrayList<>()).get(0);
    }

    private static CobolPreprocessor.Copybook copybook(CobolPreprocessor.Copybook copybook, String name) {
        return new CobolPreprocessorIsoVisitor<List<CobolPreprocessor.Copybook>>() {
            @Override
            public CobolPreprocessor.CopyStatement visitCopyStatement(CobolPreprocessor.CopyStatement copy,
                                                                      List<CobolPreprocessor.Copybook> found) {
                if (named(copy, name)) {
                    found.add(copy.getCopybook());
                }
                return super.visitCopyStatement(copy, found);
            }
        }.reduce(copybook, new ArrayList<>()).get(0);
    }

    private static boolean named(CobolPreprocessor.CopyStatement copy, String name) {
        return name.equals(copy.getCopySource().getName().getCobolWord().getWord());
    }

    private static Map<String, String> copiedFrom(SourceFile program) {
        return new CobolIsoVisitor<Map<String, String>>() {
            @Override
            public Cobol.Word visitWord(Cobol.Word word, Map<String, String> byWord) {
                word.getMarkers().findFirst(CopiedWord.class)
                  .ifPresent(copied -> byWord.put(word.getWord(), copied.getCopybook()));
                return super.visitWord(word, byWord);
            }
        }.reduce(program, new LinkedHashMap<>());
    }

    private static CobolPreprocessor.Copybook emptyCopybook() {
        return new CobolPreprocessor.Copybook(randomId(), Space.EMPTY, Markers.EMPTY, Paths.get("EMPTY.cpy"),
          null, null, false, null, List.of(), null);
    }

    private static CobolPreprocessor.CopyStatement copying(CobolPreprocessor.Copybook copybook) {
        return new CobolPreprocessor.CopyStatement(randomId(), Space.EMPTY, Markers.EMPTY, null, null, List.of(), null, copybook);
    }
}
