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
package org.openrewrite.mainframe.bms.trait;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RewriteTest;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.mainframe.bms.Assertions.bms;

class CobolSymbolicMapTest implements RewriteTest {

    @Test
    void aNamedFieldGetsLengthFlagAttributeAndValueInBothRecords() {
        symbolicMap(
          """
            COSGN00 DFHMSD LANG=COBOL,MODE=INOUT,STORAGE=AUTO
            COSGN0A DFHMDI SIZE=(24,80)
                    DFHMDF POS=(1,1),LENGTH=6,INITIAL='Tran :'
            TRNNAME DFHMDF POS=(1,8),LENGTH=4
                    DFHMSD TYPE=FINAL
                    END
            """,
          copybook -> assertThat(copybook).isEqualTo(
            """
                     01  COSGN0AI.
                         02  FILLER PIC X(12).
                         02  TRNNAMEL    COMP  PIC  S9(4).
                         02  TRNNAMEF    PICTURE X.
                         02  FILLER REDEFINES TRNNAMEF.
                           03 TRNNAMEA    PICTURE X.
                         02  TRNNAMEI  PIC X(4).
                     01  COSGN0AO REDEFINES COSGN0AI.
                         02  FILLER PIC X(12).
                         02  FILLER PICTURE X(3).
                         02  TRNNAMEO  PIC X(4).
              """)
        );
    }

    @Test
    void extendedAttributesAddAByteEachInTheirOwnOrder() {
        symbolicMap(
          """
            SET1    DFHMSD LANG=COBOL,MODE=INOUT,DSATTS=(HILIGHT,COLOR)
            MAP1    DFHMDI SIZE=(24,80)
            FLD     DFHMDF POS=(1,1),LENGTH=4
                    DFHMSD TYPE=FINAL
                    END
            """,
          copybook -> assertThat(copybook)
            .contains("           02  FILLER   PICTURE X(2).\n           02  FLDI  PIC X(4).")
            .contains("           02  FLDC    PICTURE X.\n           02  FLDH    PICTURE X.\n")
        );

        symbolicMap(
          """
            SET1    DFHMSD LANG=COBOL,MODE=INOUT,EXTATT=YES
            MAP1    DFHMDI SIZE=(24,80)
            FLD     DFHMDF POS=(1,1),LENGTH=4
                    DFHMSD TYPE=FINAL
                    END
            """,
          copybook -> assertThat(copybook)
            .contains("           02  FILLER   PICTURE X(4).")
            .contains("FLDC    PICTURE X.\n           02  FLDP    PICTURE X.\n" +
                      "           02  FLDH    PICTURE X.\n           02  FLDV    PICTURE X.\n")
        );

        symbolicMap(
          """
            SET1    DFHMSD LANG=COBOL,MODE=INOUT,EXTATT=MAPONLY
            MAP1    DFHMDI SIZE=(24,80)
            FLD     DFHMDF POS=(1,1),LENGTH=4
                    DFHMSD TYPE=FINAL
                    END
            """,
          copybook -> assertThat(copybook).doesNotContain("FLDC")
        );
    }

    @Test
    void tablesAreNumberedAcrossMapsThatShareStorage() {
        symbolicMap(
          """
            SET1    DFHMSD LANG=COBOL,MODE=INOUT,DSATTS=COLOR
            MAP1    DFHMDI SIZE=(24,80)
            LINE    DFHMDF POS=(1,1),LENGTH=8,OCCURS=4
            MAP2    DFHMDI SIZE=(24,80)
            MSG     DFHMDF POS=(1,1),LENGTH=79,OCCURS=3
                    DFHMSD TYPE=FINAL
                    END
            """,
          copybook -> assertThat(copybook).isEqualTo(
            """
                     01  MAP1I.
                         02  LINED OCCURS 4 TIMES.
                           03  LINEL    COMP  PIC  S9(4).
                           03  LINEF    PICTURE X.
                           03  FILLER   PICTURE X(1).
                           03  LINEI  PIC X(8).
                     01  MAP1O REDEFINES MAP1I.
                         02  DFHMS1 OCCURS 4 TIMES.
                           03  FILLER PICTURE X(2).
                           03  LINEA    PICTURE X.
                           03  LINEC    PICTURE X.
                           03  LINEO  PIC X(8).
                     01  MAP2I REDEFINES MAP1I.
                         02  MSGD OCCURS 3 TIMES.
                           03  MSGL    COMP  PIC  S9(4).
                           03  MSGF    PICTURE X.
                           03  FILLER   PICTURE X(1).
                           03  MSGI  PIC X(79).
                     01  MAP2O REDEFINES MAP2I.
                         02  DFHMS2 OCCURS 3 TIMES.
                           03  FILLER PICTURE X(2).
                           03  MSGA    PICTURE X.
                           03  MSGC    PICTURE X.
                           03  MSGO  PIC X(79).
              """)
        );
    }

    @Test
    void picturesSetTheValuesAndAnUnwrittenLength() {
        symbolicMap(
          """
            SET1    DFHMSD LANG=COBOL,MODE=INOUT
            MAP1    DFHMDI SIZE=(24,80)
            AMOUNT  DFHMDF POS=(1,1),LENGTH=6,PICIN='9(6)',PICOUT='ZZZ9.9'
                    DFHMSD TYPE=FINAL
                    END
            """,
          copybook -> assertThat(copybook)
            .contains("02  AMOUNTI  PIC 9(6).")
            .contains("02  AMOUNTO  PIC ZZZ9.9.")
        );

        // Without LENGTH the field is as long as its picture.
        symbolicMap(
          """
            SET1    DFHMSD LANG=COBOL,MODE=INOUT
            MAP1    DFHMDI SIZE=(24,80)
            PAGE    DFHMDF POS=(1,74),PICOUT='ZZZZ9(2)'
                    DFHMSD TYPE=FINAL
                    END
            """,
          copybook -> assertThat(copybook).contains("02  PAGEI  PIC X(6).")
        );
    }

    @Test
    void modeSaysWhichRecordsAreWritten() {
        symbolicMap(
          """
            SET1    DFHMSD LANG=COBOL,MODE=IN
            MAP1    DFHMDI SIZE=(24,80)
            FLD     DFHMDF POS=(1,1),LENGTH=4
                    DFHMSD TYPE=FINAL
                    END
            """,
          copybook -> assertThat(copybook).contains("01  MAP1I.").doesNotContain("MAP1O")
        );

        // OUT is the default.
        symbolicMap(
          """
            SET1    DFHMSD LANG=COBOL
            MAP1    DFHMDI SIZE=(24,80)
            FLD     DFHMDF POS=(1,1),LENGTH=4
                    DFHMSD TYPE=FINAL
                    END
            """,
          copybook -> assertThat(copybook).startsWith("       01  MAP1O.\n").doesNotContain("MAP1I")
        );
    }

    @Test
    void noSymbolicMapForAMapsetThisCannotWrite() {
        // Assembled for an assembler program, which is the default.
        symbolicMap(
          """
            SET1    DFHMSD MODE=INOUT
            MAP1    DFHMDI SIZE=(24,80)
            FLD     DFHMDF POS=(1,1),LENGTH=4
                    DFHMSD TYPE=FINAL
                    END
            """,
          copybook -> assertThat(copybook).isNull()
        );

        symbolicMap(
          """
            SET1    DFHMSD LANG=COBOL,MODE=INOUT
            MAP1    DFHMDI SIZE=(24,80)
            MO      DFHMDF POS=(1,1),LENGTH=2,GRPNAME=DATE
            DAY     DFHMDF POS=(1,3),LENGTH=2,GRPNAME=DATE
                    DFHMSD TYPE=FINAL
                    END
            """,
          copybook -> assertThat(copybook).isNull()
        );
    }

    private void symbolicMap(String mapset, Consumer<String> copybook) {
        rewriteRun(
          bms(mapset, spec -> spec.afterRecipe(cu ->
            copybook.accept(new Mapset.Matcher().lower(cu).findFirst().orElseThrow().getCobolSymbolicMap())))
        );
    }
}
