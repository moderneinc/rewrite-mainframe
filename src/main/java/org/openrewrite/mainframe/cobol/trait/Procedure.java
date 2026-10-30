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

import lombok.Value;
import org.jspecify.annotations.Nullable;
import org.openrewrite.Cursor;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.trait.SimpleTraitMatcher;
import org.openrewrite.trait.Trait;

import java.util.Iterator;

/**
 * A paragraph or a section: the unit {@code PERFORM} and {@code GO TO} name, and so the unit a
 * control flow graph has nodes for. A declarative section counts as a section. Control reaches it
 * when the condition its {@code USE} statement names occurs, an I/O error say.
 * <p>
 * The two are one concept as far as anything that reaches them is concerned — {@code PERFORM
 * A-SECTION} and {@code PERFORM A-PARA} are written the same and mean the same — which is why they
 * are one trait rather than two.
 */
@Value
public class Procedure implements Trait<Cobol> {

    Cursor cursor;

    /**
     * The paragraph or section name, upper cased, or empty where it has none. Callers compare these
     * against names written elsewhere in the program, which COBOL does case insensitively.
     */
    public String getName() {
        Cobol tree = getTree();
        String name = Names.upperOf(tree instanceof Cobol.Paragraph ?
                ((Cobol.Paragraph) tree).getParagraphName() :
                sectionHeader(tree).getSectionName());
        return name == null ? "" : name;
    }

    public boolean isSection() {
        return !(getTree() instanceof Cobol.Paragraph);
    }

    private static Cobol.ProcedureSectionHeader sectionHeader(Cobol section) {
        return section instanceof Cobol.ProcedureSection ?
                ((Cobol.ProcedureSection) section).getProcedureSectionHeader() :
                ((Cobol.ProcedureDeclarative) section).getProcedureSectionHeader();
    }

    private static boolean isProcedure(Object value) {
        return value instanceof Cobol.Paragraph || value instanceof Cobol.ProcedureSection ||
               value instanceof Cobol.ProcedureDeclarative;
    }

    public static class Matcher extends SimpleTraitMatcher<Procedure> {

        @Override
        protected @Nullable Procedure test(Cursor cursor) {
            return isProcedure(cursor.getValue()) ? new Procedure(cursor) : null;
        }
    }

    /**
     * The procedure {@code cursor} is inside, or null where it is in none.
     * A paragraph within a section answers with the paragraph: it is the finer of the two names, and
     * the one a {@code PERFORM} would have to use to reach that statement.
     */
    public static @Nullable Procedure of(Cursor cursor) {
        for (Iterator<Cursor> path = cursor.getPathAsCursors(); path.hasNext(); ) {
            Cursor enclosing = path.next();
            if (isProcedure(enclosing.getValue())) {
                return new Procedure(enclosing);
            }
        }
        return null;
    }

    /**
     * The name of the procedure {@code cursor} is inside, or empty where it is in none.
     */
    public static String nameOf(Cursor cursor) {
        Procedure procedure = of(cursor);
        return procedure == null ? "" : procedure.getName();
    }

    @Override
    public String toString() {
        return getName() + (isSection() ? " SECTION" : "");
    }
}
