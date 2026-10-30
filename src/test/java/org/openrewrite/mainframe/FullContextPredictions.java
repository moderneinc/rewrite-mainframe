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
package org.openrewrite.mainframe;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.DecisionInfo;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class FullContextPredictions {

    private FullContextPredictions() {
    }

    /**
     * How often prediction in the decisions of the given rules retried with full context over one parse. A syntax
     * error fails the count, and so does a rule the parse never entered, since either way the fixture pins nothing.
     */
    public static long fallbacks(Parser parser, Runnable parse, String... rules) {
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
                                    int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException(line + ":" + charPositionInLine + " " + msg);
            }
        });
        Set<String> wanted = new HashSet<>(Arrays.asList(rules));
        Set<String> unentered = new HashSet<>(wanted);
        parser.addParseListener(new ParseTreeListener() {
            @Override
            public void enterEveryRule(ParserRuleContext ctx) {
                unentered.remove(parser.getRuleNames()[ctx.getRuleIndex()]);
            }

            @Override
            public void exitEveryRule(ParserRuleContext ctx) {
            }

            @Override
            public void visitTerminal(TerminalNode node) {
            }

            @Override
            public void visitErrorNode(ErrorNode node) {
            }
        });
        parser.setProfile(true);
        parse.run();
        if (!unentered.isEmpty()) {
            throw new IllegalArgumentException("The parse never entered " + unentered);
        }

        long fallbacks = 0;
        for (DecisionInfo decision : parser.getParseInfo().getDecisionInfo()) {
            String rule = parser.getRuleNames()[parser.getATN().getDecisionState(decision.decision).ruleIndex];
            if (wanted.contains(rule)) {
                fallbacks += decision.LL_Fallback;
            }
        }
        return fallbacks;
    }
}
