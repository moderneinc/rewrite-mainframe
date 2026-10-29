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

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.openrewrite.Cursor;
import org.openrewrite.ExecutionContext;
import org.openrewrite.InMemoryExecutionContext;
import org.openrewrite.mainframe.cobol.tree.Cobol;
import org.openrewrite.mainframe.cobol.tree.CobolPreprocessor;
import org.openrewrite.mainframe.cobol.tree.CommentArea;
import org.openrewrite.mainframe.cobol.tree.Replacement;
import org.openrewrite.internal.ListUtils;
import org.openrewrite.marker.Markers;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.openrewrite.Tree.randomId;
import static org.openrewrite.mainframe.cobol.tree.Space.EMPTY;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Value
public class PreprocessReplaceVisitor<P> extends CobolPreprocessorIsoVisitor<P> {

    Map<String, Replacement> replaceMap;

    @Override
    public CobolPreprocessor.CopyStatement visitCopyStatement(CobolPreprocessor.CopyStatement copyStatement, P p) {
        CobolPreprocessor.CopyStatement c = super.visitCopyStatement(copyStatement, p);

        if (c.getCopybook() != null) {
            List<CobolPreprocessor.ReplacingPhrase> phrases = new ArrayList<>();
            for (CobolPreprocessor is : c.getCobols()) {
                if (is instanceof CobolPreprocessor.ReplacingPhrase) {
                    CobolPreprocessor.ReplacingPhrase replacingPhrase = (CobolPreprocessor.ReplacingPhrase) is;
                    phrases.add(replacingPhrase);
                }
            }

            if (!phrases.isEmpty()) {
                Map<List<CobolPreprocessor.Word>, List<CobolPreprocessor.Word>> replacements = new LinkedHashMap<>();
                phrases.forEach(it -> replacements.putAll(getReplacings(it)));

                for (Map.Entry<List<CobolPreprocessor.Word>, List<CobolPreprocessor.Word>> entry : replacements.entrySet()) {
                    //noinspection ConstantConditions
                    c = c.withCopybook(c.getCopybook().withLst(ListUtils.map(c.getCopybook().getLst(),
                            preprocessor -> replace(preprocessor, entry.getKey(), entry.getValue()))));
                }
            }
        }

        return c;
    }

    @Override
    public CobolPreprocessor.ReplaceArea visitReplaceArea(CobolPreprocessor.ReplaceArea replaceArea, P p) {
        CobolPreprocessor.ReplaceArea r = super.visitReplaceArea(replaceArea, p);

        // Unknown:
        // The CobolPreprocessor grammar does not allow a `replaceArea` in a `replaceArea`.
        // However, a `replaceArea` may contain a `copyStatement`, and the `copyStatement` may contain a `replaceArea`.
        // So, it might be possible for multiple replacements rules to be applied in a replaceArea.
        Map<List<CobolPreprocessor.Word>, List<CobolPreprocessor.Word>> replacements = getReplacements(replaceArea.getReplaceByStatement());
        for (Map.Entry<List<CobolPreprocessor.Word>, List<CobolPreprocessor.Word>> entry : replacements.entrySet()) {
            r = r.withCobols(ListUtils.map(r.getCobols(), it -> replace(it, entry.getKey(), entry.getValue())));
        }

        return r;
    }

    private CobolPreprocessor replace(CobolPreprocessor tree, List<CobolPreprocessor.Word> from, List<CobolPreprocessor.Word> to) {
        Matches matches = Matches.find(tree, from, to);
        if (matches.isEmpty()) {
            return tree;
        }
        return new ReplaceVisitor(matches, to, replaceMap).visit(tree, new InMemoryExecutionContext(), getCursor());
    }

    /**
     * Where one rule matches in a tree. A match standing apart from its neighbours covers whole text
     * words. A match run together with letters, digits or hyphens is part of a text word that the lexer
     * split at its parentheses or colons, as {@code (TAG)} in {@code FLG-(TAG)-OK}. IBM Enterprise COBOL
     * reads the replaced text back as one word, so the whole text word is replaced by one word here too,
     * which is also the one token the COBOL grammar makes of it.
     */
    private static class Matches {
        final List<List<CobolPreprocessor.Word>> wholeWords = new ArrayList<>();
        /** The text word each word of a partial match's run belongs to, from the run's first word to its last. */
        final Map<CobolPreprocessor.Word, TextWord> textWords = new IdentityHashMap<>();

        /**
         * A text word a match is part of. Its first word is replaced by the whole of it, and the
         * words after the first leave the tree.
         */
        static class TextWord {
            final List<CobolPreprocessor.Word> words = new ArrayList<>();
            final StringBuilder text = new StringBuilder();
        }

        private void add(TextWord textWord, CobolPreprocessor.Word word, String text) {
            textWord.words.add(word);
            textWord.text.append(text);
            textWords.put(word, textWord);
        }

        static Matches find(CobolPreprocessor tree, List<CobolPreprocessor.Word> from, List<CobolPreprocessor.Word> to) {
            List<CobolPreprocessor.Word> words = new ArrayList<>();
            // A word leaves the tree by mapping to null, which only a list of words lets it do.
            Set<CobolPreprocessor.Word> inText = Collections.newSetFromMap(new IdentityHashMap<>());
            new CobolPreprocessorIsoVisitor<Integer>() {
                @Override
                public CobolPreprocessor.Word visitWord(CobolPreprocessor.Word word, Integer p) {
                    words.add(word);
                    Cursor parent = getCursor().getParent();
                    Object owner = parent == null ? null : parent.getValue();
                    if (owner instanceof CobolPreprocessor.CharDataLine || owner instanceof CobolPreprocessor.ExecStatement) {
                        inText.add(word);
                    }
                    return word;
                }
            }.visit(tree, 0);

            Matches matches = new Matches();
            TextWord open = null;
            int openEnd = 0;
            for (int i = 0; i < words.size(); ) {
                if (!matchesAt(words, i, from)) {
                    i++;
                    continue;
                }
                int matchEnd = i + from.size();
                int start = i;
                while (start > 0 && isJoined(words.get(start)) && isWordChar(lastChar(words.get(start - 1)))) {
                    start--;
                }
                int end = matchEnd;
                while (end < words.size() && isJoined(words.get(end)) && isWordChar(firstChar(words.get(end)))) {
                    end++;
                }

                if (start == i && end == matchEnd) {
                    matches.wholeWords.add(new ArrayList<>(words.subList(i, end)));
                } else {
                    if (to.size() != 1 || !isWord(to.get(0).getCobolWord().getWord())) {
                        throw new UnsupportedOperationException("Replacing part of a text word with anything but a single word is not supported: " +
                                pseudoText(from) + " BY " + pseudoText(to) + ".");
                    }
                    for (int j = start + 1; j < end; j++) {
                        if (!inText.contains(words.get(j))) {
                            throw new UnsupportedOperationException("Replacing part of a text word that runs into a preprocessor statement is not supported: " +
                                    pseudoText(from) + " in " + pseudoText(words.subList(start, end)) + ".");
                        }
                    }
                    // A match abutting the text word of the previous match extends it, as in `(TAG)-(TAG)`.
                    if (open == null || start >= openEnd) {
                        open = new TextWord();
                        openEnd = start;
                    }
                    for (int j = openEnd; j < i; j++) {
                        matches.add(open, words.get(j), words.get(j).getCobolWord().getWord());
                    }
                    for (int j = i; j < matchEnd; j++) {
                        matches.add(open, words.get(j), j == i ? to.get(0).getCobolWord().getWord() : "");
                    }
                    for (int j = matchEnd; j < end; j++) {
                        matches.add(open, words.get(j), words.get(j).getCobolWord().getWord());
                    }
                    openEnd = end;
                }
                i = end;
            }
            return matches;
        }

        boolean isEmpty() {
            return wholeWords.isEmpty() && textWords.isEmpty();
        }

        private static boolean isWord(String text) {
            for (int i = 0; i < text.length(); i++) {
                if (!isWordChar(text.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        private static boolean matchesAt(List<CobolPreprocessor.Word> words, int i, List<CobolPreprocessor.Word> from) {
            if (i + from.size() > words.size()) {
                return false;
            }
            for (int k = 0; k < from.size(); k++) {
                if (!words.get(i + k).getCobolWord().getWord().equals(from.get(k).getCobolWord().getWord())) {
                    return false;
                }
            }
            return true;
        }

        private static String pseudoText(List<CobolPreprocessor.Word> words) {
            StringBuilder text = new StringBuilder("==");
            for (CobolPreprocessor.Word word : words) {
                text.append(word.getPrefix().getWhitespace()).append(word.getCobolWord().getWord());
            }
            return text.append("==").toString();
        }

        /**
         * Whether the word is run together with whatever precedes it, rather than separated from it
         * by whitespace or the start of a line.
         */
        private static boolean isJoined(CobolPreprocessor.Word word) {
            return word.getPrefix().isEmpty() && word.getCobolWord().getSequenceArea() == null;
        }

        /**
         * Whether the character would run into an adjacent word rather than separate from it. A
         * period or a quotation mark abutting a match is a separator and leaves whole words on
         * either side; a letter, digit or hyphen does not.
         */
        private static boolean isWordChar(char c) {
            return Character.isLetterOrDigit(c) || c == '-' || c == '_';
        }

        private static char firstChar(CobolPreprocessor.Word word) {
            String text = word.getCobolWord().getWord();
            return text.isEmpty() ? ' ' : text.charAt(0);
        }

        private static char lastChar(CobolPreprocessor.Word word) {
            String text = word.getCobolWord().getWord();
            return text.isEmpty() ? ' ' : text.charAt(text.length() - 1);
        }
    }

    private static class ReplaceVisitor extends CobolPreprocessorIsoVisitor<ExecutionContext> {
        private final Map<String, Replacement> replaceMap;
        // A replacement rule may match multiple sets of words, but will be changed to 1 output.
        private final Map<CobolPreprocessor.Word, List<CobolPreprocessor.Word>> from;
        private final Map<CobolPreprocessor.Word, Matches.TextWord> textWords;
        private final List<CobolPreprocessor.Word> to;
        private final ReplacementType replacementType;

        private List<CobolPreprocessor.Word> current;
        private Replacement replaceReductiveType = null;
        private int fromPos = 0;
        private int toPos = 0;
        boolean inMatch = false;

        public ReplaceVisitor(Matches matches,
                              List<CobolPreprocessor.Word> to,
                              Map<String, Replacement> replaceMap) {
            this.from = new IdentityHashMap<>(matches.wholeWords.size());
            matches.wholeWords.forEach(it -> this.from.put(it.get(0), it));
            this.textWords = matches.textWords;

            this.to = to;
            this.replacementType = init();

            this.replaceMap = replaceMap;
        }

        @Override
        public CobolPreprocessor.Copybook visitCopybook(CobolPreprocessor.Copybook copybook, ExecutionContext executionContext) {
            return copybook.withLst(ListUtils.map(copybook.getLst(), it -> visit(it, executionContext)));
        }

        @Override
        public CobolPreprocessor.CopyStatement visitCopyStatement(CobolPreprocessor.CopyStatement copyStatement, ExecutionContext executionContext) {
            if (copyStatement.getCopybook() != null ) {
                copyStatement = copyStatement.withCopybook(visitCopybook(copyStatement.getCopybook(), executionContext));
            }
            return copyStatement;
        }

        @Override
        public CobolPreprocessor.Word visitWord(CobolPreprocessor.Word word, ExecutionContext executionContext) {
            Matches.TextWord textWord = textWords.get(word);
            if (textWord != null) {
                return textWord.words.get(0) == word ? replaceTextWord(word, textWord) : null;
            }

            if (ReplacementType.SINGLE_WORD == replacementType) {
                if (from.containsKey(word)) {
                    boolean isCopiedSource = isCopiedSource();
                    CobolPreprocessor.Word toWord = to.get(toPos);
                    boolean isEmpty = toWord.getCobolWord().getWord().isEmpty();
                    Replacement replacement = new Replacement(
                            randomId(),
                            Markers.EMPTY,
                            singletonList(new Replacement.OriginalWord(word.getCobolWord().withId(randomId()), isEmpty)),
                            Replacement.Type.EQUAL, isCopiedSource);
                    replaceMap.put(replacement.getId().toString(), replacement);
                    word = word.withCobolWord(word.getCobolWord().withReplacement(replacement));
                    word = word.withCobolWord(word.getCobolWord().withWord(toWord.getCobolWord().getWord()));
                }
            } else if (ReplacementType.EQUAL == replacementType) {
                if (!inMatch) {
                    if (from.containsKey(word)) {
                        inMatch = true;
                        current = from.get(word);
                        fromPos = 0;
                        toPos = 0;

                        // Marks the changed word. Unknown: Should all the words be marked instead??
                        if (!current.get(fromPos).getCobolWord().getWord().equals(to.get(toPos).getCobolWord().getWord())) {
                            boolean isCopiedSource = isCopiedSource();
                            CobolPreprocessor.Word toWord = to.get(toPos);
                            boolean isEmpty = toWord.getCobolWord().getWord().isEmpty();
                            Replacement replacement = new Replacement(
                                    randomId(),
                                    Markers.EMPTY,
                                    singletonList(new Replacement.OriginalWord(word.getCobolWord().withId(randomId()), isEmpty)),
                                    Replacement.Type.EQUAL, isCopiedSource);
                            replaceMap.put(replacement.getId().toString(), replacement);
                            word = word.withCobolWord(word.getCobolWord().withReplacement(replacement));
                            word = word.withCobolWord(word.getCobolWord().withWord(toWord.getCobolWord().getWord()));
                        }
                        fromPos++;
                        toPos++;
                    }
                } else {
                    boolean isSame = current.get(fromPos).getCobolWord().equals(word.getCobolWord());
                    if (isSame) {
                        // Marks the changed word. Unknown: Should all the words be marked instead??
                        if (!current.get(fromPos).getCobolWord().getWord().equals(to.get(toPos).getCobolWord().getWord())) {
                            boolean isCopiedSource = isCopiedSource();
                            CobolPreprocessor.Word toWord = to.get(toPos);
                            boolean isEmpty = toWord.getCobolWord().getWord().isEmpty();
                            Replacement replacement = new Replacement(
                                    randomId(),
                                    Markers.EMPTY,
                                    singletonList(new Replacement.OriginalWord(word.getCobolWord().withId(randomId()), isEmpty)),
                                    Replacement.Type.EQUAL, isCopiedSource);
                            replaceMap.put(replacement.getId().toString(), replacement);
                            word = word.withCobolWord(word.getCobolWord().withReplacement(replacement));
                            word = word.withCobolWord(word.getCobolWord().withWord(toWord.getCobolWord().getWord()));
                        }

                        if (current.size() - 1 == fromPos) {
                            inMatch = false;
                            current = null;
                            fromPos = 0;
                            toPos = 0;
                        } else {
                            fromPos++;
                            toPos++;
                        }
                    } else {
                        throw new IllegalStateException("Fix me, this should not have happened.");
                    }
                }
            } else if (ReplacementType.REDUCTIVE == replacementType) {
                if (!inMatch) {
                    boolean isCopiedSource = isCopiedSource();
                    replaceReductiveType = new Replacement(randomId(), Markers.EMPTY, new ArrayList<>(), Replacement.Type.REDUCTIVE, isCopiedSource);
                    replaceMap.put(replaceReductiveType.getId().toString(), replaceReductiveType);
                    if (from.containsKey(word)) {
                        inMatch = true;
                        current = from.get(word);
                        fromPos = 0;
                        toPos = 0;

                        // Marks the changed word. Unknown: Should all the words be marked instead??
                        if (!current.get(fromPos).getCobolWord().getWord().equals(to.get(toPos).getCobolWord().getWord())) {
                            CobolPreprocessor.Word toWord = to.get(toPos);
                            boolean isEmpty = toWord.getCobolWord().getWord().isEmpty();
                            Replacement.OriginalWord originalWord = new Replacement.OriginalWord(word.getCobolWord().withId(randomId()), isEmpty);
                            if (isEmpty) {
                                replaceReductiveType.getOriginalWords().add(originalWord);
                                word = word.withCobolWord(word.getCobolWord().withReplacement(replaceReductiveType));
                                word = word.withCobolWord(word.getCobolWord().withWord(CobolPrinterUtils.fillArea(' ', word.getCobolWord().getWord().length())));
                            } else {
                                Replacement replacement = new Replacement(
                                        randomId(),
                                        Markers.EMPTY,
                                        singletonList(originalWord),
                                        Replacement.Type.EQUAL,
                                        isCopiedSource);
                                replaceMap.put(replacement.getId().toString(), replacement);
                                word = word.withCobolWord(word.getCobolWord().withReplacement(replacement));
                                word = word.withCobolWord(word.getCobolWord().withWord(toWord.getCobolWord().getWord()));
                            }
                        }
                        fromPos++;
                        toPos++;
                    }
                } else {
                    boolean isSame = current.get(fromPos).getCobolWord().getWord().equals(word.getCobolWord().getWord());
                    if (isSame) {
                        if (toPos >= to.size()) {
                            Replacement.OriginalWord originalWord = new Replacement.OriginalWord(word.getCobolWord().withId(randomId()), true);
                            replaceReductiveType.getOriginalWords().add(originalWord);
                            word = word.withCobolWord(word.getCobolWord().withReplacement(replaceReductiveType));
                            word = word.withCobolWord(word.getCobolWord().withWord(CobolPrinterUtils.fillArea(' ', word.getCobolWord().getWord().length())));
                        }

                        // Marks the changed word. Unknown: Should all the words be marked instead??
                        else if (!current.get(fromPos).getCobolWord().getWord().equals(to.get(toPos).getCobolWord().getWord())) {
                            CobolPreprocessor.Word toWord = to.get(toPos);
                            boolean isEmpty = toWord.getCobolWord().getWord().isEmpty();
                            Replacement.OriginalWord originalWord = new Replacement.OriginalWord(word.getCobolWord().withId(randomId()), isEmpty);
                            if (isEmpty) {
                                replaceReductiveType.getOriginalWords().add(originalWord);
                                word = word.withCobolWord(word.getCobolWord().withReplacement(replaceReductiveType));
                                word = word.withCobolWord(word.getCobolWord().withWord(CobolPrinterUtils.fillArea(' ', word.getCobolWord().getWord().length())));
                            } else {
                                boolean isCopiedSource = isCopiedSource();
                                Replacement replacement = new Replacement(
                                        randomId(),
                                        Markers.EMPTY,
                                        singletonList(originalWord),
                                        Replacement.Type.EQUAL, isCopiedSource);
                                word = word.withCobolWord(word.getCobolWord().withReplacement(replacement));
                                word = word.withCobolWord(word.getCobolWord().withWord(toWord.getCobolWord().getWord()));
                            }
                        }

                        if (current.size() - 1 == fromPos) {
                            inMatch = false;
                            current = null;
                            fromPos = 0;
                            toPos = 0;
                            replaceReductiveType = null;
                        } else {
                            fromPos++;
                            toPos++;
                        }
                    } else {
                        throw new IllegalStateException("Fix me, this should not have happened.");
                    }
                }
            } else if (ReplacementType.ADDITIVE == replacementType) {
                if (!inMatch) {
                    if (from.containsKey(word)) {
                        inMatch = true;
                        current = from.get(word);
                        fromPos = 0;
                        toPos = 0;

                        // Marks the changed word. Unknown: Should all the words be marked instead??
                        if (!current.get(fromPos).getCobolWord().getWord().equals(to.get(toPos).getCobolWord().getWord())) {
                            CobolPreprocessor.Word toWord = to.get(toPos);
                            boolean isEmpty = toWord.getCobolWord().getWord().isEmpty();
                            boolean isCopiedSource = isCopiedSource();
                            Replacement replacement = new Replacement(
                                    randomId(),
                                    Markers.EMPTY,
                                    singletonList(new Replacement.OriginalWord(word.getCobolWord().withId(randomId()), isEmpty)),
                                    Replacement.Type.EQUAL, isCopiedSource);
                            replaceMap.put(replacement.getId().toString(), replacement);
                            word = word.withCobolWord(word.getCobolWord().withReplacement(replacement));
                            word = word.withCobolWord(word.getCobolWord().withWord(toWord.getCobolWord().getWord()));
                        }
                        fromPos++;
                        toPos++;
                    }
                } else {
                    if (fromPos < current.size()) {
                        boolean isSame = current.get(fromPos).getCobolWord().getWord().equals(word.getCobolWord().getWord());
                        if (isSame) {
                            // Marks the changed word. Unknown: Should all the words be marked instead??
                            if (!current.get(fromPos).getCobolWord().getWord().equals(to.get(toPos).getCobolWord().getWord())) {
                                boolean isCopiedSource = isCopiedSource();
                                CobolPreprocessor.Word toWord = to.get(toPos);
                                boolean isEmpty = toWord.getCobolWord().getWord().isEmpty();
                                Replacement replacement = new Replacement(
                                        randomId(),
                                        Markers.EMPTY,
                                        singletonList(new Replacement.OriginalWord(word.getCobolWord().withId(randomId()), isEmpty)),
                                        Replacement.Type.EQUAL, isCopiedSource);
                                replaceMap.put(replacement.getId().toString(), replacement);
                                if (word.getPrefix().isEmpty() && !toWord.getPrefix().isEmpty()) {
                                    // Add the prefix of toWord so that words are separated correctly.
                                    word = word.withPrefix(toWord.getPrefix());
                                }
                                word = word.withCobolWord(word.getCobolWord().withReplacement(replacement));
                                word = word.withCobolWord(word.getCobolWord().withWord(toWord.getCobolWord().getWord()));
                            }
                            fromPos++;
                            toPos++;
                        } else {
                            throw new IllegalStateException("Fix me, this should not have happened.");
                        }
                    } else {
                        int difference = to.size() - current.size();
                        boolean isCopiedSource = isCopiedSource();
                        List<Replacement.OriginalWord> additiveReplaces = new ArrayList<>(difference);
                        for (int i = 0; i < difference; i++) {
                            int cur = toPos + i;
                            CobolPreprocessor.Word toWord = to.get(cur);
                            Cobol.Word addedWord = new Cobol.Word(randomId(),
                                    toWord.getPrefix(),
                                    Markers.EMPTY,
                                    null,
                                    null,
                                    null,
                                    null,
                                    toWord.getCobolWord().getWord(),
                                    null,
                                    null,
                                    emptyList()
                            );

                            Replacement.OriginalWord originalWord = new Replacement.OriginalWord(addedWord, false);
                            additiveReplaces.add(originalWord);
                        }
                        Replacement replaceAdditiveType = new Replacement(randomId(), Markers.EMPTY, additiveReplaces, Replacement.Type.ADDITIVE, isCopiedSource);
                        replaceMap.put(replaceAdditiveType.getId().toString(), replaceAdditiveType);
                        word = word.withCobolWord(word.getCobolWord().withReplacement(replaceAdditiveType));

                        inMatch = false;
                        current = null;
                        fromPos = 0;
                        toPos = 0;
                    }
                }
            }

            return super.visitWord(word, executionContext);
        }

        /**
         * The original is remembered as the one word the source spells, so that it prints back for the
         * one token the replaced word becomes.
         */
        private CobolPreprocessor.Word replaceTextWord(CobolPreprocessor.Word word, Matches.TextWord textWord) {
            StringBuilder originalText = new StringBuilder();
            for (CobolPreprocessor.Word part : textWord.words) {
                if (part != word) {
                    originalText.append(part.getPrefix().getWhitespace());
                }
                originalText.append(sourceText(part.getCobolWord()));
            }
            Cobol.Word first = word.getCobolWord();
            CommentArea commentArea = textWord.words.get(textWord.words.size() - 1).getCobolWord().getCommentArea();
            Cobol.Word original = first.withId(randomId()).withWord(originalText.toString()).withCommentArea(commentArea).withReplacement(null);
            Replacement replacement = new Replacement(randomId(), Markers.EMPTY,
                    singletonList(new Replacement.OriginalWord(original, false)), Replacement.Type.EQUAL, isCopiedSource());
            replaceMap.put(replacement.getId().toString(), replacement);
            return word.withCobolWord(first.withWord(textWord.text.toString()).withCommentArea(commentArea).withReplacement(replacement));
        }

        /**
         * The text a word had in the source, before an earlier rule replaced it.
         */
        private static String sourceText(Cobol.Word word) {
            Replacement replacement = word.getReplacement();
            return replacement != null && replacement.getType() == Replacement.Type.EQUAL ?
                    replacement.getOriginalWords().get(0).getOriginal().getWord() : word.getWord();
        }

        private boolean isCopiedSource() {
            return getCursor().dropParentUntil(is -> is instanceof CobolPreprocessor.CopyStatement ||
                    is instanceof CobolPreprocessor.ReplaceArea).getValue() instanceof CobolPreprocessor.CopyStatement;
        }

        private ReplacementType init() {
            for (List<CobolPreprocessor.Word> words : from.values()) {
				if (words.size() == 1 && to.size() == 1) {
					return ReplacementType.SINGLE_WORD;
				}
				if (!words.isEmpty() && words.size() == to.size()) {
					return ReplacementType.EQUAL;
				}
				if (words.size() < to.size()) {
					return ReplacementType.ADDITIVE;
				}
				if (words.size() > to.size()) {
					return ReplacementType.REDUCTIVE;
				}
			}
            return ReplacementType.UNKNOWN;
        }

        public enum ReplacementType {
            // Single word changes are isolated for simplicity. I.E. PIC => PICTURE.
            SINGLE_WORD,
            // A multi-word replacement of equal size. I.E. MOVE "*" AO WRK-XN-00001. => MOVE "*" TO WRK-XN-00001.
            EQUAL,
            // A reduction of words. I.E. PERFORM FAIL. => ""
            REDUCTIVE,
            // An addition of words. I.E. TO => PERFORM FAIL.
            ADDITIVE,
            UNKNOWN
        }
    }

    // Collect ReplaceClauses from CopyStatement Replacing.
    @SuppressWarnings("SpellCheckingInspection")
    private Map<List<CobolPreprocessor.Word>, List<CobolPreprocessor.Word>> getReplacings(CobolPreprocessor.ReplacingPhrase replacingPhrase) {
        // The order of matched MUST be retained for sequential replacements to work.
        Map<List<CobolPreprocessor.Word>, List<CobolPreprocessor.Word>> replacements = new LinkedHashMap<>();
        for (CobolPreprocessor.ReplaceClause clause : replacingPhrase.getClauses()) {
            List<CobolPreprocessor.Word> replaceable = resolveReplacementRule(clause.getReplaceable());
            List<CobolPreprocessor.Word> replacement = resolveReplacementRule(clause.getReplacement());
            if (!replaceable.isEmpty()) {
                replacements.put(replaceable, replacement);
            }
        }
        return replacements;
    }

    // Collect ReplaceClauses from ReplaceByStatement.
    private Map<List<CobolPreprocessor.Word>, List<CobolPreprocessor.Word>> getReplacements(CobolPreprocessor.ReplaceByStatement replaceByStatement) {
        // The order of matched MUST be retained for sequential replacements to work.
        Map<List<CobolPreprocessor.Word>, List<CobolPreprocessor.Word>> replacements = new LinkedHashMap<>();
        for (CobolPreprocessor.ReplaceClause clause : replaceByStatement.getClauses()) {
            List<CobolPreprocessor.Word> replaceable = resolveReplacementRule(clause.getReplaceable());
            List<CobolPreprocessor.Word> replacement = resolveReplacementRule(clause.getReplacement());
            if (!replaceable.isEmpty()) {
                replacements.put(replaceable, replacement);
            }
        }
        return replacements;
    }

    /**
     * Generic resolve method to interpret the CobolPreprocessor object and generate the replacement rule.
     */
    private List<CobolPreprocessor.Word> resolveReplacementRule(CobolPreprocessor cobolPreprocessor) {
        List<CobolPreprocessor.Word> words = new ArrayList<>();
        CobolPreprocessorWordVisitor wordVisitor = new CobolPreprocessorWordVisitor();

        if (cobolPreprocessor instanceof CobolPreprocessor.PseudoText) {
            CobolPreprocessor.PseudoText pseudoText = (CobolPreprocessor.PseudoText) cobolPreprocessor;
            if (pseudoText.getCharData() != null) {
                wordVisitor.visit(pseudoText.getCharData(), words);
            } else {
                words.add(new CobolPreprocessor.Word(
                        EMPTY,
                        Markers.EMPTY,
                        new Cobol.Word(randomId(),
                                EMPTY,
                                Markers.EMPTY,
                                null,
                                null,
                                null,
                                null,
                                "",
                                null,
                                null,
                                emptyList()
                        )
                ));
            }
        } else if (cobolPreprocessor instanceof CobolPreprocessor.CharDataLine) {
            CobolPreprocessor.CharDataLine charDataLine = (CobolPreprocessor.CharDataLine) cobolPreprocessor;
            wordVisitor.visit(charDataLine, words);
        } else if (cobolPreprocessor instanceof CobolPreprocessor.Word) {
            CobolPreprocessor.Word word = (CobolPreprocessor.Word) cobolPreprocessor;
            wordVisitor.visit(word, words);
        } else {
            throw new UnsupportedOperationException("Implement me.");
        }
        return words;
    }

    private static class CobolPreprocessorWordVisitor extends CobolPreprocessorIsoVisitor<List<CobolPreprocessor.Word>> {
        @Override
        public CobolPreprocessor.Word visitWord(CobolPreprocessor.Word word, List<CobolPreprocessor.Word> words) {
            words.add(word);
            return super.visitWord(word, words);
        }
    }
}
