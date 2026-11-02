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

import java.util.ArrayList;
import java.util.List;

/**
 * A data description entry, named or FILLER, placed in the record hierarchy its level number
 * describes. It is placed by the program on its cursor path, so outside a program it is under
 * nothing, holds nothing and redefines nothing.
 */
@Value
public class DataDescription implements Trait<Cobol.DataDescriptionEntry> {

    Cursor cursor;

    public int getLevel() {
        return levelOf(getTree());
    }

    /**
     * The level number, or 0 where the entry starts with no number.
     */
    static int levelOf(Cobol.DataDescriptionEntry entry) {
        try {
            return entry.getWords().isEmpty() ? 0 : Integer.parseInt(entry.getWords().get(0).getWord());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * The entry this one is declared under, such as the group holding an item. A level 88 is under
     * the item it follows, and a level 66 under the record it follows. Null for a level 01 or 77.
     */
    public @Nullable DataDescription getParent() {
        Cursor parent = DataName.parentEntry(cursor);
        return parent == null ? null : new DataDescription(parent);
    }

    /**
     * The entry this one's REDEFINES clause names, or null where it has none. COBOL writes a
     * redefinition right after the entry it redefines or after other redefinitions of it, and every
     * one names the original. So it is the nearest earlier entry under the same parent, at the same
     * level, with that name.
     */
    public @Nullable DataDescription getRedefines() {
        Cursor redefined = DataName.redefinedEntry(cursor);
        return redefined == null ? null : new DataDescription(redefined);
    }

    /**
     * The entries declared under this one, in order: each entry whose {@link #getParent()} is this one.
     */
    public List<DataDescription> getChildren() {
        return entries(DataName.childEntries(cursor));
    }

    /**
     * The entries of the DATA DIVISION at {@code division} that are under no other entry, in order.
     * In a valid program those are its level 01 and 77 entries.
     */
    public static List<DataDescription> recordsOf(Cursor division) {
        if (!(division.getValue() instanceof Cobol.DataDivision)) {
            throw new IllegalArgumentException("Expected a cursor to a DATA DIVISION, not to " + division.getValue());
        }
        return entries(DataName.recordEntries(division));
    }

    private static List<DataDescription> entries(List<Cursor> cursors) {
        List<DataDescription> entries = new ArrayList<>(cursors.size());
        for (Cursor entry : cursors) {
            entries.add(new DataDescription(entry));
        }
        return entries;
    }

    public static class Matcher extends SimpleTraitMatcher<DataDescription> {

        @Override
        protected @Nullable DataDescription test(Cursor cursor) {
            return cursor.getValue() instanceof Cobol.DataDescriptionEntry ? new DataDescription(cursor) : null;
        }
    }
}
