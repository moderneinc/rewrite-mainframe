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
import org.openrewrite.Cursor;
import org.openrewrite.ExecutionContext;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.test.RewriteTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.mainframe.cobol.Assertions.cobol;
import static org.openrewrite.test.RewriteTest.toRecipe;

class DataDescriptionTest implements RewriteTest {

    private List<DataDescription> entries(String source) {
        List<DataDescription> found = new ArrayList<>();
        rewriteRun(
          spec -> spec.recipe(toRecipe(() -> new DataDescription.Matcher().<ExecutionContext>asVisitor((entry, ctx) -> {
              found.add(entry);
              return entry.getTree();
          }))),
          cobol(source)
        );
        return found;
    }

    private static int indexOf(List<DataDescription> entries, @Nullable DataDescription entry) {
        return entry == null ? -1 : entries.indexOf(entry);
    }

    @Test
    void findsTheEntryARedefinitionNamesByPosition() {
        List<DataDescription> entries = entries(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. REDEF.                                              \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 01  WS-A.                                                       \s
            000000     05  WS-DATE     PIC X(8).                                   \s
            000000     05  WS-DATE     PIC X(8).                                   \s
            000000     05  WS-DATE-N   REDEFINES WS-DATE PIC 9(8).                 \s
            000000 01  WS-B.                                                       \s
            000000     05  WS-DATE     PIC X(8).                                   \s
            000000     05  FILLER      REDEFINES WS-DATE.                          \s
            000000         10  WS-YEAR PIC X(4).                                   \s
            000000         10  FILLER  PIC X(4).                                   \s
            000000     05  WS-DATE     REDEFINES WS-DATE PIC 9(8).                 \s
            000000 LINKAGE SECTION.                                                \s
            000000 01  LK-ALT          REDEFINES WS-B PIC X(16).                   \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     GOBACK.                                                     \s
            """
        );

        assertThat(entries).extracting(entry -> indexOf(entries, entry.getRedefines()))
          .containsExactly(-1, -1, -1, 2, -1, -1, 5, -1, -1, 5, -1);
    }

    @Test
    void placesEachEntryUnderTheOneItIsDeclaredUnder() {
        List<DataDescription> entries = entries(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. LEVELS.                                             \s
            000000 DATA DIVISION.                                                  \s
            000000 FILE SECTION.                                                   \s
            000000 FD  IN-FILE.                                                    \s
            000000 01  IN-REC          PIC X(80).                                  \s
            000000 FD  OUT-FILE.                                                   \s
            000000     05  OUT-FLD     PIC X.                                      \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 77  WS-COUNT        PIC 9.                                      \s
            000000 01  WS-REC.                                                     \s
            000000     05  WS-STATUS   PIC X.                                      \s
            000000         88  WS-OPEN VALUE 'O'.                                  \s
            000000     EXEC SQL DECLARE C1 CURSOR FOR SELECT A FROM T END-EXEC.                 \s
            000000     05  FILLER.                                                 \s
            000000         10  WS-CODE PIC X.                                      \s
            000000 66  WS-ALIAS RENAMES WS-STATUS.                                 \s
            000000 LINKAGE SECTION.                                                \s
            000000         88  LK-FLAG VALUE 'Y'.                                  \s
            000000     05  LK-FLD      PIC X.                                      \s
            000000 PROCEDURE DIVISION.                                             \s
            000000     GOBACK.                                                     \s
            """
        );

        assertThat(entries).extracting(DataDescription::getLevel).containsExactly(1, 5, 77, 1, 5, 88, 5, 10, 66, 88, 5);
        // A file description and a section start over, so OUT-FLD, LK-FLAG and LK-FLD are under nothing.
        assertThat(entries).extracting(entry -> indexOf(entries, entry.getParent()))
          .containsExactly(-1, -1, -1, -1, 3, 4, 3, 6, 3, -1, -1);
        assertThat(entries).extracting(entry -> entry.getChildren().stream().map(child -> indexOf(entries, child)).toList())
          .containsExactly(List.of(), List.of(), List.of(), List.of(4, 6, 8), List.of(5), List.of(), List.of(7),
            List.of(), List.of(), List.of(), List.of());

        // The translator's DFHEIBLK is indexed with the program but is a record of its own division.
        Cursor division = entries.get(0).getCursor().dropParentUntil(Cobol.DataDivision.class::isInstance);
        assertThat(DataDescription.recordsOf(division)).extracting(entry -> indexOf(entries, entry))
          .containsExactly(0, 1, 2, 3, 9, 10);
    }
}
