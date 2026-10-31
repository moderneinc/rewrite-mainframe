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

import org.junit.jupiter.api.Test;
import org.openrewrite.ExecutionContext;
import org.openrewrite.test.RewriteTest;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.openrewrite.mainframe.cobol.Assertions.cobol;
import static org.openrewrite.test.RewriteTest.toRecipe;

class DliCallTest implements RewriteTest {

    private List<DliCall> parse(String source) {
        List<DliCall> found = new ArrayList<>();
        rewriteRun(
          spec -> spec.recipe(toRecipe(() -> new DliCall.Matcher().<ExecutionContext>asVisitor((call, ctx) -> {
              found.add(call);
              return call.getTree();
          }))),
          cobol(source)
        );
        return found;
    }

    @Test
    void resolvesFunctionCodeFromValueClause() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. IMSPGM.                                             \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     ENTRY 'DLITCBL' USING IO-PCB DB-PCB.                        \s
            000000     CALL 'CBLTDLI' USING GU-FUNC DB-PCB IO-AREA.                \s
            000000     GOBACK.                                                     \s
            """
        );

        assertThat(calls).hasSize(1);
        DliCall gu = calls.get(0);
        assertThat(gu.getIface()).isEqualTo("CBLTDLI");
        // The literal is padded to four bytes in the source and trimmed here.
        assertThat(gu.getFunction()).isEqualTo("GU");
        assertThat(gu.getFunctionOperand()).isEqualTo("GU-FUNC");
        assertThat(gu.getPcb()).isEqualTo("DB-PCB");
        assertThat(gu.getIoArea()).isEqualTo("IO-AREA");
        assertThat(gu.getAccess()).isEqualTo(DliCall.Access.READ);
    }

    @Test
    void resolvesFunctionCodeMovedIntoAField() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. IMSPGM.                                             \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 DLI-FUNC PIC X(4).                                           \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     MOVE 'ISRT' TO DLI-FUNC.                                    \s
            000000     CALL 'CBLTDLI' USING DLI-FUNC DB-PCB IO-AREA.               \s
            000000     GOBACK.                                                     \s
            """
        );

        assertThat(calls).hasSize(1);
        assertThat(calls.get(0).getFunction()).isEqualTo("ISRT");
        assertThat(calls.get(0).getAccess()).isEqualTo(DliCall.Access.CREATE);
    }

    @Test
    void leavesTheFunctionUnresolvedWhenSeveralLiteralsReachIt() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. IMSPGM.                                             \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 DLI-FUNC PIC X(4).                                           \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     MOVE 'ISRT' TO DLI-FUNC.                                    \s
            000000     MOVE 'DLET' TO DLI-FUNC.                                    \s
            000000     CALL 'CBLTDLI' USING DLI-FUNC DB-PCB IO-AREA.               \s
            000000     GOBACK.                                                     \s
            """
        );

        assertThat(calls).hasSize(1);
        assertThat(calls.get(0).getFunction()).isNull();
        assertThat(calls.get(0).getAccess()).isEqualTo(DliCall.Access.UNKNOWN);
    }

    @Test
    void recoversPcbOrderFromTheEntryPoint() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. IMSPGM.                                             \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     ENTRY 'DLITCBL' USING IO-PCB ACCT-DB-PCB HIST-DB-PCB.       \s
            000000     CALL 'CBLTDLI' USING GU-FUNC ACCT-DB-PCB IO-AREA.           \s
            000000     GOBACK.                                                     \s
            """
        );

        assertThat(calls).hasSize(1);
        ProgramEntry pcbs = calls.get(0).getEntry();
        assertThat(pcbs).isNotNull();
        assertThat(pcbs.getPcbNames()).containsExactly("IO-PCB", "ACCT-DB-PCB", "HIST-DB-PCB");
        assertThat(pcbs.indexOf("NOT-A-PCB")).isEqualTo(-1);
        assertThat(pcbs.isIoPcb("IO-PCB")).isTrue();
        assertThat(pcbs.isIoPcb("ACCT-DB-PCB")).isFalse();
        // The position is what a PSB listing has to be joined on to name the database.
        assertThat(calls.get(0).getPcbPosition()).isEqualTo(1);
    }

    @Test
    void recoversPcbOrderFromProcedureDivisionUsing() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. IMSPGM.                                             \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 LINKAGE SECTION.                                                \s
            000000 01 IO-PCB PIC X(40).                                            \s
            000000 01 ACCT-DB-PCB PIC X(40).                                       \s
            000000 01 HIST-DB-PCB PIC X(40).                                       \s
            000000 PROCEDURE DIVISION USING IO-PCB                                 \s
            000000     BY REFERENCE ACCT-DB-PCB HIST-DB-PCB.                       \s
            000000     CALL 'CBLTDLI' USING GU-FUNC HIST-DB-PCB IO-AREA.           \s
            000000     GOBACK.                                                     \s
            """
        );

        ProgramEntry pcbs = calls.get(0).getEntry();
        assertThat(pcbs).isNotNull();
        assertThat(pcbs.getPcbNames()).containsExactly("IO-PCB", "ACCT-DB-PCB", "HIST-DB-PCB");
        assertThat(calls.get(0).getPcbPosition()).isEqualTo(2);
    }

    @Test
    void distinguishesMessageCallsFromDatabaseCalls() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. IMSPGM.                                             \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     ENTRY 'DLITCBL' USING IO-PCB ACCT-DB-PCB.                   \s
            000000     CALL 'CBLTDLI' USING GU-FUNC IO-PCB MSG-AREA.               \s
            000000     CALL 'CBLTDLI' USING GU-FUNC ACCT-DB-PCB IO-AREA.           \s
            000000     GOBACK.                                                     \s
            """
        );

        assertThat(calls).hasSize(2);
        assertThat(calls.get(0).isMessageCall()).isTrue();
        assertThat(calls.get(1).isMessageCall()).isFalse();
    }

    @Test
    void followsANestedProgramsPcbBackToTheOutermostEntry() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. OUTERPGM.                                           \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 WS-UTIL PIC X(8) VALUE 'DATEUTIL'.                           \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     ENTRY 'DLITCBL' USING IO-PCB ACCT-PCB HIST-PCB.             \s
            000000     MOVE 'TIMEUTIL' TO WS-UTIL.                                 \s
            000000     CALL WS-UTIL USING ACCT-PCB.                                \s
            000000     CALL 'MIDPGM' USING OMITTED HIST-PCB IO-PCB.                \s
            000000     GOBACK.                                                     \s
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. MIDPGM.                                             \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 LINKAGE SECTION.                                                \s
            000000 01 LK-OPT PIC X(8).                                             \s
            000000 01 LK-HIST PIC X(40).                                           \s
            000000 01 LK-TERM PIC X(40).                                           \s
            000000 PROCEDURE DIVISION USING LK-OPT LK-HIST LK-TERM.                \s
            000000     CALL 'CBLTDLI' USING GU-FUNC LK-HIST IO-AREA.               \s
            000000     CALL 'CBLTDLI' USING GU-FUNC LK-TERM MSG-AREA.              \s
            000000     CALL 'INNERPGM' USING LK-HIST.                              \s
            000000     GOBACK.                                                     \s
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. INNERPGM.                                           \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 LINKAGE SECTION.                                                \s
            000000 01 LK-PCB PIC X(40).                                            \s
            000000 PROCEDURE DIVISION USING LK-PCB.                                \s
            000000     CALL 'CBLTDLI' USING GU-FUNC LK-PCB IO-AREA.                \s
            000000     GOBACK.                                                     \s
            000000 END PROGRAM INNERPGM.                                           \s
            000000 END PROGRAM MIDPGM.                                             \s
            000000 END PROGRAM OUTERPGM.                                           \s
            """
        );

        // CALL WS-UTIL can only call DATEUTIL or TIMEUTIL, so it is no caller of MIDPGM.
        assertThat(calls)
          .extracting(call -> Program.nameOf(call.getCursor()), DliCall::getPcbPosition, DliCall::isMessageCall)
          .containsExactly(
            tuple("MIDPGM", 2, false),
            tuple("MIDPGM", 0, true),
            tuple("INNERPGM", 2, false));
    }

    @Test
    void readsAGlobalPcbsPositionFromTheEntryOfTheProgramDeclaringIt() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. OUTERPGM.                                           \s
            000000 DATA DIVISION.                                                  \s
            000000 LINKAGE SECTION.                                                \s
            000000 01 TERM-PCB PIC X(40) GLOBAL.                                   \s
            000000 01 ACCT-PCB PIC X(40) GLOBAL.                                   \s
            000000 01 HIST-PCB PIC X(40).                                          \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     ENTRY 'DLITCBL' USING TERM-PCB ACCT-PCB HIST-PCB.           \s
            000000     CALL 'INNERPGM' USING HIST-PCB.                             \s
            000000     GOBACK.                                                     \s
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. INNERPGM.                                           \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 77 ISRT-FUNC PIC X(4) VALUE 'ISRT'.                             \s
            000000 LINKAGE SECTION.                                                \s
            000000 01 LK-PCB PIC X(40).                                            \s
            000000 PROCEDURE DIVISION USING LK-PCB.                                \s
            000000     CALL 'CBLTDLI' USING GU-FUNC ACCT-PCB IO-AREA.              \s
            000000     CALL 'CBLTDLI' USING GU-FUNC LK-PCB IO-AREA.                \s
            000000     CALL 'CBLTDLI' USING ISRT-FUNC TERM-PCB MSG-AREA WS-MOD.    \s
            000000     GOBACK.                                                     \s
            000000 END PROGRAM INNERPGM.                                           \s
            000000 END PROGRAM OUTERPGM.                                           \s
            """
        );

        assertThat(calls).extracting(DliCall::getPcbPosition, DliCall::isMessageCall, DliCall::getModOperand)
          .containsExactly(
            tuple(1, false, null),
            tuple(2, false, null),
            tuple(0, true, "WS-MOD"));
    }

    @Test
    void keepsADatabasePcbPassedToANestedProgramOffTheMessageQueue() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. BATCHPGM.                                           \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     ENTRY 'DLITCBL' USING ACCT-DB-PCB HIST-DB-PCB.              \s
            000000     CALL 'INNERPGM' USING ACCT-DB-PCB.                          \s
            000000     GOBACK.                                                     \s
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. INNERPGM.                                           \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 LINKAGE SECTION.                                                \s
            000000 01 LK-PCB PIC X(40).                                            \s
            000000 PROCEDURE DIVISION USING LK-PCB.                                \s
            000000     CALL 'CBLTDLI' USING GU-FUNC LK-PCB IO-AREA.                \s
            000000     GOBACK.                                                     \s
            000000 END PROGRAM INNERPGM.                                           \s
            000000 END PROGRAM BATCHPGM.                                           \s
            """
        );

        assertThat(calls).extracting(DliCall::getPcbPosition, DliCall::isMessageCall)
          .containsExactly(tuple(0, false));
    }

    @Test
    void leavesANestedProgramsPcbUnknownWhereItsCallersDoNotSayWhichItIs() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. OUTERPGM.                                           \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     ENTRY 'DLITCBL' USING IO-PCB ACCT-PCB HIST-PCB.             \s
            000000     CALL 'TWOCALLS' USING ACCT-PCB.                             \s
            000000     CALL 'TWOCALLS' USING HIST-PCB.                             \s
            000000     CALL 'SHAREDPGM' USING ACCT-PCB.                            \s
            000000     CALL 'FEWARGS' USING ACCT-PCB.                              \s
            000000     GOBACK.                                                     \s
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. TWOCALLS.                                           \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 LINKAGE SECTION.                                                \s
            000000 01 LK-PCB PIC X(40).                                            \s
            000000 PROCEDURE DIVISION USING LK-PCB.                                \s
            000000     CALL 'CBLTDLI' USING GU-FUNC LK-PCB IO-AREA.                \s
            000000     GOBACK.                                                     \s
            000000 END PROGRAM TWOCALLS.                                           \s
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. SHAREDPGM IS COMMON PROGRAM.                        \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 LINKAGE SECTION.                                                \s
            000000 01 LK-PCB PIC X(40).                                            \s
            000000 PROCEDURE DIVISION USING LK-PCB.                                \s
            000000     CALL 'CBLTDLI' USING GU-FUNC LK-PCB IO-AREA.                \s
            000000     GOBACK.                                                     \s
            000000 END PROGRAM SHAREDPGM.                                          \s
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. FEWARGS.                                            \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 LINKAGE SECTION.                                                \s
            000000 01 LK-KEY PIC X(8).                                             \s
            000000 01 LK-PCB PIC X(40).                                            \s
            000000 PROCEDURE DIVISION USING LK-KEY LK-PCB.                         \s
            000000     CALL 'CBLTDLI' USING GU-FUNC LK-PCB IO-AREA.                \s
            000000     GOBACK.                                                     \s
            000000 END PROGRAM FEWARGS.                                            \s
            000000 END PROGRAM OUTERPGM.                                           \s
            """
        );

        assertThat(calls)
          .extracting(call -> Program.nameOf(call.getCursor()), DliCall::getPcbPosition, DliCall::isMessageCall)
          .containsExactly(
            tuple("TWOCALLS", -1, false),
            tuple("SHAREDPGM", -1, false),
            tuple("FEWARGS", -1, false));
    }

    @Test
    void leavesANestedProgramsPcbUnknownBesideADynamicCall() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. OUTERPGM.                                           \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 WS-PGM PIC X(8).                                             \s
            000000 77 WS-INPUT PIC X(8).                                           \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     ENTRY 'DLITCBL' USING IO-PCB ACCT-PCB.                      \s
            000000     MOVE WS-INPUT TO WS-PGM.                                    \s
            000000     CALL WS-PGM USING IO-PCB.                                   \s
            000000     CALL 'INNERPGM' USING ACCT-PCB.                             \s
            000000     GOBACK.                                                     \s
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. INNERPGM.                                           \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 LINKAGE SECTION.                                                \s
            000000 01 LK-PCB PIC X(40).                                            \s
            000000 PROCEDURE DIVISION USING LK-PCB.                                \s
            000000     CALL 'CBLTDLI' USING GU-FUNC LK-PCB IO-AREA.                \s
            000000     GOBACK.                                                     \s
            000000 END PROGRAM INNERPGM.                                           \s
            000000 END PROGRAM OUTERPGM.                                           \s
            """
        );

        assertThat(calls).extracting(DliCall::getPcbPosition).containsExactly(-1);
    }

    @Test
    void resolvesSegmentNamesFromSsas() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. IMSPGM.                                             \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 77 ACCT-SSA PIC X(9) VALUE 'ACCOUNT '.                          \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     CALL 'CBLTDLI' USING GU-FUNC DB-PCB IO-AREA ACCT-SSA.       \s
            000000     GOBACK.                                                     \s
            """
        );

        assertThat(calls).hasSize(1);
        assertThat(calls.get(0).getSsas()).containsExactly("ACCT-SSA");
        assertThat(calls.get(0).getSegments()).containsExactly("ACCOUNT");
    }

    @Test
    void fallsBackToTheSsaNamingConvention() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. IMSPGM.                                             \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                               \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     CALL 'CBLTDLI' USING GU-FUNC DB-PCB IO-AREA CUSTOMER-SSA.   \s
            000000     GOBACK.                                                     \s
            """
        );

        assertThat(calls.get(0).getSegments()).containsExactly("CUSTOMER");
    }

    /**
     * A program that never moves anything into an SSA writes the segment name as the {@code VALUE}
     * of the group's first field, the eight bytes at offset 0, and builds the qualification after it.
     * That name stands over the data name's: neither SSA here is named for its segment.
     */
    @Test
    void readsAGroupSsaThroughItsFirstField() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                         \s
            000000 PROGRAM-ID. IMSPGM.                                              \s
            000000 DATA DIVISION.                                                   \s
            000000 WORKING-STORAGE SECTION.                                         \s
            000000 77 GU-FUNC PIC X(4) VALUE 'GU  '.                                \s
            000000 01  ROOT-QUAL-SSA.                                               \s
            000000     05  FILLER        PIC X(8)  VALUE 'CLMROOT '.                \s
            000000     05  FILLER        PIC X     VALUE '('.                       \s
            000000     05  FILLER        PIC X(8)  VALUE 'CLMNO   '.                \s
            000000     05  FILLER        PIC X(2)  VALUE ' ='.                      \s
            000000     05  SSA-KEY       PIC X(10).                                 \s
            000000     05  FILLER        PIC X     VALUE ')'.                       \s
            000000 01  CLMROOT-ADJ-SSA.                                             \s
            000000     05  SSA-NAME.                                                \s
            000000         10  FILLER    PIC X(8)  VALUE 'CLMROOT '.                \s
            000000     05  FILLER        PIC X     VALUE ' '.                       \s
            000000 PROCEDURE DIVISION.                                              \s
            000000     CALL 'CBLTDLI' USING GU-FUNC DB-PCB IO-AREA ROOT-QUAL-SSA.   \s
            000000     CALL 'CBLTDLI' USING GU-FUNC DB-PCB IO-AREA CLMROOT-ADJ-SSA. \s
            000000     GOBACK.                                                      \s
            """
        );

        assertThat(calls).extracting(DliCall::getSegments)
          .containsExactly(singletonList("CLMROOT"), singletonList("CLMROOT"));
    }

    @Test
    void readsANestedProgramsCallFromTheDataItCanSee() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. OUTERPGM.                                           \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 01  GU-FUNC PIC X(4) GLOBAL VALUE 'GU  '.                       \s
            000000 01  CHILD-QUAL GLOBAL.                                          \s
            000000     05  FILLER        PIC X(8)  VALUE 'CLMCHILD'.               \s
            000000     05  FILLER        PIC X     VALUE ' '.                      \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     CALL 'CBLTDLI' USING GU-FUNC DB-PCB IO-AREA ROOT-QUAL.      \s
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. INNERPGM.                                           \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 01  ROOT-QUAL.                                                  \s
            000000     05  FILLER        PIC X(8)  VALUE 'CLMROOT '.               \s
            000000     05  FILLER        PIC X     VALUE ' '.                      \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     CALL 'CBLTDLI' USING GU-FUNC DB-PCB IO-AREA ROOT-QUAL       \s
            000000                          CHILD-QUAL.                            \s
            000000 END PROGRAM INNERPGM.                                           \s
            000000 END PROGRAM OUTERPGM.                                           \s
            """
        );

        // Only the nested program declares ROOT-QUAL, so the outer program's call names no segment.
        assertThat(calls).extracting(DliCall::getFunction, DliCall::getSegments).containsExactly(
          tuple("GU", emptyList()),
          tuple("GU", List.of("CLMROOT", "CLMCHILD")));
    }

    /**
     * A checkpoint's later arguments are the areas it saves, which a program declares with values
     * as it declares an SSA, and are no search arguments.
     */
    @Test
    void aCheckpointCarriesNoSsas() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                         \s
            000000 PROGRAM-ID. IMSPGM.                                              \s
            000000 DATA DIVISION.                                                   \s
            000000 WORKING-STORAGE SECTION.                                         \s
            000000 77 CHKP-FUNC PIC X(4) VALUE 'CHKP'.                              \s
            000000 77 CHKP-LEN PIC S9(9) COMP VALUE 8.                              \s
            000000 77 CHKP-ID PIC X(8) VALUE 'CLMCHK01'.                            \s
            000000 PROCEDURE DIVISION.                                              \s
            000000     CALL 'CBLTDLI' USING CHKP-FUNC IO-PCB CHKP-LEN               \s
            000000                          CHKP-ID SAVE-AREA.                      \s
            000000     GOBACK.                                                      \s
            """
        );

        assertThat(calls).singleElement().satisfies(chkp -> {
            assertThat(chkp.getAccess()).isEqualTo(DliCall.Access.CHECKPOINT);
            assertThat(chkp.getSsas()).isEmpty();
            assertThat(chkp.getSegments()).isEmpty();
        });
    }

    @Test
    void keepsEachSsasSegmentInItsPlace() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                         \s
            000000 PROGRAM-ID. IMSPGM.                                              \s
            000000 DATA DIVISION.                                                   \s
            000000 WORKING-STORAGE SECTION.                                         \s
            000000 77 GN-FUNC PIC X(4) VALUE 'GN  '.                                \s
            000000 77 ACCT-SSA PIC X(9) VALUE 'ACCOUNT '.                           \s
            000000 PROCEDURE DIVISION.                                              \s
            000000     CALL 'CBLTDLI' USING GN-FUNC DB-PCB IO-AREA WS-QUAL ACCT-SSA.\s
            000000     GOBACK.                                                      \s
            """
        );

        assertThat(calls).singleElement().satisfies(gn -> {
            assertThat(gn.getSsas()).containsExactly("WS-QUAL", "ACCT-SSA");
            assertThat(gn.getSsaSegments()).containsExactly(null, "ACCOUNT");
            assertThat(gn.getSegments()).containsExactly("ACCOUNT");
        });
    }

    @Test
    void recognisesTheCommandLevelInterface() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. DLIPGM.                                             \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     EXEC DLI GU USING PCB(1) SEGMENT(ACCOUNT) INTO(WS-REC)      \s
            000000          END-EXEC.                                              \s
            000000     GOBACK.                                                     \s
            """
        );

        assertThat(calls).hasSize(1);
        DliCall gu = calls.get(0);
        assertThat(gu.getIface()).isEqualTo("EXEC DLI");
        assertThat(gu.getFunction()).isEqualTo("GU");
        assertThat(gu.getPcb()).isEqualTo("1");
        assertThat(gu.getSegments()).containsExactly("ACCOUNT");
        assertThat(gu.getAccess()).isEqualTo(DliCall.Access.READ);
    }

    /**
     * A program's whole say about a screen under IMS: the MOD's name, passed as the fourth argument
     * of an {@code ISRT} against a message PCB and kept in working storage. Read as a segment search
     * argument it resolves to a plausible segment name no database has, which is the defect this
     * exists to prevent.
     */
    @Test
    void resolvesTheModAnIsrtNames() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. CLMI030.                                            \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 01 WS-MOD-NAMES.                                                \s
            000000     05 MOD-INQ PIC X(08) VALUE 'CLMI1O  '.                      \s
            000000     05 MOD-ACK PIC X(08) VALUE 'CLMI6O  '.                      \s
            000000 77 ISRT-FUNC PIC X(4) VALUE 'ISRT'.                             \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     ENTRY 'DLITCBL' USING IO-PCB ALT-PCB DB-PCB.                \s
            000000     CALL 'CBLTDLI' USING ISRT-FUNC IO-PCB OUT-MSG MOD-INQ.      \s
            000000     CALL 'CBLTDLI' USING ISRT-FUNC ALT-PCB ACK-MSG MOD-ACK.     \s
            000000     CALL 'CBLTDLI' USING ISRT-FUNC DB-PCB IO-AREA CLAIM-SSA.    \s
            000000     CALL 'CBLTDLI' USING ISRT-FUNC IO-PCB SPA-AREA.             \s
            000000     GOBACK.                                                     \s
            """
        );

        assertThat(calls).hasSize(4);
        assertThat(calls).extracting(DliCall::getMod, DliCall::getModOperand, DliCall::getSsas,
            DliCall::getSegments)
          .containsExactly(
            tuple("CLMI1O", "MOD-INQ", emptyList(), emptyList()),
            // An alternate PCB sends to another terminal and names a MOD the same way.
            tuple("CLMI6O", "MOD-ACK", emptyList(), emptyList()),
            tuple(null, null, singletonList("CLAIM-SSA"), singletonList("CLAIM")),
            // The SPA goes back with no format at all.
            tuple(null, null, emptyList(), emptyList()));
    }

    @Test
    void ignoresOrdinaryProgramCalls() {
        List<DliCall> calls = parse(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. IMSPGM.                                             \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     CALL 'SUBPGM' USING WS-PARM.                                \s
            000000     GOBACK.                                                     \s
            """
        );

        assertThat(calls).isEmpty();
    }
}
