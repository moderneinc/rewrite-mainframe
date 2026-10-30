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
import org.openrewrite.test.RewriteTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.mainframe.cobol.Assertions.cobol;
import static org.openrewrite.test.RewriteTest.toRecipe;

/**
 * Each program ends in an {@code XCTL} per item under test, and each item is asked about where its
 * {@code XCTL} names it.
 */
class LiteralAssignmentTest implements RewriteTest {

    private Map<String, @Nullable Set<String>> candidates(String source) {
        Map<String, @Nullable Set<String>> found = new HashMap<>();
        rewriteRun(
          spec -> spec.recipe(toRecipe(() -> new CicsCommand.Matcher().<ExecutionContext>asVisitor((command, ctx) -> {
              if (command.isVerb("XCTL")) {
                  String operand = command.getOption("PROGRAM");
                  found.put(operand, LiteralAssignment.candidates(command.getCursor(), operand));
              }
              return command.getTree();
          }))),
          cobol(source)
        );
        return found;
    }

    @Test
    void readsLeaveTheSetComplete() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. READS.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 01 WS-NAMES.
            000000    05 WS-PGM PIC X(8) VALUE 'PGMA'.
            000000    05 WS-IX PIC 9 VALUE 1.
            000000 01 WS-TABLE.
            000000    05 WS-ENTRY PIC X(8) OCCURS 9.
            000000 77 WS-COPY PIC X(8).
            000000 PROCEDURE DIVISION.
            000000     MOVE 'PGMB' TO WS-PGM OF WS-NAMES.
            000000     IF WS-PGM = 'PGMC' DISPLAY WS-PGM END-IF.
            000000     EVALUATE WS-PGM WHEN 'PGMD' CONTINUE END-EVALUATE.
            000000     MOVE WS-PGM TO WS-COPY.
            000000     MOVE WS-PGM TO WS-ENTRY(WS-IX).
            000000     CALL 'LOGGER' USING BY CONTENT WS-PGM.
            000000     EXEC SQL SELECT A INTO :WS-COPY FROM T WHERE B = :WS-PGM
            000000     END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-PGM) COMMAREA(WS-NAMES)
            000000     END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-IX) END-EXEC.
            000000     GOBACK.
            """
        );

        assertThat(found.get("WS-PGM")).containsExactlyInAnyOrder("PGMA", "PGMB");
        // A subscript reads its item.
        assertThat(found.get("WS-IX")).isEmpty();
    }

    @Test
    void moveFromAnIdentifierMakesTheSetIncomplete() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. MOVES.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 77 WS-PGM PIC X(8) VALUE 'PGMA'.
            000000 77 WS-INPUT PIC X(8).
            000000 PROCEDURE DIVISION.
            000000     MOVE WS-INPUT TO WS-PGM.
            000000     EXEC CICS XCTL PROGRAM(WS-PGM) END-EXEC.
            000000     GOBACK.
            """
        );

        assertThat(found).containsEntry("WS-PGM", null);
    }

    @Test
    void writeToAnEnclosingGroupMakesTheSetIncomplete() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. GROUPS.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 01 CARDDEMO-COMMAREA.
            000000    05 CDEMO-GENERAL-INFO.
            000000       10 CDEMO-TO-PROGRAM PIC X(8).
            000000    05 CDEMO-OTHER PIC X(8).
            000000 77 WS-PGM PIC X(8).
            000000 LINKAGE SECTION.
            000000 01 DFHCOMMAREA PIC X(16).
            000000 PROCEDURE DIVISION.
            000000     MOVE DFHCOMMAREA(1:EIBCALEN) TO CARDDEMO-COMMAREA.
            000000     MOVE 'COSGN00C' TO CDEMO-TO-PROGRAM.
            000000     MOVE 'PGMA' TO WS-PGM.
            000000     EXEC CICS XCTL PROGRAM(CDEMO-TO-PROGRAM) END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-PGM) END-EXEC.
            000000     GOBACK.
            """
        );

        assertThat(found).containsEntry("CDEMO-TO-PROGRAM", null);
        assertThat(found.get("WS-PGM")).containsExactly("PGMA");
    }

    @Test
    void writeToAnOverlappingItemMakesTheSetIncomplete() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. OVERLAP.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 01 WS-NAMES.
            000000    05 WS-PGM PIC X(8) VALUE 'PGMA'.
            000000    05 WS-ALIAS REDEFINES WS-PGM.
            000000       10 WS-PREFIX PIC X(3).
            000000       10 WS-SUFFIX PIC X(5).
            000000 01 WS-GROUP.
            000000    05 WS-PART PIC X(8) VALUE 'PGMC'.
            000000 PROCEDURE DIVISION.
            000000     MOVE 'XYZ' TO WS-PREFIX.
            000000     MOVE 'PGMB' TO WS-GROUP.
            000000     EXEC CICS XCTL PROGRAM(WS-PGM) END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-PART) END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-GROUP) END-EXEC.
            000000     GOBACK.
            """
        );

        assertThat(found).containsEntry("WS-PGM", null);
        assertThat(found).containsEntry("WS-PART", null);
        assertThat(found.get("WS-GROUP")).containsExactly("PGMB");
    }

    @Test
    void referenceModifiedLiteralMakesTheSetIncomplete() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. REFMOD.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 77 WS-PGM PIC X(8) VALUE 'PGMA'.
            000000 PROCEDURE DIVISION.
            000000     MOVE 'X' TO WS-PGM(4:1).
            000000     EXEC CICS XCTL PROGRAM(WS-PGM) END-EXEC.
            000000     GOBACK.
            """
        );

        assertThat(found).containsEntry("WS-PGM", null);
    }

    @Test
    void anyOtherStatementNamingTheItemMakesTheSetIncomplete() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. STMTS.
            000000 ENVIRONMENT DIVISION.
            000000 INPUT-OUTPUT SECTION.
            000000 FILE-CONTROL.
            000000     SELECT PGMFILE ASSIGN TO PGMFILE.
            000000 DATA DIVISION.
            000000 FILE SECTION.
            000000 FD PGMFILE.
            000000 01 PGM-REC PIC X(8).
            000000 WORKING-STORAGE SECTION.
            000000 77 WS-READ PIC X(8) VALUE 'PGMA'.
            000000 PROCEDURE DIVISION.
            000000     READ PGMFILE INTO WS-READ.
            000000     EXEC CICS XCTL PROGRAM(WS-READ) END-EXEC.
            000000     GOBACK.
            """
        );

        assertThat(found).containsEntry("WS-READ", null);
    }

    @Test
    void cicsOptionsReturningDataMakeTheSetIncomplete() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. CICSOUT.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 77 WS-RETRIEVED PIC X(8) VALUE 'PGMA'.
            000000 77 WS-KEY PIC X(8) VALUE 'PGMB'.
            000000 77 WS-LINKED PIC X(8) VALUE 'PGMC'.
            000000 77 WS-REC PIC X(80).
            000000 PROCEDURE DIVISION.
            000000     EXEC CICS RETRIEVE INTO(WS-RETRIEVED) END-EXEC.
            000000     EXEC CICS READNEXT FILE('PGMS') INTO(WS-REC)
            000000          RIDFLD(WS-KEY) END-EXEC.
            000000     EXEC CICS LINK PROGRAM('LOOKUP') COMMAREA(WS-LINKED)
            000000     END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-RETRIEVED) END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-KEY) END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-LINKED) END-EXEC.
            000000     GOBACK.
            """
        );

        assertThat(found).containsEntry("WS-RETRIEVED", null);
        // READNEXT returns the key of the record it read.
        assertThat(found).containsEntry("WS-KEY", null);
        // The linked program can change the commarea it is handed.
        assertThat(found).containsEntry("WS-LINKED", null);
    }

    @Test
    void sqlIntoMakesTheSetIncomplete() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. SQLINTO.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 77 WS-SELECTED PIC X(8) VALUE 'PGMA'.
            000000 77 WS-FETCHED PIC X(8) VALUE 'PGMB'.
            000000 PROCEDURE DIVISION.
            000000     EXEC SQL SELECT PGM INTO :WS-SELECTED FROM T END-EXEC.
            000000     EXEC SQL FETCH C1 INTO :WS-FETCHED END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-SELECTED) END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-FETCHED) END-EXEC.
            000000     GOBACK.
            """
        );

        assertThat(found).containsEntry("WS-SELECTED", null);
        assertThat(found).containsEntry("WS-FETCHED", null);
    }

    @Test
    void callByReferenceMakesTheSetIncomplete() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. CALLS.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 77 WS-DEFAULT PIC X(8) VALUE 'PGMA'.
            000000 77 WS-EXPLICIT PIC X(8) VALUE 'PGMB'.
            000000 77 WS-CONTENT PIC X(8) VALUE 'PGMC'.
            000000 PROCEDURE DIVISION.
            000000     CALL 'ROUTER' USING WS-DEFAULT BY CONTENT WS-CONTENT
            000000                         BY REFERENCE WS-EXPLICIT.
            000000     EXEC CICS XCTL PROGRAM(WS-DEFAULT) END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-EXPLICIT) END-EXEC.
            000000     GOBACK.
            """
        );

        assertThat(found).containsEntry("WS-DEFAULT", null);
        assertThat(found).containsEntry("WS-EXPLICIT", null);
    }

    @Test
    void dliCallWritesWhatItReturns() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. DLICALL.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.
            000000 77 XRST-FUNC PIC X(4) VALUE 'XRST'.
            000000 77 WS-SSA PIC X(9) VALUE 'ACCOUNT '.
            000000 77 WS-IO PIC X(8) VALUE 'PGMA'.
            000000 77 WS-SAVED PIC X(8) VALUE 'PGMB'.
            000000 PROCEDURE DIVISION.
            000000     CALL 'CBLTDLI' USING GU-FUNC DB-PCB WS-IO WS-SSA.
            000000     CALL 'CBLTDLI' USING XRST-FUNC IO-PCB WS-IO WS-SAVED.
            000000     EXEC CICS XCTL PROGRAM(GU-FUNC) END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-SSA) END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-IO) END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-SAVED) END-EXEC.
            000000     GOBACK.
            """
        );

        assertThat(found.get("GU-FUNC")).containsExactly("GU");
        assertThat(found.get("WS-SSA")).containsExactly("ACCOUNT");
        assertThat(found).containsEntry("WS-IO", null);
        // A restart writes the areas after the I/O area.
        assertThat(found).containsEntry("WS-SAVED", null);
    }

    @Test
    void setOfAConditionNameMakesTheSetIncomplete() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. SETCOND.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 77 WS-PGM PIC X(8) VALUE 'PGMA'.
            000000    88 PGM-IS-B VALUE 'PGMB'.
            000000 PROCEDURE DIVISION.
            000000     SET PGM-IS-B TO TRUE.
            000000     EXEC CICS XCTL PROGRAM(WS-PGM) END-EXEC.
            000000     GOBACK.
            """
        );

        assertThat(found).containsEntry("WS-PGM", null);
    }

    @Test
    void storageOthersWriteMakesTheSetIncomplete() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. STORAGE.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 01 WS-SHARED EXTERNAL PIC X(8).
            000000 LINKAGE SECTION.
            000000 01 LK-PGM PIC X(8).
            000000 PROCEDURE DIVISION.
            000000     MOVE 'PGMA' TO WS-SHARED LK-PGM.
            000000     EXEC CICS XCTL PROGRAM(WS-SHARED) END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(LK-PGM) END-EXEC.
            000000     EXEC CICS XCTL PROGRAM(WS-UNDECLARED) END-EXEC.
            000000     GOBACK.
            """
        );

        assertThat(found).containsEntry("WS-SHARED", null);
        assertThat(found).containsEntry("LK-PGM", null);
        // An item the program does not declare could share storage with anything.
        assertThat(found).containsEntry("WS-UNDECLARED", null);
    }

    @Test
    void figurativeConstantsLeaveTheSetComplete() {
        Map<String, @Nullable Set<String>> found = candidates(
          """
            000000 IDENTIFICATION DIVISION.
            000000 PROGRAM-ID. CLEARS.
            000000 DATA DIVISION.
            000000 WORKING-STORAGE SECTION.
            000000 01 WS-NAMES.
            000000    05 WS-PGM PIC X(8).
            000000 PROCEDURE DIVISION.
            000000     INITIALIZE WS-NAMES.
            000000     MOVE SPACES TO WS-PGM.
            000000     MOVE 'PGMA' TO WS-PGM.
            000000     EXEC CICS XCTL PROGRAM(WS-PGM) END-EXEC.
            000000     GOBACK.
            """
        );

        // Spaces or zeros are no resource name, any more than VALUE SPACES is.
        assertThat(found.get("WS-PGM")).containsExactly("PGMA");
    }
}
