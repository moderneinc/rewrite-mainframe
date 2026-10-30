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

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import org.openrewrite.mainframe.cobol.internal.grammar.CobolLexer;
import org.openrewrite.mainframe.cobol.internal.grammar.CobolParser;
import org.openrewrite.mainframe.cobol.internal.grammar.CobolPreprocessorLexer;
import org.openrewrite.mainframe.cobol.internal.grammar.CobolPreprocessorParser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.mainframe.FullContextPredictions.fallbacks;

/**
 * Pins the decisions where SLL prediction never retries with full context. The statement-list predicates are
 * explained at {@code startsStatement} in the grammar, and the identifier rule at {@code visitIdentifier}.
 */
class FullContextPredictionTest {

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

        assertThat(fullContextFallbacks(program, "readStatement", "addStatement", "stringStatement",
          "callStatement", "writeStatement", "receiveIntoStatement")).isZero();
    }

    @Test
    void statementTerminators() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. ENDINGS.
          PROCEDURE DIVISION.
              READ F INTO X AT END MOVE 1 TO Y END-READ
              COMPUTE X = A + 1 - B ON SIZE ERROR MOVE 2 TO Y NOT ON SIZE ERROR MOVE 3 TO Y END-COMPUTE
              STRING A DELIMITED BY SIZE INTO B END-STRING
              UNSTRING A INTO B C END-UNSTRING
              CALL 'P' USING A END-CALL
              DIVIDE A BY B GIVING C END-DIVIDE
              WRITE R FROM A END-WRITE
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "readStatement", "computeStatement", "stringStatement",
          "unstringStatement", "callStatement", "divideStatement", "writeStatement")).isZero();
    }

    @Test
    void subscriptedAndReferenceModifiedNames() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. SUBSCR.
          PROCEDURE DIVISION.
              MOVE A (1) TO B (I, J).
              MOVE WS-DATE (1:8) TO C (K) (3:2).
              IF D (I) = E (1:2) DISPLAY F (I) G.
              MOVE H IN T (I) TO C OF R OF T (I, J) (1:2).
              MOVE T (A (1)) TO W.
              MOVE FUNCTION MAX(A (1) B) TO W (1:2).
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "identifier", "qualifiedInData")).isZero();
    }

    @Test
    void pictureStrings() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. PICS.
          DATA DIVISION.
          WORKING-STORAGE SECTION.
          01  A   PIC X(10).
          01  B   PIC S9(5)V99.
          01  C   PIC ZZ,ZZ9.99-.
          01  D   PIC 9(3)V9(2) VALUE 1.5.
          01  E   PIC XX OCCURS 3 TIMES.
          01  F   PICTURE IS $$$9 COMP-3.
          PROCEDURE DIVISION.
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "pictureString", "pictureCardinality")).isZero();
    }

    @Test
    void dataDescriptionEntriesAndQualifiedNames() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. ENTRIES.
          DATA DIVISION.
          WORKING-STORAGE SECTION.
          01  WS-REC.
              05  WS-A        PIC X(4) VALUE 'ABCD'.
              05  FILLER      PIC X VALUE SPACE.
              05  WS-N        PIC 9(3) COMP VALUE 7.
                  88  WS-N-SET    VALUE 1 THRU 9.
              05  WS-T OCCURS 3 TIMES INDEXED BY WS-I.
                  10  WS-E    PIC X.
          01  WS-OLD REDEFINES WS-REC PIC X(20).
          01  WS-BIN          BINARY PIC 9(4).
          PROCEDURE DIVISION.
              MOVE WS-A OF WS-REC TO WS-E OF WS-T IN WS-REC (1).
              IF WS-N-SET MOVE 1 TO WS-N.
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "qualifiedDataName", "qualifiedDataNameFormat1",
          "dataDescriptionEntryFormat1", "dataValueClause")).isZero();
    }

    @Test
    void screenEntryNamedCol() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. SCRCOL.
          DATA DIVISION.
          SCREEN SECTION.
          01  S1.
              05  COL 10 VALUE 'A'.
              05  COL COL 20 VALUE 'B'.
              05  COL LINE 3 COL 5 PIC X(5) USING F1.
          PROCEDURE DIVISION.
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "screenDescriptionEntry", "screenDescriptionColumnClause")).isZero();
    }

    @Test
    void foldedAlternatives() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. FOLDS.
          ENVIRONMENT DIVISION.
          INPUT-OUTPUT SECTION.
          FILE-CONTROL.
              SELECT IN-FILE ASSIGN TO INDD.
              SELECT OUT-FILE ASSIGN TO OUTDD.
          DATA DIVISION.
          FILE SECTION.
          FD  IN-FILE.
          01  IN-REC   PIC X(80).
          FD  OUT-FILE.
          01  OUT-REC  PIC X(80).
          WORKING-STORAGE SECTION.
          01  I        PIC 9.
          01  J        PIC 9.
          01  T.
              05  E OCCURS 3 TIMES PIC X.
          01  FLAGS    PIC X.
              88  DONE  VALUE 'Y'.
          PROCEDURE DIVISION USING IN-REC OUT-REC.
          MAIN SECTION.
          START-PARA.
              PERFORM VARYING I FROM 1 BY 1 UNTIL I > 3
                 MOVE E (I) TO OUT-REC
              END-PERFORM.
              PERFORM VARYING I FROM J BY I UNTIL DONE
                 CONTINUE
              END-PERFORM.
              PERFORM UNTIL EXIT
                 EXIT PERFORM
              END-PERFORM.
              EVALUATE I
                WHEN 1 CONTINUE
                WHEN J CONTINUE
                WHEN ZERO CONTINUE
                WHEN J + 1 CONTINUE
              END-EVALUATE.
              CALL 'SUB' USING IN-REC OUT-REC BY VALUE I BY REFERENCE J.
              CALL 'SUB' USING BY REFERENCE I J.
              MOVE FUNCTION MAX(I J) (1:1) TO FLAGS.
              IF DONE OF FLAGS GO TO END-PARA IN MAIN.
              GO TO START-PARA END-PARA DEPENDING ON I.
              GO TO END-PARA.
          END-PARA.
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "procedureName", "paragraph", "goToStatement", "callByReference",
          "callByReferencePhrase", "callUsingPhrase", "procedureDivisionByReference", "conditionNameReference",
          "literal", "numericLiteral", "argument", "evaluateValue", "performBy", "performFrom", "performUntil",
          "functionCall", "fileControlParagraph")).isZero();
    }

    /**
     * How often prediction in the decisions of the given rules retried with full context, over one parse of the
     * program. The program is the text the preprocessor hands the grammar, so it carries no sequence numbers.
     */
    @Test
    void preprocessorCharDataLines() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. CHARDATA.
          DATA DIVISION.
          WORKING-STORAGE SECTION.
          01  A   PIC X(10) VALUE 'ABC'.
          PROCEDURE DIVISION.
              MOVE A TO B.
              DISPLAY 'X' A (1:2).
              EXEC CICS RETURN TRANSID('T1')
              END-EXEC.
              STOP RUN.
          """;
        assertThat(preprocessorFullContextFallbacks(program, "compilationUnit", "charData", "charDataLine")).isZero();
    }

    @Test
    void preprocessorStatementEndings() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. ENDINGS.
          AUTHOR. *>CE A. PERSON.
          *>CE SECOND LINE OF THE ENTRY.
          INSTALLATION. *>CE SOMEWHERE.
          PROCEDURE DIVISION.
              EXEC CICS RETURN END-EXEC.
              EXEC SQL SELECT A INTO :B FROM T END-EXEC.
              EXEC SQL INCLUDE SQLCA END-EXEC.
              STOP RUN.
          """;
        assertThat(preprocessorFullContextFallbacks(program, "commentEntry")).isZero();

        assertThat(preprocessorFullContextFallbacks(program, "execCicsStatement", "execSqlStatement",
          "execSqlIncludeStatement")).isZero();
    }

    @Test
    void overlappingAlternatives() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. OVERLAP.
          PROCEDURE DIVISION.
              MOVE E (I) TO X
              MOVE E (I J) TO X
              MOVE E (1) TO X
              MOVE E (I + 1) TO X
              MOVE E (I +1) TO X
              MOVE E (I, 1) TO X
              MOVE E (I OF T) TO X
              MOVE E (F (1)) TO X
              MOVE DFHVALUE(NOUCTRAN) TO X
              IF A = 1 AND B = 2 OR C = 3 MOVE 1 TO Y END-IF
              IF A = 1 AND 2 OR NOT 3 MOVE 1 TO Y END-IF
              IF A = 1 OR B (I:1) OR C (I:1) MOVE 1 TO Y END-IF
              IF A = 1 AND B NOT = 2 MOVE 1 TO Y END-IF
              IF A = 1 AND (B = 2 OR C = 3) MOVE 1 TO Y END-IF
              IF FLAG-ON AND NOT FLAG-OFF MOVE 1 TO Y END-IF
              COMPUTE X = (A + B) * C - 1
              COMPUTE X = A - WS-LEN + 1
              COMPUTE X = DFHRESP(NORMAL)
              EVALUATE A
                 WHEN 1 MOVE 1 TO Y
                 WHEN 'X' MOVE 2 TO Y
                 WHEN B MOVE 3 TO Y
                 WHEN A + 1 MOVE 5 TO Y
                 WHEN NOT 2 MOVE 6 TO Y
                 WHEN 1 THRU 5 MOVE 7 TO Y
                 WHEN OTHER MOVE 8 TO Y
              END-EVALUATE
              EVALUATE TRUE ALSO A
                 WHEN A = 1 ALSO ANY MOVE 1 TO Y
              END-EVALUATE
              EVALUATE 'A' WHEN 'A' MOVE 1 TO Y END-EVALUATE
              EVALUATE A + 1 WHEN 2 MOVE 1 TO Y END-EVALUATE
              EVALUATE A = B WHEN TRUE MOVE 1 TO Y END-EVALUATE
              EVALUATE (A = 1) WHEN TRUE MOVE 1 TO Y END-EVALUATE
              EVALUATE TRUE
                 WHEN PFK07 AND FIRST-PAGE MOVE 1 TO Y
                 WHEN PFK07 AND NOT FIRST-PAGE MOVE 2 TO Y
              END-EVALUATE
              IF (PGM-ENTER AND FROM-PROGRAM NOT EQUAL THIS-PGM) MOVE 1 TO Y END-IF
              IF A = 1 AND 2 AND 3 MOVE 1 TO Y END-IF
              SET PGM-ENTER TO TRUE
              MOVE ALL 'X' TO Y
              MOVE FUNCTION INTEGER (A) TO Y
              IF A = 1 NEXT SENTENCE ELSE NEXT SENTENCE END-IF
              CALL 'P' USING BY CONTENT LENGTH OF A BY VALUE B BY REFERENCE C
              DIVIDE A INTO B GIVING C
              DIVIDE A INTO B C ROUNDED
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "subscript", "andOrCondition", "basis", "arithmeticExpression",
          "evaluateSelect", "evaluateCondition", "moveToSendingArea", "callByContent", "callByValue",
          "callByReference", "divideStatement", "moveStatement", "functionName", "ifThen", "ifElse")).isZero();
    }

    @Test
    void lineLoopsAndNestedPrograms() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. OUTER.
          PROCEDURE DIVISION USING A B BY VALUE C D.
          *>EXECCICS EXEC CICS SEND MAP('M')
          *>EXECCICS END-EXEC
              STOP RUN.
          IDENTIFICATION DIVISION.
          PROGRAM-ID. INNER.
          PROCEDURE DIVISION.
              STOP RUN.
          END PROGRAM INNER.
          END PROGRAM OUTER.
          """;
        assertThat(fullContextFallbacks(program, "compilationUnit", "programUnit", "procedureDivisionByReferencePhrase",
          "procedureDivisionByValuePhrase", "execCicsStatement")).isZero();
    }

    @Test
    void preprocessorCompilerOptionsAndCopyLibraries() {
        String program = """
          CBL CICS,DATEPROC,TEST,XREF(FULL),SQL('X'),NAME,OPT
          CBL CICS('SP'),DATEPROC(FLAG,TRIG),TEST(HOOK,SEP),MDECK(C)
          PROCESS ADATA,DBCS
          *> a comment line
          IDENTIFICATION DIVISION.
          PROGRAM-ID. OPTS.
          PROCEDURE DIVISION.
              COPY X IN MYLIB.
              COPY Y.
              STOP RUN.
          """;
        assertThat(preprocessorFullContextFallbacks(program, "compilerOptions", "compilerOption", "copySource")).isZero();
    }

    @Test
    void inspectGroups() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. INSPGRP.
          PROCEDURE DIVISION.
              INSPECT X TALLYING C FOR ALL 'S' ALL 'U' LEADING SPACES LOW-VALUES
                 D FOR CHARACTERS BEFORE 'A' ALL 'V' BEFORE INITIAL 'B'
              INSPECT X REPLACING ALL 'S' BY '1' ALL 'U' BY '1' '0' BY '9' '2' BY '9'
                 LEADING 'A' BY 'B' FIRST 'C' BY 'D' AFTER 'E' CHARACTERS BY '0'
              INSPECT X TALLYING C FOR ALL 'S' ALL 'U' REPLACING ALL 'S' BY '1' ALL 'U' BY '1'.
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "inspectAllLeadings", "inspectReplacingAllLeadings", "inspectFor",
          "inspectTallyingPhrase", "inspectReplacingPhrase", "inspectTallyingReplacingPhrase")).isZero();
    }

    @Test
    void preprocessorReplaceOperands() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. REPLOPS.
          DATA DIVISION.
          WORKING-STORAGE SECTION.
          REPLACE ==A== BY B C BY 'D' 1 BY +2.5 E OF F BY G IN H (1) ==I== BY ====.
          COPY K REPLACING J BY ==K L== M BY N OF O IN P (1, Q) R BY S (2) (1:2) T BY U (I + 1).
          COPY K OF LIB SUPPRESS REPLACING ==V== BY W.
          REPLACE OFF.
          PROCEDURE DIVISION.
              STOP RUN.
          """;
        assertThat(preprocessorFullContextFallbacks(program, "replaceable", "replacement", "replaceClause",
          "replacingPhrase", "replaceByStatement", "copyStatement", "identifierWords")).isZero();
    }

    @Test
    void enterpriseCobolStatements() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. ENTSTMT.
          PROCEDURE DIVISION.
              JSON GENERATE DOC FROM REC COUNT IN CNT
                  NAME OF ACCT IS 'account' BAL IS OMITTED
                  SUPPRESS BAL WHEN ZERO OR SPACES
                  CONVERTING FLAG TO JSON BOOLEAN USING ACTIVE
                  ON EXCEPTION MOVE 1 TO CNT
              END-JSON
              XML GENERATE DOC FROM REC WITH ATTRIBUTES NAMESPACE IS NS
                  TYPE OF BAL IS ELEMENT SUPPRESS EVERY NUMERIC ELEMENT WHEN ZERO
              JSON PARSE DOC INTO REC WITH DETAIL
              ALLOCATE CNT * 2 CHARACTERS INITIALIZED LOC 31 RETURNING PTR
              ALLOCATE REC RETURNING PTR
              FREE PTR PTR2
              INITIALIZE REC WITH FILLER ALL TO VALUE THEN REPLACING NUMERIC DATA BY ZERO THEN TO DEFAULT
              INITIALIZE REC NAME
              INITIALIZE REC THEN TO DEFAULT
              INITIALIZE REC ALL TO VALUE THEN TO DEFAULT
              EVALUATE CNT
                WHEN 1 XML GENERATE DOC FROM REC SUPPRESS BAL
                WHEN 2 JSON GENERATE DOC FROM REC SUPPRESS BAL WHEN ZERO
                WHEN OTHER CONTINUE
              END-EVALUATE
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "jsonXmlStatement", "jsonXmlPhrase", "allocateStatement",
          "freeStatement", "initializeStatement", "initializeValuePhrase", "initializeDefaultPhrase")).isZero();
    }

    @Test
    void enterpriseCobolDataClauses() {
        String program = """
          IDENTIFICATION DIVISION.
          PROGRAM-ID. ENTDATA.
          ENVIRONMENT DIVISION.
          CONFIGURATION SECTION.
          SPECIAL-NAMES.
              XML-SCHEMA CLMSCHEMA IS 'CLMXSD'.
          DATA DIVISION.
          WORKING-STORAGE SECTION.
          01  U1  PIC U(5) USAGE UTF-8 VOLATILE.
          01  P1  POINTER-32.
          01  G1  GROUP-USAGE NATIONAL.
              05  G1A PIC N(5).
          01  D1  PIC X DYNAMIC LENGTH LIMIT 100 VALUE SPACE.
          01  D2  PIC X DYNAMIC.
          01  C1  USAGE IS SQL TYPE IS CLOB(1M).
          01  C2  USAGE SQL TYPE IS XML AS CLOB(1M).
          01  C3  SQL TYPE IS XML AS CLOB-FILE.
          01  B1  SQL TYPE IS BINARY(8) VALUE SPACES.
          01  B2  SQL TYPE IS BINARY VARYING(8).
          01  B3  SQL TYPE IS BINARY LARGE OBJECT(1M).
          01  R1  SQL TYPE IS RESULT-SET-LOCATOR VARYING.
          PROCEDURE DIVISION.
              STOP RUN.
          """;
        assertThat(fullContextFallbacks(program, "dataDescriptionEntryFormat1", "dataUsageClause",
          "dataGroupUsageClause", "dataDynamicLengthClause", "dataVolatileClause", "dataTypeClause", "specialNameClause",
          "xmlSchemaClause")).isZero();
    }

    @Test
    void preprocessorCompilerDirectives() {
        String program = """
          >>CALLINTERFACE STATIC
          IDENTIFICATION DIVISION.
          PROGRAM-ID. DIRECT.
          PROCEDURE DIVISION.
              REPLACE ==:P:== BY ==WS==.
              >>IF TRACE-LEVEL > 1
              DISPLAY :P:-TEXT
              >>END-IF
              REPLACE OFF.
              STOP RUN.
          """;
        assertThat(preprocessorFullContextFallbacks(program, "compilationUnit", "replaceArea")).isZero();
    }

    private static long fullContextFallbacks(String program, String... rules) {
        CobolParser parser = new CobolParser(new CommonTokenStream(new CobolLexer(CharStreams.fromString(program))));
        return fallbacks(parser, parser::compilationUnit, rules);
    }

    private static long preprocessorFullContextFallbacks(String program, String... rules) {
        CobolPreprocessorParser parser = new CobolPreprocessorParser(
          new CommonTokenStream(new CobolPreprocessorLexer(CharStreams.fromString(program))));
        return fallbacks(parser, parser::compilationUnit, rules);
    }
}
