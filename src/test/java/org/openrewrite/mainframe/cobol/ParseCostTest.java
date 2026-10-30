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

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.dfa.DFAState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.jspecify.annotations.Nullable;
import org.openrewrite.ExecutionContext;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.SourceFile;
import org.openrewrite.mainframe.assembler.AssemblerParser;
import org.openrewrite.mainframe.bms.BmsParser;
import org.openrewrite.mainframe.bms.internal.grammar.BMSParser;
import org.openrewrite.mainframe.controlcard.idcams.IdcamsParser;
import org.openrewrite.mainframe.controlcard.idcams.internal.grammar.IdcamsCardParser;
import org.openrewrite.mainframe.controlcard.sort.SortParser;
import org.openrewrite.mainframe.controlcard.sort.internal.grammar.SortCardParser;
import org.openrewrite.mainframe.controlcard.utility.UtilityParser;
import org.openrewrite.mainframe.controlcard.utility.internal.grammar.UtilityCardParser;
import org.openrewrite.mainframe.controlm.ControlMParser;
import org.openrewrite.mainframe.db2.Db2Parser;
import org.openrewrite.mainframe.db2.bind.BindParser;
import org.openrewrite.mainframe.db2.internal.grammar.DB2Parser;
import org.openrewrite.mainframe.ims.ImsParser;
import org.openrewrite.mainframe.ims.internal.grammar.IMSParser;
import org.openrewrite.mainframe.internal.PredictionListener;
import org.openrewrite.mainframe.jcl.JclParser;
import org.openrewrite.mainframe.jcl.internal.grammar.JCLParser;
import org.openrewrite.mainframe.linkedit.LinkEditParser;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reports what parsing a corpus costs, language by language, so that a grammar or parser change can be compared
 * against main.
 * <p>
 * Each language's files are parsed four times in one JVM. The last pass follows a cleared prediction cache, so its
 * excess over the warm passes is what building the cache costs. The cache's size is deterministic, and so is the
 * count of its configurations that differ only in predicate order.
 * <p>
 * The first pass also counts the predictions that fell back to full context, by rule, with the longest token span
 * one scanned. A prediction that scans further than {@link #LONGEST_FULL_CONTEXT_SCAN} fails the test, since that
 * is the shape of a grammar change that makes a parse quadratic, as the dangling ELSE did before #189.
 * <p>
 * Run with {@code MAINFRAME_CORPUS} naming a directory of repositories, and {@code MAINFRAME_LANGUAGES} naming the
 * languages to measure by their report names, or none of them for all.
 */
class ParseCostTest {

    private static final int LONGEST_FULL_CONTEXT_SCAN = 50;

    @EnabledIfEnvironmentVariable(named = "MAINFRAME_CORPUS", matches = ".+")
    @Test
    void measure() throws IOException {
        Path root = Paths.get(System.getenv("MAINFRAME_CORPUS"));
        List<Language> languages = selected(asList(
          new Language("COBOL", Corpus::programs,
            repository -> CobolParser.builder().copybooks(copybooks(root, repository)).build(),
            new org.openrewrite.mainframe.cobol.internal.grammar.CobolParser(null),
            new org.openrewrite.mainframe.cobol.internal.grammar.CobolPreprocessorParser(null)),
          new Language("JCL", Corpus::jobs, repository -> JclParser.builder().build(), new JCLParser(null)),
          new Language("Db2", Corpus::sqlScripts, repository -> Db2Parser.builder().build(), new DB2Parser(null)),
          new Language("Bind", Corpus::bindDecks, repository -> BindParser.builder().build(),
            new org.openrewrite.mainframe.db2.bind.internal.grammar.BindParser(null)),
          new Language("BMS", Corpus::mapsets, repository -> BmsParser.builder().build(), new BMSParser(null)),
          new Language("IMS", Corpus::imsDefinitions, repository -> ImsParser.builder().build(), new IMSParser(null)),
          new Language("IDCAMS", Corpus::idcamsCards, repository -> IdcamsParser.builder().build(),
            new IdcamsCardParser(null)),
          new Language("Sort", Corpus::sortCards, repository -> SortParser.builder().build(), new SortCardParser(null)),
          new Language("Utility", Corpus::utilityCards, repository -> UtilityParser.builder().build(),
            new UtilityCardParser(null)),
          new Language("LinkEdit", Corpus::linkEditDecks, repository -> LinkEditParser.builder().build(),
            new org.openrewrite.mainframe.linkedit.internal.grammar.LinkEditParser(null)),
          new Language("Control-M", Corpus::schedules, repository -> ControlMParser.builder().build(),
            new org.openrewrite.mainframe.controlm.internal.grammar.ControlMParser(null)),
          new Language("Assembler", Corpus::assemblerMembers, repository -> AssemblerParser.builder().build())),
          System.getenv("MAINFRAME_LANGUAGES"));

        List<Path> repositories = Corpus.repositories(root);
        FullContextWatch watch = new FullContextWatch();
        CacheReport caches = new CacheReport();
        StringBuilder report = new StringBuilder(String.format(Locale.ROOT, "%-10s %7s  %-18s  %-18s  %-18s  %s%n",
          "", "files", "pass 1, cold", "passes 2-3, warm", "pass 4, cleared", "building the cache"));
        int files = 0;
        for (Language language : languages) {
            report.append(language.measure(root, repositories, watch, caches));
            files += language.files;
        }
        System.out.print(report + caches.describe() + watch.describe());

        assertThat(files).as("files under %s", root).isPositive();
        assertThat(watch.longestScan).as("tokens scanned by one full-context prediction, in %s", watch.longestScanRule)
          .isLessThanOrEqualTo(LONGEST_FULL_CONTEXT_SCAN);
    }

    @Test
    void measuresOnlyTheLanguagesNamed() {
        List<Language> all = asList(language("COBOL"), language("Db2"), language("JCL"));
        assertThat(selected(all, null)).isEqualTo(all);
        assertThat(selected(all, "db2, JCL")).extracting(language -> language.name).containsExactly("Db2", "JCL");
    }

    private static Language language(String name) {
        return new Language(name, repository -> emptyList(), repository -> null);
    }

    /**
     * The languages a comma-separated list names, in any case, or all of them for a null or empty list.
     */
    private static List<Language> selected(List<Language> languages, @Nullable String names) {
        if (names == null || names.trim().isEmpty()) {
            return languages;
        }
        Set<String> wanted = Arrays.stream(names.split(","))
          .map(name -> name.trim().toLowerCase(Locale.ROOT))
          .collect(toSet());
        List<Language> selected = languages.stream()
          .filter(language -> wanted.contains(language.name.toLowerCase(Locale.ROOT)))
          .collect(toList());
        assertThat(selected).as("languages named by MAINFRAME_LANGUAGES=%s", names).isNotEmpty();
        return selected;
    }

    private static List<SourceFile> copybooks(Path root, Path repository) throws IOException {
        return CopybookParser.builder().build()
          .parseInputs(Corpus.inputs(Corpus.copybooks(repository)), root, new InMemoryExecutionContext())
          .collect(toList());
    }

    @FunctionalInterface
    private interface ForRepository<T> {
        T apply(Path repository) throws IOException;
    }

    /**
     * The files of one language, the parser that reads them, and the generated parsers whose caches it fills.
     */
    private static final class Language {
        final String name;
        final ForRepository<List<Path>> finder;
        final ForRepository<org.openrewrite.Parser> parser;
        final List<Parser> grammars;
        int files;

        Language(String name, ForRepository<List<Path>> finder, ForRepository<org.openrewrite.Parser> parser,
                 Parser... grammars) {
            this.name = name;
            this.finder = finder;
            this.parser = parser;
            this.grammars = asList(grammars);
        }

        String measure(Path root, List<Path> repositories, FullContextWatch watch, CacheReport caches)
          throws IOException {
            List<org.openrewrite.Parser> parsers = new ArrayList<>();
            List<List<org.openrewrite.Parser.Input>> inputs = new ArrayList<>();
            for (Path repository : repositories) {
                List<Path> paths = finder.apply(repository);
                if (!paths.isEmpty()) {
                    parsers.add(parser.apply(repository));
                    inputs.add(Corpus.inputs(paths));
                    files += paths.size();
                }
            }

            long[] nanos = new long[4];
            long[] bytes = new long[4];
            for (int pass = 0; pass < 4; pass++) {
                if (pass == 3) {
                    grammars.forEach(PredictionCache::clear);
                }
                ExecutionContext ctx = new InMemoryExecutionContext();
                if (pass == 0) {
                    ctx.putMessage(PredictionListener.KEY, watch);
                }
                long allocatedBefore = allocated();
                long start = System.nanoTime();
                for (int i = 0; i < parsers.size(); i++) {
                    parsers.get(i).parseInputs(inputs.get(i), root, ctx).collect(toList());
                }
                nanos[pass] = System.nanoTime() - start;
                bytes[pass] = allocated() - allocatedBefore;
                if (pass == 0) {
                    grammars.forEach(caches::add);
                }
            }

            long warmNanos = (nanos[1] + nanos[2]) / 2;
            long warmBytes = (bytes[1] + bytes[2]) / 2;
            return String.format(Locale.ROOT,
              "%-10s %,7d  %6.2f s %6.2f GB  %6.2f s %6.2f GB  %6.2f s %6.2f GB  %6.2f s %6.2f GB%n",
              name, files, nanos[0] / 1e9, bytes[0] / 1e9, warmNanos / 1e9, warmBytes / 1e9,
              nanos[3] / 1e9, bytes[3] / 1e9, (nanos[3] - warmNanos) / 1e9, (bytes[3] - warmBytes) / 1e9);
        }
    }

    /**
     * Counts the predictions that fell back to full context and how far each scanned. ANTLR reports the span SLL
     * conflicted on when it starts the retry, and the span full context needed when it finishes.
     */
    private static final class FullContextWatch extends BaseErrorListener {
        final Map<String, long[]> byRule = new TreeMap<>();
        long fallbacks;
        long longestScan;
        String longestScanRule = "";

        @Override
        public void reportAttemptingFullContext(Parser parser, DFA dfa, int startIndex, int stopIndex,
                                                BitSet conflictingAlts, ATNConfigSet configs) {
            fallbacks++;
            byRule.computeIfAbsent(rule(parser, dfa), r -> new long[1])[0]++;
            scanned(parser, dfa, stopIndex - startIndex + 1);
        }

        @Override
        public void reportContextSensitivity(Parser parser, DFA dfa, int startIndex, int stopIndex, int prediction,
                                             ATNConfigSet configs) {
            scanned(parser, dfa, stopIndex - startIndex + 1);
        }

        @Override
        public void reportAmbiguity(Parser parser, DFA dfa, int startIndex, int stopIndex, boolean exact,
                                    BitSet ambigAlts, ATNConfigSet configs) {
            scanned(parser, dfa, stopIndex - startIndex + 1);
        }

        private void scanned(Parser parser, DFA dfa, int tokens) {
            if (tokens > longestScan) {
                longestScan = tokens;
                longestScanRule = rule(parser, dfa);
            }
        }

        private static String rule(Parser parser, DFA dfa) {
            return nameOf(parser) + " " + parser.getRuleNames()[dfa.atnStartState.ruleIndex];
        }

        String describe() {
            StringBuilder report = new StringBuilder(String.format(Locale.ROOT,
              "full-context predictions in pass 1: %,d in %d rules, the longest scanning %d tokens in %s%n",
              fallbacks, byRule.size(), longestScan, longestScanRule));
            byRule.entrySet().stream()
              .sorted((a, b) -> Long.compare(b.getValue()[0], a.getValue()[0]))
              .limit(5)
              .forEach(e -> report.append(String.format(Locale.ROOT, "  %,9d  %s%n", e.getValue()[0], e.getKey())));
            return report.toString();
        }
    }

    /**
     * The prediction cache of every generated parser, as each language's first pass left it.
     */
    private static final class CacheReport {
        final StringBuilder byGrammar = new StringBuilder();
        final Map<String, long[]> byRule = new HashMap<>();

        void add(Parser grammar) {
            long states = 0;
            long configs = 0;
            long orderOnly = 0;
            for (DFA dfa : PredictionCache.shared(grammar)) {
                String rule = nameOf(grammar) + " " + grammar.getRuleNames()[dfa.atnStartState.ruleIndex];
                for (DFAState state : dfa.states.keySet()) {
                    states++;
                    configs += state.configs.size();
                    orderOnly += PredictionCache.orderOnlyDuplicates(state);
                    long[] counts = byRule.computeIfAbsent(rule, r -> new long[2]);
                    counts[0]++;
                    counts[1] += state.configs.size();
                }
            }
            byGrammar.append(String.format(Locale.ROOT, "  %-26s %,9d states  %,11d configurations  %,9d%n",
              nameOf(grammar), states, configs, orderOnly));
        }

        String describe() {
            StringBuilder report = new StringBuilder(
              "prediction caches after pass 1, and their configurations differing only in predicate order:\n")
              .append(byGrammar)
              .append("rules holding the most configurations:\n");
            byRule.entrySet().stream()
              .sorted((a, b) -> Long.compare(b.getValue()[1], a.getValue()[1]))
              .limit(5)
              .forEach(e -> report.append(String.format(Locale.ROOT, "  %,11d configurations in %,7d states  %s%n",
                e.getValue()[1], e.getValue()[0], e.getKey())));
            return report.toString();
        }
    }

    /**
     * The generated parser's class. The COBOL parser is an anonymous subclass of it.
     */
    private static String nameOf(Parser parser) {
        Class<?> type = parser.getClass();
        return (type.isAnonymousClass() ? type.getSuperclass() : type).getSimpleName();
    }

    private static long allocated() {
        return ((com.sun.management.ThreadMXBean) ManagementFactory.getThreadMXBean()).getThreadAllocatedBytes(Thread.currentThread().getId());
    }
}
