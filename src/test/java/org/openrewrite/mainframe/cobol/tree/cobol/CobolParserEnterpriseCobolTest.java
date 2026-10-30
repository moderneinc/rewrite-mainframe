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
import org.openrewrite.mainframe.cobol.CobolIsoVisitor;
import org.openrewrite.mainframe.cobol.CobolTest;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.Statement;

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
                List<Cobol.JsonXml> statements = statementsOf(cu, Cobol.JsonXml.class);
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
              assertThat(statementsOf(cu, Cobol.JsonXml.class)).singleElement().satisfies(s -> {
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
                assertThat(statementsOf(cu, Cobol.Allocate.class))
                  .extracting(a -> a.getPointer() == null ? null : a.getPointer().printTrimmed(root))
                  .containsExactly("PTR", "PTR2", "PTR");
                assertThat(statementsOf(cu, Cobol.Free.class)).singleElement()
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
            spec -> spec.afterRecipe(cu -> assertThat(statementsOf(cu, Cobol.Initialize.class))
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
            spec -> spec.afterRecipe(cu -> assertThat(statementsOf(cu, Cobol.JsonXml.class))
              .extracting(s -> s.getPhrases().get(0).getElements().size())
              .containsExactly(2, 4))
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
              000013 PROCEDURE DIVISION.
              000014     MOVE NAME TO LOC
              000015     MOVE ELEMENT TO ATTRIBUTES NAMESPACE NONNUMERIC XML-DECLARATION
              000016     IF INITIALIZED = 'Y' DISPLAY NAME END-IF
              000017     GOBACK.
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

    private static String words(List<Cobol.Word> words) {
        return words.stream().map(Cobol.Word::getWord).collect(joining(" "));
    }

    private static <T extends Statement> List<T> statementsOf(Cobol.CompilationUnit cu, Class<T> type) {
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
