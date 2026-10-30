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
package org.openrewrite.mainframe.cobol.tree.cobol;

import org.junit.jupiter.api.Test;
import org.openrewrite.Cursor;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.mainframe.cobol.CobolIsoVisitor;
import org.openrewrite.mainframe.cobol.CobolPreprocessorIsoVisitor;
import org.openrewrite.mainframe.cobol.CobolPreprocessorParser;
import org.openrewrite.mainframe.cobol.CobolTest;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.CobolPreprocessor;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.tuple;
import static org.openrewrite.mainframe.cobol.Assertions.cobol;

/**
 * Statements and phrases Enterprise COBOL 6 added to the language.
 */
class CobolParserEnterpriseCobolTest extends CobolTest {

    @Test
    void jsonGenerateAndParseReadTheirDocumentAndDataItem() {
        rewriteRun(
          cobol(
            """
              000001 IDENTIFICATION DIVISION.
              000002 PROGRAM-ID. ENTCOB.
              000003 DATA DIVISION.
              000004 WORKING-STORAGE SECTION.
              000005 01  DOC        PIC X(200).
              000006 01  CNT        PIC 9(4).
              000007 01  PTR        USAGE POINTER.
              000008 01  PTR2       USAGE POINTER.
              000009 01  NS         PIC X(20).
              000010 01  REC.
              000011     05  ACCT   PIC X(10).
              000012     05  BAL    PIC 9(7).
              000013     05  FLAG   PIC X.
              000014         88  ACTIVE VALUE 'Y'.
              000015 PROCEDURE DIVISION.
              000016     JSON GENERATE DOC FROM REC COUNT IN CNT
              000017         NAME OF ACCT IS 'account' BAL IS OMITTED
              000018         SUPPRESS BAL WHEN ZERO OR SPACES
              000019         CONVERTING FLAG TO JSON BOOLEAN USING ACTIVE
              000020         ON EXCEPTION MOVE 1 TO CNT
              000021         NOT ON EXCEPTION MOVE 0 TO CNT
              000022     END-JSON
              000023     JSON PARSE DOC INTO REC WITH DETAIL
              000024         NAME OF ACCT IS 'account'
              000025         SUPPRESS BAL
              000026     END-JSON
              000027     JSON GENERATE DOC FROM REC.
              000028     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                List<Cobol.JsonXml> statements = treesOf(cu, Cobol.JsonXml.class);
                Cursor root = new Cursor(null, cu);
                assertThat(statements).extracting(s -> s.getDocument().printTrimmed(root), s -> s.getData().printTrimmed(root))
                  .containsExactly(tuple("DOC", "REC"), tuple("DOC", "REC"), tuple("DOC", "REC"));
                assertThat(statements.get(0).getPhrases()).hasSize(4);
                assertThat(statements.get(0).getOnException()).isNotNull();
                assertThat(statements.get(0).getNotOnException()).isNotNull();
                assertThat(statements.get(2).getEnd()).isNull();
            })
          )
        );
    }

    @Test
    void xmlGenerateReadsEachOfItsPhrases() {
        rewriteRun(
          cobol(
            """
              000001 IDENTIFICATION DIVISION.
              000002 PROGRAM-ID. ENTCOB.
              000003 DATA DIVISION.
              000004 WORKING-STORAGE SECTION.
              000005 01  DOC        PIC X(200).
              000006 01  CNT        PIC 9(4).
              000007 01  PTR        USAGE POINTER.
              000008 01  PTR2       USAGE POINTER.
              000009 01  NS         PIC X(20).
              000010 01  REC.
              000011     05  ACCT   PIC X(10).
              000012     05  BAL    PIC 9(7).
              000013     05  FLAG   PIC X.
              000014         88  ACTIVE VALUE 'Y'.
              000015 PROCEDURE DIVISION.
              000016     XML GENERATE DOC FROM REC COUNT IN CNT
              000017         WITH ENCODING 1208 WITH XML-DECLARATION
              000018         WITH ATTRIBUTES NAMESPACE IS NS
              000019         NAMESPACE-PREFIX IS 'ns'
              000020         NAME OF ACCT IS 'account'
              000021         TYPE OF BAL IS ELEMENT
              000022         SUPPRESS EVERY NUMERIC ATTRIBUTE WHEN ZERO
              000023         ON EXCEPTION DISPLAY 'FAILED'
              000024     END-XML
              000025     GOBACK.
              """,
            spec -> spec.afterRecipe(cu ->
              assertThat(treesOf(cu, Cobol.JsonXml.class)).singleElement().satisfies(s -> {
                  assertThat(s.getPhrases()).hasSize(9);
                  assertThat(s.getPhrases().get(8).getElements()).allMatch(Cobol.Word.class::isInstance);
              }))
          )
        );
    }

    @Test
    void allocateAndFree() {
        rewriteRun(
          cobol(
            """
              000001 IDENTIFICATION DIVISION.
              000002 PROGRAM-ID. ENTCOB.
              000003 DATA DIVISION.
              000004 WORKING-STORAGE SECTION.
              000005 01  DOC        PIC X(200).
              000006 01  CNT        PIC 9(4).
              000007 01  PTR        USAGE POINTER.
              000008 01  PTR2       USAGE POINTER.
              000009 01  NS         PIC X(20).
              000010 01  REC.
              000011     05  ACCT   PIC X(10).
              000012     05  BAL    PIC 9(7).
              000013     05  FLAG   PIC X.
              000014         88  ACTIVE VALUE 'Y'.
              000015 PROCEDURE DIVISION.
              000016     ALLOCATE 100 CHARACTERS INITIALIZED LOC 31
              000017         RETURNING PTR
              000018     ALLOCATE REC RETURNING PTR2
              000019     ALLOCATE CNT * 2 CHARACTERS RETURNING PTR
              000020     FREE PTR PTR2
              000021     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                Cursor root = new Cursor(null, cu);
                assertThat(treesOf(cu, Cobol.Allocate.class))
                  .extracting(a -> a.getPointer() == null ? null : a.getPointer().printTrimmed(root))
                  .containsExactly("PTR", "PTR2", "PTR");
                assertThat(treesOf(cu, Cobol.Free.class)).singleElement()
                  .satisfies(f -> assertThat(f.getPointers()).hasSize(2));
            })
          )
        );
    }

    @Test
    void initializeWithFillerToValueThenToDefault() {
        rewriteRun(
          cobol(
            """
              000001 IDENTIFICATION DIVISION.
              000002 PROGRAM-ID. ENTCOB.
              000003 DATA DIVISION.
              000004 WORKING-STORAGE SECTION.
              000005 01  DOC        PIC X(200).
              000006 01  CNT        PIC 9(4).
              000007 01  PTR        USAGE POINTER.
              000008 01  PTR2       USAGE POINTER.
              000009 01  NS         PIC X(20).
              000010 01  REC.
              000011     05  ACCT   PIC X(10).
              000012     05  BAL    PIC 9(7).
              000013     05  FLAG   PIC X.
              000014         88  ACTIVE VALUE 'Y'.
              000015 PROCEDURE DIVISION.
              000016     INITIALIZE REC WITH FILLER ALL TO VALUE
              000017         THEN REPLACING ALPHANUMERIC DATA BY SPACES
              000018         THEN TO DEFAULT
              000019     INITIALIZE REC NUMERIC TO VALUE
              000020     INITIALIZE REC THEN TO DEFAULT
              000021     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> assertThat(treesOf(cu, Cobol.Initialize.class))
              .extracting(init -> words(init.getValuePhrase()), init -> words(init.getDefaultPhrase()))
              .containsExactly(
                tuple("WITH FILLER ALL TO VALUE THEN", "THEN TO DEFAULT"),
                tuple("NUMERIC TO VALUE", ""),
                tuple("", "THEN TO DEFAULT")))
          )
        );
    }

    @Test
    void aWhenAfterSuppressIsTheEvaluatesUnlessAConditionFollows() {
        rewriteRun(
          cobol(
            """
              000001 IDENTIFICATION DIVISION.
              000002 PROGRAM-ID. ENTCOB.
              000003 DATA DIVISION.
              000004 WORKING-STORAGE SECTION.
              000005 01  DOC        PIC X(200).
              000006 01  CNT        PIC 9(4).
              000007 01  REC.
              000008     05  BAL    PIC 9(7).
              000009 PROCEDURE DIVISION.
              000010     EVALUATE CNT
              000011       WHEN 1 JSON GENERATE DOC FROM REC SUPPRESS BAL
              000012       WHEN 2 JSON GENERATE DOC FROM REC SUPPRESS BAL WHEN ZERO
              000013       WHEN OTHER CONTINUE
              000014     END-EVALUATE
              000015     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> assertThat(treesOf(cu, Cobol.JsonXml.class))
              .extracting(s -> s.getPhrases().get(0).getElements().size())
              .containsExactly(2, 4))
          )
        );
    }

    @Test
    void dataClausesOfEnterpriseCobol() {
        rewriteRun(
          cobol(
            """
              000001 IDENTIFICATION DIVISION.
              000002 PROGRAM-ID. ENTCOB.
              000003 DATA DIVISION.
              000004 WORKING-STORAGE SECTION.
              000005 01  U1  PIC U(5) USAGE UTF-8.
              000006 01  U2  PIC U(5) USAGE IS UTF-8.
              000007 01  P1  USAGE POINTER-32.
              000008 01  P2  POINTER-32.
              000009 01  G1  GROUP-USAGE NATIONAL.
              000010     05  G1A PIC N(5).
              000011 01  G2  GROUP-USAGE IS UTF-8.
              000012     05  G2A PIC U(5).
              000013 01  D1  PIC X DYNAMIC LENGTH LIMIT 100.
              000014 01  D2  PIC X DYNAMIC LENGTH.
              000015 01  D3  PIC X DYNAMIC LIMIT IS 8.
              000016 01  V1  PIC X VOLATILE.
              000017 01  C1  USAGE IS SQL TYPE IS CLOB(1M).
              000018 01  C2  USAGE IS SQL TYPE IS BLOB(64 K).
              000019 01  C3  USAGE IS SQL TYPE IS DBCLOB(100).
              000020 PROCEDURE DIVISION.
              000021     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> {
                assertThat(treesOf(cu, Cobol.DataUsageClause.class))
                  .extracting(usage -> words(usage.getWords()))
                  .contains("USAGE UTF-8", "USAGE IS UTF-8", "USAGE POINTER-32", "POINTER-32");
                assertThat(treesOf(cu, Cobol.DataGroupUsageClause.class)).extracting(usage -> words(usage.getWords()))
                  .containsExactly("GROUP-USAGE NATIONAL", "GROUP-USAGE IS UTF-8");
                assertThat(treesOf(cu, Cobol.DataDynamicLengthClause.class))
                  .extracting(dynamic -> dynamic.getLimit() == null ? null : dynamic.getLimit().printTrimmed(new Cursor(null, cu)))
                  .containsExactly("100", null, "8");
                assertThat(treesOf(cu, Cobol.DataVolatileClause.class)).hasSize(1);
                assertThat(treesOf(cu, Cobol.DataTypeClause.class))
                  .extracting(type -> type.getParenthesized().printTrimmed(new Cursor(null, cu)))
                  .containsExactly("(1M)", "(64 K)", "(100)");
            })
          )
        );
    }

    @Test
    void sqlTypesOfDb2HostVariables() {
        rewriteRun(
          cobol(
            """
              000001 IDENTIFICATION DIVISION.
              000002 PROGRAM-ID. ENTCOB.
              000003 DATA DIVISION.
              000004 WORKING-STORAGE SECTION.
              000005 01  RS  USAGE SQL TYPE IS RESULT-SET-LOCATOR VARYING.
              000006 01  TL  SQL TYPE IS TABLE LIKE DSN8C10.EMP AS LOCATOR.
              000007 01  RI  SQL TYPE IS ROWID.
              000008 01  B1  SQL TYPE IS BINARY(8).
              000009 01  B2  SQL TYPE IS VARBINARY(100).
              000010 01  B3  SQL TYPE IS BINARY VARYING(100).
              000011 01  L1  SQL TYPE IS BINARY LARGE OBJECT(1M).
              000012 01  L2  SQL TYPE IS CHARACTER LARGE OBJECT(10 K).
              000013 01  L3  SQL TYPE IS CHAR LARGE OBJECT(1G).
              000014 01  L4  SQL TYPE IS BLOB-LOCATOR.
              000015 01  L5  SQL TYPE IS CLOB-LOCATOR.
              000016 01  L6  SQL TYPE IS DBCLOB-LOCATOR.
              000017 01  L7  SQL TYPE IS BLOB-FILE.
              000018 01  L8  SQL TYPE IS CLOB-FILE.
              000019 01  L9  SQL TYPE IS DBCLOB-FILE.
              000020 01  X1  SQL TYPE IS XML AS CLOB(1M).
              000021 01  X2  SQL TYPE IS XML AS DBCLOB-FILE.
              000022 PROCEDURE DIVISION.
              000023     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> assertThat(treesOf(cu, Cobol.DataTypeClause.class))
              .extracting(type -> words(type.getWords()),
                type -> type.getParenthesized() == null ? null : type.getParenthesized().printTrimmed(new Cursor(null, cu)))
              .containsExactly(
                tuple("TYPE IS RESULT-SET-LOCATOR VARYING", null),
                tuple("TYPE IS TABLE LIKE DSN8C10 . EMP AS LOCATOR", null),
                tuple("TYPE IS ROWID", null),
                tuple("TYPE IS BINARY", "(8)"),
                tuple("TYPE IS VARBINARY", "(100)"),
                tuple("TYPE IS BINARY VARYING", "(100)"),
                tuple("TYPE IS BINARY LARGE OBJECT", "(1M)"),
                tuple("TYPE IS CHARACTER LARGE OBJECT", "(10 K)"),
                tuple("TYPE IS CHAR LARGE OBJECT", "(1G)"),
                tuple("TYPE IS BLOB-LOCATOR", null),
                tuple("TYPE IS CLOB-LOCATOR", null),
                tuple("TYPE IS DBCLOB-LOCATOR", null),
                tuple("TYPE IS BLOB-FILE", null),
                tuple("TYPE IS CLOB-FILE", null),
                tuple("TYPE IS DBCLOB-FILE", null),
                tuple("TYPE IS XML AS CLOB", "(1M)"),
                tuple("TYPE IS XML AS DBCLOB-FILE", null)))
          )
        );
    }

    @Test
    void xmlSchemaNamesTheFileXmlParseValidatesWith() {
        rewriteRun(
          cobol(
            """
              000001 IDENTIFICATION DIVISION.
              000002 PROGRAM-ID. ENTCOB.
              000003 ENVIRONMENT DIVISION.
              000004 CONFIGURATION SECTION.
              000005 SPECIAL-NAMES.
              000006     XML-SCHEMA CLMSCHEMA IS 'CLMXSD'
              000007     XML-SCHEMA OTHERSCH IS SCHEMADD.
              000008 DATA DIVISION.
              000009 WORKING-STORAGE SECTION.
              000010 01  DOC PIC X(200).
              000011 PROCEDURE DIVISION.
              000012     XML PARSE DOC VALIDATING WITH FILE CLMSCHEMA
              000013         PROCESSING PROCEDURE HANDLER
              000014     END-XML
              000015     GOBACK.
              000016 HANDLER.
              000017     CONTINUE.
              """,
            spec -> spec.afterRecipe(cu -> assertThat(treesOf(cu, Cobol.XmlSchemaClause.class)).hasSize(2))
          )
        );
    }

    @Test
    void compilerDirectivesPrintBackAndLeaveTheProgramAlone() {
        rewriteRun(
          cobol(
            """
              000001 >>CALLINTERFACE STATIC
              000002 >>DEFINE TRACE-LEVEL AS 2
              000003 IDENTIFICATION DIVISION.
              000004 PROGRAM-ID. ENTCOB.
              000005 DATA DIVISION.
              000006 WORKING-STORAGE SECTION.
              000007 >>DATA 31
              000008 01  CNT PIC 9(4).
              000009 PROCEDURE DIVISION.
              000010     >>INLINE OFF
              000011     >>IF TRACE-LEVEL > 1
              000012     DISPLAY 'TRACING'
              000013     >>ELSE
              000014     DISPLAY 'QUIET'
              000015     >>END-IF
              000016     >>EVALUATE TRACE-LEVEL
              000017     >>WHEN 2
              000018     MOVE 2 TO CNT
              000019     >>WHEN OTHER
              000020     MOVE 0 TO CNT
              000021     >>END-EVALUATE
              000022     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> assertThat(treesOf(cu, Cobol.Display.class)).hasSize(2))
          )
        );
    }

    @Test
    void directivesAreNodesOfTheirOwnAndLeaveReplaceAndPseudoTextAlone() {
        String source = """
              000001 IDENTIFICATION DIVISION.
              000002 PROGRAM-ID. ENTCOB.
              000003 AUTHOR. A PERSON.
              000004 >>CALLINTERFACE DLL
              000005 DATA DIVISION.
              000006 WORKING-STORAGE SECTION.
              000007 01  WS-TEXT PIC X(10).
              000008 PROCEDURE DIVISION.
              000009     REPLACE ==:P:== BY ==WS== ==A>>B== BY ==C==.
              000010     >>INLINE OFF
              000011     DISPLAY :P:-TEXT
              000012     REPLACE OFF.
              000013     GOBACK.
              """;
        rewriteRun(
          cobol(source, spec -> spec.afterRecipe(cu ->
            assertThat(treesOf(cu, Cobol.Display.class)).singleElement()
              .satisfies(display -> assertThat(wordsIn(display)).containsExactly("DISPLAY", "WS-TEXT"))))
        );

        CobolPreprocessor.CompilationUnit preprocessed = (CobolPreprocessor.CompilationUnit) CobolPreprocessorParser.builder().build()
          .parse(new InMemoryExecutionContext(), source).findFirst().orElseThrow();
        List<String> directives = new ArrayList<>();
        new CobolPreprocessorIsoVisitor<List<String>>() {
            @Override
            public CobolPreprocessor.CompilerDirective visitCompilerDirective(CobolPreprocessor.CompilerDirective directive, List<String> found) {
                found.add(directive.getWord().getCobolWord().getWord());
                return directive;
            }
        }.visit(preprocessed, directives);
        assertThat(directives).containsExactly(">>CALLINTERFACE DLL", ">>INLINE OFF");
    }

    @Test
    void performUntilExitHasNoCondition() {
        rewriteRun(
          cobol(
            """
              000001 IDENTIFICATION DIVISION.
              000002 PROGRAM-ID. ENTCOB.
              000003 PROCEDURE DIVISION.
              000004     PERFORM UNTIL EXIT
              000005         ADD 1 TO N
              000006         IF N > 9
              000007             EXIT PERFORM
              000008         END-IF
              000009     END-PERFORM
              000010     GOBACK.
              """,
            spec -> spec.afterRecipe(cu -> assertThat(treesOf(cu, Cobol.PerformUntil.class)).singleElement()
              .satisfies(until -> {
                  assertThat(until.getExit()).extracting(Cobol.Word::getWord).isEqualTo("EXIT");
                  assertThat(until.getCondition()).isNull();
              }))
          )
        );
    }

    @Test
    void phraseWordsStillNameData() {
        rewriteRun(
          cobol(
            """
              000001 IDENTIFICATION DIVISION.
              000002 PROGRAM-ID. ENTCOB.
              000003 DATA DIVISION.
              000004 WORKING-STORAGE SECTION.
              000005 01  NAME            PIC X(20).
              000006 01  LOC             PIC X(20).
              000007 01  INITIALIZED     PIC X.
              000008 01  ELEMENT         PIC X.
              000009 01  ATTRIBUTES      PIC X.
              000010 01  NAMESPACE       PIC X.
              000011 01  NONNUMERIC      PIC X.
              000012 01  XML-DECLARATION PIC X.
              000013 01  COL             PIC S9(04) COMP VALUE ZEROES.
              000014 01  OBJECT          PIC X.
              000014 PROCEDURE DIVISION.
              000015     MOVE NAME TO LOC
              000016     MOVE ELEMENT TO ATTRIBUTES NAMESPACE NONNUMERIC XML-DECLARATION
              000017     IF INITIALIZED = 'Y' DISPLAY NAME END-IF
              000018     DIVIDE 80 INTO LOC GIVING NAME REMAINDER COL
              000018     MOVE OBJECT TO ELEMENT
              000019     GOBACK.
              """
          )
        );
    }

    @Test
    void currencySignWithPictureSymbol() {
        rewriteRun(
          cobol(
            """
              000001 IDENTIFICATION DIVISION.
              000002 PROGRAM-ID. ENTCOB.
              000003 ENVIRONMENT DIVISION.
              000004 CONFIGURATION SECTION.
              000005 SPECIAL-NAMES.
              000006     CURRENCY SIGN IS 'EUR' WITH PICTURE SYMBOL 'E'.
              000007 DATA DIVISION.
              000008 WORKING-STORAGE SECTION.
              000009 01  AMT PIC E9(5).99.
              000010 PROCEDURE DIVISION.
              000011     GOBACK.
              """
          )
        );
    }

    private static List<String> wordsIn(Cobol tree) {
        List<String> words = new ArrayList<>();
        new CobolIsoVisitor<List<String>>() {
            @Override
            public Cobol.Word visitWord(Cobol.Word word, List<String> found) {
                found.add(word.getWord());
                return word;
            }
        }.visit(tree, words);
        return words;
    }

    private static String words(List<Cobol.Word> words) {
        return words.stream().map(Cobol.Word::getWord).collect(joining(" "));
    }

    private static <T extends Cobol> List<T> treesOf(Cobol.CompilationUnit cu, Class<T> type) {
        List<T> found = new ArrayList<>();
        new CobolIsoVisitor<List<T>>() {
            @Override
            public Cobol preVisit(Cobol tree, List<T> p) {
                if (type.isInstance(tree)) {
                    p.add(type.cast(tree));
                }
                return tree;
            }
        }.visit(cu, found);
        return found;
    }
}
