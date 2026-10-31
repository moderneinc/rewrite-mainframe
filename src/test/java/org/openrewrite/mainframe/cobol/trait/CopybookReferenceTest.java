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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openrewrite.ExecutionContext;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.Parser;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.bms.BmsParser;
import org.openrewrite.mainframe.cobol.CobolParser;
import org.openrewrite.mainframe.cobol.CopybookParser;
import org.openrewrite.mainframe.cobol.marker.SymbolicMap;
import org.openrewrite.test.RewriteTest;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.mainframe.cobol.Assertions.cobol;
import static org.openrewrite.test.RewriteTest.toRecipe;

class CopybookReferenceTest implements RewriteTest {

    private List<CopybookReference> parse(String source) {
        List<CopybookReference> found = new ArrayList<>();
        rewriteRun(
          spec -> spec.recipe(toRecipe(() ->
            new CopybookReference.Matcher().<ExecutionContext>asVisitor((reference, ctx) -> {
                found.add(reference);
                return reference.getTree();
            }))),
          cobol(source)
        );
        return found;
    }

    /**
     * {@code COPY} and {@code EXEC SQL INCLUDE} are unrelated node types holding the name in
     * different places. They mean the same thing, so they read the same here.
     */
    @ParameterizedTest
    @ValueSource(strings = {
      "COPY INCEPTION_3.",
      "EXEC SQL INCLUDE INCEPTION_3 END-EXEC.",
    })
    void readsEitherWayOfWritingIt(String written) {
        List<CopybookReference> references = parse(
          """
            000000 IDENTIFICATION DIVISION.                                         *
                   PROGRAM-ID. IC109A.                                              *
                   DATA DIVISION.                                                   *
                   LINKAGE SECTION.                                                 *
                       01  GRP-01.                                                  *
                           %s
            """.formatted(written)
        );

        assertThat(references).singleElement().satisfies(reference -> {
            assertThat(reference.getName()).isEqualTo("INCEPTION_3");
            assertThat(reference.isResolved()).isTrue();
            assertThat(reference.isMissing()).isFalse();
        });
    }

    @Test
    void reportsACopybookNothingResolvedTo() {
        List<CopybookReference> references = parse(
          """
            000000 IDENTIFICATION DIVISION.                                         *
                   PROGRAM-ID. IC109A.                                              *
                   DATA DIVISION.                                                   *
                   LINKAGE SECTION.                                                 *
                       01  GRP-01.                                                  *
                           COPY NOSUCHBOOK.                                         *
            """
        );

        assertThat(references).singleElement().satisfies(reference -> {
            assertThat(reference.isMissing()).isTrue();
            assertThat(reference.isResolved()).isFalse();
            assertThat(reference.getSourcePath()).isNull();
        });
    }

    /**
     * A copybook that copies another is reached too, and reached once. INCEPTION copies INCEPTION_2,
     * which copies INCEPTION_3, and preprocessing hangs each of the three off a word of the program
     * — so walking into a resolved copybook as well reports the inner ones once per level above them.
     */
    @Test
    void reachesACopybookThatCopiesAnotherExactlyOnce() {
        List<CopybookReference> references = parse(
          """
            000000 IDENTIFICATION DIVISION.                                         *
                   PROGRAM-ID. IC109A.                                              *
                   DATA DIVISION.                                                   *
                   LINKAGE SECTION.                                                 *
                       01  GRP-01.                                                  *
                           COPY INCEPTION.                                          *
            """
        );

        assertThat(references).extracting(CopybookReference::getName)
          .containsExactly("INCEPTION", "INCEPTION_2", "INCEPTION_3");
    }

    /**
     * The word the reference hangs off is where the program text around it is, which is what a
     * caller reports as context.
     */
    @Test
    void knowsTheWordItHangsOff() {
        List<CopybookReference> references = parse(
          """
            000000 IDENTIFICATION DIVISION.                                         *
                   PROGRAM-ID. IC109A.                                              *
                   DATA DIVISION.                                                   *
                   LINKAGE SECTION.                                                 *
                       01  GRP-01.                                                  *
                           COPY INCEPTION_3.                                        *
            """
        );

        assertThat(references).singleElement().satisfies(reference ->
          assertThat(reference.getEnclosingWord()).isNotNull());
    }

    @Test
    void resolvesAMemberTheCompilerSupplies() {
        List<CopybookReference> references = parse(
          """
            000000 IDENTIFICATION DIVISION.                                         *
                   PROGRAM-ID. SUPPLIED.                                            *
                   DATA DIVISION.                                                   *
                   WORKING-STORAGE SECTION.                                         *
                       EXEC SQL INCLUDE SQLCA END-EXEC.                             *
                       COPY DFHAID.                                                 *
                       COPY DFHBMSCA.                                               *
            """
        );

        assertThat(references).extracting(CopybookReference::getName).containsExactly("SQLCA", "DFHAID", "DFHBMSCA");
        assertThat(references).allSatisfy(reference -> {
            assertThat(reference.isResolved()).isTrue();
            assertThat(reference.isSystemSupplied()).isTrue();
            assertThat(reference.getSourcePath()).isNull();
        });
    }

    @Test
    void prefersAMemberTheRepositoryHolds() {
        String sqlca = """
                 01  SQLCA.                                                       \s
                     05  SQLCODE    PIC S9(9) COMP-5.                             \s
          """;
        List<SourceFile> copybooks = CopybookParser.builder().build().parseInputs(singletonList(
            new Parser.Input(Paths.get("SQLCA.cpy"), () -> new ByteArrayInputStream(sqlca.getBytes(UTF_8)))),
          null, new InMemoryExecutionContext()).collect(toList());
        SourceFile program = CobolParser.builder().copybooks(copybooks).build().parse(
          """
            000000 IDENTIFICATION DIVISION.                                         *
                   PROGRAM-ID. OWNSQLCA.                                            *
                   DATA DIVISION.                                                   *
                   WORKING-STORAGE SECTION.                                         *
                       EXEC SQL INCLUDE SQLCA END-EXEC.                             *
            """
        ).findFirst().orElseThrow();

        List<CopybookReference> references = new ArrayList<>();
        new CopybookReference.Matcher().<Integer>asVisitor((reference, p) -> {
            references.add(reference);
            return reference.getTree();
        }).visit(program, 0);

        assertThat(references).singleElement().satisfies(reference -> {
            assertThat(reference.isSystemSupplied()).isFalse();
            assertThat(reference.getSourcePath()).isEqualTo(Paths.get("SQLCA.cpy"));
        });
    }

    @Test
    void aMapsetSuppliesTheSymbolicMapItGenerates() {
        CopybookReference reference = copying("COSGN00", mapset("COSGN00.bms", COSGN00));

        assertThat(reference.isResolved()).isTrue();
        assertThat(reference.getSourcePath()).isEqualTo(Paths.get("COSGN00.bms"));
        //noinspection DataFlowIssue
        assertThat(reference.getCopybook().getMarkers().findFirst(SymbolicMap.class)).isPresent();
        assertThat(reference.getCopybook().printAll()).contains("02  TRNNAMEI  PIC X(4).");
    }

    @Test
    void aCopybookOfTheMapsetsNameWins() {
        String cosgn00 = """
                 01  COSGN0AI.                                                    \s
                     05  TRNNAMEI   PIC X(4).                                     \s
          """;
        List<SourceFile> copybook = CopybookParser.builder().build().parseInputs(singletonList(
            new Parser.Input(Paths.get("COSGN00.cpy"), () -> new ByteArrayInputStream(cosgn00.getBytes(UTF_8)))),
          null, new InMemoryExecutionContext()).collect(toList());

        // Ahead of the copybook in the library, whether it parsed or not.
        for (String mapset : new String[]{COSGN00, "not a map set"}) {
            List<SourceFile> library = new ArrayList<>(mapset("COSGN00.bms", mapset));
            library.addAll(copybook);
            assertThat(copying("COSGN00", library).getSourcePath()).isEqualTo(Paths.get("COSGN00.cpy"));
        }
    }

    private static final String COSGN00 = """
      COSGN00 DFHMSD LANG=COBOL,MODE=INOUT,STORAGE=AUTO
      COSGN0A DFHMDI SIZE=(24,80)
      TRNNAME DFHMDF POS=(1,8),LENGTH=4
              DFHMSD TYPE=FINAL
              END
      """;

    private static List<SourceFile> mapset(String path, String source) {
        return BmsParser.builder().build().parseInputs(singletonList(
            new Parser.Input(Paths.get(path), () -> new ByteArrayInputStream(source.getBytes(UTF_8)))),
          null, new InMemoryExecutionContext()).collect(toList());
    }

    private static CopybookReference copying(String member, List<SourceFile> library) {
        SourceFile program = CobolParser.builder().copybooks(library).build().parse(
          """
            000000 IDENTIFICATION DIVISION.                                         *
                   PROGRAM-ID. SIGNON.                                              *
                   DATA DIVISION.                                                   *
                   WORKING-STORAGE SECTION.                                         *
                       COPY %s.                                                *
            """.formatted(member)
        ).findFirst().orElseThrow();

        List<CopybookReference> references = new ArrayList<>();
        new CopybookReference.Matcher().<Integer>asVisitor((reference, p) -> {
            references.add(reference);
            return reference.getTree();
        }).visit(program, 0);
        assertThat(references).hasSize(1);
        return references.get(0);
    }
}
