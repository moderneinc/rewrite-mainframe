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
package org.openrewrite.mainframe.cobol;

import org.junit.jupiter.api.Test;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.cobol.tree.Cobol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CobolVisitorTest {

    @Test
    void visitsEachWordOfAnEvaluateOnce() {
        SourceFile cu = CobolParser.builder().build().parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. EVAL.                                               \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     EVALUATE TRUE ALSO WS-B                                     \s
            000000         WHEN WS-A = 1 ALSO 2 THRU 3                             \s
            000000             CONTINUE                                            \s
            000000     END-EVALUATE.                                               \s
            """
        ).findFirst().orElseThrow();

        Set<UUID> visited = new HashSet<>();
        List<String> visitedAgain = new ArrayList<>();
        new CobolIsoVisitor<Integer>() {
            @Override
            public Cobol.Word visitWord(Cobol.Word word, Integer p) {
                if (!visited.add(word.getId())) {
                    visitedAgain.add(word.getWord());
                }
                return super.visitWord(word, p);
            }
        }.visit(cu, 0);

        assertThat(visited).isNotEmpty();
        assertThat(visitedAgain).isEmpty();
    }
}
