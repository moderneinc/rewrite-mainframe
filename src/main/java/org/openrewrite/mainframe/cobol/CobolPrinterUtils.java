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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openrewrite.mainframe.cobol.internal.CobolDialect;
import org.openrewrite.internal.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CobolPrinterUtils {

    public static int getInsertIndex(String output) {
        return getInsertIndex((CharSequence) output);
    }

    /**
     * The index just past the output's last line break, or 0 when it has none.
     * Printers pass their {@code PrintOutputCapture.out}, since {@code getOut()} copies it.
     */
    public static int getInsertIndex(CharSequence output) {
        int newline = lastNewline(output);
        return newline == -1 ? 0 : newline + 1;
    }

    public static int getCurrentIndex(String output) {
        return getCurrentIndex((CharSequence) output);
    }

    /**
     * The column the output's last line has reached, or -1 when the output has no line break.
     */
    public static int getCurrentIndex(CharSequence output) {
        int newline = lastNewline(output);
        return newline == -1 ? -1 : output.length() - newline - 1;
    }

    public static boolean endsWithNewline(CharSequence output) {
        return output.length() > 0 && output.charAt(output.length() - 1) == '\n';
    }

    private static int lastNewline(CharSequence output) {
        for (int i = output.length() - 1; i >= 0; i--) {
            if (output.charAt(i) == '\n') {
                return i;
            }
        }
        return -1;
    }

    public static String generateWhitespace(int count) {
        if (count < 0) {
            throw new IllegalStateException("Negative index detected.");
        }
        return fillArea(' ', count);
    }

    public static String fillArea(Character character, int count) {
        if (count < 0) {
            throw new IllegalStateException("Negative index detected.");
        }
        return StringUtils.repeat(String.valueOf(character), count);
    }

    public static int getContentAreaLength(CobolDialect cobolDialect) {
        if (cobolDialect.getColumns().getOtherArea() - cobolDialect.getColumns().getContentArea() < 0) {
            throw new IllegalStateException("Negative index detected.");
        }
        return cobolDialect.getColumns().getOtherArea() - cobolDialect.getColumns().getContentArea();
    }
}
