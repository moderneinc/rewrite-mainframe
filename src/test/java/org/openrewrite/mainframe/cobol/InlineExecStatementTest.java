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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openrewrite.mainframe.cobol.tree.Cobol;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.mainframe.cobol.Assertions.cobol;

class InlineExecStatementTest extends CobolTest {
    private static String source(String body) {
        return ("IDENTIFICATION DIVISION.\nPROGRAM-ID. INLINEEXEC.\nDATA DIVISION.\n" +
          "WORKING-STORAGE SECTION.\n01 FLAG-NUM PIC 9.\nPROCEDURE DIVISION.\nMAIN-PARA.\n" +
          body + "\nGOBACK.\n").lines().map(line -> "000000 " + line + "\n").reduce("", String::concat);
    }

    private static Class<?> statementType(String command) {
        if (command.startsWith("EXEC CICS")) return Cobol.ExecCicsStatement.class;
        if (command.startsWith("EXEC DLI")) return Cobol.ExecDliStatement.class;
        if (command.startsWith("EXEC SQLIMS")) return Cobol.ExecSqlImsStatement.class;
        return Cobol.ExecSqlStatement.class;
    }

    @ParameterizedTest
    @ValueSource(strings = {"EXEC CICS RETURN END-EXEC", "EXEC SQL COMMIT END-EXEC",
      "EXEC DLI GU END-EXEC", "EXEC SQLIMS COMMIT END-EXEC"})
    void whenAndCommandCanShareALine(String command) {
        rewriteRun(cobol(source("EVALUATE FLAG-NUM\nWHEN 1 " + command + "\nWHEN OTHER CONTINUE\nEND-EVALUATE."),
          spec -> spec.afterRecipe(cu -> new CobolIsoVisitor<Integer>() {
              @Override
              public Cobol.Evaluate visitEvaluate(Cobol.Evaluate evaluate, Integer p) {
                  assertThat(evaluate.getWhenPhrase()).hasSize(1);
                  assertThat(evaluate.getWhenPhrase().get(0).getStatements()).singleElement().isInstanceOf(statementType(command));
                  assertThat(evaluate.getWhenOther().getStatements()).singleElement().isInstanceOf(Cobol.Continue.class);
                  return super.visitEvaluate(evaluate, p);
              }
          }.visit(cu, 0))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"EXEC CICS RETURN END-EXEC", "EXEC SQL COMMIT END-EXEC",
      "EXEC DLI GU END-EXEC", "EXEC SQLIMS COMMIT END-EXEC"})
    void conditionsBeforeACommandKeepTheirSource(String command) {
        rewriteRun(cobol(source("IF FLAG-NUM = 1 " + command + "\nEND-IF.")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"EXEC CICS RETURN END-EXEC", "EXEC SQL COMMIT END-EXEC",
      "EXEC DLI GU END-EXEC", "EXEC SQLIMS COMMIT END-EXEC"})
    void ordinaryStatementsBeforeACommandKeepTheirSource(String command) {
        rewriteRun(cobol(source("MOVE 1 TO FLAG-NUM " + command + ".")));
    }

    @Test
    void twoCommandsAndTheirTerminatingPeriodCanShareALine() {
        rewriteRun(cobol(source("EXEC CICS RETURN END-EXEC EXEC SQL COMMIT END-EXEC.")));
    }
    @ParameterizedTest
    @ValueSource(strings = {"EXEC CICS RETURN END-EXEC", "EXEC SQL COMMIT END-EXEC",
      "EXEC DLI GU END-EXEC", "EXEC SQLIMS COMMIT END-EXEC"})
    void ordinarySourceAfterTheCommandCanStayOnTheSameLine(String command) {
        rewriteRun(cobol(source("IF FLAG-NUM = 1 " + command + " ELSE CONTINUE\nEND-IF.")));
        rewriteRun(cobol(source("EVALUATE FLAG-NUM\nWHEN 1 " + command + " WHEN OTHER CONTINUE\nEND-EVALUATE.")));
    }

    @Test
    void multilineCommandsCanEndBeforeOrdinarySourceOnTheSameLine() {
        rewriteRun(cobol(source("IF FLAG-NUM = 1 EXEC CICS SEND TEXT FROM(BUFFER-TEXT)\n" +
          "LENGTH(16) RESP(RESPONSE) END-EXEC END-IF.")));
        rewriteRun(cobol(source("EVALUATE FLAG-NUM\nWHEN 1 EXEC CICS SEND TEXT FROM(BUFFER-TEXT)\n" +
          "LENGTH(16) RESP(RESPONSE) END-EXEC\nWHEN OTHER CONTINUE\nEND-EVALUATE.")));
    }

    @Test
    void commentsSequenceNumbersAndCrLfKeepTheirExactBytes() {
        String input = source("EVALUATE FLAG-NUM\nWHEN 1 EXEC CICS RETURN END-EXEC\nWHEN OTHER CONTINUE\nEND-EVALUATE.")
          .replace("000000 WHEN 1", "123456 WHEN 1")
          .replace("000000 EVALUATE", "012345* BEFORE SELECTION\n000000 EVALUATE")
          .replace("END-EXEC\n", "END-EXEC *> AFTER COMMAND\n");
        rewriteRun(cobol(input));
        rewriteRun(cobol(input.replace("\n", "\r\n")));
    }

    @Test
    void aLiteralBeforeTheCommandIsNotPartOfItsTemplate() {
        rewriteRun(cobol(source("DISPLAY 'EXEC WHEN' EXEC CICS RETURN END-EXEC.")));
    }

    @Test
    void aFinalCommandDoesNotAddAlignmentWhitespaceAtEndOfFile() {
        rewriteRun(cobol(source("MOVE 1 TO FLAG-NUM EXEC CICS RETURN END-EXEC.")
          .replace("000000 GOBACK.\n", "").stripTrailing()));
        rewriteRun(cobol(source("EXEC CICS RETURN END-EXEC.")
          .replace("000000 GOBACK.\n", "").stripTrailing()));
    }

    @ParameterizedTest
    @ValueSource(ints = {71, 72})
    void finalCommandsAtTheContentBoundaryKeepTrailingSourceSpaces(int column) {
        String line = "000000 MOVE 1 TO FLAG-NUM EXEC CICS RETURN END-EXEC.";
        String padded = line.replace("FLAG-NUM EXEC", "FLAG-NUM" + " ".repeat(column - line.length()) + " EXEC");
        String input = source("MOVE 1 TO FLAG-NUM EXEC CICS RETURN END-EXEC.")
          .replace("000000 GOBACK.\n", "").stripTrailing().replace(line, padded) + "   ";
        rewriteRun(cobol(input));
    }

}
