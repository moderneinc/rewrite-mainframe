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
package org.openrewrite.mainframe.cobol;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.jspecify.annotations.Nullable;
import org.openrewrite.*;
import org.openrewrite.Parser;
import org.openrewrite.mainframe.cobol.internal.CobolDialect;
import org.openrewrite.mainframe.cobol.internal.CobolParserVisitor;
import org.openrewrite.mainframe.cobol.internal.CobolPreprocessorOutputSourcePrinter;
import org.openrewrite.mainframe.cobol.internal.grammar.CobolLexer;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.CobolPreprocessor;
import org.openrewrite.mainframe.internal.PredictionListener;
import org.openrewrite.tree.ParseError;
import org.openrewrite.tree.ParsingEventListener;
import org.openrewrite.tree.ParsingExecutionContextView;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class CobolParser implements Parser {
    /**
     * Compared case-insensitively, so {@code .CBL} and {@code .COB} are read too.
     */
    public static final List<String> COBOL_FILE_EXTENSIONS = Arrays.asList(".cbl", ".cob", ".cobol");

    private static final Pattern IDENTIFICATION_DIVISION =
            Pattern.compile("\\bID(ENTIFICATION)?\\s+DIVISION\\b", Pattern.CASE_INSENSITIVE);

    private final CobolDialect cobolDialect;
    private final List<SourceFile> copybooks;
    private final Duration timeout;

    @Override
    public Stream<SourceFile> parseInputs(Iterable<Input> sourceFiles, @Nullable Path relativeTo, ExecutionContext ctx) {
        CobolPreprocessorParser cobolPreprocessorParser = CobolPreprocessorParser.builder()
                .cobolDialect(cobolDialect)
                .copybooks(copybooks)
                .build();

        ParsingEventListener parserListener = ParsingExecutionContextView.view(ctx).getParsingListener();
        return acceptedInputs(sourceFiles).map(s -> parseInput(s, relativeTo, ctx, cobolPreprocessorParser, parserListener));
    }

    private SourceFile parseInput(Input input, @Nullable Path relativeTo, ExecutionContext ctx,
                                  CobolPreprocessorParser cobolPreprocessorParser, ParsingEventListener parserListener) {
        try {
            parserListener.startedParsing(input);
            cobolPreprocessorParser.reset();
            SourceFile preprocessedCU = cobolPreprocessorParser.parseInputs(singletonList(input), relativeTo, ctx).collect(toList()).get(0);
            assert preprocessedCU != null;
            if (preprocessedCU instanceof ParseError) {
                WrongLanguageException notCobol = notCobol(input, ctx, null);
                return notCobol == null ? preprocessedCU : ParseError.build(this, input, relativeTo, ctx, notCobol);
            }

            // Expand source first so token generation and source reconstruction agree about copied debug lines.
            PrintOutputCapture<ExecutionContext> sourceOutput = new PrintOutputCapture<>(new InMemoryExecutionContext());
            CobolPreprocessorOutputSourcePrinter<ExecutionContext> printWithColumns = new CobolPreprocessorOutputSourcePrinter<>(cobolDialect, true);
            printWithColumns.visit(preprocessedCU, sourceOutput);

            // Print processed code to parse COBOL.
            PrintOutputCapture<ExecutionContext> cobolParserOutput = new PrintOutputCapture<>(new InMemoryExecutionContext());
            CobolPreprocessorOutputSourcePrinter<ExecutionContext> printWithoutColumns = new CobolPreprocessorOutputSourcePrinter<>(cobolDialect, false,
                    !CobolStringUtils.isDebuggingModeEnabled(sourceOutput.getOut(), cobolDialect));
            printWithoutColumns.visit(preprocessedCU, cobolParserOutput);

            // Any character with no token is skipped silently: a trailing SUB has to parse, and ForwardingErrorListener throws.
            CobolLexer lexer = new CobolLexer(CharStreams.fromString(cobolParserOutput.getOut()));
            lexer.removeErrorListeners();
            CommonTokenStream tokens = new CommonTokenStream(new ImpliedSeparators(lexer));
            org.openrewrite.mainframe.cobol.internal.grammar.CobolParser parser =
                    new org.openrewrite.mainframe.cobol.internal.grammar.CobolParser(tokens) {{
                        _interp = new TimeLimitingParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
                    }};
            parser.removeErrorListeners();
            parser.addErrorListener(new ForwardingErrorListener(input.getPath()));
            PredictionListener.addTo(parser, ctx);

            try {
                org.openrewrite.mainframe.cobol.internal.grammar.CobolParser.CompilationUnitContext tokenizedCU = parser.compilationUnit();
                // The preprocessor read the source, so it knows the charset the source was detected in.
                CobolPreprocessor.CompilationUnit preprocessed = (CobolPreprocessor.CompilationUnit) preprocessedCU;
                Cobol.CompilationUnit compilationUnit = new CobolParserVisitor(
                        input.getRelativePath(relativeTo),
                        input.getFileAttributes(),
                        sourceOutput.getOut(),
                        preprocessed.getCharset(),
                        preprocessed.isCharsetBomMarked(),
                        cobolDialect,
                        preprocessed.getPreprocessorStatements(),
                        preprocessed.getReplacements(),
                        printWithoutColumns.getElidedExecs(),
                        timeout,
                        tokens
                ).visitCompilationUnit(tokenizedCU);
                parserListener.parsed(input, compilationUnit);
                return requirePrintEqualsInput(compilationUnit, input, relativeTo, ctx);
            } catch (ParseCancellationException e) {
                throw new CobolParsingTimeoutException(relativeTo == null ? input.getPath() :
                        relativeTo.relativize(input.getPath()));
            }
        } catch (CobolParsingTimeoutException t) {
            ctx.getOnError().accept(t);
            return ParseError.build(this, input, relativeTo, ctx, t);
        } catch (Throwable t) {
            ctx.getOnError().accept(t);
            WrongLanguageException notCobol = notCobol(input, ctx, t);
            return ParseError.build(this, input, relativeTo, ctx, notCobol == null ? t : notCobol);
        }
    }

    /**
     * Whether a file the grammar could not read is not a program at all. Only asked after a
     * failure: a program the grammar reads has its division header wherever it is, and a control
     * card kept as {@code .cbl} has none.
     */
    private static @Nullable WrongLanguageException notCobol(Input input, ExecutionContext ctx, @Nullable Throwable cause) {
        if (input.isSynthetic()) {
            return null;
        }
        String source;
        try {
            source = input.getSource(ctx).readFully();
        } catch (RuntimeException e) {
            return null;
        }
        if (IDENTIFICATION_DIVISION.matcher(source).find()) {
            return null;
        }
        return new WrongLanguageException(input.getPath(),
                input.getPath() + " is not a COBOL program: it has no IDENTIFICATION DIVISION.", cause);
    }

    @Override
    public boolean accept(Path path) {
        String s = path.toString().toLowerCase();
        for (String COBOL_FILE_EXTENSION : COBOL_FILE_EXTENSIONS) {
            if (s.endsWith(COBOL_FILE_EXTENSION)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Path sourcePathFromSourceText(Path prefix, String sourceCode) {
        return prefix.resolve("file.CBL");
    }

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static class ForwardingErrorListener extends BaseErrorListener {
        private final Path sourcePath;

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine, String msg, RecognitionException e) {
            throw new CobolParsingException(sourcePath,
                    String.format("Syntax error in %s at line %d:%d %s.", sourcePath, line, charPositionInLine, msg), e);
        }
    }

    public static CobolParser.Builder builder() {
        return new CobolParser.Builder();
    }

    public static class Builder extends org.openrewrite.Parser.Builder {
        private CobolDialect cobolDialect = CobolDialect.ibmAnsi85();
        private List<SourceFile> copybooks = emptyList();
        private Duration timeout = Duration.ofMinutes(10);

        public Builder() {
            super(Cobol.CompilationUnit.class);
        }

        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder cobolDialect(CobolDialect cobolDialect) {
            this.cobolDialect = cobolDialect;
            return this;
        }

        public Builder copybooks(List<SourceFile> copybooks) {
            this.copybooks = copybooks;
            return this;
        }

        @Override
        public CobolParser build() {
            return new CobolParser(cobolDialect, copybooks, timeout);
        }

        @Override
        public String getDslName() {
            return "cobol";
        }
    }

    private class TimeLimitingParserATNSimulator extends ParserATNSimulator {
        private final Instant start = Instant.now();

        public TimeLimitingParserATNSimulator(org.antlr.v4.runtime.Parser parser, ATN atn, DFA[] decisionToDFA,
                                              PredictionContextCache sharedContextCache) {
            super(parser, atn, decisionToDFA, sharedContextCache);
        }

        @Override
        protected void closure(ATNConfig config,
                               ATNConfigSet configs,
                               Set<ATNConfig> closureBusy,
                               boolean collectPredicates,
                               boolean fullCtx,
                               boolean treatEofAsEpsilon) {
            Duration timeElapsed = Duration.between(start, Instant.now());
            if (timeElapsed.compareTo(timeout) > 0) {
                throw new ParseCancellationException();
            }
            super.closure(config, configs, closureBusy, collectPredicates, fullCtx, treatEofAsEpsilon);
        }

        @Override
        protected @Nullable ATNConfig predTransition(ATNConfig config, PredicateTransition pt,
                                                     boolean collectPredicates, boolean inContext, boolean fullCtx) {
            return inOperandOrder(super.predTransition(config, pt, collectPredicates, inContext, fullCtx));
        }
    }

    // TODO Remove OPERAND_ORDER, inOperandOrder and the predTransition override once an ANTLR release fixes
    //  antlr/antlr4#4990. If the fix keeps the operands in an order of its own and stores a hash over it, sorting
    //  them in place into another order would leave that hash stale.
    private static final Comparator<SemanticContext> OPERAND_ORDER = Comparator
            .comparing((SemanticContext c) -> c.getClass().getName())
            .thenComparingInt(c -> c instanceof SemanticContext.Predicate ? ((SemanticContext.Predicate) c).ruleIndex : 0)
            .thenComparingInt(c -> c instanceof SemanticContext.Predicate ? ((SemanticContext.Predicate) c).predIndex : 0)
            .thenComparing(SemanticContext::toString);

    /**
     * ANTLR stores the predicates a configuration collected in hash-set order and compares them in that order, so the
     * same predicates collected in another order would make a second configuration. The grammar has no precedence
     * predicates, so every combined predicate is made in {@code predTransition} and sorted here before a configuration
     * set holds it.
     */
    private static @Nullable ATNConfig inOperandOrder(@Nullable ATNConfig config) {
        if (config != null && config.semanticContext instanceof SemanticContext.AND) {
            Arrays.sort(((SemanticContext.AND) config.semanticContext).opnds, OPERAND_ORDER);
        }
        return config;
    }

    /**
     * The lexer's tokens with commas and periods where GnuCOBOL's IBM dialect reads them. A comma is a decimal point
     * only in a program declaring DECIMAL-POINT IS COMMA. Otherwise it separates outside parentheses, even with no
     * space after it, and delimits a subscript or an argument inside them. A name that starts a line in Area A and
     * heads a paragraph or section ends the sentence before it, as if a period preceded it. That sentence has to have
     * begun in Area B, since a program writing its statements in Area A says nothing by starting a word there.
     */
    static final class ImpliedSeparators implements TokenSource {

        /**
         * Area A is columns 8 to 11, the first four of a line once the sequence and indicator areas are printed away.
         */
        private static final int AREA_B = 4;

        private static final IntervalSet PARAGRAPH_NAME_START = org.openrewrite.mainframe.cobol.internal.grammar.CobolParser._ATN
                .nextTokens(org.openrewrite.mainframe.cobol.internal.grammar.CobolParser._ATN
                        .ruleToStartState[org.openrewrite.mainframe.cobol.internal.grammar.CobolParser.RULE_paragraphName]);

        /**
         * Words that still need the name after them, which is then their operand however it is laid out.
         */
        private static final IntervalSet NEEDS_OPERAND = new IntervalSet(CobolLexer.TO, CobolLexer.FROM, CobolLexer.BY,
                CobolLexer.INTO, CobolLexer.GIVING, CobolLexer.THRU, CobolLexer.THROUGH, CobolLexer.OF, CobolLexer.IN,
                CobolLexer.USING);

        private final TokenSource lexer;
        private final Deque<Token> lookahead = new ArrayDeque<>();
        private final Deque<Token> ready = new ArrayDeque<>();
        private int parenthesisDepth;
        private boolean inProcedureDivision;
        private int previous = Token.INVALID_TYPE;
        private int beforePrevious = Token.INVALID_TYPE;
        private int previousLine;
        private boolean sentenceInAreaB;
        private boolean decimalPointIsComma;

        ImpliedSeparators(TokenSource lexer) {
            this.lexer = lexer;
        }

        @Override
        public Token nextToken() {
            if (!ready.isEmpty()) {
                return ready.poll();
            }
            Token token = lookahead.isEmpty() ? lexer.nextToken() : lookahead.poll();
            if (token.getType() == CobolLexer.NUMERICLITERAL && !decimalPointIsComma && token.getText().indexOf(',') >= 0) {
                List<Token> pieces = split(token);
                for (int i = pieces.size() - 1; i > 0; i--) {
                    lookahead.addFirst(pieces.get(i));
                }
                token = pieces.get(0);
            }
            if (token.getChannel() != Token.DEFAULT_CHANNEL) {
                return token;
            }
            if (headsParagraph(token)) {
                ready.add(read(token));
                return implicitPeriod(token);
            }
            return read(token);
        }

        private boolean headsParagraph(Token token) {
            if (!inProcedureDivision || !sentenceInAreaB || previous == CobolLexer.DOT_FS || NEEDS_OPERAND.contains(previous) ||
                token.getLine() == previousLine || token.getCharPositionInLine() >= AREA_B ||
                !PARAGRAPH_NAME_START.contains(token.getType())) {
                return false;
            }
            int next = nextOnDefaultChannel();
            return next == CobolLexer.DOT_FS || next == CobolLexer.SECTION;
        }

        private int nextOnDefaultChannel() {
            for (Token token : lookahead) {
                if (token.getChannel() == Token.DEFAULT_CHANNEL) {
                    return token.getType();
                }
            }
            while (true) {
                Token token = lexer.nextToken();
                lookahead.add(token);
                if (token.getChannel() == Token.DEFAULT_CHANNEL || token.getType() == Token.EOF) {
                    return token.getType();
                }
            }
        }

        private Token read(Token token) {
            int type = token.getType();
            if (type == CobolLexer.COMMACHAR && parenthesisDepth == 0) {
                ((WritableToken) token).setChannel(Token.HIDDEN_CHANNEL);
                return token;
            }
            if (previous == CobolLexer.DOT_FS) {
                // An EXEC block is a statement whatever column the parser's input stands it up in.
                sentenceInAreaB = token.getCharPositionInLine() >= AREA_B || type == CobolLexer.EXECCICSLINE ||
                                  type == CobolLexer.EXECDLILINE || type == CobolLexer.EXECSQLLINE ||
                                  type == CobolLexer.EXECSQLIMSLINE;
            }
            if (type == CobolLexer.LPARENCHAR) {
                parenthesisDepth++;
            } else if (type == CobolLexer.RPARENCHAR && parenthesisDepth > 0) {
                parenthesisDepth--;
            } else if (type == CobolLexer.DOT_FS) {
                parenthesisDepth = 0;
            } else if (type == CobolLexer.DIVISION) {
                inProcedureDivision = previous == CobolLexer.PROCEDURE;
            } else if (type == CobolLexer.COMMA && (previous == CobolLexer.DECIMAL_POINT ||
                                                    previous == CobolLexer.IS && beforePrevious == CobolLexer.DECIMAL_POINT)) {
                decimalPointIsComma = true;
            }
            beforePrevious = previous;
            previous = type;
            previousLine = token.getLine();
            return token;
        }

        /**
         * The pieces a numeric literal falls into where its commas are no decimal points: each run between them read
         * as the lexer reads it alone, and the commas.
         */
        private List<Token> split(Token literal) {
            List<Token> pieces = new ArrayList<>();
            String text = literal.getText();
            for (int at = 0; ; ) {
                int comma = text.indexOf(',', at);
                int end = comma < 0 ? text.length() : comma;
                CobolLexer alone = new CobolLexer(CharStreams.fromString(text.substring(at, end)));
                alone.removeErrorListeners();
                for (Token token = alone.nextToken(); token.getType() != Token.EOF; token = alone.nextToken()) {
                    pieces.add(piece(literal, token.getType(), token.getChannel(), token.getText(), at + token.getStartIndex()));
                }
                if (comma < 0) {
                    return pieces;
                }
                pieces.add(piece(literal, CobolLexer.COMMACHAR, Token.DEFAULT_CHANNEL, ",", comma));
                at = comma + 1;
            }
        }

        private Token piece(Token literal, int type, int channel, String text, int offset) {
            CommonToken piece = new CommonToken(new Pair<>(this, literal.getInputStream()), type, channel,
                    literal.getStartIndex() + offset, literal.getStartIndex() + offset + text.length() - 1);
            piece.setText(text);
            piece.setLine(literal.getLine());
            piece.setCharPositionInLine(literal.getCharPositionInLine() + offset);
            return piece;
        }

        private Token implicitPeriod(Token before) {
            CommonToken period = new CommonToken(new Pair<>(this, before.getInputStream()),
                    org.openrewrite.mainframe.cobol.internal.grammar.CobolParser.IMPLICIT_PERIOD, Token.DEFAULT_CHANNEL,
                    before.getStartIndex(), before.getStartIndex() - 1);
            period.setLine(before.getLine());
            period.setCharPositionInLine(before.getCharPositionInLine());
            period.setText("");
            return period;
        }

        @Override
        public int getLine() {
            return lexer.getLine();
        }

        @Override
        public int getCharPositionInLine() {
            return lexer.getCharPositionInLine();
        }

        @Override
        public CharStream getInputStream() {
            return lexer.getInputStream();
        }

        @Override
        public String getSourceName() {
            return lexer.getSourceName();
        }

        @Override
        public void setTokenFactory(TokenFactory<?> factory) {
            lexer.setTokenFactory(factory);
        }

        @Override
        public TokenFactory<?> getTokenFactory() {
            return lexer.getTokenFactory();
        }
    }
}
