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
package org.openrewrite.mainframe.ims.internal;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jspecify.annotations.Nullable;
import org.openrewrite.FileAttributes;
import org.openrewrite.mainframe.ims.internal.grammar.IMSLexer;
import org.openrewrite.mainframe.ims.internal.grammar.IMSParser;
import org.openrewrite.mainframe.ims.internal.grammar.IMSParserBaseVisitor;
import org.openrewrite.mainframe.ims.marker.SequenceArea;
import org.openrewrite.mainframe.ims.tree.Ims;
import org.openrewrite.mainframe.ims.tree.Space;
import org.openrewrite.mainframe.ims.tree.Statement;
import org.openrewrite.marker.Markers;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.openrewrite.Tree.randomId;
import static org.openrewrite.mainframe.ims.tree.Space.EMPTY;

@RequiredArgsConstructor
public class ImsParserVisitor extends IMSParserBaseVisitor<Ims> {

    private final Path path;
    private final @Nullable FileAttributes fileAttributes;
    private final String source;
    private final Charset charset;
    private final boolean charsetBomMarked;

    /**
     * The line reader works out which lines open a statement, which continue one, and which carry a
     * name field. It is the only place that information survives, so grouping words back into
     * statements needs the token stream rather than just the parse tree.
     */
    private final BufferedTokenStream tokens;

    private int cursor = 0;

    public <T> T visit(@Nullable ParseTree... trees) {
        for (ParseTree tree : trees) {
            if (tree != null) {
                //noinspection unchecked
                return (T) visit(tree);
            }
        }
        throw new IllegalStateException("Expected one of the supplied trees to be non-null");
    }

    @Override
    public Ims.CompilationUnit visitCompilationUnit(IMSParser.CompilationUnitContext ctx) {
        List<Statement> statements = new ArrayList<>(ctx.statement().size());
        List<IMSParser.ImsContext> pending = new ArrayList<>();

        for (IMSParser.StatementContext statement : ctx.statement()) {
            if (statement.ims() == null) {
                flush(pending, statements);
                statements.add((Statement) visitStatement(statement));
                continue;
            }
            if (beginsStatement(statement.ims())) {
                flush(pending, statements);
            }
            pending.add(statement.ims());
        }
        flush(pending, statements);

        return new Ims.CompilationUnit(
                randomId(),
                path,
                fileAttributes,
                EMPTY,
                Markers.EMPTY,
                charset.name(),
                charsetBomMarked,
                null,
                statements,
                Space.build(source, cursor, source.length())
        );
    }

    /**
     * Turns the words gathered so far into one statement: name field, operation, and everything
     * after it.
     * <p>
     * What follows the operand field is the comment field, the character in column 72 saying the
     * statement carries on among it. Those words stay in the operand list as plain words, so that
     * the statement still prints back exactly while only the parts that mean something are typed.
     */
    private void flush(List<IMSParser.ImsContext> pending, List<Statement> statements) {
        if (pending.isEmpty()) {
            return;
        }
        boolean named = isNamed(pending.get(0));
        Space prefix = whitespace();
        List<Ims.Word> words = new ArrayList<>(pending.size());
        List<Boolean> startsLine = new ArrayList<>(pending.size());
        for (IMSParser.ImsContext ims : pending) {
            words.add(word(ims));
            startsLine.add(beginsLine(ims));
        }
        pending.clear();

        Ims.Word name = named ? words.get(0).withPrefix(EMPTY) : null;
        int operationIndex = named ? 1 : 0;
        if (operationIndex >= words.size()) {
            // A name field with nothing after it. Not a macro invocation, so it is not typed as one.
            statements.add(new Ims.Unknown(randomId(), prefix, Markers.EMPTY,
                    words.get(0).withPrefix(EMPTY)));
            return;
        }
        Ims.Word operation = named ? words.get(operationIndex) : words.get(0).withPrefix(EMPTY);

        List<Ims> operands = new ArrayList<>();
        boolean expectOperand = true;
        for (int i = operationIndex + 1; i < words.size(); i++) {
            Ims.Word word = words.get(i);
            if (startsLine.get(i)) {
                // A continuation line resumes the operand field. Unlike JCL there is no punctuation
                // opening it — the line simply picks up where the one above left off.
                expectOperand = true;
            }
            if (expectOperand) {
                if (word.getText().length() == 1 && i + 1 < words.size() && startsLine.get(i + 1)) {
                    // Only the character in column 72 is on this card, so the field begins on the next.
                    operands.add(word);
                    continue;
                }
                List<List<Ims.Word>> cards = new ArrayList<>();
                i = field(words, startsLine, i, cards);
                operands.addAll(operands(cards));
                expectOperand = false;
                continue;
            }
            operands.add(word);
        }

        statements.add(new Ims.MacroStatement(randomId(), prefix, Markers.EMPTY, name, operation, operands));
    }

    /**
     * Gathers the operand field starting at {@code words.get(from)} into {@code cards}, one entry per
     * card it covers. An entry holds the card's part of the field as one word, followed by the other
     * words that end that card.
     * <p>
     * The field ends at the first blank outside a literal. It resumes in column 16 of the next card
     * when it reaches column 71, or when a literal or a sublist is still open. The rest of a card
     * that breaks off a sublist is a remark.
     */
    private static int field(List<Ims.Word> words, List<Boolean> startsLine, int from, List<List<Ims.Word>> cards) {
        Ims.Word word = words.get(from);
        Space cardPrefix = word.getPrefix();
        StringBuilder card = new StringBuilder(word.getText());
        Markers cardMarkers = word.getMarkers();
        Nesting nesting = new Nesting().read(word.getText());
        int i = from;
        while (i + 1 < words.size()) {
            Ims.Word next = words.get(i + 1);
            String blanks = next.getPrefix().getWhitespace();
            List<Ims.Word> rest = new ArrayList<>();
            if (startsLine.get(i + 1)) {
                // The field reached column 72, so its last character is the continuation character.
                char continuation = card.charAt(card.length() - 1);
                nesting.unread(continuation);
                card.setLength(card.length() - 1);
                rest.add(new Ims.Word(randomId(), EMPTY, cardMarkers, String.valueOf(continuation)));
                cardMarkers = Markers.EMPTY;
            } else if (blanks.isEmpty() || nesting.quoted) {
                card.append(blanks).append(next.getText());
                cardMarkers = next.getMarkers();
                nesting.read(next.getText());
                i++;
                continue;
            } else if (nesting.depth > 0 && continues(startsLine, i + 1)) {
                while (!startsLine.get(i + 1)) {
                    rest.add(words.get(++i));
                }
            } else {
                break;
            }
            rest.add(0, new Ims.Word(randomId(), cardPrefix, cardMarkers, card.toString()));
            cards.add(rest);
            Ims.Word resumed = words.get(++i);
            cardPrefix = resumed.getPrefix();
            card = new StringBuilder(resumed.getText());
            cardMarkers = resumed.getMarkers();
            nesting.read(resumed.getText());
        }
        List<Ims.Word> last = new ArrayList<>(1);
        last.add(new Ims.Word(randomId(), cardPrefix, cardMarkers, card.toString()));
        cards.add(last);
        return i;
    }

    /**
     * Whether a continuation card follows the word at {@code from} in the same statement.
     */
    private static boolean continues(List<Boolean> startsLine, int from) {
        for (int i = from; i < startsLine.size(); i++) {
            if (startsLine.get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Whether the operand field read so far is inside a literal, and how many sublists are open.
     */
    private static class Nesting {
        boolean quoted;
        int depth;

        Nesting read(String text) {
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (c == '\'') {
                    quoted = !quoted;
                } else if (!quoted) {
                    depth += c == '(' ? 1 : c == ')' ? -1 : 0;
                }
            }
            return this;
        }

        void unread(char c) {
            if (c == '\'') {
                quoted = !quoted;
            } else if (!quoted) {
                depth -= c == '(' ? 1 : c == ')' ? -1 : 0;
            }
        }
    }

    /**
     * The operands of one operand field, given the cards {@link #field} gathered it from.
     * <p>
     * The field is split on its text at each comma outside a literal or a sublist, and the comma
     * stays on the operand it follows. An operand keeps one word per card it covers. The other words
     * ending a card go with the operand open there, so every word prints where it was written.
     */
    private static List<Ims> operands(List<List<Ims.Word>> cards) {
        StringBuilder field = new StringBuilder();
        for (List<Ims.Word> card : cards) {
            field.append(card.get(0).getText());
        }

        List<String> texts = splitOnTopLevelCommas(field.toString());
        List<List<Ims.Word>> values = new ArrayList<>(texts.size());
        int card = 0;
        int offset = 0;
        for (String text : texts) {
            List<Ims.Word> value = new ArrayList<>();
            for (int taken = 0; taken < text.length(); ) {
                Ims.Word from = cards.get(card).get(0);
                int end = Math.min(from.getText().length(), offset + text.length() - taken);
                value.add(new Ims.Word(randomId(), offset == 0 ? from.getPrefix() : EMPTY,
                        end == from.getText().length() ? from.getMarkers() : Markers.EMPTY,
                        from.getText().substring(offset, end)));
                taken += end - offset;
                offset = end;
                if (offset == from.getText().length()) {
                    value.addAll(cards.get(card).subList(1, cards.get(card).size()));
                    card++;
                    offset = 0;
                }
            }
            values.add(value);
        }
        // A card left with nothing of the field once its column 72 is taken off.
        for (; card < cards.size(); card++) {
            values.get(values.size() - 1).addAll(cards.get(card));
        }

        List<Ims> operands = new ArrayList<>(values.size());
        for (int v = 0; v < values.size(); v++) {
            List<Ims.Word> value = values.get(v);
            Ims.Word first = value.get(0);
            value.set(0, first.withPrefix(EMPTY));
            int equals = indexOfAssignment(texts.get(v));
            if (equals <= 0) {
                operands.add(new Ims.PositionalOperand(randomId(), first.getPrefix(), Markers.EMPTY, value));
            } else if (equals < first.getText().length()) {
                // The keyword is its own word so it can be read and replaced without string work.
                Ims.Word keyword = new Ims.Word(randomId(), EMPTY, Markers.EMPTY, first.getText().substring(0, equals));
                value.set(0, value.get(0).withText(first.getText().substring(equals)));
                operands.add(new Ims.KeywordOperand(randomId(), first.getPrefix(), Markers.EMPTY, keyword, value));
            } else {
                // The = is on a later card, so the value has nothing on this one.
                value.set(0, new Ims.Word(randomId(), EMPTY, Markers.EMPTY, ""));
                operands.add(new Ims.KeywordOperand(randomId(), first.getPrefix(), Markers.EMPTY,
                        first.withPrefix(EMPTY), value));
            }
        }
        return operands;
    }

    /**
     * Splits on commas outside parentheses and quotes, keeping each comma on the operand it follows
     * so that the pieces still concatenate to the original.
     */
    private static List<String> splitOnTopLevelCommas(String run) {
        List<String> parts = new ArrayList<>();
        int depth = 0;
        int start = 0;
        boolean quoted = false;
        for (int i = 0; i < run.length(); i++) {
            char c = run.charAt(i);
            if (c == '\'') {
                quoted = !quoted;
            } else if (quoted) {
                continue;
            } else if (c == '(') {
                depth++;
            } else if (c == ')') {
                depth--;
            } else if (c == ',' && depth == 0) {
                parts.add(run.substring(start, i + 1));
                start = i + 1;
            }
        }
        if (start < run.length()) {
            parts.add(run.substring(start));
        }
        return parts;
    }

    /**
     * The first {@code =} outside parentheses and quotes, so {@code ACCESS=(HDAM,VSAM)} is one
     * keyword rather than two.
     */
    private static int indexOfAssignment(String operand) {
        int depth = 0;
        boolean quoted = false;
        for (int i = 0; i < operand.length(); i++) {
            char c = operand.charAt(i);
            if (c == '\'') {
                quoted = !quoted;
            } else if (quoted) {
                continue;
            } else if (c == '(') {
                depth++;
            } else if (c == ')') {
                depth--;
            } else if (c == '=' && depth == 0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Ims visitCommentWord(IMSParser.CommentWordContext ctx) {
        Space prefix = whitespace();
        Markers markers = Markers.EMPTY;
        Ims.Word word = (Ims.Word) visit(ctx.COMMENT_TEXT());
        if (ctx.commentSequenceArea() != null) {
            markers = markers.addIfAbsent(mapSequenceArea(ctx.commentSequenceArea()));
        }
        return new Ims.Comment(
                randomId(),
                prefix,
                markers,
                word
        );
    }

    @Override
    public Ims visitUnknownWord(IMSParser.UnknownWordContext ctx) {
        Space prefix = whitespace();
        Markers markers = Markers.EMPTY;
        Ims.Word word;
        if (ctx.UNKNOWN_TEXT() == null && ctx.UNKNOWN_STRINGLITERAL() == null) {
            word = new Ims.Word(randomId(), EMPTY, Markers.EMPTY, "");
        } else {
            word = visit(ctx.UNKNOWN_TEXT(), ctx.UNKNOWN_STRINGLITERAL());
        }
        if (ctx.unknownSequenceArea() != null) {
            markers = markers.addIfAbsent(mapSequenceArea(ctx.unknownSequenceArea()));
        }
        return new Ims.Unknown(
                randomId(),
                prefix,
                markers,
                word
        );
    }

    @Override
    public Ims visitTerminal(TerminalNode node) {
        return new Ims.Word(
                randomId(),
                sourceBefore(node.getText()),
                Markers.EMPTY,
                node.getText()
        );
    }

    private Ims.Word word(IMSParser.ImsContext ctx) {
        IMSParser.ImsWordContext word = ctx.imsWord();
        Ims.Word w = visit(word.IMS_TEXT(), word.IMS_STRINGLITERAL());
        if (word.imsSequenceArea() != null) {
            w = w.withMarkers(w.getMarkers().addIfAbsent(mapSequenceArea(word.imsSequenceArea())));
        }
        return w;
    }

    private SequenceArea mapSequenceArea(ParserRuleContext ctx) {
        String text = ctx.getText().substring("^^CA_START^^".length()).trim();
        return new SequenceArea(randomId(), sourceBefore(text), text);
    }

    /**
     * Whether this word opens a statement rather than continuing the one before it.
     */
    private boolean beginsStatement(IMSParser.ImsContext ctx) {
        int marker = lineMarker(ctx);
        return marker == IMSLexer.IMS_NAMED || marker == IMSLexer.IMS_STATEMENT;
    }

    /**
     * Whether the statement this word opens writes a name field in column 1.
     */
    private boolean isNamed(IMSParser.ImsContext ctx) {
        return lineMarker(ctx) == IMSLexer.IMS_NAMED;
    }

    /**
     * Whether this word is the first on its line, either opening a statement or continuing one.
     */
    private boolean beginsLine(IMSParser.ImsContext ctx) {
        return lineMarker(ctx) != -1;
    }

    private int lineMarker(IMSParser.ImsContext ctx) {
        List<Token> hidden = tokens.getHiddenTokensToLeft(ctx.getStart().getTokenIndex());
        if (hidden != null) {
            for (Token token : hidden) {
                if (token.getType() == IMSLexer.IMS_NAMED ||
                    token.getType() == IMSLexer.IMS_STATEMENT ||
                    token.getType() == IMSLexer.IMS_CONTINUATION) {
                    return token.getType();
                }
            }
        }
        return -1;
    }

    private void skip(@Nullable String token) {
        if (token != null && source.startsWith(token, cursor)) {
            cursor += token.length();
        }
    }

    private Space whitespace() {
        int endIndex = indexOfNextNonWhitespace(cursor, source);
        Space prefix = Space.build(source, cursor, endIndex);
        cursor = endIndex;
        return prefix;
    }

    private Space sourceBefore(String untilDelim) {
        Space prefix = whitespace();
        skip(untilDelim);
        return Space.build(prefix.getWhitespace());
    }

    public static int indexOfNextNonWhitespace(int cursor, String source) {
        int delimIndex = cursor;
        for (; delimIndex < source.length(); delimIndex++) {
            if (!Character.isWhitespace(source.charAt(delimIndex))) {
                break;
            }
        }
        return delimIndex;
    }
}
