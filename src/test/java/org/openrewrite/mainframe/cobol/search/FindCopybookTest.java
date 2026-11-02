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
package org.openrewrite.mainframe.cobol.search;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openrewrite.DocumentExample;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.Issue;
import org.openrewrite.Parser;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.bms.BmsParser;
import org.openrewrite.mainframe.cobol.CobolParser;
import org.openrewrite.mainframe.cobol.CobolTest;
import org.openrewrite.mainframe.cobol.table.CopybookSource;
import org.openrewrite.mainframe.cobol.table.CopybookSource.Row;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.SourceSpec;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.mainframe.cobol.Assertions.cobol;
import static org.openrewrite.mainframe.cobol.table.CopybookSource.ResolutionStatus.MISSING_SOURCE;
import static org.openrewrite.mainframe.cobol.table.CopybookSource.ResolutionStatus.RESOLVED;

class FindCopybookTest extends CobolTest {

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new FindCopybook(null, false));
    }


	@DocumentExample @Test void isUsedInCopyStatement() {
        rewriteRun(
          spec -> spec.recipe(new FindCopybook("INCEPTION", false)).dataTable(Row.class, rows -> {
              assertThat(rows).extracting(CopybookSource.Row::getCopybookName).containsOnly("INCEPTION");
              assertThat(rows).extracting(Row::getResolutionStatus).containsOnly(RESOLVED);
          }),
          cobol(
            """
              000000 IDENTIFICATION DIVISION.                                         *
                     PROGRAM-ID. IC109A.                                              *
                     DATA DIVISION.                                                   *
                     LINKAGE SECTION.                                                 *
                         01  GRP-01.                                                  *
                             COPY INCEPTION.                                          *
              """,
            """
              000000 IDENTIFICATION DIVISION.                                         *
                     PROGRAM-ID. IC109A.                                              *
                     DATA DIVISION.                                                   *
                     LINKAGE SECTION.                                                 *
                         01  GRP-01.                                                  *
                             COPY ~~>INCEPTION.                                          *
              """
          )
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
      "COPY INCEPTION.                                          *",
      "EXEC SQL INCLUDE INCEPTION END-EXEC.                     *",
    })
    void bookIsNotUsed(String input) {
        rewriteRun(
          spec -> spec.recipe(new FindCopybook("KP008", false)),
          cobol(
            """
              000000 IDENTIFICATION DIVISION.                                         *
                     PROGRAM-ID. IC109A.                                              *
                     DATA DIVISION.                                                   *
                     LINKAGE SECTION.                                                 *
                         01  GRP-01.                                                  *
                             %s
              """.formatted(input)
          )
        );
    }

    @Issue("https://github.com/moderneinc/rewrite-mainframe/issues/102")
    @ParameterizedTest
    @ValueSource(strings = {
      "COPY MISSING_BOOK.",
      "EXEC SQL INCLUDE MISSING_BOOK END-EXEC.",
    })
    void findMissingCopybookInCopySource(String input) {
        rewriteRun(
          spec -> spec.recipe(new FindCopybook(null, true)).dataTable(Row.class, rows -> {
              assertThat(rows).extracting(CopybookSource.Row::getCopybookName).containsOnly("MISSING");
              assertThat(rows).extracting(Row::getResolutionStatus).containsOnly(MISSING_SOURCE);
          }),
          cobol(
            """
              000000 IDENTIFICATION DIVISION.                                         *
                     PROGRAM-ID.                                                      *
                         MISSING_BOOK.                                                *
                     DATA DIVISION.                                                   *
                     LINKAGE SECTION.                                                 *
                     01  GRP-01.                                                      *
                         %s
              
                    *******************************************************************
                    /                                                                 *
                             02  SPECIAL-FLAGS.                                       *
                                 03  DN7 PICTURE X.                                   *
                                 03  DN8 PICTURE X.                                   *
                                 03  DN9 PICTURE X.                                   *
              """.formatted(input),
            """
              000000 IDENTIFICATION DIVISION.                                         *
                     PROGRAM-ID.                                                      *
                         MISSING_BOOK.                                                *
                     DATA DIVISION.                                                   *
                     LINKAGE SECTION.                                                 *
                     01  GRP-01.                                                      *
                         %s
              
                    *******************************************************************
                    /                                                                 *
                             02  SPECIAL-FLAGS.                                       *
                                 03  DN7 PICTURE X.                                   *
                                 03  DN8 PICTURE X.                                   *
                                 03  DN9 PICTURE X.                                   *
              """.formatted(input)
          )
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
      "COPY INCEPTION.                                          *",
      "EXEC SQL INCLUDE INCEPTION END-EXEC.                     *",
    })
    void onlyFindMissingCopybooks(String input) {
        rewriteRun(
          spec -> spec.recipe(new FindCopybook(null, true)).dataTable(Row.class, rows -> {
              assertThat(rows).extracting(CopybookSource.Row::getCopybookName).containsOnly("MISSING");
              assertThat(rows).extracting(Row::getResolutionStatus).containsOnly(MISSING_SOURCE);
          }),
          cobol(
            """
              000000 IDENTIFICATION DIVISION.                                         *
                     PROGRAM-ID. IC109A.                                              *
                     DATA DIVISION.                                                   *
                     LINKAGE SECTION.                                                 *
                         01  GRP-01.                                                  *
                             COPY MISSING.                                            *
                             EXEC SQL INCLUDE MISSING END-EXEC.                       *
                             %s
              """.formatted(input),
            """
              000000 IDENTIFICATION DIVISION.                                         *
                     PROGRAM-ID. IC109A.                                              *
                     DATA DIVISION.                                                   *
                     LINKAGE SECTION.                                                 *
                         01  GRP-01.                                                  *
                             COPY ~~>MISSING.                                            *
                             EXEC SQL INCLUDE ~~>MISSING END-EXEC.                       *
                             %s
              """.formatted(input)
          )
        );
    }

    @Test
    void isUsedInIncludeStatement() {
        rewriteRun(
          spec -> spec.recipe(new FindCopybook("INCEPTION", false)).dataTable(Row.class, rows -> {
              assertThat(rows).extracting(CopybookSource.Row::getCopybookName).containsOnly("INCEPTION");
              assertThat(rows).extracting(Row::getResolutionStatus).containsOnly(RESOLVED);
          }),
          cobol(
            """
              000000 IDENTIFICATION DIVISION.                                         *
                     PROGRAM-ID. IC109A.                                              *
                     DATA DIVISION.                                                   *
                     LINKAGE SECTION.                                                 *
                         01  GRP-01.                                                  *
                             EXEC SQL INCLUDE INCEPTION END-EXEC.                     *
              """,
            """
              000000 IDENTIFICATION DIVISION.                                         *
                     PROGRAM-ID. IC109A.                                              *
                     DATA DIVISION.                                                   *
                     LINKAGE SECTION.                                                 *
                         01  GRP-01.                                                  *
                             EXEC SQL INCLUDE ~~>INCEPTION END-EXEC.                     *
              """
          )
        );
    }

    @Test
    void sm206a() {
        rewriteRun(
          spec -> spec.dataTable(Row.class, rows -> {
              assertThat(rows).extracting(Row::getCopybookName)
                .containsExactly(IntStream.range(1, 10).mapToObj(n -> "KP00" + n).toArray(String[]::new));
              assertThat(rows).extracting(Row::getResolutionStatus).containsOnly(RESOLVED);
          }),
          cobol(
            getNistResource("SM206A.CBL"),
            "",
            spec -> spec.after(s -> s)
          )
        );
    }

    @Test
    void aSymbolicMapIsReportedFromItsMapset() {
        List<SourceFile> mapset = BmsParser.builder().build().parseInputs(singletonList(
            new Parser.Input(Paths.get("COSGN00.bms"), () -> new ByteArrayInputStream(
              """
                COSGN00 DFHMSD LANG=COBOL,MODE=INOUT,STORAGE=AUTO
                COSGN0A DFHMDI SIZE=(24,80)
                TRNNAME DFHMDF POS=(1,8),LENGTH=4
                        DFHMSD TYPE=FINAL
                        END
                """.getBytes(UTF_8)))),
          null, new InMemoryExecutionContext()).collect(toList());

        rewriteRun(
          spec -> spec.recipe(new FindCopybook(null, false)).dataTable(Row.class, rows ->
            assertThat(rows).singleElement().satisfies(row -> {
                assertThat(row.getCopybookSourcePath()).isEqualTo("COSGN00.bms");
                assertThat(row.getResolutionStatus()).isEqualTo(RESOLVED);
            })),
          new SourceSpec<>(Cobol.CompilationUnit.class, null, CobolParser.builder().copybooks(mapset),
            """
              000000 IDENTIFICATION DIVISION.                                         *
                     PROGRAM-ID. SIGNON.                                              *
                     DATA DIVISION.                                                   *
                     WORKING-STORAGE SECTION.                                         *
                         COPY COSGN00.                                                *
              """,
            SourceSpec.ValidateSource.noop, ctx -> {
          }).after(s -> s.replace("COPY COSGN00.", "COPY ~~>COSGN00."))
        );
    }
}
