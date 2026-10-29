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

import static org.openrewrite.mainframe.cobol.Assertions.cobolPostProcess;

class PartialWordReplacingTest extends CobolTest {

    /**
     * A reductive replacement chooses its type by comparing the number of words it matches against
     * the number it replaces them with. It used to compare against the number of <em>sites</em> the
     * rule matched instead, so a rule was silently skipped whenever it happened to match exactly as
     * many times as it has words — three words matching at three sites, here.
     * <p>
     * Nothing fails when that happens: the copybook is expanded with the library text intact, and
     * {@code DISPLAY OLD ONE TWO} is as valid as {@code DISPLAY NEWNAME}. The program parses, and
     * every analysis built on it is wrong.
     */
    @Test
    void reductiveReplacementMatchingAsManyTimesAsItHasWords() {
        rewriteRun(
          cobolPostProcess(
            """
              000001 IDENTIFICATION DIVISION.                                         000000000
              000002 PROGRAM-ID. REDUC3.                                              000000000
              000003 PROCEDURE DIVISION.                                              000000000
              000004     COPY REDUC3 REPLACING                                        000000000
              000005       ==OLD ONE TWO== BY ==NEWNAME== .                           000000000""",
            """
              IDENTIFICATION DIVISION.
              PROGRAM-ID. REDUC3.
              PROCEDURE DIVISION.
                  DISPLAY        NEWNAME
                  DISPLAY        NEWNAME
                  DISPLAY        NEWNAME
              .
              """
          )
        );
    }

    @Test
    void replacesInsideAndAtTheEndOfAWord() {
        rewriteRun(
          cobolPostProcess(
            """
              000001 IDENTIFICATION DIVISION.                                         000000000
              000002 PROGRAM-ID. PARTWRD.                                             000000000
              000003 PROCEDURE DIVISION.                                              000000000
              000004     COPY PARTWRD REPLACING                                       000000000
              000005       ==(TESTVAR1)== BY ==DESCRIPTION==                          000000000
              000006       ==(SCRNVAR2)== BY ==TRTYDSC== .                            000000000""",
            """
              IDENTIFICATION DIVISION.
              PROGRAM-ID. PARTWRD.
              PROCEDURE DIVISION.
                  IF      FLG-DESCRIPTION-NOT-OK
                     MOVE 'X' TO    TRTYDSCC
                  END-IF.
              """
          )
        );
    }

    @Test
    void replacesAtTheStartOfAWord() {
        rewriteRun(
          // Text the program wrote itself prints back from the replacement, which copied text never exercises.
          cobolPostProcess(
            """
              000001 IDENTIFICATION DIVISION.                                         000000000
              000002 PROGRAM-ID. TAGWRD.                                              000000000
              000003 DATA DIVISION.                                                   000000000
              000004 WORKING-STORAGE SECTION.                                         000000000
              000005 REPLACE ==:TAG:== BY ==CUST==.                                   000000000
              000006 01  :TAG:-REC.                                                   000000000
              000007     05  :TAG:-NAME          PIC X(17).                           000000000
              000008 REPLACE OFF.                                                     000000000""",
            """
              IDENTIFICATION DIVISION.
              PROGRAM-ID. TAGWRD.
              DATA DIVISION.
              WORKING-STORAGE SECTION.
              01   CUST-REC.
                  05   CUST-NAME          PIC X(17).
              """
          )
        );
    }
}
