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
package org.openrewrite.mainframe.bms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.ParseExceptionResult;
import org.openrewrite.Parser;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.bms.marker.SequenceArea;
import org.openrewrite.mainframe.bms.tree.Bms;
import org.openrewrite.tree.ParseError;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Printing back exactly, over the layouts a test fixture cannot express.
 * <p>
 * These parse directly rather than through {@code Assertions.bms}, because the test framework trims
 * the common indentation off a source block — which for BMS would move the name field, the operand
 * field and column 72, and so change what is being tested.
 */
class BmsParserTest {

    @Test
    void aSequenceAreaIsKeptAndPrintedBack() {
        String source =
          "COSGN00 DFHMSD LANG=COBOL" + blanks(47) + "COSGN001\n" +
          "        DFHMDF POS=(1,1),LENGTH=6" + blanks(40) + "COSGN002\n";

        Bms.CompilationUnit cu = parse(source);
        assertThat(cu.printAll()).isEqualTo(source);

        // Columns 73-80 are not operands. They are carried as a marker on the word that ends the
        // line they belong to, which is where a statement written over several lines needs them.
        assertThat(sequenceAreasIn(cu)).containsExactly("COSGN001", "COSGN002");
    }

    /**
     * A line padded out to 80 with blanks has no sequence area, only trailing space. Reading one
     * there would put a word where the source has none.
     */
    @Test
    void blankColumnsAreNotASequenceArea() {
        String source = "COSGN00 DFHMSD LANG=COBOL" + blanks(55) + "\n";

        Bms.CompilationUnit cu = parse(source);
        assertThat(cu.printAll()).isEqualTo(source);
        assertThat(cu.getStatements()).hasSize(1);
    }

    @Test
    void aSequenceAreaIsReadWhole() {
        String source =
          // Numbered from column 74, so the field opens with a blank.
          "* A COMMENT CARD" + blanks(56) + " 010208\n" +
          padTo72("         DFHMDF POS=(1,1),LENGTH=40,INITIAL='A LITERAL WRITTEN OVER") + "MAP 0010\n" +
          "               TWO CARDS'\n";

        Bms.CompilationUnit cu = parse(source);
        assertThat(cu.printAll()).isEqualTo(source);
        assertThat(sequenceAreasIn(cu)).containsExactly("010208", "MAP 0010");
    }

    @Test
    void aLiteralDoesNotRunIntoTheSequenceArea() {
        String source =
          // The literal's closing quote falls in column 73.
          "         DFHMDF POS=(1,1),LENGTH=16,ATTRB=PROT,INITIAL='Account balance:'\n" +
          "ACCTBAL  DFHMDF POS=(1,18),LENGTH=10\n";

        Bms.CompilationUnit cu = parse(source);
        assertThat(cu.printAll()).isEqualTo(source);
        assertThat(sequenceAreasIn(cu)).containsExactly("'");
    }

    @Test
    void aCommentCardInsideAContinuedStatement() {
        String source =
          padTo72("         DFHMDF POS=(2,50),LENGTH=11,ATTRB=(ASKIP,NORM),") + "\n" +
          "*              INITIAL='OLD LABEL:'\n" +
          "               INITIAL='NEW LABEL:'\n" +
          "CRDCARD  DFHMDF POS=(2,62),LENGTH=16\n";

        Bms.CompilationUnit cu = parse(source);
        assertThat(cu.printAll()).isEqualTo(source);
        assertThat(cu.getStatements()).hasSize(2);
        Bms.MacroStatement field = (Bms.MacroStatement) cu.getStatements().get(0);
        assertThat(field.getParameter("INITIAL").getValueText()).isEqualTo("'NEW LABEL:'");
        assertThat(field.getOperands()).anySatisfy(operand -> assertThat(operand).isInstanceOf(Bms.Comment.class));
    }

    @Test
    void aCommentCardBeforeAnyOperationEndsTheStatement() {
        String source =
          // A name running into column 72, so the line is continued with no operation on it.
          blanks(71).replace(' ', 'A') + "X\n" +
          "* A COMMENT CARD\n" +
          "               DFHMDF POS=(1,1),LENGTH=5\n";

        Bms.CompilationUnit cu = parse(source);
        assertThat(cu.printAll()).isEqualTo(source);
        assertThat(((Bms.MacroStatement) cu.getStatements().get(2)).isOperation("DFHMDF")).isTrue();
    }

    @Test
    void aTabSeparatesFieldsAsABlankDoes() {
        String source = "COSGN00\tDFHMSD\tLANG=COBOL\n";

        Bms.CompilationUnit cu = parse(source);
        assertThat(cu.printAll()).isEqualTo(source);
        Bms.MacroStatement mapset = (Bms.MacroStatement) cu.getStatements().get(0);
        assertThat(mapset.getSimpleName()).isEqualTo("COSGN00");
        assertThat(mapset.isOperation("DFHMSD")).isTrue();
    }

    @Test
    void aSourceTheReaderCannotFollowIsAParseError() {
        // A carriage return with no line feed ends a line for the line reader but not for the lexer.
        Parser.Input input = new Parser.Input(Paths.get("COSGN00.bms"), () -> new ByteArrayInputStream(
          "COSGN00 DFHMSD LANG=COBOL\r        DFHMDF POS=(1,1)\n".getBytes(StandardCharsets.UTF_8)));
        SourceFile parsed = BmsParser.builder().build()
          .parseInputs(singletonList(input), null, new InMemoryExecutionContext()).findFirst().orElseThrow();

        assertThat(parsed).isInstanceOf(ParseError.class);
        ParseExceptionResult failure = parsed.getMarkers().findFirst(ParseExceptionResult.class).orElseThrow();
        assertThat(failure.getMessage()).doesNotContain("not print idempotent").doesNotContain("^^");
    }

    @Test
    void windowsLineEndings() {
        String source =
          "COSGN00 DFHMSD LANG=COBOL,                                             -\r\n" +
          "               MODE=INOUT\r\n" +
          "        END\r\n";

        Bms.CompilationUnit cu = parse(source);
        assertThat(cu.printAll()).isEqualTo(source);
        assertThat(cu.getStatements()).hasSize(2);
    }

    @Test
    void noTrailingNewline() {
        String source =
          "COSGN00 DFHMSD LANG=COBOL\n" +
          "        END";

        Bms.CompilationUnit cu = parse(source);
        assertThat(cu.printAll()).isEqualTo(source);
        assertThat(cu.getStatements()).hasSize(2);
    }

    @Test
    void anEmptyLineBetweenStatements() {
        String source =
          "COSGN00 DFHMSD LANG=COBOL\n" +
          "\n" +
          "        END\n";

        Bms.CompilationUnit cu = parse(source);
        assertThat(cu.printAll()).isEqualTo(source);
        assertThat(cu.getStatements()).hasSize(2);
    }

    /**
     * An apostrophe in a comment is not the start of a literal, and a comment line runs to the end
     * of the line whatever is written in it.
     */
    @Test
    void anApostropheInAComment() {
        String source =
          "* Don't take this as the start of a string\n" +
          "COSGN00 DFHMSD LANG=COBOL\n";

        Bms.CompilationUnit cu = parse(source);
        assertThat(cu.printAll()).isEqualTo(source);
        assertThat(cu.getStatements()).hasSize(2);
        assertThat(cu.getStatements().get(0)).isInstanceOf(Bms.Comment.class);
        assertThat(((Bms.Comment) cu.getStatements().get(0)).getWord().getText())
          .isEqualTo("* Don't take this as the start of a string");
    }

    @ParameterizedTest
    @ValueSource(strings = {"COSGN00.bms", "COSGN00.BMS", "bms/COSGN00.Bms"})
    void acceptsAMapSetByExtension(String name) {
        assertThat(BmsParser.builder().build().accept(Paths.get(name))).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"COSGN00.cbl", "COSGN00.cpy", "COSGN00.jcl", "COSGN00"})
    void rejectsOtherExtensions(String name) {
        assertThat(BmsParser.builder().build().accept(Paths.get(name))).isFalse();
    }

    /**
     * The grammar reads any assembler-shaped text, so a file that defines no map is refused by name
     * rather than read as an empty map set — and under its own type, so a parse-quality report can
     * tell it from a grammar gap.
     */
    @Test
    void aFileThatIsNotAMapSetIsSaidSo() {
        Parser.Input input = new Parser.Input(Paths.get("COSGN00.bms"),
          () -> new ByteArrayInputStream("         MACRO\n         MYMAC\n         MEND\n".getBytes(StandardCharsets.UTF_8)));
        SourceFile parsed = BmsParser.builder().build()
          .parseInputs(singletonList(input), null, new InMemoryExecutionContext()).findFirst().orElseThrow();

        assertThat(parsed).isInstanceOf(ParseError.class);
        ParseExceptionResult failure = parsed.getMarkers().findFirst(ParseExceptionResult.class).orElseThrow();
        assertThat(failure.getParserType()).isEqualTo("BmsParser");
        assertThat(failure.getExceptionType()).isEqualTo("WrongLanguageException");
        assertThat(failure.getMessage()).contains("COSGN00.bms is not a BMS map set: it has no DFHMSD, DFHMDI or DFHMDF macro.");
    }

    private static List<String> sequenceAreasIn(Bms.CompilationUnit cu) {
        List<String> areas = new ArrayList<>();
        new BmsIsoVisitor<Integer>() {
            @Override
            public Bms preVisit(Bms tree, Integer p) {
                tree.getMarkers().findFirst(SequenceArea.class)
                  .ifPresent(sequenceArea -> areas.add(sequenceArea.getText()));
                return tree;
            }
        }.visit(cu, 0);
        return areas;
    }

    private static Bms.CompilationUnit parse(String source) {
        List<SourceFile> parsed = BmsParser.builder().build()
          .parse(new InMemoryExecutionContext(), source)
          .collect(Collectors.toList());
        assertThat(parsed).hasSize(1);
        assertThat(parsed.get(0)).isInstanceOf(Bms.CompilationUnit.class);
        return (Bms.CompilationUnit) parsed.get(0);
    }

    /**
     * The line padded out to column 71 with an {@code X} in column 72, saying the next line continues it.
     */
    private static String padTo72(String line) {
        return line + blanks(71 - line.length()) + "X";
    }

    private static String blanks(int n) {
        StringBuilder blanks = new StringBuilder();
        for (int i = 0; i < n; i++) {
            blanks.append(' ');
        }
        return blanks.toString();
    }
}
