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
package org.openrewrite.mainframe.cobol;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class CopybookParserTest {

    private final CopybookParser parser = CopybookParser.builder().build();

    @ParameterizedTest
    @ValueSource(strings = {"ACCTREC.cpy", "ACCTREC.CPY", "ACCTREC.copy", "ACCTREC.COPY", "ACCTREC.dcl", "ACCTREC.DCL", "copy/ACCTREC.Cpy"})
    void acceptsACopybookByExtension(String name) {
        assertThat(parser.accept(Path.of(name))).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"ACCTPOST.cbl", "ACCTPOST.cob", "ACCTPOST.cobol", "ACCTPOST.jcl", "ACCTREC.inc", "ACCTREC"})
    void rejectsOtherExtensions(String name) {
        assertThat(parser.accept(Path.of(name))).isFalse();
    }

    @Test
    void aMemberWithAProgramExtensionThatHoldsNoProgramIsACopybook(@TempDir Path dir) throws IOException {
        Path copybook = Files.writeString(dir.resolve("STRFLDS.COB"), """
                 01  STRUCTURED-FIELDS GLOBAL.
                     05  SF-BEGIN-PAGE PIC X(3) VALUE X'D3A8AF'.
          """);
        assertThat(parser.accept(copybook)).isTrue();
        assertThat(CobolParser.builder().build().accept(copybook)).isFalse();

        Path oneLine = Files.writeString(dir.resolve("FLAG.cob"), "       01  WS-FLAG PIC X.");
        assertThat(parser.accept(oneLine)).isTrue();
    }

    @Test
    void aMemberThatIsNotFixedFormatCobolStaysWithTheProgramParser(@TempDir Path dir) throws IOException {
        Path ebcdic = Files.write(dir.resolve("HELLO.cbl"),
          "       IDENTIFICATION DIVISION.\n       PROGRAM-ID. HELLO.\n".getBytes(Charset.forName("IBM037")));
        assertThat(parser.accept(ebcdic)).isFalse();
        assertThat(CobolParser.builder().build().accept(ebcdic)).isTrue();

        Path controlCards = Files.writeString(dir.resolve("IEFBR14.cbl"), " DELETE PROD.ACCOUNT.MASTER\n SET MAXCC=0\n");
        assertThat(parser.accept(controlCards)).isFalse();
    }

    @Test
    void aProgramWhoseIdentificationDivisionFollowsALongBannerIsAProgram(@TempDir Path dir) throws IOException {
        String banner = ("      * " + "x".repeat(63) + "\n").repeat(80);
        Path program = Files.writeString(dir.resolve("BANNER.cbl"), banner + """
                 IDENTIFICATION DIVISION.
                 PROGRAM-ID. BANNER.
          """);
        assertThat(Files.size(program)).isGreaterThan(4096);
        assertThat(parser.accept(program)).isFalse();
        assertThat(CobolParser.builder().build().accept(program)).isTrue();
    }
}
