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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.cobol.tree.Cobol;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CopiedDebugLinesTest {
    private static String program(boolean debugging, String data, String procedure) {
        return ("IDENTIFICATION DIVISION.\nPROGRAM-ID. COPYDEBUG.\n" +
          (debugging ? "ENVIRONMENT DIVISION.\nCONFIGURATION SECTION.\nSOURCE-COMPUTER. IBM WITH DEBUGGING MODE.\n" : "") +
          "DATA DIVISION.\nWORKING-STORAGE SECTION.\n" + data + "PROCEDURE DIVISION.\n" + procedure + "GOBACK.\n")
          .lines().map(line -> "000000 " + line + "\n").reduce("", String::concat);
    }

    private static List<SourceFile> book(String content) {
        var ctx = new InMemoryExecutionContext(t -> { throw new AssertionError(t); });
        var parsed = CopybookParser.builder().build().parse(ctx, content).toList();
        assertThat(parsed).singleElement().satisfies(source -> assertThat(source.printAll()).isEqualTo(content));
        return parsed.stream().<SourceFile>map(source -> source.withSourcePath(Path.of("DEBUGREC.cpy"))).toList();
    }

    private static Cobol.CompilationUnit parse(CobolParser parser, String source) {
        var ctx = new InMemoryExecutionContext(t -> { throw new AssertionError(t); });
        var parsed = parser.parse(ctx, source).toList();
        assertThat(parsed).singleElement().isInstanceOf(Cobol.CompilationUnit.class);
        assertThat(parsed.get(0).printAll()).isEqualTo(source);
        return (Cobol.CompilationUnit) parsed.get(0);
    }

    private static List<String> declarations(Cobol.CompilationUnit cu) {
        List<String> names = new ArrayList<>();
        new CobolIsoVisitor<Integer>() {
            @Override
            public Cobol.DataDescriptionEntry visitDataDescriptionEntry(Cobol.DataDescriptionEntry entry, Integer p) {
                names.add(entry.getName().getWord());
                return super.visitDataDescriptionEntry(entry, p);
            }
        }.visit(cu, 0);
        return names;
    }

    static Stream<Arguments> cases() {
        List<Arguments> cases = new ArrayList<>();
        for (boolean debugging : new boolean[]{false, true})
            for (String indicator : List.of("D", "d"))
                for (boolean atEnd : new boolean[]{false, true})
                    for (String newline : List.of("\n", "\r\n")) cases.add(Arguments.of(debugging, indicator, atEnd, newline));
        return cases.stream();
    }

    @ParameterizedTest
    @MethodSource("cases")
    void copiedDeclarationsFollowTheIncludingProgramsDebugMode(boolean debugging, String indicator, boolean atEnd, String newline) {
        String debug = "000000" + indicator + "05 DEBUG-FIELD PIC X(8).\n";
        String normal = "000000 05 NORMAL-FIELD PIC X(4).\n";
        String content = ("000000 01 RECORD-DATA.\n" + (atEnd ? normal + debug : debug + normal)).replace("\n", newline);
        var books = book(content);
        String source = program(debugging, "COPY DEBUGREC.\n01 AFTER-COPY PIC X.\n", "CONTINUE.\n").replace("\n", newline);
        var names = declarations(parse(CobolParser.builder().copybooks(books).build(), source));
        assertThat(names).contains("RECORD-DATA", "NORMAL-FIELD", "AFTER-COPY");
        assertThat(names.contains("DEBUG-FIELD")).isEqualTo(debugging);
        assertThat(books.get(0).printAll()).isEqualTo(content);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void aProcedureCopyContainingOnlyADebugLinePreservesFollowingHostStatements(boolean debugging) {
        var books = book("000000DDISPLAY 'DEBUG'.\n");
        var cu = parse(CobolParser.builder().copybooks(books).build(), program(debugging,
          "01 BUFFER-TEXT PIC X.\n", "COPY DEBUGREC.\nCONTINUE.\n"));
        int[] displays = {0};
        int[] returns = {0};
        new CobolIsoVisitor<Integer>() {
            @Override
            public Cobol.Display visitDisplay(Cobol.Display display, Integer p) {
                displays[0]++;
                return display;
            }
            @Override
            public Cobol.GoBack visitGoBack(Cobol.GoBack goBack, Integer p) {
                returns[0]++;
                return goBack;
            }
        }.visit(cu, 0);
        assertThat(displays[0]).isEqualTo(debugging ? 1 : 0);
        assertThat(returns[0]).isEqualTo(1);
    }

    @Test
    void sharedCopybooksDoNotCarryOneProgramsDebugModeIntoAnother() {
        String content = "000000 01 RECORD-DATA.\n000000D05 DEBUG-FIELD PIC X(8).\n000000 05 NORMAL-FIELD PIC X(4).\n";
        var books = book(content);
        var parser = CobolParser.builder().copybooks(books).build();
        for (boolean debugging : new boolean[]{true, false, true, false}) {
            var names = declarations(parse(parser, program(debugging, "COPY DEBUGREC.\n", "CONTINUE.\n")));
            assertThat(names.contains("DEBUG-FIELD")).isEqualTo(debugging);
            assertThat(books.get(0).printAll()).isEqualTo(content);
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void aDebugCopyWithoutAnEndingNewlineDoesNotConsumeFollowingHostWords(boolean debugging) {
        var books = book("000000DDISPLAY 'DEBUG'.");
        parse(CobolParser.builder().copybooks(books).build(), program(debugging,
          "01 BUFFER-TEXT PIC X.\n", "COPY DEBUGREC. CONTINUE.\n"));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void nestedCopiesKeepTheIncludingDebugMode(boolean debugging) {
        var books = new ArrayList<>(book("000000D05 DEBUG-FIELD PIC X(8).\n"));
        CopybookParser.builder().build().parse("000000 01 RECORD-DATA.\n000000 COPY DEBUGREC.\n000000 05 NORMAL-FIELD PIC X(4).\n")
          .forEach(source -> books.add(source.withSourcePath(Path.of("OUTERREC.cpy"))));
        var cu = parse(CobolParser.builder().copybooks(books).build(), program(debugging, "COPY OUTERREC.\n", "CONTINUE.\n"));
        assertThat(declarations(cu)).contains("RECORD-DATA", "NORMAL-FIELD");
        assertThat(declarations(cu).contains("DEBUG-FIELD")).isEqualTo(debugging);
    }

    @Test
    void copiedConfigurationUsesTheSameExpandedModeForTokensAndSource() {
        var books = new ArrayList<>(book("000000 01 RECORD-DATA.\n000000D05 DEBUG-FIELD PIC X(8).\n000000 05 NORMAL-FIELD PIC X(4).\n"));
        CopybookParser.builder().build().parse("000000 SOURCE-COMPUTER. IBM WITH DEBUGGING MODE.\n")
          .forEach(source -> books.add(source.withSourcePath(Path.of("CONFIGCP.cpy"))));
        String source = program(false, "COPY DEBUGREC.\n", "CONTINUE.\n").replace("000000 DATA DIVISION.",
          "000000 ENVIRONMENT DIVISION.\n000000 CONFIGURATION SECTION.\n000000 COPY CONFIGCP.\n000000 DATA DIVISION.");
        assertThat(declarations(parse(CobolParser.builder().copybooks(books).build(), source))).contains("DEBUG-FIELD");
    }
}
