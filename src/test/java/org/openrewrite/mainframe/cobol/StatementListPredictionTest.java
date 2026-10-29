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

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.DecisionInfo;
import org.junit.jupiter.api.Test;
import org.openrewrite.mainframe.cobol.internal.grammar.CobolLexer;
import org.openrewrite.mainframe.cobol.internal.grammar.CobolParser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins that the predicates on optional statement-list endings keep SLL prediction from retrying with full context.
 * The rationale sits with {@code startsStatement} in the grammar.
 */
class StatementListPredictionTest {

    @Test
    void nestedIfsWithoutEndIf() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. NESTIF.
          PROCEDURE DIVISION.
              IF A = 1
                 IF B = 2
                    MOVE 1 TO C
                    IF D = 3
                       MOVE 2 TO E
                       DISPLAY 'X'
                    ELSE
                       MOVE 3 TO F
                 ELSE
                    MOVE 4 TO G
              END-IF
              MOVE 5 TO H.
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "ifStatement", "ifThen", "ifElse")).isZero();
    }

    @Test
    void nestedEvaluateAndSearchWithoutTerminators() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. NESTEVAL.
          PROCEDURE DIVISION.
              EVALUATE A
                WHEN 1
                  EVALUATE B
                    WHEN 2 MOVE 1 TO C
                    WHEN 3 WHEN 4 MOVE 2 TO C
                    WHEN OTHER MOVE 3 TO C
                WHEN OTHER
                  MOVE 4 TO C
              END-EVALUATE
              SEARCH T AT END MOVE 1 TO X
                WHEN T-K(I) = 1 MOVE 2 TO X
                WHEN T-K(I) = 2 SEARCH U WHEN U-K(J) = 1 MOVE 3 TO X
              END-SEARCH.
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "evaluateStatement", "evaluateWhenPhrase", "evaluateWhenOther",
          "searchStatement", "searchWhen")).isZero();
    }

    @Test
    void nestedConditionalPhrases() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. NESTPHR.
          PROCEDURE DIVISION.
              READ F AT END
                 ADD 1 TO X ON SIZE ERROR
                    STRING A DELIMITED BY SIZE INTO B ON OVERFLOW
                       CALL 'P' ON EXCEPTION
                          WRITE R INVALID KEY
                             MOVE 1 TO Y
                          NOT INVALID KEY
                             MOVE 2 TO Y
                       NOT ON EXCEPTION
                          MOVE 3 TO Y
                    NOT ON OVERFLOW
                       MOVE 4 TO Y
                 NOT ON SIZE ERROR
                    MOVE 5 TO Y
              NOT AT END
                 WRITE R AT END-OF-PAGE
                    MOVE 6 TO Y
                 NOT AT EOP
                    RECEIVE CD-IN MESSAGE INTO M NO DATA
                       MOVE 7 TO Y
                    WITH DATA
                       MOVE 8 TO Y
              END-READ.
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "atEndPhrase", "notAtEndPhrase", "onSizeErrorPhrase",
          "notOnSizeErrorPhrase", "onOverflowPhrase", "notOnOverflowPhrase", "onExceptionClause",
          "notOnExceptionClause", "invalidKeyPhrase", "notInvalidKeyPhrase", "writeAtEndOfPagePhrase",
          "writeNotAtEndOfPagePhrase", "receiveNoData", "receiveWithData")).isZero();
    }

    /**
     * How often prediction in the decisions of the given rules retried with full context, over one parse of the
     * program. The program is the text the preprocessor hands the grammar, so it carries no sequence numbers.
     */
    private static long fullContextFallbacks(String program, String... rules) {
        CobolLexer lexer = new CobolLexer(CharStreams.fromString(program));
        CobolParser parser = new CobolParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                                    int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException(line + ":" + charPositionInLine + " " + msg);
            }
        });
        parser.setProfile(true);
        parser.compilationUnit();

        Set<String> ruleSet = new HashSet<>(Arrays.asList(rules));
        long fallbacks = 0;
        for (DecisionInfo decision : parser.getParseInfo().getDecisionInfo()) {
            String rule = parser.getRuleNames()[parser.getATN().getDecisionState(decision.decision).ruleIndex];
            if (ruleSet.contains(rule)) {
                fallbacks += decision.LL_Fallback;
            }
        }
        return fallbacks;
    }
}
