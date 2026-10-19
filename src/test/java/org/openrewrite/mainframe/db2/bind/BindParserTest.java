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
package org.openrewrite.mainframe.db2.bind;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.ParseExceptionResult;
import org.openrewrite.Parser;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.db2.bind.trait.RunCommand;
import org.openrewrite.mainframe.db2.bind.tree.Bind;
import org.openrewrite.test.RewriteTest;
import org.openrewrite.tree.ParseError;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openrewrite.mainframe.db2.bind.Assertions.bind;

class BindParserTest implements RewriteTest {

    @Test
    void packageBind() {
        rewriteRun(
          bind(
            """
              DSN SYSTEM(DB2P)
              BIND PACKAGE(CLMPKG) OWNER(CLMPROD) QUALIFIER(CLM) -
                   MEMBER(CLMD010) LIBRARY('CLM.PROD.DBRMLIB') -
                   ACTION(REPLACE) VALIDATE(BIND) ISOLATION(CS)
              END
              """,
            spec -> spec.afterRecipe(cu -> {
                assertThat(cu.getStatements()).hasSize(3);
                Bind.Command command = (Bind.Command) cu.getStatements().get(1);
                assertThat(command.getVerb().getText()).isEqualTo("BIND");
                assertThat(command.getParameter("MEMBER").getValueText()).isEqualTo("(CLMD010)");
                // The lexer breaks the quoted data set name out on its own, so the value is three
                // words that have to read back as one.
                assertThat(command.getParameter("LIBRARY").getValueText()).isEqualTo("('CLM.PROD.DBRMLIB')");
            })
          )
        );
    }

    @Test
    void keywordSeparatedFromItsValueByABlank() {
        rewriteRun(
          bind(
            """
              DSN     SYSTEM    (DB2D)
              BIND    PACKAGE   (STOCKTRD)   -
                      MEMBER    (ACCT01)     -
                      ACTION (REPLACE)  RETAIN
              END
              """,
            spec -> spec.afterRecipe(cu -> {
                assertThat(((Bind.Command) cu.getStatements().get(0)).getParameter("SYSTEM").getValueText())
                  .isEqualTo("(DB2D)");
                Bind.Command command = (Bind.Command) cu.getStatements().get(1);
                assertThat(command.getParameter("PACKAGE").getValueText()).isEqualTo("(STOCKTRD)");
                // RETAIN takes no value, so it has to survive as an operand of its own rather than
                // being swallowed by the one in front of it.
                assertThat(command.getParameter("RETAIN").getValue()).isEmpty();
            })
          )
        );
    }

    @Test
    void verbOnALineOfItsOwn() {
        rewriteRun(
          bind(
            """
              BIND                    -
                   PLAN(&PLAN.)       -
                   PKLIST(&COLLID..*) -
                   ACTION(REPLACE)
              """,
            spec -> spec.afterRecipe(cu -> {
                assertThat(cu.getStatements()).hasSize(1);
                Bind.Command command = (Bind.Command) cu.getStatements().get(0);
                assertThat(command.getParameter("PLAN").getValueText()).isEqualTo("(&PLAN.)");
                assertThat(command.getParameter("PKLIST").getValueText()).isEqualTo("(&COLLID..*)");
            })
          )
        );
    }

    @Test
    void listContinuedInsideItsOwnParentheses() {
        rewriteRun(
          bind(
            """
              BIND PLAN(BANK) -
               PKLIST( -
               NULLID.*,PHBANK.* )
              """,
            spec -> spec.afterRecipe(cu -> {
                Bind.Command command = (Bind.Command) cu.getStatements().get(0);
                assertThat(command.getParameter("PKLIST").getValueText()).isEqualTo("( NULLID.*,PHBANK.* )");
            })
          )
        );
    }

    @Test
    void aBlankLineEndsACommand() {
        rewriteRun(
          bind(
            """
              DSN SYSTEM(DB2D)

              BIND PACKAGE(A) MEMBER(P1)

              BIND PACKAGE(A) MEMBER(P2)
              END
              """,
            spec -> spec.afterRecipe(cu -> assertThat(cu.getStatements()).hasSize(4))
          )
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"BINDPKG1.bnd", "BINDPKG1.BND", "cardlib/BINDPKG1.Bnd"})
    void acceptsADeckByExtension(String name) {
        assertThat(BindParser.builder().build().accept(Paths.get(name))).isTrue();
    }

    /**
     * A {@code CARDLIB} member has no extension to be known by, so the parser reads far enough into
     * one to see whether it binds anything.
     */
    @Test
    void acceptsAnExtensionlessMemberThatBinds(@TempDir Path tempDir) throws IOException {
        Path deck = write(tempDir, "BINDPKG1", "DSN SYSTEM(DB2P)\nBIND PACKAGE(CLMPKG) MEMBER(CLMD010)\nEND\n");
        Path queries = write(tempDir, "CATPKDEP", "SELECT * FROM SYSIBM.SYSPACKDEP;\n");
        Path notice = write(tempDir, "LICENSE", "Apache License, Version 2.0\n");

        BindParser parser = BindParser.builder().build();
        assertThat(parser.accept(deck)).isTrue();
        assertThat(parser.accept(queries)).isFalse();
        assertThat(parser.accept(notice)).isFalse();
    }

    /**
     * The grammar reads any command deck, so a member named for the language but binding, running and
     * freeing nothing is refused by name rather than read as an empty deck — and under its own type,
     * so a parse-quality report can tell it from a grammar gap.
     */
    @Test
    void aMemberThatBindsNothingIsSaidSo() {
        Parser.Input input = new Parser.Input(Paths.get("DSNONLY.bnd"),
          () -> new ByteArrayInputStream("DSN SYSTEM(DB2P)\nEND\n".getBytes(StandardCharsets.UTF_8)));
        SourceFile parsed = BindParser.builder().build()
          .parseInputs(singletonList(input), null, new InMemoryExecutionContext()).findFirst().orElseThrow();

        assertThat(parsed).isInstanceOf(ParseError.class);
        ParseExceptionResult failure = parsed.getMarkers().findFirst(ParseExceptionResult.class).orElseThrow();
        assertThat(failure.getParserType()).isEqualTo("BindParser");
        assertThat(failure.getExceptionType()).isEqualTo("WrongLanguageException");
        assertThat(failure.getMessage()).contains("DSNONLY.bnd is not a bind deck: it has no BIND, REBIND, RUN or FREE subcommand.");
    }

    /**
     * {@code FREE} is a DSN subcommand, so a member that frees packages is a deck to read, not one to
     * refuse.
     */
    @Test
    void aMemberThatFreesIsRead() {
        Parser.Input input = new Parser.Input(Paths.get("FREEPKG.bnd"),
          () -> new ByteArrayInputStream("DSN SYSTEM(DB2P)\nFREE PACKAGE(CLMPKG.CLMD010)\nEND\n".getBytes(StandardCharsets.UTF_8)));
        SourceFile parsed = BindParser.builder().build()
          .parseInputs(singletonList(input), null, new InMemoryExecutionContext()).findFirst().orElseThrow();

        assertThat(parsed).isInstanceOfSatisfying(Bind.CompilationUnit.class, cu ->
          assertThat(cu.getStatements()).extracting(s -> ((Bind.Command) s).getVerb().getText())
            .containsExactly("DSN", "FREE", "END"));
    }

    /**
     * CardDemo opens each DSN deck with its licence, a block of comment lines. They print back where
     * they were written and are no subcommands, so the deck's first subcommand is its {@code DSN} and
     * a line is still counted from the top of the member.
     */
    @Test
    void readsADeckThatOpensWithComments() {
        rewriteRun(
          bind(
            """
              /* Copyright Amazon.com, Inc. or its affiliates.                   */
              /* All Rights Reserved.                                            */
              /*                                                                 */
              /* Licensed under the Apache License, Version 2.0 (the "License"). */
                DSN SYSTEM(DAZ1)
                RUN PROGRAM(DSNTEP4) -
                PLAN(DSNTEP4) -
                PARMS('/ALIGN(LHS) MIXED')
              """,
            spec -> spec.afterRecipe(cu -> {
                assertThat(cu.getStatements()).extracting(s -> ((Bind.Command) s).getVerb().getText())
                  .containsExactly("DSN", "RUN");
                assertThat(new RunCommand.Matcher().lower(cu)).singleElement().satisfies(run -> {
                    assertThat(run.getProgram()).isEqualTo("DSNTEP4");
                    assertThat(run.getPlan()).isEqualTo("DSNTEP4");
                    assertThat(run.getParameters()).isEqualTo("/ALIGN(LHS) MIXED");
                    assertThat(run.getSubsystem()).isEqualTo("DAZ1");
                    assertThat(run.getLine()).isEqualTo(6);
                });
            })
          )
        );
    }

    /**
     * A deck that frees plans and packages and nothing else. CardDemo's ends on its {@code END} with
     * no line ending after it.
     */
    @Test
    void readsADeckThatFrees() {
        rewriteRun(
          bind(
            """
              /* Copyright Amazon.com, Inc. or its affiliates.                   */
               DSN SYSTEM(DAZ1)
                FREE PLAN(CARDDEMO)
                FREE PLAN(COTRTLIC)
                FREE PACKAGE(COTRTLIC.*)
               END""",
            spec -> spec.afterRecipe(cu -> {
                assertThat(cu.getStatements()).extracting(s -> ((Bind.Command) s).getVerb().getText())
                  .containsExactly("DSN", "FREE", "FREE", "FREE", "END");
                Bind.Command freePackage = (Bind.Command) cu.getStatements().get(3);
                assertThat(freePackage.getParameter("PACKAGE").getValueText()).isEqualTo("(COTRTLIC.*)");
            })
          )
        );
    }

    /**
     * A comment written among the cards of one subcommand leaves it one subcommand, whether it has a
     * line of its own or follows the operands, and it may hold the apostrophe a word may not.
     */
    @Test
    void aCommentInsideASubcommand() {
        rewriteRun(
          bind(
            """
              DSN SYSTEM(DB2P)
              BIND PACKAGE(CLMPKG) -
              /* THE CLAIM SYSTEM'S DBRMS */
                   MEMBER(CLMD010) LIBRARY('CLM.PROD.DBRMLIB') /* PROD */ -
                   ACTION(REPLACE)
              END
              """,
            spec -> spec.afterRecipe(cu -> {
                assertThat(cu.getStatements()).hasSize(3);
                Bind.Command bind = (Bind.Command) cu.getStatements().get(1);
                assertThat(bind.getParameters()).extracting(operand -> operand.getKeyword().getText())
                  .containsExactly("PACKAGE", "MEMBER", "LIBRARY", "ACTION");
                assertThat(bind.getParameter("MEMBER").getValueText()).isEqualTo("(CLMD010)");
                assertThat(bind.getParameter("LIBRARY").getValueText()).isEqualTo("('CLM.PROD.DBRMLIB')");
            })
          )
        );
    }

    /**
     * What the lexer takes for a comment becomes white space, and nothing else does. A comment written
     * hard against a word is lexed as part of that word, the longest token being the one taken, and
     * two comments written end to end fill a line of their own. Either way the deck prints back.
     */
    @Test
    void onlyWhatIsLexedAsACommentIsWhiteSpace() {
        rewriteRun(
          bind(
            """
              DSN SYSTEM(DB2P)
              /*FIRST*//*SECOND*/
              BIND /*X*/PACKAGE(CLMPKG) MEMBER(CLMD010) /*Y*/
              END
              """,
            spec -> spec.afterRecipe(cu -> {
                assertThat(cu.getStatements()).extracting(s -> ((Bind.Command) s).getVerb().getText())
                  .containsExactly("DSN", "BIND", "END");
                Bind.Command bind = (Bind.Command) cu.getStatements().get(1);
                assertThat(bind.getParameter("MEMBER").getValueText()).isEqualTo("(CLMD010)");
            })
          )
        );
    }

    /**
     * CardDemo's three DSN decks, as written: .ctl members that open with its licence, told from a
     * SORT card kept beside them by the {@code DSN} past it — DB2FREE included, which frees and does
     * nothing else. Each is read from its file and prints back as it was.
     */
    @Test
    void readsCardDemosDsnDecks(@TempDir Path tempDir) throws IOException {
        String tiad = CARDDEMO_LICENCE + "  DSN SYSTEM(DAZ1)\n  RUN PROGRAM(DSNTIAD) -\n  PARMS('RC0')\n";
        String tep4 = CARDDEMO_LICENCE +
          "  DSN SYSTEM(DAZ1)\n  RUN PROGRAM(DSNTEP4) -\n  PLAN(DSNTEP4) -\n  PARMS('/ALIGN(LHS) MIXED')\n";
        String free = CARDDEMO_LICENCE +
          " DSN SYSTEM(DAZ1)\n  FREE PLAN(CARDDEMO)\n  FREE PLAN(COTRTLIC)\n  FREE PACKAGE(COTRTLIC.*)\n END";
        List<Path> decks = Arrays.asList(write(tempDir, "DB2TIAD1.ctl", tiad), write(tempDir, "DB2TEP41.ctl", tep4),
          write(tempDir, "DB2FREE.ctl", free));
        Path sort = write(tempDir, "SRTCLM01.ctl", CARDDEMO_LICENCE + "  SORT FIELDS=(1,8,CH,A)\n");

        BindParser parser = BindParser.builder().build();
        assertThat(decks).allMatch(parser::accept);
        assertThat(parser.accept(sort)).isFalse();

        List<SourceFile> parsed = parser.parse(decks, tempDir, new InMemoryExecutionContext(t -> {
            throw new IllegalStateException(t);
        })).collect(Collectors.toList());
        assertThat(parsed).allMatch(Bind.CompilationUnit.class::isInstance);
        assertThat(parsed).extracting(SourceFile::printAll).containsExactly(tiad, tep4, free);

        assertThat(new RunCommand.Matcher().lower(parsed.get(0))).singleElement().satisfies(run -> {
            assertThat(run.getProgram()).isEqualTo("DSNTIAD");
            assertThat(run.getPlan()).isNull();
            assertThat(run.getParameters()).isEqualTo("RC0");
            assertThat(run.getSubsystem()).isEqualTo("DAZ1");
            assertThat(run.getLine()).isEqualTo(16);
        });
        assertThat(((Bind.CompilationUnit) parsed.get(2)).getStatements())
          .extracting(s -> ((Bind.Command) s).getVerb().getText())
          .containsExactly("DSN", "FREE", "FREE", "FREE", "END");
    }

    /**
     * The licence each of CardDemo's DSN decks opens with, every line a comment closed on itself.
     */
    private static final String CARDDEMO_LICENCE =
      """
        /* Copyright Amazon.com, Inc. or its affiliates.                   */
        /* All Rights Reserved.                                            */
        /*                                                                 */
        /* Licensed under the Apache License, Version 2.0 (the "License"). */
        /* You may not use this file except in compliance with the License.*/
        /* You may obtain a copy of the License at                         */
        /*                                                                 */
        /*    http://www.apache.org/licenses/LICENSE-2.0                   */
        /*                                                                 */
        /* Unless required by applicable law or agreed to in writing,      */
        /* software distributed under the License is distributed on an     */
        /* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,    */
        /* either express or implied. See the License for the specific     */
        /* language governing permissions and limitations under the License*/
        """;

    /**
     * A TSO batch step's JCL names only {@code IKJEFT01}; the program it runs, the plan and the library
     * are in the deck its {@code SYSTSIN} reads.
     */
    @Test
    void readsTheProgramARunDeckRuns() {
        rewriteRun(
          bind(
            """
              DSN SYSTEM(DB2P)
              RUN PROGRAM(CLMD010) PLAN(CLMPLAN) LIB('CLM.PROD.LOADLIB')
              END
              """,
            spec -> spec.afterRecipe(cu -> assertThat(new RunCommand.Matcher().lower(cu))
              .singleElement().satisfies(run -> {
                  assertThat(run.getProgram()).isEqualTo("CLMD010");
                  assertThat(run.getPlan()).isEqualTo("CLMPLAN");
                  assertThat(run.getLibrary()).isEqualTo("CLM.PROD.LOADLIB");
                  assertThat(run.getSubsystem()).isEqualTo("DB2P");
                  assertThat(run.getParameters()).isNull();
                  assertThat(run.getLine()).isEqualTo(2);
              }))
          )
        );
    }

    @Test
    void readsARunsAbbreviatedKeywords() {
        rewriteRun(
          bind(
            """
              DSN SYSTEM(DB2T)
              RUN PROG(DSNTEP2) PLAN(DSNTEP61) LIBRARY('DSN610.RUNLIB.LOAD') PARMS('/ALIGN(MID)')
              END
              """,
            spec -> spec.afterRecipe(cu -> assertThat(new RunCommand.Matcher().lower(cu))
              .singleElement().satisfies(run -> {
                  assertThat(run.getProgram()).isEqualTo("DSNTEP2");
                  assertThat(run.getLibrary()).isEqualTo("DSN610.RUNLIB.LOAD");
                  assertThat(run.getParameters()).isEqualTo("/ALIGN(MID)");
                  assertThat(run.getSubsystem()).isEqualTo("DB2T");
              }))
          )
        );
    }

    /**
     * A deck kept with the other control cards is a .ctl member, told from a SORT or IDCAMS card by
     * opening with {@code DSN}.
     */
    @Test
    void acceptsAControlCardMemberThatOpensWithDsn(@TempDir Path tempDir) throws IOException {
        Path run = write(tempDir, "RUNCLM01.ctl", "  DSN SYSTEM(DB2P)\n  RUN PROGRAM(CLMD010) PLAN(CLMPLAN)\n  END\n");
        Path binds = write(tempDir, "BNDCLM01.ctl", "DSN SYSTEM(DB2P)\nBIND PACKAGE(CLMPKG) MEMBER(CLMD010)\nEND\n");
        Path sort = write(tempDir, "SRTCLM01.ctl", "  SORT FIELDS=(1,8,CH,A)\n");
        Path bare = write(tempDir, "RUNCLM09.ctl", "  RUN PROGRAM(CLMD010) PLAN(CLMPLAN)\n");
        Path nothing = write(tempDir, "DSNCLM01.ctl", "  DSN SYSTEM(DB2P)\n  END\n");

        BindParser parser = BindParser.builder().build();
        assertThat(parser.accept(run)).isTrue();
        assertThat(parser.accept(binds)).isTrue();
        assertThat(parser.accept(sort)).isFalse();
        assertThat(parser.accept(bare)).isFalse();
        assertThat(parser.accept(nothing)).isFalse();
    }

    private static Path write(Path directory, String name, String content) throws IOException {
        Path path = directory.resolve(name);
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        return path;
    }
}
