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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class CorpusTest {

    @Test
    void findsRepositoriesClonedFlatOrUnderTheirOwner(@TempDir Path root) throws IOException {
        Files.createDirectories(root.resolve("IBM/Bank-of-Z/.git"));
        Files.createDirectories(root.resolve("IBM/dbb-zappbuild/.git"));
        Files.createDirectories(root.resolve("moderneinc/mainframe-fixtures/.git"));
        Files.createDirectories(root.resolve("genapp/.git"));
        Files.createDirectories(root.resolve("carddemo/src"));
        Files.createDirectories(root.resolve(".moderne/run"));

        assertThat(Corpus.repositories(root)).extracting(repository -> root.relativize(repository).toString())
          .containsExactly("IBM/Bank-of-Z", "IBM/dbb-zappbuild", "carddemo", "genapp", "moderneinc/mainframe-fixtures");
        assertThat(Corpus.repositories(root)).filteredOn(Corpus::isFixture).hasSize(1);
    }
}
