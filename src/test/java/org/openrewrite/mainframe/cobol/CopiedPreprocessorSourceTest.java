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
import org.openrewrite.mainframe.cobol.tree.CobolPreprocessor;
import org.openrewrite.mainframe.cobol.trait.CopybookReference;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CopiedPreprocessorSourceTest {
    private static final String RECORD = "000000 01 RECORD-DATA.\n000000 05 ORIGINAL-FIELD PIC X(8).\n";
    private static final String REPLACE = "000000 REPLACE ==ORIGINAL-FIELD== BY ==RENAMED-FIELD==.\n";
    private static final String OFF = "000000 REPLACE OFF.\n";

    private static String program(String data, String procedure) {
        return "000000 IDENTIFICATION DIVISION.\n000000 PROGRAM-ID. COPYDIRECT.\n" +
          "000000 DATA DIVISION.\n000000 WORKING-STORAGE SECTION.\n" + data +
          "000000 PROCEDURE DIVISION.\n" + procedure + "000000 GOBACK.\n";
    }

    private static SourceFile book(String name, String content) {
        var ctx = new InMemoryExecutionContext(t -> { throw new AssertionError(t); });
        var parsed = CopybookParser.builder().build().parse(ctx, content).toList();
        assertThat(parsed).singleElement().satisfies(source -> assertThat(source.printAll()).isEqualTo(content));
        return parsed.get(0).withSourcePath(Path.of(name + ".cpy"));
    }

    private static Cobol.CompilationUnit parse(CobolParser parser, String source) {
        var parsed = parser.parse(new InMemoryExecutionContext(t -> { throw new AssertionError(t); }), source).toList();
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

    static Stream<Arguments> replacements() {
        List<Arguments> args = new ArrayList<>();
        for (boolean active : new boolean[]{true, false})
            for (boolean trailing : new boolean[]{true, false})
                for (String newline : List.of("\n", "\r\n")) args.add(Arguments.of(active, trailing, newline));
        return args.stream();
    }

    @ParameterizedTest
    @MethodSource("replacements")
    void copiedReplacementDirectivesStayInTheCopybook(boolean active, boolean trailing, String newline) {
        String replacement = active ? REPLACE : REPLACE.replace("==ORIGINAL-FIELD==", "==UNUSED==");
        String content = (replacement + RECORD + OFF + (trailing ? "" : "000000 01 LAST-FIELD PIC X.\n")).replace("\n", newline);
        var copy = book("DATAREC", content);
        var parser = CobolParser.builder().copybooks(List.of(copy)).build();
        String source = program("000000 COPY DATAREC.\n000000 01 AFTER-COPY PIC X.\n", "000000 CONTINUE.\n").replace("\n", newline);
        assertThat(declarations(parse(parser, source))).contains("RECORD-DATA", "AFTER-COPY", active ? "RENAMED-FIELD" : "ORIGINAL-FIELD");
        assertThat(copy.printAll()).isEqualTo(content);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void nestedCopiedDirectivesDoNotAppearInTheHost(boolean endingNewline) {
        String inner = REPLACE + RECORD + OFF;
        if (!endingNewline) inner = inner.stripTrailing();
        var copies = List.of(book("DATAREC", inner), book("OUTERREC", "000000 COPY DATAREC.\n"));
        var cu = parse(CobolParser.builder().copybooks(copies).build(), program("000000 COPY OUTERREC.\n", "000000 CONTINUE.\n"));
        assertThat(declarations(cu)).contains("RENAMED-FIELD");
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void aDirectiveOnlyCopyDoesNotLeakIntoFollowingHostSource(boolean endingNewline) {
        String content = REPLACE + OFF;
        if (!endingNewline) content = content.stripTrailing();
        var parser = CobolParser.builder().copybooks(List.of(book("DIRECTIVE", content))).build();
        parse(parser, program("000000 COPY DIRECTIVE.\n" + RECORD, "000000 CONTINUE.\n"));
    }

    @Test
    void hostDirectivesRemainWhileSharedCopiedDirectivesStayHidden() {
        String content = REPLACE + RECORD + OFF;
        var copy = book("DATAREC", content);
        var parser = CobolParser.builder().copybooks(List.of(copy)).build();
        String source = program("000000 COPY DATAREC.\n" + REPLACE +
          "000000 01 ORIGINAL-FIELD PIC X.\n" + OFF, "000000 CONTINUE.\n");
        for (int i = 0; i < 3; i++) {
            assertThat(declarations(parse(parser, source))).contains("RENAMED-FIELD");
            assertThat(copy.printAll()).isEqualTo(content);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"EXEC CICS RETURN END-EXEC.", "EXEC SQL COMMIT END-EXEC."})
    void copiedEmbeddedCommandsRemainInTheResolvedCopybook(String command) {
        var parser = CobolParser.builder().copybooks(List.of(book("COMMANDS", "000000 " + command + "\n"))).build();
        var cu = parse(parser, program(RECORD, "000000 COPY COMMANDS.\n"));
        int[] commands = {0};
        new CopybookReference.Matcher().asVisitor((reference, p) -> {
            assertThat(reference.isResolved()).isTrue();
            new CobolPreprocessorIsoVisitor<Integer>() {
                @Override
                public CobolPreprocessor.ExecStatement visitExecStatement(CobolPreprocessor.ExecStatement statement, Integer p) {
                    commands[0]++;
                    return statement;
                }
            }.visit(reference.getCopybook(), 0);
            return reference.getTree();
        }).visit(cu, 0);
        assertThat(commands[0]).isEqualTo(1);
    }
}
