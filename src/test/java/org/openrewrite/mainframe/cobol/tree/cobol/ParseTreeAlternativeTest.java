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
package org.openrewrite.mainframe.cobol.tree.cobol;

import org.junit.jupiter.api.Test;
import org.openrewrite.ExecutionContext;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.cobol.CobolIsoVisitor;
import org.openrewrite.mainframe.cobol.CobolParser;
import org.openrewrite.mainframe.cobol.CobolTest;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.tree.ParseError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.mainframe.cobol.Assertions.cobol;
import static org.openrewrite.test.RewriteTest.toRecipe;

/**
 * A grammar rule with several alternatives reaches the LST through whichever parse-tree child is
 * present. These pin alternatives the NIST programs never use.
 */
class ParseTreeAlternativeTest extends CobolTest {

    private List<Class<?>> nodesIn(String source) {
        List<Class<?>> nodes = new ArrayList<>();
        rewriteRun(
          spec -> spec.recipe(toRecipe(() -> new CobolIsoVisitor<ExecutionContext>() {
              @Override
              public Cobol.ScreenDescriptionBackgroundColorClause visitScreenDescriptionBackgroundColorClause(
                Cobol.ScreenDescriptionBackgroundColorClause clause, ExecutionContext ctx) {
                  nodes.add(clause.getClass());
                  return super.visitScreenDescriptionBackgroundColorClause(clause, ctx);
              }
          })),
          cobol(source)
        );
        return nodes;
    }

    @Test
    void aDataReferenceIsNotAParagraphQualifiedByItsSection() {
        // A paragraph name can be an integer and a data name cannot, so this is not a data reference at all.
        List<SourceFile> parsed = CobolParser.builder().build().parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. QDN2.                                               \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     MOVE 1 TO 100 IN S1.                                        \s
            """
        ).collect(Collectors.toList());
        assertThat(parsed).singleElement().isInstanceOf(ParseError.class);
    }

    @Test
    void aScreenBackgroundColourGivenAsAnInteger() {
        assertThat(nodesIn(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. SCRBG.                                              \s
            000000 DATA DIVISION.                                                  \s
            000000 SCREEN SECTION.                                                 \s
            000000 01  SCREEN1 BACKGROUND-COLOR IS 1.                              \s
            """
        )).containsExactly(Cobol.ScreenDescriptionBackgroundColorClause.class);
    }

    @Test
    void onlyASignedIntegerMakesADataNameSubscriptRelative() {
        List<Long> dimensions = new ArrayList<>();
        rewriteRun(
          spec -> spec.recipe(toRecipe(() -> new CobolIsoVisitor<ExecutionContext>() {
              @Override
              public Cobol.TableCall visitTableCall(Cobol.TableCall tableCall, ExecutionContext ctx) {
                  dimensions.add(tableCall.getSubscripts().get(0).getContents().stream()
                    .filter(Cobol.Subscript.class::isInstance).count());
                  return tableCall;
              }
          })),
          cobol(
            """
              000000 IDENTIFICATION DIVISION.                                        \s
              000000 PROGRAM-ID. RELSUB.                                             \s
              000000 PROCEDURE DIVISION.                                             \s
              000000     MOVE C (R1, 1) TO X                                         \s
              000000     MOVE C (R1 R2 3) TO X                                       \s
              000000     MOVE C (R1 +1) TO X                                         \s
              000000     MOVE C (R1 - 1) TO X.                                       \s
              """
          )
        );
        assertThat(dimensions).containsExactly(2L, 3L, 1L, 1L);
    }

    @Test
    void eachAllLeadingOrFirstOpensAnInspectGroup() {
        List<String> groups = new ArrayList<>();
        rewriteRun(
          spec -> spec.recipe(toRecipe(() -> new CobolIsoVisitor<ExecutionContext>() {
              @Override
              public Cobol.InspectAllLeadings visitInspectAllLeadings(Cobol.InspectAllLeadings leadings, ExecutionContext ctx) {
                  groups.add(leadings.getWord().getWord() + " " + leadings.getLeadings().stream()
                    .map(l -> l.getName().getClass().getSimpleName()).collect(Collectors.joining(" ")));
                  return super.visitInspectAllLeadings(leadings, ctx);
              }

              @Override
              public Cobol.InspectReplacingAllLeadings visitInspectReplacingAllLeadings(Cobol.InspectReplacingAllLeadings leadings, ExecutionContext ctx) {
                  groups.add(leadings.getWord().getWord() + " " + leadings.getInspections().size());
                  return super.visitInspectReplacingAllLeadings(leadings, ctx);
              }
          })),
          cobol(
            """
              000000 IDENTIFICATION DIVISION.                                        \s
              000000 PROGRAM-ID. INSPGRP.                                            \s
              000000 PROCEDURE DIVISION.                                             \s
              000000     INSPECT X TALLYING C FOR ALL 'S' ALL 'U'                   \s
              000000         LEADING SPACES LOW-VALUES                              \s
              000000     INSPECT X REPLACING ALL 'S' BY '1' ALL 'U' BY '1' '0' BY '9'\s
              000000         FIRST 'A' BY 'B'.                                      \s
              """
          )
        );
        assertThat(groups).containsExactly("ALL Word", "ALL Word", "LEADING FigurativeConstant FigurativeConstant",
          "ALL 1", "ALL 2", "FIRST 1");
    }
}
