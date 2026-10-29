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
package org.openrewrite.mainframe.cobol.tree;

import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class SpaceTest {

    @Test
    void sharesOneInstancePerWhitespaceAcrossGarbageCollections() {
        String whitespace = " ".repeat(37);
        // A fresh string that nothing else holds, like the token text a parser hands in.
        Space first = Space.build(new String(whitespace));

        WeakReference<Object> unreachable = new WeakReference<>(new Object());
        for (int i = 0; i < 10 && unreachable.get() != null; i++) {
            System.gc();
        }
        assumeTrue(unreachable.get() == null, "no collection cleared weak references");

        assertThat(Space.build(new String(whitespace))).isSameAs(first);
    }

    @Test
    void buildsARangeOfASourceAsTheSpaceOfItsText() {
        String source = "MOVE  A   TO B";
        Space interned = Space.build("  ");

        assertThat(Space.build(source, 4, 6)).isSameAs(interned);

        assertThat(Space.build(source, 7, 10).getWhitespace()).isEqualTo("   ");
        assertThat(Space.build(source, 4, 4)).isSameAs(Space.EMPTY);
    }
}
