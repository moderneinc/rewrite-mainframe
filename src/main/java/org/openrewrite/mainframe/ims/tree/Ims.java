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
package org.openrewrite.mainframe.ims.tree;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;
import org.jspecify.annotations.Nullable;
import org.openrewrite.*;
import org.openrewrite.mainframe.ims.ImsVisitor;
import org.openrewrite.mainframe.ims.internal.ImsPrinter;
import org.openrewrite.marker.Markers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface Ims extends Tree {

    @SuppressWarnings("unchecked")
    @Override
    default <R extends Tree, P> R accept(TreeVisitor<R, P> v, P p) {
        return (R) acceptIms(v.adapt(ImsVisitor.class), p);
    }

    default <P> @Nullable Ims acceptIms(ImsVisitor<P> v, P p) {
        return v.defaultValue(this, p);
    }

    @Override
    default <P> boolean isAcceptable(TreeVisitor<?, P> v, P p) {
        return v.isAdaptableTo(ImsVisitor.class);
    }

    Space getPrefix();

    <P extends Ims> P withPrefix(Space prefix);

    @Value
    @EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
    @With
    class CompilationUnit implements Ims, SourceFile {

        @EqualsAndHashCode.Include
        UUID id;

        Path sourcePath;

        @Nullable
        FileAttributes fileAttributes;

        Space prefix;
        Markers markers;

        @Nullable // for backwards compatibility
        @With(AccessLevel.PRIVATE)
        String charsetName;

        boolean charsetBomMarked;

        @Nullable
        Checksum checksum;

        @Override
        public Charset getCharset() {
            return charsetName == null ? StandardCharsets.UTF_8 : Charset.forName(charsetName);
        }

        @SuppressWarnings("unchecked")
        @Override
        public SourceFile withCharset(Charset charset) {
            return withCharsetName(charset.name());
        }

        List<Statement> statements;
        Space eof;

        @Override
        public <P> Ims acceptIms(ImsVisitor<P> v, P p) {
            return v.visitCompilationUnit(this, p);
        }

        @Override
        public <P> TreeVisitor<?, PrintOutputCapture<P>> printer(Cursor cursor) {
            return new ImsPrinter<>();
        }
    }

    /**
     * A macro statement: an optional name field, an operation, and the operands belonging to it,
     * however many lines they are written over.
     * <p>
     * Every IMS gen library is written this way — {@code DBD}, {@code DATASET}, {@code SEGM},
     * {@code FIELD}, {@code LCHILD} and {@code XDFLD} in a DBD, {@code PCB} and {@code SENSEG} in a
     * PSB, {@code FMT} and {@code MFLD} in a format set — so they share a node and are told apart by
     * {@code getOperation()}. Named for the assembler construct rather than simply {@link Statement},
     * which is what every line of a member is, comments among them.
     */
    @Value
    @EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
    @With
    class MacroStatement implements Ims, Statement {

        @EqualsAndHashCode.Include
        UUID id;

        Space prefix;
        Markers markers;

        /**
         * The name field, which begins in column 1. Null when the statement has none — most DBDGEN
         * statements write no name, and the ones that do are labelling a data set group.
         */
        @Nullable
        Word name;

        /**
         * The macro invoked: {@code DBD}, {@code DATASET}, {@code SEGM}, {@code FIELD},
         * {@code LCHILD}, {@code XDFLD}, {@code DBDGEN}, {@code FINISH}, {@code END}.
         */
        Word operation;

        /**
         * Everything after the operation, in source order: the {@link Operand}s of the operand
         * field, the words of any comment field, and the character in column 72 ending each
         * continued line, which an operand carried on past it holds instead. Keeping them in one
         * ordered list is what lets the statement print back exactly, however it was laid out.
         */
        List<Ims> operands;

        @Override
        public <P> Ims acceptIms(ImsVisitor<P> v, P p) {
            return v.visitMacroStatement(this, p);
        }

        public boolean isOperation(String operation) {
            return this.operation.getText().equalsIgnoreCase(operation);
        }

        /**
         * The name field, or empty for a statement with none.
         */
        public String getSimpleName() {
            return name == null ? "" : name.getText();
        }

        public List<Operand> getParameters() {
            List<Operand> parameters = new ArrayList<>(operands.size());
            for (Ims operand : operands) {
                if (operand instanceof Operand) {
                    parameters.add((Operand) operand);
                }
            }
            return parameters;
        }

        /**
         * The keyword operand of this name, or null. Keyword operands are unique within a macro, so
         * the first is the only one.
         */
        public Ims.@Nullable KeywordOperand getParameter(String keyword) {
            for (Operand operand : getParameters()) {
                if (operand instanceof KeywordOperand &&
                    ((KeywordOperand) operand).getKeywordText().equalsIgnoreCase(keyword)) {
                    return (KeywordOperand) operand;
                }
            }
            return null;
        }
    }

    @Value
    @EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
    @With
    class Comment implements Ims, Statement {

        @EqualsAndHashCode.Include
        UUID id;

        Space prefix;
        Markers markers;
        Word word;

        @Override
        public <P> Ims acceptIms(ImsVisitor<P> v, P p) {
            return v.visitComment(this, p);
        }
    }

    @Value
    @EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
    @With
    class Unknown implements Ims, Statement {

        @EqualsAndHashCode.Include
        UUID id;

        Space prefix;
        Markers markers;
        Word word;

        @Override
        public <P> Ims acceptIms(ImsVisitor<P> v, P p) {
            return v.visitUnknown(this, p);
        }
    }

    /**
     * An operand written as {@code KEYWORD=value}.
     * <p>
     * The value is a list of words, one per card it is written over, so that each card prints back
     * exactly as it was. {@link #getValueText()} reads them as one.
     */
    @Value
    @EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
    @With
    class KeywordOperand implements Ims, Operand {

        @EqualsAndHashCode.Include
        UUID id;

        Space prefix;
        Markers markers;

        /**
         * The keyword as written on the operand's first card, without the {@code =}: {@code NAME},
         * {@code PARENT}, {@code BYTES}. Where column 71 falls inside it, the rest is in the value
         * and {@link #getKeywordText()} reads it whole.
         */
        Word keyword;

        List<Word> value;

        @Override
        public <P> Ims acceptIms(ImsVisitor<P> v, P p) {
            return v.visitKeywordOperand(this, p);
        }

        /**
         * What the operand means: the value without the {@code =} that introduces it or the comma
         * that separates it from the next. Printing walks the words instead, so the punctuation is
         * kept there and left out here.
         */
        public String getValueText() {
            String text = join(value);
            return trimSeparators(text.substring(text.indexOf('=') + 1));
        }

        public String getKeywordText() {
            String text = join(value);
            return keyword.getText() + text.substring(0, Math.max(text.indexOf('='), 0));
        }
    }

    /**
     * An operand with no keyword: the {@code 12} of a {@code CLMSAVE 12}, or the quoted heading of a
     * {@code TITLE}. The gen macros take keyword operands almost throughout, so this is what anything
     * else in the operand field becomes.
     */
    @Value
    @EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
    @With
    class PositionalOperand implements Ims, Operand {

        @EqualsAndHashCode.Include
        UUID id;

        Space prefix;
        Markers markers;

        List<Word> value;

        @Override
        public <P> Ims acceptIms(ImsVisitor<P> v, P p) {
            return v.visitPositionalOperand(this, p);
        }

        /**
         * The value without the comma separating it from the next operand.
         */
        public String getValueText() {
            return trimSeparators(join(value));
        }
    }

    static String trimSeparators(String text) {
        return text.endsWith(",") ? text.substring(0, text.length() - 1) : text;
    }

    /**
     * The value as the assembler reads it. A value carried onto further cards keeps the words that
     * end each card, the continuation character among them, between its parts. So only the first
     * word and a word that begins a card add to the value.
     */
    static String join(List<Word> words) {
        StringBuilder text = new StringBuilder(words.get(0).getText());
        for (int i = 1; i < words.size(); i++) {
            String prefix = words.get(i).getPrefix().getWhitespace();
            int newline = prefix.lastIndexOf('\n');
            if (newline >= 0) {
                // Columns 1-15 are blank, and only a literal keeps the blanks after them.
                if (insideLiteral(text)) {
                    text.append(prefix.substring(Math.min(prefix.length(), newline + 16)));
                }
                text.append(words.get(i).getText());
            }
        }
        return text.toString();
    }

    static boolean insideLiteral(CharSequence text) {
        int quotes = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\'') {
                quotes++;
            }
        }
        return quotes % 2 == 1;
    }

    @Value
    @EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
    @With
    class Word implements Ims {

        @EqualsAndHashCode.Include
        UUID id;

        Space prefix;
        Markers markers;
        String text;

        @Override
        public <P> Ims acceptIms(ImsVisitor<P> v, P p) {
            return v.visitWord(this, p);
        }
    }
}
