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

import org.antlr.v4.runtime.atn.ATNConfig;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.dfa.DFAState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.Parser;
import org.openrewrite.SourceFile;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reports what parsing a corpus costs, so that a grammar or parser change can be compared against main.
 * <p>
 * The corpus is parsed four times in one JVM. The last pass follows a cleared prediction cache, so its excess over the
 * warm passes is what building the cache costs. The cache's size is deterministic, and so is the count of its
 * configurations that differ only in predicate order, which ANTLR compares in the hash-set order it stored them in.
 * <p>
 * Run with {@code COBOL_CORPUS=/path/to/corpus}.
 */
class ParseCostTest {

    @EnabledIfEnvironmentVariable(named = "COBOL_CORPUS", matches = ".+")
    @Test
    void measure() throws IOException {
        Path root = Paths.get(System.getenv("COBOL_CORPUS"));
        List<CobolParser> parsers = new ArrayList<>();
        List<List<Parser.Input>> programs = new ArrayList<>();
        for (Path repository : Corpus.repositories(root)) {
            List<SourceFile> copybooks = CopybookParser.builder().build()
              .parseInputs(Corpus.inputs(Corpus.copybooks(repository)), root, new InMemoryExecutionContext())
              .collect(toList());
            parsers.add(CobolParser.builder().copybooks(copybooks).build());
            programs.add(Corpus.inputs(Corpus.programs(repository)));
        }
        assertThat(programs.stream().mapToInt(List::size).sum()).as("programs under %s", root).isPositive();

        DFA[] cache = new org.openrewrite.mainframe.cobol.internal.grammar.CobolParser(null).getInterpreter().decisionToDFA;
        long[] nanos = new long[4];
        long[] bytes = new long[4];
        String cacheReport = "";
        for (int pass = 0; pass < 4; pass++) {
            if (pass == 3) {
                for (int d = 0; d < cache.length; d++) {
                    cache[d] = new DFA(cache[d].atnStartState, d);
                }
            }
            long allocatedBefore = allocated();
            long start = System.nanoTime();
            for (int i = 0; i < parsers.size(); i++) {
                parsers.get(i).parseInputs(programs.get(i), root, new InMemoryExecutionContext()).collect(toList());
            }
            nanos[pass] = System.nanoTime() - start;
            bytes[pass] = allocated() - allocatedBefore;
            if (pass == 0) {
                cacheReport = describe(cache);
            }
        }

        long warmNanos = (nanos[1] + nanos[2]) / 2;
        long warmBytes = (bytes[1] + bytes[2]) / 2;
        System.out.print(String.format(Locale.ROOT, "pass 1, cold JIT and cache          %6.2f s  %6.2f GB%n" +
                                                    "passes 2 and 3, warm (mean)         %6.2f s  %6.2f GB%n" +
                                                    "pass 4, warm JIT, cache cleared     %6.2f s  %6.2f GB%n" +
                                                    "building the cache, pass 4 - warm   %6.2f s  %6.2f GB%n",
          nanos[0] / 1e9, bytes[0] / 1e9, warmNanos / 1e9, warmBytes / 1e9, nanos[3] / 1e9, bytes[3] / 1e9,
          (nanos[3] - warmNanos) / 1e9, (bytes[3] - warmBytes) / 1e9) + cacheReport);
    }

    private static String describe(DFA[] cache) {
        Map<String, long[]> byRule = new HashMap<>();
        long states = 0;
        long configs = 0;
        long orderOnly = 0;
        for (DFA dfa : cache) {
            String rule = org.openrewrite.mainframe.cobol.internal.grammar.CobolParser.ruleNames[dfa.atnStartState.ruleIndex];
            for (DFAState state : dfa.states.keySet()) {
                states++;
                configs += state.configs.size();
                Set<String> distinct = new HashSet<>();
                for (ATNConfig config : state.configs) {
                    distinct.add(config.state.stateNumber + "/" + config.alt + "/" + unordered(config.semanticContext));
                }
                orderOnly += state.configs.size() - distinct.size();
                long[] counts = byRule.computeIfAbsent(rule, r -> new long[2]);
                counts[0]++;
                counts[1] += state.configs.size();
            }
        }
        StringBuilder report = new StringBuilder(String.format(Locale.ROOT,
          "prediction cache after pass 1: %,d states, %,d configurations, %,d differing only in predicate order%n" +
          "rules holding the most configurations:%n", states, configs, orderOnly));
        byRule.entrySet().stream()
          .sorted((a, b) -> Long.compare(b.getValue()[1], a.getValue()[1]))
          .limit(5)
          .forEach(e -> report.append(String.format(Locale.ROOT, "  %,9d configurations in %,5d states  %s%n",
            e.getValue()[1], e.getValue()[0], e.getKey())));
        return report.toString();
    }

    private static String unordered(SemanticContext context) {
        SemanticContext[] operands = context instanceof SemanticContext.AND ? ((SemanticContext.AND) context).opnds :
          context instanceof SemanticContext.OR ? ((SemanticContext.OR) context).opnds : null;
        if (operands == null) {
            return context.toString();
        }
        return (context instanceof SemanticContext.AND ? "&&" : "||") +
               Arrays.toString(Arrays.stream(operands).map(ParseCostTest::unordered).sorted().toArray());
    }

    private static long allocated() {
        return ((com.sun.management.ThreadMXBean) ManagementFactory.getThreadMXBean()).getThreadAllocatedBytes(Thread.currentThread().getId());
    }
}
