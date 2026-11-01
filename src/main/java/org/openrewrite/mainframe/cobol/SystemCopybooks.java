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

import org.jspecify.annotations.Nullable;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.Parser;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.cobol.marker.SystemCopybook;
import org.openrewrite.mainframe.cobol.tree.CobolPreprocessor;

import java.nio.file.Paths;

import static java.util.Collections.singletonList;
import static org.openrewrite.Tree.randomId;

/**
 * The members the CICS translator and the Db2 precompiler supply, declared with the layout IBM
 * documents for them. Each holds the fields and constants programs commonly name, not every one a
 * release has added.
 * <p>
 * Package-private because a caller reads a member's declarations where a program copies it, or
 * through {@code Program} for the DFHEIBLK and DLZDIB the translator inserts. A caller
 * needing a member some other way, or a program naming a member missing here, is reason to extend
 * this class.
 */
final class SystemCopybooks {

    private SystemCopybooks() {
    }

    /**
     * The member by its upper-cased name, or null for one neither supplies. A member that does not
     * parse is returned as the parse error, which the caller reports as it would for a repository's.
     */
    static @Nullable SourceFile find(String member) {
        // A .cpy resource is found by a classpath scan for copybooks and read as one of the repository's.
        String resource = "system/" + member + ".txt";
        if (SystemCopybooks.class.getResource(resource) == null) {
            return null;
        }
        SourceFile parsed = CopybookParser.builder().build().parseInputs(singletonList(new Parser.Input(
                        Paths.get(member + ".cpy"), () -> SystemCopybooks.class.getResourceAsStream(resource))),
                null, new InMemoryExecutionContext()).findFirst().orElseThrow(IllegalStateException::new);
        if (!(parsed instanceof CobolPreprocessor.Copybook)) {
            return parsed;
        }
        CobolPreprocessor.Copybook copybook = (CobolPreprocessor.Copybook) parsed;
        return copybook.withMarkers(copybook.getMarkers().add(new SystemCopybook(randomId())));
    }
}
