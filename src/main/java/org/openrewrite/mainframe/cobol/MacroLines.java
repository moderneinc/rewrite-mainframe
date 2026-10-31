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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

/**
 * Classifies the lines of assembler macro source before they are lexed.
 * <p>
 * A BMS map set and all four IMS gen libraries are written this way, so the layout is the assembler's:
 * a name field beginning in column 1, an operation, an operand field, and column 72 saying whether the
 * statement carries on. Nothing in the text of a continuation line marks it as one — it is a
 * continuation because of a character on the line <em>above</em> it — so the lexer cannot work this out
 * and the grouping has to be decided here.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MacroLines {

    /**
     * Column 72, where a non-blank says the next line continues this statement. Any character will do;
     * the corpus writes {@code -}, {@code X}, {@code C} and {@code *} interchangeably.
     */
    private static final int CONTINUATION_COLUMN = 71;

    /**
     * Column 16, where a continuation line begins.
     */
    private static final int CONTINUE_COLUMN = 15;

    /**
     * Columns 73-80, the identification-sequence field. Ignored by the assembler, so it is carried as a
     * marker rather than as operands.
     */
    private static final int SEQUENCE_AREA = 72;

    /**
     * Put in front of columns 73-80 when they hold something.
     */
    public static final String SEQUENCE_AREA_MARKER = "^^CA_START^^";

    /**
     * The assembler listing controls, which say nothing about what a member is. CardDemo opens both
     * of its DBDs with a {@code TITLE}, so what a member is has to be looked for past them.
     */
    private static final Set<String> LISTING_CONTROLS =
            new HashSet<>(Arrays.asList("TITLE", "EJECT", "SPACE", "PRINT"));

    /**
     * Puts each line on the hidden channel that says what it is, where {@code language} names the
     * grammar's own tokens: {@code ^^BMS^^}, {@code ^^BMS_NAMED^^} and {@code ^^BMS_CONT^^}.
     */
    public static String read(String source, String language) {
        String statement = "^^" + language + "^^";
        String named = "^^" + language + "_NAMED^^";
        String continuation = "^^" + language + "_CONT^^";

        StringBuilder p = new StringBuilder();
        int cursor = 0;
        boolean continued = false;
        boolean commentedOut = false;
        Scanner scanner = new Scanner(source);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // Only when columns 73-80 hold something. A line merely padded out to 80 would otherwise
            // open a sequence area with nothing in it, which the parser cannot match — it needs a
            // word, and blanks are hidden.
            String sequenceArea = null;
            if (line.length() > SEQUENCE_AREA && !line.substring(SEQUENCE_AREA).trim().isEmpty()) {
                sequenceArea = line.substring(SEQUENCE_AREA);
                line = line.substring(0, SEQUENCE_AREA);
            }

            boolean continues = continuesOnNextLine(line);
            if (isComment(line)) {
                p.append("^^COMMENT^^");
                commentedOut |= continued;
            } else if (line.trim().isEmpty()) {
                p.append("^^UNKNOWN^^");
                continued = false;
                commentedOut = false;
            } else {
                // HLASM forbids a comment card inside a continued statement, but rehosted map sets
                // comment out a card that way. The statement stays open for a line beginning in
                // column 16. Any other line is a statement of its own, and the mark in column 72
                // above it an accident, such as a literal run into that column.
                boolean continuing = continued && (!commentedOut || beginsInContinueColumn(line));
                p.append(continuing ? continuation : hasNameField(line) ? named : statement);
                continued = continues;
                commentedOut = false;
            }

            p.append(line);
            if (sequenceArea != null) {
                p.append(SEQUENCE_AREA_MARKER);
                p.append(sequenceArea);
            }

            cursor = LineEndings.append(p, source,
                    cursor + line.length() + (sequenceArea == null ? 0 : sequenceArea.length()));
        }
        return p.toString();
    }

    public static boolean isComment(String line) {
        return line.startsWith("*");
    }

    /**
     * The operation of the first statement, or null for a member that has none. This is what a
     * member kept under a borrowed extension is known by, whether an {@code .asm} that gens or a
     * {@code .copy} that lays out a DSECT.
     */
    public static @Nullable String firstOperation(String source) {
        Scanner scanner = new Scanner(source);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (isComment(line) || line.trim().isEmpty()) {
                continue;
            }
            String[] words = line.trim().split("\\s+");
            int operation = hasNameField(line) ? 1 : 0;
            if (words.length <= operation) {
                continue;
            }
            String word = words[operation].toUpperCase(Locale.ROOT);
            if (!LISTING_CONTROLS.contains(word)) {
                return word;
            }
        }
        return null;
    }

    /**
     * Whether the line writes a name field, which begins in column 1. A macro with no name of its own —
     * every {@code DFHMDF} holding a screen literal rather than a field a program can read, most of a
     * DBD — starts its operation further along instead.
     */
    public static boolean hasNameField(String line) {
        char c = line.charAt(0);
        return c != ' ' && c != '\t';
    }

    private static boolean beginsInContinueColumn(String line) {
        return line.length() > CONTINUE_COLUMN && line.substring(0, CONTINUE_COLUMN).trim().isEmpty() &&
               line.charAt(CONTINUE_COLUMN) != ' ';
    }

    private static boolean continuesOnNextLine(String line) {
        return line.length() > CONTINUATION_COLUMN && line.charAt(CONTINUATION_COLUMN) != ' ';
    }
}
