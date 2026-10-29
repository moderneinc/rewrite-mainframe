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
}
