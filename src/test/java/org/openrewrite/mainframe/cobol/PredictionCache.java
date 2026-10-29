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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The prediction cache that every instance of the generated COBOL parser shares.
 */
final class PredictionCache {

    private PredictionCache() {
    }

    static DFA[] shared() {
        return new org.openrewrite.mainframe.cobol.internal.grammar.CobolParser(null).getInterpreter().decisionToDFA;
    }

    static void clear() {
        DFA[] cache = shared();
        for (int d = 0; d < cache.length; d++) {
            cache[d] = new DFA(cache[d].atnStartState, d);
        }
    }

    /**
     * The configurations in the state that equal another one once their predicates are compared as a set.
     */
    static int orderOnlyDuplicates(DFAState state) {
        Set<String> distinct = new HashSet<>();
        for (ATNConfig config : state.configs) {
            distinct.add(config.state.stateNumber + "/" + config.alt + "/" + unordered(config.semanticContext));
        }
        return state.configs.size() - distinct.size();
    }

    private static String unordered(SemanticContext context) {
        SemanticContext[] operands = context instanceof SemanticContext.AND ? ((SemanticContext.AND) context).opnds :
          context instanceof SemanticContext.OR ? ((SemanticContext.OR) context).opnds : null;
        if (operands == null) {
            return context.toString();
        }
        return (context instanceof SemanticContext.AND ? "&&" : "||") +
               Arrays.toString(Arrays.stream(operands).map(PredictionCache::unordered).sorted().toArray());
    }
}
