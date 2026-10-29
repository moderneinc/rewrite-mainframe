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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.Name;

import java.util.List;

/**
 * Telling a literal from a data name.
 * <p>
 * This is the difference between a resource an analysis can name and one it cannot: {@code LINK
 * PROGRAM('PAYROLL')} says which program, {@code LINK PROGRAM(WS-PGM)} says only that a field decides
 * at run time. See {@link LiteralAssignment} for the second case.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Literals {

    /**
     * Whether {@code word} is a quoted alphanumeric literal rather than a name.
     */
    public static boolean isLiteral(String word) {
        return word.length() >= 2 &&
               (word.charAt(0) == '\'' || word.charAt(0) == '"') &&
               word.charAt(word.length() - 1) == word.charAt(0);
    }

    /**
     * Whether {@code reference} is {@code DFHRESP(NORMAL)} or {@code DFHVALUE(BUSY)}: a literal the
     * CICS translator replaces with a number, parsed as the keyword subscripted by the condition. That
     * is a {@code TableCall}, or a {@code ConditionNameReference} after {@code OR} in an abbreviated
     * condition, where a condition name is written the same way.
     */
    public static boolean isCicsLiteral(@Nullable Object reference) {
        Name name;
        List<Cobol.Parenthesized> subscripts;
        if (reference instanceof Cobol.TableCall) {
            Cobol.TableCall call = (Cobol.TableCall) reference;
            if (call.getReferenceModifier() != null) {
                return false;
            }
            Cobol qualified = call.getQualifiedDataName().getDataName();
            name = qualified instanceof Cobol.QualifiedDataNameFormat1 ?
                    ((Cobol.QualifiedDataNameFormat1) qualified).getName() : null;
            subscripts = call.getSubscripts();
        } else if (reference instanceof Cobol.ConditionNameReference) {
            Cobol.ConditionNameReference condition = (Cobol.ConditionNameReference) reference;
            if (condition.getInDatas() != null && !condition.getInDatas().isEmpty()) {
                return false;
            }
            name = condition.getName();
            subscripts = condition.getReferences();
        } else {
            return false;
        }
        String keyword = name instanceof Cobol.Word ? ((Cobol.Word) name).getWord() : "";
        return (keyword.equalsIgnoreCase("DFHRESP") || keyword.equalsIgnoreCase("DFHVALUE")) &&
               subscripts != null && subscripts.size() == 1;
    }

    /**
     * The contents of a quoted literal, trimmed, or null for anything that is not one. A null answer
     * is how a caller tells a literal operand from a data name.
     */
    public static @Nullable String valueOf(String word) {
        return isLiteral(word) ? word.substring(1, word.length() - 1).trim() : null;
    }

    /**
     * The contents of a quoted literal, or the word unchanged when it is not one.
     */
    public static String unquote(String word) {
        String literal = valueOf(word);
        return literal == null ? word : literal;
    }
}
