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

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.openrewrite.ExecutionContext;
import org.openrewrite.mainframe.cobol.CobolIsoVisitor;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.test.RewriteTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.mainframe.cobol.Assertions.cobol;
import static org.openrewrite.test.RewriteTest.toRecipe;

class LiteralsTest implements RewriteTest {

    @Test
    void readsTheKeywordAndNameOfACicsLiteral() {
        List<String> read = new ArrayList<>();
        rewriteRun(
          spec -> spec.recipe(toRecipe(() -> new CobolIsoVisitor<ExecutionContext>() {
              @Override
              public Cobol.TableCall visitTableCall(Cobol.TableCall call, ExecutionContext ctx) {
                  read.add(describe(Literals.cicsLiteralOf(call)));
                  return super.visitTableCall(call, ctx);
              }

              @Override
              public Cobol.ConditionNameReference visitConditionNameReference(Cobol.ConditionNameReference reference,
                                                                              ExecutionContext ctx) {
                  read.add(describe(Literals.cicsLiteralOf(reference)));
                  return super.visitConditionNameReference(reference, ctx);
              }
          })),
          cobol(
            """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. RESPPGM.                                            \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 01  WS-RESP   PIC S9(8) COMP.                                   \s
            000000     88  RESP-NORMAL  VALUE DFHRESP(NORMAL).                     \s
            000000 01  WS-TABLE  PIC X OCCURS 5.                                   \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     IF WS-RESP = DFHVALUE(NORMAL) CONTINUE.                     \s
            000000     IF WS-RESP = DFHRESP(NOTFND) OR DFHRESP(DUPKEY) CONTINUE.   \s
            000000     if ws-resp = dfhresp(sysiderr) continue.                    \s
            000000     MOVE WS-TABLE(1) TO WS-TABLE(2).                            \s
            """
          )
        );

        assertThat(read).containsExactly("DFHRESP NORMAL", "DFHVALUE NORMAL", "DFHRESP NOTFND", "DFHRESP DUPKEY",
          "DFHRESP SYSIDERR", "-", "-");
    }

    private static String describe(Literals.@Nullable CicsLiteral literal) {
        return literal == null ? "-" : literal.getKeyword() + " " + literal.getName();
    }
}
