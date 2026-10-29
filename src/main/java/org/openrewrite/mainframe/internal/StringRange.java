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
package org.openrewrite.mainframe.internal;

import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * A range of a string that a map keyed by strings finds as if it were the string itself, so a lookup copies nothing.
 * Its hash code is the one {@link String#hashCode()} gives the range's text, and a map compares a probe key to its keys
 * with the probe's own {@code equals}.
 */
public final class StringRange {
    private final String source;
    private final int beginIndex;
    private final int endIndex;

    private StringRange(String source, int beginIndex, int endIndex) {
        this.source = source;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    public static <V> @Nullable V get(Map<String, V> map, String source, int beginIndex, int endIndex) {
        return map.get(new StringRange(source, beginIndex, endIndex));
    }

    public static boolean contains(Set<String> set, String source, int beginIndex, int endIndex) {
        return set.contains(new StringRange(source, beginIndex, endIndex));
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = beginIndex; i < endIndex; i++) {
            hash = 31 * hash + source.charAt(i);
        }
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof String && ((String) other).length() == endIndex - beginIndex &&
               source.regionMatches(beginIndex, (String) other, 0, endIndex - beginIndex);
    }
}
