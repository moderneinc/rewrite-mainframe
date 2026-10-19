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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openrewrite.mainframe.cobol.LineEndings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

/**
 * Classifies the lines of a bind deck before they are lexed.
 * <p>
 * A DSN subcommand runs to the end of its line unless that line ends in a dash, in which case the
 * next line carries on where it left off. Nothing in the text of a continuation line marks it as one
 * — it is a continuation because of the line <em>above</em> it — so the lexer cannot work this out
 * and the grouping has to be decided here.
 * <p>
 * A deck is a TSO command stream, so all 80 columns are data: there is no sequence area to take off
 * the end, and the dash a line continues with is simply its last non-blank character.
 * <p>
 * TSO reads a {@code /*}…{@code *}{@code /} comment wherever a blank may stand. A line holding nothing
 * but comments is neither a subcommand nor part of one: it does not open a subcommand, a subcommand
 * continued over it goes on being continued, and a deck that opens with a block of them — CardDemo's
 * licence — is typed by the first line after it.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BindLineReader {

    /**
     * The subcommands a deck may open with. {@code DSN} is the usual one — it names the subsystem the
     * binds run against — and a deck read straight from {@code SYSTSIN} of a step already under DSN
     * opens with the bind itself.
     */
    private static final Set<String> OPENING_VERBS = new HashSet<>(Arrays.asList("DSN", "BIND", "REBIND"));

    /**
     * Whether text is a bind deck, judged by its first subcommand and by binding something. A
     * {@code CARDLIB} member kept without an extension is known by nothing else, and the library
     * holds catalog query decks and IDCAMS cards beside the binds.
     */
    public static boolean isBindDeck(String source) {
        return opensAndDoes(source, BINDING_VERBS);
    }

    /**
     * Whether text is a DSN command deck that binds, runs or frees something: a bind deck, a deck that
     * runs a program under a plan, {@code RUN PROGRAM(CLMD010) PLAN(CLMPLAN)}, as a TSO batch step
     * reads one from its {@code SYSTSIN}, or a deck that frees the plans and packages it names,
     * {@code FREE PLAN(CARDDEMO)}. All of them are read by the same grammar.
     */
    public static boolean isDsnDeck(String source) {
        return opensAndDoes(source, DECK_VERBS);
    }

    /**
     * Whether the first subcommand of text, past any lines of comments, is {@code DSN}, as a deck kept
     * among other control cards, where only that tells it apart, opens.
     */
    public static boolean opensWithDsn(String source) {
        for (String line : source.split("\n")) {
            String verb = verbOf(line);
            if (!verb.isEmpty()) {
                return "DSN".equals(verb);
            }
        }
        return false;
    }

    private static final Set<String> BINDING_VERBS = new HashSet<>(Arrays.asList("BIND", "REBIND"));

    private static final Set<String> DECK_VERBS = new HashSet<>(Arrays.asList("BIND", "REBIND", "RUN", "FREE"));

    private static boolean opensAndDoes(String source, Set<String> doing) {
        boolean opens = false;
        for (String line : source.split("\n")) {
            String verb = verbOf(line);
            if (verb.isEmpty()) {
                continue;
            }
            if (!opens) {
                if (!OPENING_VERBS.contains(verb)) {
                    return false;
                }
                opens = true;
            }
            if (doing.contains(verb)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The first word of a line, past the comments in front of it, or empty for a line of nothing but
     * blanks and comments.
     */
    private static String verbOf(String line) {
        return withoutComments(line).trim().split("[\\s(]+", 2)[0].toUpperCase(Locale.ROOT);
    }

    public static String readLines(String source) {
        StringBuilder p = new StringBuilder();

        int cursor = 0;
        boolean continued = false;
        Scanner scanner = new Scanner(source);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (withoutComments(line).trim().isEmpty()) {
                if (line.trim().isEmpty()) {
                    // A blank line carries no operands, so it cannot be the continuation of anything.
                    continued = false;
                } else {
                    // A comment says nothing about the subcommand it interrupts.
                    p.append("^^COMMENT^^");
                }
            } else {
                p.append(continued ? "^^CARD_CONT^^" : "^^CARD^^");
                continued = continuesOnNextLine(line);
            }
            p.append(line);
            cursor = LineEndings.append(p, source, cursor + line.length());
        }
        return p.toString();
    }

    /**
     * A line with its comments blanked out, which is how the reader decides whether the line says
     * anything at all. Only a comment closed on the line it opens on counts, which is the rule the
     * lexer follows too, and the one an IDCAMS card is read by.
     */
    static String withoutComments(String line) {
        StringBuilder text = new StringBuilder(line);
        int open = text.indexOf("/*");
        while (open >= 0) {
            int close = text.indexOf("*/", open + 2);
            if (close < 0) {
                break;
            }
            for (int i = open; i < close + 2; i++) {
                text.setCharAt(i, ' ');
            }
            open = text.indexOf("/*", close + 2);
        }
        return text.toString();
    }

    private static boolean continuesOnNextLine(String line) {
        String text = withoutComments(line);
        int last = text.length() - 1;
        while (last >= 0 && Character.isWhitespace(text.charAt(last))) {
            last--;
        }
        return last >= 0 && text.charAt(last) == '-';
    }
}
