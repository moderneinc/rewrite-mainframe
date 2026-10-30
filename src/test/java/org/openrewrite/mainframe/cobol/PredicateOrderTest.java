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

import org.junit.jupiter.api.Test;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.cobol.tree.Cobol;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class PredicateOrderTest {

    @Test
    void predicatesCollectedInAnotherOrderMakeNoSecondConfiguration() {
        // The cache is shared with every parser in the JVM, including ones built on ANTLR's own simulator.
        org.antlr.v4.runtime.Parser grammar = new org.openrewrite.mainframe.cobol.internal.grammar.CobolParser(null);
        PredictionCache.clear(grammar);

        List<SourceFile> parsed = CobolParser.builder().build().parse(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. PREDORD.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 01  T.
            000000     05  E OCCURS 3 TIMES PIC X.
            000000 01  I   PIC 9.
            000000 01  J   PIC 9.
            000000 01  W   PIC X(8).
            000000 PROCEDURE DIVISION.
            000000     IF I = 1
            000000        IF J = 2
            000000           MOVE E (I) TO W
            000000        ELSE
            000000           EVALUATE I
            000000             WHEN 1 MOVE W (1:2) TO E (J)
            000000             WHEN OTHER
            000000               IF J = 3 DISPLAY FUNCTION MAX(I J) END-IF
            000000           END-EVALUATE
            000000     ELSE
            000000        PERFORM UNTIL I > 3
            000000           ADD 1 TO I ON SIZE ERROR DISPLAY 'Y'
            000000           SEARCH E WHEN E (I) = 'X' MOVE 1 TO J
            000000           END-SEARCH
            000000        END-PERFORM
            000000     END-IF.
            000000     STOP RUN.
            """
        ).collect(toList());

        assertThat(parsed).singleElement().isInstanceOf(Cobol.CompilationUnit.class);
        assertThat(Arrays.stream(PredictionCache.shared(grammar))
          .flatMap(dfa -> dfa.states.keySet().stream())
          .mapToInt(PredictionCache::orderOnlyDuplicates)
          .sum()).isZero();
    }
}
