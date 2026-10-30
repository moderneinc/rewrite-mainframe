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
import org.openrewrite.Cursor;
import org.openrewrite.mainframe.cobol.CobolIsoVisitor;
import org.openrewrite.mainframe.cobol.CobolParser;
import org.openrewrite.mainframe.cobol.tree.Cobol;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProgramTest {

    private static Program program(String source) {
        Cobol.CompilationUnit cu = (Cobol.CompilationUnit) CobolParser.builder().build().parse(source)
          .findFirst().orElseThrow();
        return new Program(new Cursor(new Cursor(null, cu), cu.getProgramUnits().get(0)));
    }

    @Test
    void translatorAddsDfheiblkToTheLinkageSectionUnlessTheProgramDeclaresOne() {
        Program program = program(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. NOEIB.                                              \s
            000000 DATA DIVISION.                                                  \s
            000000 WORKING-STORAGE SECTION.                                        \s
            000000 01  WS-LEN         PIC S9(4) COMP.                              \s
            """
        );
        Cursor added = program.getExecInterfaceBlock();
        assertThat(added).isNotNull();
        assertThat(added.getParentOrThrow()).isSameAs(program.getCursor());
        List<String> linkage = new ArrayList<>();
        new CobolIsoVisitor<Integer>() {
            @Override
            public Cobol.DataDescriptionEntry visitDataDescriptionEntry(Cobol.DataDescriptionEntry entry, Integer p) {
                if (getCursor().firstEnclosing(Cobol.LinkageSection.class) != null &&
                    new DataName(new Cursor(getCursor(), entry.getName())).isSystemSupplied()) {
                    linkage.add(entry.getName().getWord());
                }
                return super.visitDataDescriptionEntry(entry, p);
            }
        }.visit(added.getValue(), 0, added.getParentOrThrow());
        assertThat(linkage).contains("DFHEIBLK", "EIBCALEN");

        assertThat(program(
          """
            000000 IDENTIFICATION DIVISION.                                        \s
            000000 PROGRAM-ID. OWNEIB.                                             \s
            000000 DATA DIVISION.                                                  \s
            000000 LINKAGE SECTION.                                                \s
            000000 01  DFHEIBLK.                                                   \s
            000000     05  EIBCALEN   PIC S9(4) COMP.                              \s
            """
        ).getExecInterfaceBlock()).isNull();
    }
}
