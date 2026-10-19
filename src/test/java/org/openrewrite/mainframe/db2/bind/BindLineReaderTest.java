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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class BindLineReaderTest {

    /**
     * A line continues the one above it because that one ended in a dash, and nothing in its own text
     * says so.
     */
    @Test
    void continuationComesFromTheDashOnTheLineAbove() {
        String source =
          "DSN SYSTEM(DB2P)\n" +
          "BIND PACKAGE(CLMPKG) -   \n" +
          "     MEMBER(CLMD010)\n";
        assertThat(BindLineReader.readLines(source)).isEqualTo(
          "^^CARD^^DSN SYSTEM(DB2P)\n" +
          "^^CARD^^BIND PACKAGE(CLMPKG) -   \n" +
          "^^CARD_CONT^^     MEMBER(CLMD010)\n");
    }

    @Test
    void carriageReturnsAreKept() {
        assertThat(BindLineReader.readLines("BIND PACKAGE(A) -\r\n MEMBER(P)\r\n")).isEqualTo(
          "^^CARD^^BIND PACKAGE(A) -\r\n^^CARD_CONT^^ MEMBER(P)\r\n");
    }

    @ValueSource(strings = {
      "DSN SYSTEM(DB2P)\nBIND PACKAGE(A) MEMBER(P)\nEND\n",
      "BIND PACKAGE(A) MEMBER(P)\n",
      " REBIND PLAN(CLMPLAN)\n",
      "DSN SYSTEM(DB2P)\n\n*** BASE TABLES\nBIND PACKAGE(A) MEMBER(P)\n"
    })
    @ParameterizedTest
    void readsAsABindDeck(String source) {
        assertThat(BindLineReader.isBindDeck(source)).isTrue();
    }

    @ValueSource(strings = {
      "DSN SYSTEM(DB2P)\nRUN PROGRAM(DSNTEP2) PLAN(DSNTEP2)\nEND\n",
      "//DB2BIND JOB (),CLASS=A\n//BIND EXEC PGM=IKJEFT01\n",
      "SELECT * FROM SYSIBM.SYSPACKDEP;\n",
      "  SORT FIELDS=(1,8,CH,A)\n",
      ""
    })
    @ParameterizedTest
    void doesNotReadAsABindDeck(String source) {
        assertThat(BindLineReader.isBindDeck(source)).isFalse();
    }

    /**
     * A deck that runs a program under DSN is read by the same grammar as one that binds, and is no
     * bind deck.
     */
    @Test
    void aRunDeckIsADsnDeck() {
        String run = "DSN SYSTEM(DB2P)\nRUN PROGRAM(DSNTEP2) PLAN(DSNTEP2)\nEND\n";
        assertThat(BindLineReader.isDsnDeck(run)).isTrue();
        assertThat(BindLineReader.isBindDeck(run)).isFalse();
        assertThat(BindLineReader.isDsnDeck("DSN SYSTEM(DB2P)\nBIND PACKAGE(A) MEMBER(P)\nEND\n")).isTrue();
        assertThat(BindLineReader.isDsnDeck("DSN SYSTEM(DB2P)\nEND\n")).isFalse();
        assertThat(BindLineReader.isDsnDeck("  SORT FIELDS=(1,8,CH,A)\n")).isFalse();
    }

    /**
     * {@code FREE PLAN} and {@code FREE PACKAGE} are DSN subcommands as {@code BIND} is, so a deck
     * that does nothing but free them, as CardDemo's DB2FREE does, is a DSN deck. It binds nothing,
     * so it is no bind deck.
     */
    @Test
    void aDeckThatFreesIsADsnDeck() {
        String free = " DSN SYSTEM(DAZ1)\n  FREE PLAN(CARDDEMO)\n  FREE PACKAGE(COTRTLIC.*)\n END";
        assertThat(BindLineReader.isDsnDeck(free)).isTrue();
        assertThat(BindLineReader.isBindDeck(free)).isFalse();
    }

    @Test
    void opensWithDsnReadsTheFirstSubcommand() {
        assertThat(BindLineReader.opensWithDsn("\n  DSN SYSTEM(DB2P)\nRUN PROGRAM(X)\n")).isTrue();
        assertThat(BindLineReader.opensWithDsn("BIND PACKAGE(A) MEMBER(P)\n")).isFalse();
        assertThat(BindLineReader.opensWithDsn("")).isFalse();
    }

    /**
     * CardDemo opens each of its DSN decks with its licence, a block of comment lines, so the first
     * subcommand is the first line past them.
     */
    @Test
    void aDeckIsTypedPastTheCommentsItOpensWith() {
        String run =
          "/* Copyright Amazon.com, Inc. or its affiliates.                   */\n" +
          "/* All Rights Reserved.                                            */\n" +
          "  DSN SYSTEM(DAZ1)\n" +
          "  RUN PROGRAM(DSNTIAD) -\n" +
          "  PARMS('RC0')\n";
        assertThat(BindLineReader.opensWithDsn(run)).isTrue();
        assertThat(BindLineReader.isDsnDeck(run)).isTrue();
        assertThat(BindLineReader.isBindDeck(run)).isFalse();

        assertThat(BindLineReader.opensWithDsn("/* RUNCLM01 */\n\n /* AGAIN */ DSN SYSTEM(DB2P)\n")).isTrue();
        assertThat(BindLineReader.isBindDeck("/* BIND THE CLAIM PACKAGES */\nBIND PACKAGE(A) MEMBER(P)\n")).isTrue();
        assertThat(BindLineReader.opensWithDsn("/* SORT THE CLAIMS */\n  SORT FIELDS=(1,8,CH,A)\n")).isFalse();
        assertThat(BindLineReader.opensWithDsn("/* NOTHING BUT A COMMENT */\n")).isFalse();
    }

    /**
     * A line of nothing but comments is marked as one: it does not open a subcommand, and a
     * subcommand continued over it goes on being continued.
     */
    @Test
    void aCommentLineIsNeitherASubcommandNorABreakInOne() {
        assertThat(BindLineReader.readLines(
          "/* LICENCE */\n" +
          "RUN PROGRAM(DSNTEP4) - /* CONTINUED */\n" +
          "  /* THE PARMS DSNTEP4 TAKES */\n" +
          "PARMS('/ALIGN(LHS) MIXED')\n")).isEqualTo(
          "^^COMMENT^^/* LICENCE */\n" +
          "^^CARD^^RUN PROGRAM(DSNTEP4) - /* CONTINUED */\n" +
          "^^COMMENT^^  /* THE PARMS DSNTEP4 TAKES */\n" +
          "^^CARD_CONT^^PARMS('/ALIGN(LHS) MIXED')\n");
    }
}
