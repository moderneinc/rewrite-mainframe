/*
* Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
* All rights reserved.
*
* This software may be modified and distributed under the terms
* of the MIT license. See the LICENSE file for details.
*/

/*
* COBOL Grammar for ANTLR4
*
* This is a COBOL grammar, which is part of the COBOL parser at
* https://github.com/uwol/proleap-cobol-parser.
*
* The grammar passes the NIST test suite and has successfully been applied to
* numerous COBOL files from banking and insurance. To be used in conjunction
* with the provided preprocessor, which executes COPY and REPLACE statements.
*/

parser grammar CobolParser;

options { tokenVocab = CobolLexer; }

@members {
    private static IntervalSet statementStart;

    /**
     * Whether the next token begins a statement. A statement list whose end is optional, the body of an IF
     * without END-IF say, takes every statement that follows: COBOL's rule, and SLL prediction's greedy choice.
     * Without the predicate on the list's exit SLL cannot rule out leaving the statement to an enclosing list,
     * and retries with full context, which scans ahead as far as the sentence period. The predicates on the
     * other optional endings, ELSE and END-IF say, settle the same question for the keyword.
     */
    boolean startsStatement() {
        if (statementStart == null) {
            statementStart = _ATN.nextTokens(_ATN.ruleToStartState[RULE_statement]);
        }
        return statementStart.contains(_input.LA(1));
    }

    boolean startsWhenCondition() {
        return _input.LA(1) == WHEN && _input.LA(2) != OTHER;
    }

    /**
     * Whether the next token opens a subscript group. A reference modifier opens with the same parenthesis and
     * is told apart only by the colon at its top level, so the group is scanned to its closing parenthesis.
     * A parenthesis after a name always belongs to the name, so the only other question at that point is
     * which of the two it opens.
     */
    boolean startsSubscripts() {
        if (_input.LA(1) != LPARENCHAR) {
            return false;
        }
        int depth = 0;
        for (int i = 1; ; i++) {
            int type = _input.LA(i);
            if (type == Token.EOF) {
                return false;
            } else if (type == LPARENCHAR) {
                depth++;
            } else if (type == RPARENCHAR && --depth == 0) {
                return true;
            } else if (type == COLONCHAR && depth == 1) {
                return false;
            }
        }
    }

    /**
     * Whether the conditional phrase the word names starts here, past an optional AT or ON. A statement takes the
     * phrases and the END- terminator that follow it before an enclosing statement can, as statement lists do at
     * startsStatement. The context parameter, unused, makes ANTLR treat these predicates as context-dependent, so
     * SLL evaluates each only in its own statement's decisions and the predicates of nested statements never combine.
     */
    boolean startsPhrase(ParserRuleContext statement, int word) {
        return phraseWord(1) == word;
    }

    boolean startsNotPhrase(ParserRuleContext statement, int word) {
        return _input.LA(1) == NOT && phraseWord(2) == word;
    }

    boolean ahead(ParserRuleContext statement, int type) {
        return _input.LA(1) == type;
    }

    boolean ahead(ParserRuleContext statement, int first, int second) {
        return _input.LA(1) == first && _input.LA(2) == second;
    }

    /**
     * Whether the word ahead is one a JSON or XML statement reads as a keyword, though it names data elsewhere.
     */
    boolean jsonXmlKeywordAhead(ParserRuleContext statement) {
        switch (_input.LA(1)) {
            case NAME: case NAMESPACE: case NAMESPACE_PREFIX: case ATTRIBUTE: case ATTRIBUTES: case ELEMENT:
            case NONNUMERIC: case XML_DECLARATION:
                return true;
            default:
                return false;
        }
    }

    /**
     * Whether WHEN and a figurative constant follow, which is how a SUPPRESS phrase names its condition.
     */
    boolean suppressWhenAhead(ParserRuleContext phrase) {
        if (_input.LA(1) != WHEN) {
            return false;
        }
        switch (_input.LA(2)) {
            case ZERO: case ZEROS: case ZEROES: case SPACE: case SPACES:
            case LOW_VALUE: case LOW_VALUES: case HIGH_VALUE: case HIGH_VALUES:
                return true;
            default:
                return false;
        }
    }

    /**
     * Whether the next word opens another ALL, LEADING or FIRST group of an INSPECT phrase. The operands of one
     * group follow its word without repeating it, and INSPECT forbids a figurative constant beginning with ALL
     * among them, so the word after an operand always opens a group.
     */
    boolean opensInspectGroup(ParserRuleContext group) {
        int type = _input.LA(1);
        return type == ALL || type == LEADING || type == FIRST;
    }

    private int phraseWord(int i) {
        int type = _input.LA(i);
        return type == AT || type == ON ? _input.LA(i + 1) : type;
    }

    static final int OPERAND = 1, EXPRESSION = 2, CONDITION = 3;
    private static IntervalSet operandStart, cobolWordStart, integerLiteralStart, byReferenceStart, byValueStart;

    private IntervalSet firstOf(int rule) {
        return _ATN.nextTokens(_ATN.ruleToStartState[rule]);
    }

    /**
     * What the tokens from LT(i) form before the construct around them goes on: a single operand, an identifier or
     * a literal, an arithmetic expression of several operands, or a condition, which a comparison, class or sign
     * word follows. The rules whose alternatives are one of these each (evaluateSelect, evaluateCondition, the
     * abbreviated combined condition) take the alternative this names.
     */
    int operandKind(ParserRuleContext rule, int i) {
        if (_input.LA(i) == LPARENCHAR && conditionInside(i)) {
            return CONDITION;
        }
        int end = afterExpression(i);
        if (end == i) {
            return 0;
        }
        int next = _input.LA(end);
        if (conditionFollows(next)) {
            return CONDITION;
        }
        boolean operand = afterOperand(i) == end;
        if ((next == AND || next == OR) && operand && cobolWordStart().contains(_input.LA(i))) {
            return CONDITION;
        }
        return operand ? OPERAND : EXPRESSION;
    }

    private IntervalSet cobolWordStart() {
        if (cobolWordStart == null) {
            cobolWordStart = firstOf(RULE_cobolWord);
        }
        return cobolWordStart;
    }

    private int afterExpression(int i) {
        int end = i;
        while (true) {
            int j = i;
            if (_input.LA(j) == PLUSCHAR || _input.LA(j) == MINUSCHAR) {
                j++;
            }
            if (_input.LA(j) == LPARENCHAR) {
                j = afterParens(j);
            } else {
                int k = afterOperand(j);
                if (k == j) {
                    return end;
                }
                j = k;
            }
            end = j;
            if (!isArithmeticOperator(_input.LA(j))) {
                return end;
            }
            i = j + 1;
        }
    }

    private int afterOperand(int i) {
        if (operandStart == null) {
            operandStart = firstOf(RULE_identifier).or(firstOf(RULE_literal));
        }
        int type = _input.LA(i);
        if (type == FUNCTION) {
            i += 2;
        } else if ((type == LENGTH || type == ADDRESS) && _input.LA(i + 1) == OF) {
            return afterOperand(i + 2);
        } else if (type == ALL) {
            return afterOperand(i + 1);
        } else if (operandStart.contains(type)) {
            i++;
        } else {
            return i;
        }
        while (true) {
            type = _input.LA(i);
            if (type == IN || type == OF) {
                i += 2;
            } else if (type == LPARENCHAR) {
                i = afterParens(i);
            } else {
                return i;
            }
        }
    }

    private int afterParens(int i) {
        for (int depth = 0; ; i++) {
            int type = _input.LA(i);
            if (type == Token.EOF) {
                return i;
            } else if (type == LPARENCHAR) {
                depth++;
            } else if (type == RPARENCHAR && --depth == 0) {
                return i + 1;
            }
        }
    }

    private boolean conditionInside(int i) {
        for (int depth = 0; ; i++) {
            int type = _input.LA(i);
            if (type == Token.EOF) {
                return false;
            } else if (type == LPARENCHAR) {
                depth++;
            } else if (type == RPARENCHAR) {
                if (--depth == 0) {
                    return false;
                }
            } else if (type == AND || type == OR || isComparison(type)) {
                return true;
            }
        }
    }

    private boolean conditionFollows(int type) {
        return isComparison(type) || type == POSITIVE || type == NEGATIVE || type == ZERO;
    }

    private static boolean isComparison(int type) {
        switch (type) {
            case IS: case ARE: case NOT: case GREATER: case LESS: case EQUAL: case MORETHANCHAR: case LESSTHANCHAR:
            case EQUALCHAR: case NOTEQUALCHAR: case MORETHANOREQUAL: case LESSTHANOREQUAL:
            case NUMERIC: case ALPHABETIC: case ALPHABETIC_LOWER: case ALPHABETIC_UPPER: case DBCS: case KANJI:
                return true;
            default:
                return false;
        }
    }

    private static boolean isArithmeticOperator(int type) {
        return type == PLUSCHAR || type == MINUSCHAR || type == ASTERISKCHAR || type == SLASHCHAR || type == DOUBLEASTERISKCHAR;
    }

    boolean signAhead(ParserRuleContext rule) {
        return _input.LA(1) == PLUSCHAR || _input.LA(1) == MINUSCHAR;
    }

    boolean startsIntegerLiteral(ParserRuleContext rule) {
        if (integerLiteralStart == null) {
            integerLiteralStart = firstOf(RULE_integerLiteral);
        }
        return integerLiteralStart.contains(_input.LA(1));
    }

    /**
     * Whether the subscript ahead is a name, with qualifiers and a trailing signed integer, or an integer, and
     * then the subscript list goes on. Such a subscript is not an arithmetic expression.
     */
    boolean plainSubscriptAhead(ParserRuleContext subscript) {
        int i = 1;
        if (startsIntegerLiteral(subscript)) {
            i = 2;
        } else if (cobolWordStart().contains(_input.LA(1))) {
            i = 2;
            while (_input.LA(i) == IN || _input.LA(i) == OF) {
                i += 2;
            }
            if (integerLiteralStart.contains(_input.LA(i))) {
                i++;
            }
        } else {
            return false;
        }
        int next = _input.LA(i);
        return next == RPARENCHAR || next == COMMACHAR || next == ALL || cobolWordStart().contains(next) ||
               integerLiteralStart.contains(next);
    }

    /**
     * Whether AND or OR is followed by a full condition, which the condition takes before an abbreviation can.
     */
    boolean combinableAhead(ParserRuleContext andOr) {
        int i = _input.LA(1) == NOT ? 2 : 1;
        int kind = operandKind(andOr, i);
        return kind == CONDITION || kind == OPERAND && cobolWordStart().contains(_input.LA(i));
    }

    /**
     * Whether GIVING follows the operand after INTO, which makes a DIVIDE INTO GIVING rather than a DIVIDE INTO
     * with that operand as its first quotient.
     */
    boolean givingAhead(ParserRuleContext divide) {
        return _input.LA(afterOperand(2)) == GIVING;
    }

    boolean prefixAhead(ParserRuleContext parameter) {
        int type = _input.LA(1);
        return type == ADDRESS && _input.LA(2) == OF || type == LENGTH || type == INTEGER || type == STRING;
    }

    boolean startsByReference(ParserRuleContext phrase) {
        if (byReferenceStart == null) {
            byReferenceStart = firstOf(RULE_procedureDivisionByReference);
        }
        return byReferenceStart.contains(_input.LA(1));
    }

    boolean startsByValue(ParserRuleContext phrase) {
        if (byValueStart == null) {
            byValueStart = firstOf(RULE_procedureDivisionByValue);
        }
        return byValueStart.contains(_input.LA(1));
    }

    boolean namedFunctionAhead(ParserRuleContext name) {
        switch (_input.LA(1)) {
            case INTEGER: case LENGTH: case RANDOM: case SUM: case WHEN_COMPILED:
                return true;
            default:
                return false;
        }
    }
}

// A batch is read flat and CobolParserVisitor nests its programs. Nesting them here would put a predicate on the
// FOLLOW path of every division rule.
compilationUnit
   : (programUnit | endProgramStatement)* EOF
   ;

programUnit
   : identificationDivision environmentDivision? dataDivision? procedureDivision?
   ;

endProgramStatement
   : END PROGRAM programName DOT_FS
   ;

// --- identification division --------------------------------------------------------------------

identificationDivision
   : (IDENTIFICATION | ID) DIVISION DOT_FS programIdParagraph identificationDivisionBody*
   ;

identificationDivisionBody
   : authorParagraph | installationParagraph | dateWrittenParagraph | dateCompiledParagraph | securityParagraph | remarksParagraph
   ;

// - program id paragraph ----------------------------------

programIdParagraph
   : PROGRAM_ID DOT_FS programName (IS? (COMMON | INITIAL | LIBRARY | DEFINITION | RECURSIVE) PROGRAM?)? DOT_FS? commentEntry?
   ;

// - author paragraph ----------------------------------

authorParagraph
   : AUTHOR DOT_FS commentEntry?
   ;

// - installation paragraph ----------------------------------

installationParagraph
   : INSTALLATION DOT_FS commentEntry?
   ;

// - date written paragraph ----------------------------------

dateWrittenParagraph
   : DATE_WRITTEN DOT_FS commentEntry?
   ;

// - date compiled paragraph ----------------------------------

dateCompiledParagraph
   : DATE_COMPILED DOT_FS commentEntry?
   ;

// - security paragraph ----------------------------------

securityParagraph
   : SECURITY DOT_FS commentEntry?
   ;

// - remarks paragraph ----------------------------------

remarksParagraph
   : REMARKS DOT_FS commentEntry? END_REMARKS? DOT_FS?
   ;

// --- environment division --------------------------------------------------------------------

environmentDivision
   : ENVIRONMENT DIVISION DOT_FS environmentDivisionBody*
   ;

environmentDivisionBody
   : configurationSection | specialNamesParagraph | inputOutputSection
   ;

// -- configuration section ----------------------------------

configurationSection
   : CONFIGURATION SECTION DOT_FS configurationSectionParagraph*
   ;

// - configuration section paragraph ----------------------------------

configurationSectionParagraph
   : sourceComputerParagraph | objectComputerParagraph | specialNamesParagraph | repositoryParagraph
   // strictly, specialNamesParagraph does not belong into configurationSectionParagraph, but IBM-COBOL allows this
   ;

// - source computer paragraph ----------------------------------

sourceComputerParagraph
   : SOURCE_COMPUTER DOT_FS (computerName (WITH? DEBUGGING MODE)? DOT_FS)?
   ;

// - object computer paragraph ----------------------------------

objectComputerParagraph
   : OBJECT_COMPUTER DOT_FS (computerName objectComputerClause* DOT_FS)?
   ;

objectComputerClause
   : memorySizeClause | diskSizeClause | collatingSequenceClause | segmentLimitClause | characterSetClause
   ;

memorySizeClause
   : MEMORY SIZE? (integerLiteral | cobolWord) (WORDS | CHARACTERS | MODULES)?
   ;

diskSizeClause
   : DISK SIZE? IS? (integerLiteral | cobolWord) (WORDS | MODULES)?
   ;

collatingSequenceClause
   : PROGRAM? COLLATING? SEQUENCE (IS? alphabetName+) collatingSequenceClauseAlphanumeric? collatingSequenceClauseNational?
   ;

collatingSequenceClauseAlphanumeric
   : FOR? ALPHANUMERIC IS? alphabetName
   ;

collatingSequenceClauseNational
   : FOR? NATIONAL IS? alphabetName
   ;

segmentLimitClause
   : SEGMENT_LIMIT IS? integerLiteral
   ;

characterSetClause
   : CHARACTER SET DOT_FS
   ;

// - special names paragraph ----------------------------------

specialNamesParagraph
   : SPECIAL_NAMES DOT_FS (specialNameClause+ DOT_FS)?
   ;

specialNameClause
   : channelClause | odtClause | alphabetClause | classClause | currencySignClause | decimalPointClause | symbolicCharactersClause | environmentSwitchNameClause | defaultDisplaySignClause | defaultComputationalSignClause | reserveNetworkClause
   ;

alphabetClause
   : alphabetClauseFormat1 | alphabetClauseFormat2
   ;

alphabetClauseFormat1
   : ALPHABET alphabetName (FOR ALPHANUMERIC)? IS? (EBCDIC | ASCII | STANDARD_1 | STANDARD_2 | NATIVE | cobolWord | alphabetLiterals+)
   ;

alphabetLiterals
   : literal (alphabetThrough | alphabetAlso+)?
   ;

alphabetThrough
   : (THROUGH | THRU) literal
   ;

alphabetAlso
   : ALSO literal+
   ;

alphabetClauseFormat2
   : ALPHABET alphabetName FOR? NATIONAL IS? (NATIVE | CCSVERSION literal)
   ;

channelClause
   : CHANNEL integerLiteral IS? mnemonicName
   ;

classClause
   : CLASS className (FOR? (ALPHANUMERIC | NATIONAL))? IS? classClauseThrough+
   ;

classClauseThrough
   : classClauseFrom ((THROUGH | THRU) classClauseTo)?
   ;

classClauseFrom
   : identifier | literal
   ;

classClauseTo
   : identifier | literal
   ;

currencySignClause
   : CURRENCY SIGN? IS? literal (WITH? PICTURE SYMBOL literal)?
   ;

decimalPointClause
   : DECIMAL_POINT IS? COMMA
   ;

defaultComputationalSignClause
   : DEFAULT (COMPUTATIONAL | COMP)? (SIGN IS?)? (LEADING | TRAILING)? (SEPARATE CHARACTER?)
   ;

defaultDisplaySignClause
   : DEFAULT_DISPLAY (SIGN IS?)? (LEADING | TRAILING) (SEPARATE CHARACTER?)?
   ;

environmentSwitchNameClause
   : environmentName IS? mnemonicName environmentSwitchNameSpecialNamesStatusPhrase? | environmentSwitchNameSpecialNamesStatusPhrase
   ;

environmentSwitchNameSpecialNamesStatusPhrase
   : ON STATUS? IS? condition (OFF STATUS? IS? condition)? | OFF STATUS? IS? condition (ON STATUS? IS? condition)?
   ;

odtClause
   : ODT IS? mnemonicName
   ;

reserveNetworkClause
   : RESERVE WORDS? LIST? IS? NETWORK CAPABLE?
   ;

symbolicCharactersClause
   : SYMBOLIC CHARACTERS? (FOR? (ALPHANUMERIC | NATIONAL))? symbolicCharacters+ (IN alphabetName)?
   ;

symbolicCharacters
   : symbolicCharacter+ (IS | ARE)? integerLiteral+
   ;

// - repository paragraph ----------------------------------

repositoryParagraph
   : REPOSITORY DOT_FS (repositoryEntry+ DOT_FS)?
   ;

repositoryEntry
   : FUNCTION ALL INTRINSIC
   | (CLASS | INTERFACE | FUNCTION | PROGRAM) cobolWord (IS? literal)?
   ;

// -- input output section ----------------------------------

inputOutputSection
   : INPUT_OUTPUT SECTION DOT_FS inputOutputSectionParagraph*
   ;

// - input output section paragraph ----------------------------------

inputOutputSectionParagraph
   : fileControlParagraph | ioControlParagraph
   ;

// - file control paragraph ----------------------------------

fileControlParagraph
   : FILE_CONTROL (DOT_FS? fileControlEntry)* DOT_FS
   ;

fileControlEntry
   : selectClause fileControlClause*
   ;

selectClause
   : SELECT OPTIONAL? fileName
   ;

fileControlClause
   : assignClause | reserveClause | organizationClause | paddingCharacterClause | recordDelimiterClause | accessModeClause | recordKeyClause | alternateRecordKeyClause | fileStatusClause | passwordClause | relativeKeyClause
   ;

assignClause
   : ASSIGN TO? (DISK | DISPLAY | KEYBOARD | PORT | PRINTER | READER | REMOTE | TAPE | VIRTUAL | (DYNAMIC | EXTERNAL)? assignmentName | literal)
   ;

reserveClause
   : RESERVE (NO | integerLiteral) ALTERNATE? (AREA | AREAS)?
   ;

organizationClause
   : (ORGANIZATION IS?)? (LINE | RECORD BINARY | RECORD | BINARY)? (SEQUENTIAL | RELATIVE | INDEXED)
   ;

paddingCharacterClause
   : PADDING CHARACTER? IS? (qualifiedDataName | literal)
   ;

recordDelimiterClause
   : RECORD DELIMITER IS? (STANDARD_1 | IMPLICIT | assignmentName)
   ;

accessModeClause
   : ACCESS MODE? IS? (SEQUENTIAL | RANDOM | DYNAMIC | EXCLUSIVE)
   ;

recordKeyClause
   : RECORD KEY? IS? qualifiedDataName passwordClause? (WITH? DUPLICATES)?
   ;

alternateRecordKeyClause
   : ALTERNATE RECORD KEY? IS? qualifiedDataName passwordClause? (WITH? DUPLICATES)?
   ;

passwordClause
   : PASSWORD IS? dataName
   ;

fileStatusClause
   : FILE? STATUS IS? qualifiedDataName qualifiedDataName?
   ;

relativeKeyClause
   : RELATIVE KEY? IS? qualifiedDataName
   ;

// - io control paragraph ----------------------------------

ioControlParagraph
   : I_O_CONTROL DOT_FS (fileName DOT_FS)? (ioControlClause* DOT_FS)?
   ;

ioControlClause
   : rerunClause | sameClause | multipleFileClause | commitmentControlClause
   ;

rerunClause
   : RERUN (ON (assignmentName | fileName))? EVERY (rerunEveryRecords | rerunEveryOf | rerunEveryClock)
   ;

rerunEveryRecords
   : integerLiteral RECORDS
   ;

rerunEveryOf
   : END? OF? (REEL | UNIT) OF fileName
   ;

rerunEveryClock
   : integerLiteral CLOCK_UNITS?
   ;

sameClause
   : SAME (RECORD | SORT | SORT_MERGE)? AREA? FOR? fileName+
   ;

multipleFileClause
   : MULTIPLE FILE TAPE? CONTAINS? multipleFilePosition+
   ;

multipleFilePosition
   : fileName (POSITION integerLiteral)?
   ;

commitmentControlClause
   : COMMITMENT CONTROL FOR? fileName
   ;

// --- data division --------------------------------------------------------------------

dataDivision
   : DATA DIVISION DOT_FS dataDivisionSection*
   ;

dataDivisionSection
   : fileSection | dataBaseSection | workingStorageSection | linkageSection | communicationSection | localStorageSection | screenSection | reportSection | programLibrarySection
   ;

// -- file section ----------------------------------

fileSection
   : FILE SECTION DOT_FS fileDescriptionEntry*
   ;

fileDescriptionEntry
   : (FD | SD) fileName ((DOT_FS | COMMACHAR)? fileDescriptionEntryClause)* DOT_FS dataDescriptionEntry*
   ;

fileDescriptionEntryClause
   : externalClause | globalClause | blockContainsClause | recordContainsClause | labelRecordsClause | valueOfClause | dataRecordsClause | linageClause | codeSetClause | reportClause | recordingModeClause
   ;

externalClause
   : IS? EXTERNAL
   ;

globalClause
   : IS? GLOBAL
   ;

blockContainsClause
   : BLOCK CONTAINS? integerLiteral blockContainsTo? (RECORDS | CHARACTERS)?
   ;

blockContainsTo
   : TO integerLiteral
   ;

recordContainsClause
   : RECORD (recordContainsClauseFormat1 | recordContainsClauseFormat2 | recordContainsClauseFormat3)
   ;

recordContainsClauseFormat1
   : CONTAINS? integerLiteral CHARACTERS?
   ;

recordContainsClauseFormat2
   : IS? VARYING IN? SIZE? (FROM? integerLiteral recordContainsTo? CHARACTERS?)? (DEPENDING ON? qualifiedDataName)?
   ;

recordContainsClauseFormat3
   : CONTAINS? integerLiteral recordContainsTo CHARACTERS?
   ;

recordContainsTo
   : TO integerLiteral
   ;

labelRecordsClause
   : LABEL (RECORD | RECORDS) (IS | ARE)? (OMITTED | STANDARD | dataName+)
   ;

valueOfClause
   : VALUE OF valuePair+
   ;

valuePair
   : systemName IS? (qualifiedDataName | literal)
   ;

dataRecordsClause
   : DATA (RECORD | RECORDS) (IS | ARE)? dataName+
   ;

linageClause
   : LINAGE IS? (dataName | integerLiteral) LINES? linageAt*
   ;

linageAt
   : linageFootingAt | linageLinesAtTop | linageLinesAtBottom
   ;

linageFootingAt
   : WITH? FOOTING AT? (dataName | integerLiteral)
   ;

linageLinesAtTop
   : LINES? AT? TOP (dataName | integerLiteral)
   ;

linageLinesAtBottom
   : LINES? AT? BOTTOM (dataName | integerLiteral)
   ;

recordingModeClause
   : RECORDING MODE? IS? modeStatement
   ;

modeStatement
   : cobolWord
   ;

codeSetClause
   : CODE_SET IS? alphabetName
   ;

reportClause
   : (REPORT | REPORTS) (IS | ARE)? reportName+
   ;

// -- data base section ----------------------------------

dataBaseSection
   : DATA_BASE SECTION DOT_FS dataBaseSectionEntry*
   ;

dataBaseSectionEntry
   : integerLiteral literal INVOKE literal
   ;

// -- working storage section ----------------------------------

workingStorageSection
   : WORKING_STORAGE SECTION DOT_FS dataDescriptionEntry*
   ;

// -- linkage section ----------------------------------

linkageSection
   : LINKAGE SECTION DOT_FS dataDescriptionEntry*
   ;

// -- communication section ----------------------------------

communicationSection
   : COMMUNICATION SECTION DOT_FS (communicationDescriptionEntry | dataDescriptionEntry)*
   ;

communicationDescriptionEntry
   : communicationDescriptionEntryFormat1 | communicationDescriptionEntryFormat2 | communicationDescriptionEntryFormat3
   ;

communicationDescriptionEntryFormat1
   : CD cdName FOR? INITIAL? INPUT ((symbolicQueueClause | symbolicSubQueueClause | messageDateClause | messageTimeClause | symbolicSourceClause | textLengthClause | endKeyClause | statusKeyClause | messageCountClause) | dataDescName)* DOT_FS
   ;

communicationDescriptionEntryFormat2
   : CD cdName FOR? OUTPUT (destinationCountClause | textLengthClause | statusKeyClause | destinationTableClause | errorKeyClause | symbolicDestinationClause)* DOT_FS
   ;

communicationDescriptionEntryFormat3
   : CD cdName FOR? INITIAL I_O ((messageDateClause | messageTimeClause | symbolicTerminalClause | textLengthClause | endKeyClause | statusKeyClause) | dataDescName)* DOT_FS
   ;

destinationCountClause
   : DESTINATION COUNT IS? dataDescName
   ;

destinationTableClause
   : DESTINATION TABLE OCCURS integerLiteral TIMES (INDEXED BY indexName+)?
   ;

endKeyClause
   : END KEY IS? dataDescName
   ;

errorKeyClause
   : ERROR KEY IS? dataDescName
   ;

messageCountClause
   : MESSAGE? COUNT IS? dataDescName
   ;

messageDateClause
   : MESSAGE DATE IS? dataDescName
   ;

messageTimeClause
   : MESSAGE TIME IS? dataDescName
   ;

statusKeyClause
   : STATUS KEY IS? dataDescName
   ;

symbolicDestinationClause
   : SYMBOLIC? DESTINATION IS? dataDescName
   ;

symbolicQueueClause
   : SYMBOLIC? QUEUE IS? dataDescName
   ;

symbolicSourceClause
   : SYMBOLIC? SOURCE IS? dataDescName
   ;

symbolicTerminalClause
   : SYMBOLIC? TERMINAL IS? dataDescName
   ;

symbolicSubQueueClause
   : SYMBOLIC? (SUB_QUEUE_1 | SUB_QUEUE_2 | SUB_QUEUE_3) IS? dataDescName
   ;

textLengthClause
   : TEXT LENGTH IS? dataDescName
   ;

// -- local storage section ----------------------------------

localStorageSection
   : LOCAL_STORAGE SECTION DOT_FS (LD localName DOT_FS)? dataDescriptionEntry*
   ;

// -- screen section ----------------------------------

screenSection
   : SCREEN SECTION DOT_FS screenDescriptionEntry*
   ;

screenDescriptionEntry
   : INTEGERLITERAL (FILLER | screenName)? (screenDescriptionBlankClause | screenDescriptionBellClause | screenDescriptionBlinkClause | screenDescriptionEraseClause | screenDescriptionLightClause | screenDescriptionGridClause | screenDescriptionReverseVideoClause | screenDescriptionUnderlineClause | screenDescriptionSizeClause | screenDescriptionLineClause | screenDescriptionColumnClause | screenDescriptionForegroundColorClause | screenDescriptionBackgroundColorClause | screenDescriptionControlClause | screenDescriptionValueClause | screenDescriptionPictureClause | (screenDescriptionFromClause | screenDescriptionUsingClause) | screenDescriptionUsageClause | screenDescriptionBlankWhenZeroClause | screenDescriptionJustifiedClause | screenDescriptionSignClause | screenDescriptionAutoClause | screenDescriptionSecureClause | screenDescriptionRequiredClause | screenDescriptionPromptClause | screenDescriptionFullClause | screenDescriptionZeroFillClause)* DOT_FS
   ;

screenDescriptionBlankClause
   : BLANK (SCREEN | LINE)
   ;

screenDescriptionBellClause
   : BELL | BEEP
   ;

screenDescriptionBlinkClause
   : BLINK
   ;

screenDescriptionEraseClause
   : ERASE (EOL | EOS)
   ;

screenDescriptionLightClause
   : HIGHLIGHT | LOWLIGHT
   ;

screenDescriptionGridClause
   : GRID | LEFTLINE | OVERLINE
   ;

screenDescriptionReverseVideoClause
   : REVERSE_VIDEO
   ;

screenDescriptionUnderlineClause
   : UNDERLINE
   ;

screenDescriptionSizeClause
   : SIZE IS? (identifier | integerLiteral)
   ;

screenDescriptionLineClause
   : LINE (NUMBER? IS? (PLUS | PLUSCHAR | MINUSCHAR))? (identifier | integerLiteral)
   ;

screenDescriptionColumnClause
   : (COLUMN | COL) (NUMBER? IS? (PLUS | PLUSCHAR | MINUSCHAR))? (identifier | integerLiteral)
   ;

screenDescriptionForegroundColorClause
   : (FOREGROUND_COLOR | FOREGROUND_COLOUR) IS? (identifier | integerLiteral)
   ;

screenDescriptionBackgroundColorClause
   : (BACKGROUND_COLOR | BACKGROUND_COLOUR) IS? (identifier | integerLiteral)
   ;

screenDescriptionControlClause
   : CONTROL IS? identifier
   ;

screenDescriptionValueClause
   : (VALUE IS?) literal
   ;

screenDescriptionPictureClause
   : (PICTURE | PIC) IS? pictureString
   ;

screenDescriptionFromClause
   : FROM (identifier | literal) screenDescriptionToClause?
   ;

screenDescriptionToClause
   : TO identifier
   ;

screenDescriptionUsingClause
   : USING identifier
   ;

screenDescriptionUsageClause
   : (USAGE IS?) (DISPLAY | DISPLAY_1)
   ;

screenDescriptionBlankWhenZeroClause
   : BLANK WHEN? ZERO
   ;

screenDescriptionJustifiedClause
   : (JUSTIFIED | JUST) RIGHT?
   ;

screenDescriptionSignClause
   : (SIGN IS?)? (LEADING | TRAILING) (SEPARATE CHARACTER?)?
   ;

screenDescriptionAutoClause
   : AUTO | AUTO_SKIP
   ;

screenDescriptionSecureClause
   : SECURE | NO_ECHO
   ;

screenDescriptionRequiredClause
   : REQUIRED | EMPTY_CHECK
   ;

screenDescriptionPromptClause
   : PROMPT CHARACTER? IS? (identifier | literal) screenDescriptionPromptOccursClause?
   ;

screenDescriptionPromptOccursClause
   : OCCURS integerLiteral TIMES?
   ;

screenDescriptionFullClause
   : FULL | LENGTH_CHECK
   ;

screenDescriptionZeroFillClause
   : ZERO_FILL
   ;

// -- report section ----------------------------------

reportSection
   : REPORT SECTION DOT_FS reportDescription*
   ;

reportDescription
   : reportDescriptionEntry reportGroupDescriptionEntry+
   ;

reportDescriptionEntry
   : RD reportName reportDescriptionGlobalClause? (reportDescriptionPageLimitClause reportDescriptionHeadingClause? reportDescriptionFirstDetailClause? reportDescriptionLastDetailClause? reportDescriptionFootingClause?)? DOT_FS
   ;

reportDescriptionGlobalClause
   : IS? GLOBAL
   ;

reportDescriptionPageLimitClause
   : PAGE (LIMIT | LIMITS)? (IS | ARE)? integerLiteral (LINE | LINES)?
   ;

reportDescriptionHeadingClause
   : HEADING integerLiteral
   ;

reportDescriptionFirstDetailClause
   : FIRST DETAIL integerLiteral
   ;

reportDescriptionLastDetailClause
   : LAST DETAIL integerLiteral
   ;

reportDescriptionFootingClause
   : FOOTING integerLiteral
   ;

reportGroupDescriptionEntry
   : reportGroupDescriptionEntryFormat1 | reportGroupDescriptionEntryFormat2 | reportGroupDescriptionEntryFormat3
   ;

reportGroupDescriptionEntryFormat1
   : integerLiteral dataName reportGroupLineNumberClause? reportGroupNextGroupClause? reportGroupTypeClause reportGroupUsageClause? DOT_FS
   ;

reportGroupDescriptionEntryFormat2
   : integerLiteral dataName? reportGroupLineNumberClause? reportGroupUsageClause DOT_FS
   ;

reportGroupDescriptionEntryFormat3
   : integerLiteral dataName? (reportGroupPictureClause | reportGroupUsageClause | reportGroupSignClause | reportGroupJustifiedClause | reportGroupBlankWhenZeroClause | reportGroupLineNumberClause | reportGroupColumnNumberClause | (reportGroupSourceClause | reportGroupValueClause | reportGroupSumClause | reportGroupResetClause) | reportGroupIndicateClause)* DOT_FS
   ;

reportGroupBlankWhenZeroClause
   : BLANK WHEN? ZERO
   ;

reportGroupColumnNumberClause
   : COLUMN NUMBER? IS? integerLiteral
   ;

reportGroupIndicateClause
   : GROUP INDICATE?
   ;

reportGroupJustifiedClause
   : (JUSTIFIED | JUST) RIGHT?
   ;

reportGroupLineNumberClause
   : LINE? NUMBER? IS? (reportGroupLineNumberNextPage | reportGroupLineNumberPlus)
   ;

reportGroupLineNumberNextPage
   : integerLiteral (ON? NEXT PAGE)?
   ;

reportGroupLineNumberPlus
   : PLUS integerLiteral
   ;

reportGroupNextGroupClause
   : NEXT GROUP IS? (integerLiteral | reportGroupNextGroupNextPage | reportGroupNextGroupPlus)
   ;

reportGroupNextGroupPlus
   : PLUS integerLiteral
   ;

reportGroupNextGroupNextPage
   : NEXT PAGE
   ;

reportGroupPictureClause
   : (PICTURE | PIC) IS? pictureString
   ;

reportGroupResetClause
   : RESET ON? (FINAL | dataName)
   ;

reportGroupSignClause
   : SIGN IS? (LEADING | TRAILING) SEPARATE CHARACTER?
   ;

reportGroupSourceClause
   : SOURCE IS? identifier
   ;

reportGroupSumClause
   : SUM (identifier COMMACHAR?)+ (UPON (dataName COMMACHAR?)+)?
   ;

reportGroupTypeClause
   : TYPE IS? (reportGroupTypeReportHeading | reportGroupTypePageHeading | reportGroupTypeControlHeading | reportGroupTypeDetail | reportGroupTypeControlFooting | reportGroupTypePageFooting | reportGroupTypeReportFooting)
   ;

reportGroupTypeReportHeading
   : REPORT HEADING | RH
   ;

reportGroupTypePageHeading
   : PAGE HEADING | PH
   ;

reportGroupTypeControlHeading
   : (CONTROL HEADING | CH) (FINAL | dataName)
   ;

reportGroupTypeDetail
   : DETAIL | DE
   ;

reportGroupTypeControlFooting
   : (CONTROL FOOTING | CF) (FINAL | dataName)
   ;

reportGroupUsageClause
   : (USAGE IS?)? (DISPLAY | DISPLAY_1)
   ;

reportGroupTypePageFooting
   : PAGE FOOTING | PF
   ;

reportGroupTypeReportFooting
   : REPORT FOOTING | RF
   ;

reportGroupValueClause
   : VALUE IS? literal
   ;

// -- program library section ----------------------------------

programLibrarySection
   : PROGRAM_LIBRARY SECTION DOT_FS libraryDescriptionEntry*
   ;

libraryDescriptionEntry
   : libraryDescriptionEntryFormat1 | libraryDescriptionEntryFormat2
   ;

libraryDescriptionEntryFormat1
   : LD libraryName EXPORT libraryAttributeClauseFormat1? libraryEntryProcedureClauseFormat1?
   ;

libraryDescriptionEntryFormat2
   : LB libraryName IMPORT libraryIsGlobalClause? libraryIsCommonClause? (libraryAttributeClauseFormat2 | libraryEntryProcedureClauseFormat2)*
   ;

libraryAttributeClauseFormat1
   : ATTRIBUTE (SHARING IS? (DONTCARE | PRIVATE | SHAREDBYRUNUNIT | SHAREDBYALL))?
   ;

libraryAttributeClauseFormat2
   : ATTRIBUTE libraryAttributeFunction? (LIBACCESS IS? (BYFUNCTION | BYTITLE))? libraryAttributeParameter? libraryAttributeTitle?
   ;

libraryAttributeFunction
   : FUNCTIONNAME IS literal
   ;

libraryAttributeParameter
   : LIBPARAMETER IS? literal
   ;

libraryAttributeTitle
   : TITLE IS? literal
   ;

libraryEntryProcedureClauseFormat1
   : ENTRY_PROCEDURE programName libraryEntryProcedureForClause?
   ;

libraryEntryProcedureClauseFormat2
   : ENTRY_PROCEDURE programName libraryEntryProcedureForClause? libraryEntryProcedureWithClause? libraryEntryProcedureUsingClause? libraryEntryProcedureGivingClause?
   ;

libraryEntryProcedureForClause
   : FOR literal
   ;

libraryEntryProcedureGivingClause
   : GIVING dataName
   ;

libraryEntryProcedureUsingClause
   : USING libraryEntryProcedureUsingName+
   ;

libraryEntryProcedureUsingName
   : dataName | fileName
   ;

libraryEntryProcedureWithClause
   : WITH libraryEntryProcedureWithName+
   ;

libraryEntryProcedureWithName
   : localName | fileName
   ;

libraryIsCommonClause
   : IS? COMMON
   ;

libraryIsGlobalClause
   : IS? GLOBAL
   ;

// data description entry ----------------------------------

dataDescriptionEntry
   : dataDescriptionEntryFormat1 | dataDescriptionEntryFormat2 | dataDescriptionEntryFormat3 | dataDescriptionEntryExecSql
   ;

dataDescriptionEntryFormat1
   : (INTEGERLITERAL | LEVEL_NUMBER_77) (FILLER | dataName)? dataDescriptionEntryFormat1Clause* DOT_FS
   ;

dataDescriptionEntryFormat1Clause
   : dataRedefinesClause | dataIntegerStringClause | dataExternalClause | dataGlobalClause | dataTypeDefClause | dataThreadLocalClause | dataPictureClause | dataCommonOwnLocalClause | dataTypeClause | dataUsingClause | dataUsageClause | dataValueClause | dataReceivedByClause | dataOccursClause | dataSignClause | dataSynchronizedClause | dataJustifiedClause | dataBlankWhenZeroClause | dataWithLowerBoundsClause | dataAlignedClause | dataRecordAreaClause
   ;

dataDescriptionEntryFormat2
   : LEVEL_NUMBER_66 dataName dataRenamesClause DOT_FS
   ;

dataDescriptionEntryFormat3
   : LEVEL_NUMBER_88 conditionName dataValueClause DOT_FS
   ;

dataDescriptionEntryExecSql
   : EXECSQLLINE+ DOT_FS?
   ;

dataAlignedClause
   : ALIGNED
   ;

dataBlankWhenZeroClause
   : BLANK WHEN? (ZERO | ZEROS | ZEROES)
   ;

dataCommonOwnLocalClause
   : COMMON | OWN | LOCAL
   ;

dataExternalClause
   : IS? EXTERNAL (BY literal)?
   ;

dataGlobalClause
   : IS? GLOBAL
   ;

dataIntegerStringClause
   : INTEGER | STRING
   ;

dataJustifiedClause
   : (JUSTIFIED | JUST) RIGHT?
   ;

dataOccursClause
   : OCCURS (identifier | integerLiteral) dataOccursTo? TIMES? dataOccursDepending? (dataOccursSort | dataOccursIndexed)*
   ;

dataOccursTo
   : TO integerLiteral
   ;

dataOccursDepending
   : DEPENDING ON? qualifiedDataName
   ;

dataOccursSort
   : (ASCENDING | DESCENDING) KEY? IS? qualifiedDataName+
   ;

dataOccursIndexed
   : INDEXED BY? LOCAL? indexName+
   ;

dataPictureClause
   : (PICTURE | PIC) IS? pictureString
   ;

pictureString
   : (PICTURE_SYMBOLS | pictureCardinality)+
   ;

pictureCardinality
   : LPARENCHAR integerLiteral RPARENCHAR
   ;

dataReceivedByClause
   : RECEIVED? BY? (CONTENT | REFERENCE | REF)
   ;

dataRecordAreaClause
   : RECORD AREA
   ;

dataRedefinesClause
   : REDEFINES dataName
   ;

dataRenamesClause
   : RENAMES qualifiedDataName ((THROUGH | THRU) qualifiedDataName)?
   ;

dataSignClause
   : (SIGN IS?)? (LEADING | TRAILING) (SEPARATE CHARACTER?)?
   ;

dataSynchronizedClause
   : (SYNCHRONIZED | SYNC) (LEFT | RIGHT)?
   ;

dataThreadLocalClause
   : IS? THREAD_LOCAL
   ;

dataTypeClause
   : TYPE IS? (SHORT_DATE | LONG_DATE | NUMERIC_DATE | NUMERIC_TIME | LONG_TIME | (CLOB | BLOB | DBCLOB) LPARENCHAR integerLiteral RPARENCHAR)
   ;

dataTypeDefClause
   : IS? TYPEDEF
   ;

dataUsageClause
   : (USAGE IS?)? (BINARY (TRUNCATED | EXTENDED)? | BIT | COMP | COMP_1 | COMP_2 | COMP_3 | COMP_4 | COMP_5 | COMPUTATIONAL | COMPUTATIONAL_1 | COMPUTATIONAL_2 | COMPUTATIONAL_3 | COMPUTATIONAL_4 | COMPUTATIONAL_5 | CONTROL_POINT | DATE | DISPLAY | DISPLAY_1 | DOUBLE | EVENT | FUNCTION_POINTER | INDEX | KANJI | LOCK | NATIONAL | PACKED_DECIMAL | POINTER | PROCEDURE_POINTER | REAL | SQL | TASK)
   ;

dataUsingClause
   : USING (LANGUAGE | CONVENTION) OF? (cobolWord | dataName)
   ;

dataValueClause
   : (VALUE | VALUES) (IS | ARE)? (dataValueInterval COMMACHAR?)+
   ;

dataValueInterval
   : dataValueIntervalFrom dataValueIntervalTo?
   ;

dataValueIntervalFrom
   : literal | cobolWord
   ;

dataValueIntervalTo
   : (THROUGH | THRU) literal
   ;

dataWithLowerBoundsClause
   : WITH? LOWER BOUNDS
   ;

// --- procedure division --------------------------------------------------------------------

procedureDivision
   : PROCEDURE DIVISION procedureDivisionUsingClause? procedureDivisionGivingClause? DOT_FS procedureDeclaratives? procedureDivisionBody
   ;

procedureDivisionUsingClause
   : (USING | CHAINING) procedureDivisionUsingParameter+
   ;

procedureDivisionGivingClause
   : (GIVING | RETURNING) dataName
   ;

procedureDivisionUsingParameter
   : procedureDivisionByReferencePhrase | procedureDivisionByValuePhrase
   ;

procedureDivisionByReferencePhrase
   : (BY? REFERENCE)? procedureDivisionByReference+ {!startsByReference($ctx)}?
   ;

procedureDivisionByReference
   : (OPTIONAL? identifier) | ANY
   ;

procedureDivisionByValuePhrase
   : BY? VALUE procedureDivisionByValue+ {!startsByValue($ctx)}?
   ;

procedureDivisionByValue
   : identifier | literal | ANY
   ;

procedureDeclaratives
   : DECLARATIVES DOT_FS procedureDeclarative+ END DECLARATIVES DOT_FS
   ;

procedureDeclarative
   : procedureSectionHeader DOT_FS useStatement DOT_FS paragraphs
   ;

procedureSectionHeader
   : sectionName SECTION integerLiteral?
   ;

procedureDivisionBody
   : paragraphs procedureSection*
   ;

// -- procedure section ----------------------------------

procedureSection
   : procedureSectionHeader DOT_FS paragraphs
   ;

paragraphs
   : sentence* paragraph*
   ;

paragraph
   : paragraphName DOT_FS (alteredGoTo | sentence*)
   ;

sentence
   : statement* DOT_FS
   ;

imperativeStatements
   : statement* {!startsStatement()}?
   ;

statement
   : acceptStatement | addStatement | alterStatement | callStatement | cancelStatement | closeStatement | computeStatement | continueStatement | deleteStatement | disableStatement | displayStatement | divideStatement | enableStatement | entryStatement | evaluateStatement | exhibitStatement | execCicsStatement | execDliStatement | execSqlStatement | execSqlImsStatement | exitStatement | generateStatement | gobackStatement | goToStatement | ifStatement | initializeStatement | initiateStatement | inspectStatement | mergeStatement | moveStatement | multiplyStatement | nextSentenceStatement | openStatement | performStatement | purgeStatement | readStatement | receiveStatement | releaseStatement | returnStatement | rewriteStatement | searchStatement | sendStatement | serviceStatement | setStatement | sortStatement | startStatement | stopStatement | stringStatement | subtractStatement | terminateStatement | unstringStatement | writeStatement | xmlParseStatement | jsonXmlStatement | allocateStatement | freeStatement
   ;

// accept statement

acceptStatement
   : ACCEPT identifier (acceptFromDateStatement | acceptFromEscapeKeyStatement | acceptFromMnemonicStatement | acceptMessageCountStatement)? (onExceptionClause | {!startsPhrase($ctx, EXCEPTION)}?) (notOnExceptionClause | {!startsNotPhrase($ctx, EXCEPTION)}?) (END_ACCEPT | {!ahead($ctx, END_ACCEPT)}?)
   ;

acceptFromDateStatement
   : FROM (DATE YYYYMMDD? | DAY YYYYDDD? | DAY_OF_WEEK | TIME | TIMER | TODAYS_DATE MMDDYYYY? | TODAYS_NAME | YEAR | YYYYMMDD | YYYYDDD)
   ;

acceptFromMnemonicStatement
   : FROM mnemonicName
   ;

acceptFromEscapeKeyStatement
   : FROM ESCAPE KEY
   ;

acceptMessageCountStatement
   : MESSAGE? COUNT
   ;

roundable
   : identifier ROUNDED?
   ;

// add statement

addStatement
   : ADD (addToStatement | addToGivingStatement | addCorrespondingStatement) (onSizeErrorPhrase | {!startsPhrase($ctx, SIZE)}?) (notOnSizeErrorPhrase | {!startsNotPhrase($ctx, SIZE)}?) (END_ADD | {!ahead($ctx, END_ADD)}?)
   ;

addToStatement
   : addFrom+ TO addTo+
   ;

addTo
   : roundable
   ;

addToGivingStatement
   : addFrom+ (TO addToGiving+)? GIVING addGiving+
   ;

addCorrespondingStatement
   : (CORRESPONDING | CORR) identifier TO addTo
   ;

addFrom
   : identifier | literal
   ;

addToGiving
   : identifier | literal
   ;

addGiving
   : roundable
   ;

// altered go to statement

alteredGoTo
   : GO TO? DOT_FS
   ;

// alter statement

alterStatement
   : ALTER alterProceedTo+
   ;

alterProceedTo
   : procedureName TO (PROCEED TO)? procedureName
   ;

// call statement

callStatement
   : CALL (identifier | literal) callUsingPhrase? callGivingPhrase? (onOverflowPhrase | {!startsPhrase($ctx, OVERFLOW)}?) (onExceptionClause | {!startsPhrase($ctx, EXCEPTION)}?) (notOnExceptionClause | {!startsNotPhrase($ctx, EXCEPTION)}?) (END_CALL | {!ahead($ctx, END_CALL)}?)
   ;

callUsingPhrase
   : USING (callByReferenceBarePhrase callUsingParameter* | callUsingParameter+)
   ;

callUsingParameter
   : callByReferencePhrase | callByValuePhrase | callByContentPhrase
   ;

callByReferenceBarePhrase
   : callByReference+
   ;

callByReferencePhrase
   : BY? REFERENCE callByReference+
   ;

callByReference
   : ((ADDRESS OF | INTEGER | STRING | {!prefixAhead($ctx)}?) identifier | literal) | OMITTED
   ;

callByValuePhrase
   : BY? VALUE callByValue+
   ;

callByValue
   : (ADDRESS OF | LENGTH OF? | {!prefixAhead($ctx)}?) (identifier | literal)
   ;

callByContentPhrase
   : BY? CONTENT callByContent+
   ;

callByContent
   : (ADDRESS OF | LENGTH OF? | {!prefixAhead($ctx)}?) identifier | literal | OMITTED
   ;

callGivingPhrase
   : (GIVING | RETURNING) identifier
   ;

// cancel statement

cancelStatement
   : CANCEL cancelCall+
   ;

cancelCall
   : libraryName (BYTITLE | BYFUNCTION) | identifier | literal
   ;

// close statement
closeStatement
   : CLOSE closeFile+
   ;

closeFile
   : fileName (closeReelUnitStatement | closeRelativeStatement | closePortFileIOStatement)?
   ;

closeReelUnitStatement
   : (REEL | UNIT) (FOR? REMOVAL)? (WITH? (NO REWIND | LOCK))?
   ;

closeRelativeStatement
   : WITH? (NO REWIND | LOCK)
   ;

closePortFileIOStatement
   : (WITH? NO WAIT | WITH WAIT) (USING closePortFileIOUsing+)?
   ;

closePortFileIOUsing
   : closePortFileIOUsingCloseDisposition | closePortFileIOUsingAssociatedData | closePortFileIOUsingAssociatedDataLength
   ;

closePortFileIOUsingCloseDisposition
   : CLOSE_DISPOSITION OF? (ABORT | ORDERLY)
   ;

closePortFileIOUsingAssociatedData
   : ASSOCIATED_DATA (identifier | integerLiteral)
   ;

closePortFileIOUsingAssociatedDataLength
   : ASSOCIATED_DATA_LENGTH OF? (identifier | integerLiteral)
   ;

// compute statement

computeStatement
   : COMPUTE computeStore+ (EQUALCHAR | EQUAL) arithmeticExpression (onSizeErrorPhrase | {!startsPhrase($ctx, SIZE)}?) (notOnSizeErrorPhrase | {!startsNotPhrase($ctx, SIZE)}?) (END_COMPUTE | {!ahead($ctx, END_COMPUTE)}?)
   ;

computeStore
   : roundable
   ;

// continue statement

continueStatement
   : CONTINUE
   ;

// delete statement

deleteStatement
   : DELETE fileName RECORD? (invalidKeyPhrase | {!startsPhrase($ctx, INVALID)}?) (notInvalidKeyPhrase | {!startsNotPhrase($ctx, INVALID)}?) (END_DELETE | {!ahead($ctx, END_DELETE)}?)
   ;

// disable statement

disableStatement
   : DISABLE (INPUT TERMINAL? | I_O TERMINAL | OUTPUT) cdName WITH? KEY (identifier | literal)
   ;

// display statement

displayStatement
   : DISPLAY (displayOperand COMMACHAR?)+ displayAt? displayUpon? displayWith? (onExceptionClause | {!startsPhrase($ctx, EXCEPTION)}?) (notOnExceptionClause | {!startsNotPhrase($ctx, EXCEPTION)}?) (END_DISPLAY | {!ahead($ctx, END_DISPLAY)}?)
   ;

displayOperand
   : identifier | literal
   ;

displayAt
   : AT (identifier | literal)
   ;

displayUpon
   : UPON (mnemonicName | environmentName)
   ;

displayWith
   : WITH? NO ADVANCING
   ;

// divide statement

divideStatement
   : DIVIDE (identifier | literal) (divideIntoStatement | divideIntoGivingStatement | divideByGivingStatement) divideRemainder? (onSizeErrorPhrase | {!startsPhrase($ctx, SIZE)}?) (notOnSizeErrorPhrase | {!startsNotPhrase($ctx, SIZE)}?) (END_DIVIDE | {!ahead($ctx, END_DIVIDE)}?)
   ;

divideIntoStatement
   : INTO divideInto+
   ;

divideIntoGivingStatement
   : {givingAhead($ctx)}? INTO (identifier | literal) divideGivingPhrase?
   ;

divideByGivingStatement
   : BY (identifier | literal) divideGivingPhrase?
   ;

divideGivingPhrase
   : GIVING divideGiving+
   ;

divideInto
   : roundable
   ;

divideGiving
   : roundable
   ;

divideRemainder
   : REMAINDER identifier
   ;

// enable statement

enableStatement
   : ENABLE (INPUT TERMINAL? | I_O TERMINAL | OUTPUT) cdName WITH? KEY (literal | identifier)
   ;

// entry statement

entryStatement
   : ENTRY literal (USING identifier+)?
   ;

// evaluate statement

evaluateStatement
   : EVALUATE evaluateSelect evaluateAlsoSelect* evaluateWhenPhrase* (evaluateWhenOther | {_input.LA(1) != WHEN}?) (END_EVALUATE | {_input.LA(1) != END_EVALUATE}?)
   ;

evaluateSelect
   : {operandKind($ctx, 1) == OPERAND}? identifier
   | {operandKind($ctx, 1) == OPERAND}? literal
   | {operandKind($ctx, 1) == EXPRESSION}? arithmeticExpression
   | {operandKind($ctx, 1) != OPERAND && operandKind($ctx, 1) != EXPRESSION}? condition
   ;

evaluateAlsoSelect
   : ALSO evaluateSelect
   ;

evaluateWhenPhrase
   : evaluateWhen+ {!startsWhenCondition()}? imperativeStatements
   ;

evaluateWhen
   : WHEN evaluateCondition evaluateAlsoCondition*
   ;

evaluateCondition
   : ANY
   | {operandKind($ctx, _input.LA(1) == NOT ? 2 : 1) != CONDITION}? NOT? evaluateValue evaluateThrough?
   | {operandKind($ctx, _input.LA(1) == NOT ? 2 : 1) == CONDITION}? condition
   ;

evaluateThrough
   : (THROUGH | THRU) evaluateValue
   ;

evaluateAlsoCondition
   : ALSO evaluateCondition
   ;

evaluateWhenOther
   : WHEN OTHER imperativeStatements
   ;

evaluateValue
   : arithmeticExpression
   ;

// exec cics statement

execCicsStatement
   : EXECCICSLINE+ {!ahead($ctx, EXECCICSLINE)}?
   ;

// exec dli statement

execDliStatement
   : EXECDLILINE+ {!ahead($ctx, EXECDLILINE)}?
   ;

// exec sql statement

execSqlStatement
   : EXECSQLLINE+ {!ahead($ctx, EXECSQLLINE)}?
   ;

// exec sql ims statement

execSqlImsStatement
   : EXECSQLIMSLINE+ {!ahead($ctx, EXECSQLIMSLINE)}?
   ;

// exhibit statement

exhibitStatement
   : EXHIBIT NAMED? CHANGED? exhibitOperand+
   ;

exhibitOperand
   : identifier | literal
   ;

// exit statement

// Format-1 is written `paragraph-name. EXIT.`, but the name opens a paragraph rather than belonging
// to the statement: PERFORM THRU and GO TO target it, so it has to reach the LST as a paragraph.
exitStatement
   : EXIT (PROGRAM | METHOD | PERFORM CYCLE? | SECTION | PARAGRAPH)?
   ;

// generate statement

generateStatement
   : GENERATE reportName
   ;

// goback statement

gobackStatement
   : GOBACK
   ;

// goto statement

goToStatement
   : GO TO? (MORE_LABELS | procedureName+ (DEPENDING ON? identifier)?)
   ;

// if statement

ifStatement
   : IF condition ifThen (ifElse | {_input.LA(1) != ELSE}?) (END_IF | {_input.LA(1) != END_IF}?)
   ;

ifThen
   : THEN? (NEXT SENTENCE | {!ahead($ctx, NEXT)}? imperativeStatements)
   ;

ifElse
   : ELSE (NEXT SENTENCE | {!ahead($ctx, NEXT)}? imperativeStatements)
   ;

// initialize statement

initializeStatement
   : INITIALIZE identifier+ initializeValuePhrase? (THEN? initializeReplacingPhrase)? initializeDefaultPhrase?
   ;

initializeValuePhrase
   : WITH? FILLER ((ALL | initializeCategory) TO VALUE)?
   | (ALL | initializeCategory) TO VALUE
   ;

initializeDefaultPhrase
   : THEN? TO DEFAULT
   ;

initializeCategory
   : ALPHABETIC | ALPHANUMERIC | ALPHANUMERIC_EDITED | NATIONAL | NATIONAL_EDITED | NUMERIC | NUMERIC_EDITED | DBCS | EGCS
   ;

initializeReplacingPhrase
   : REPLACING initializeReplacingBy+
   ;

initializeReplacingBy
   : initializeCategory DATA? BY (identifier | literal)
   ;

// initiate statement

initiateStatement
   : INITIATE reportName+
   ;

// inspect statement

inspectStatement
   : INSPECT identifier (inspectTallyingPhrase | inspectReplacingPhrase | inspectTallyingReplacingPhrase | inspectConvertingPhrase)
   ;

inspectTallyingPhrase
   : TALLYING inspectFor+
   ;

inspectReplacingPhrase
   : REPLACING (inspectReplacingCharacters | inspectReplacingAllLeadings)+
   ;

inspectTallyingReplacingPhrase
   : TALLYING inspectFor+ inspectReplacingPhrase+
   ;

inspectConvertingPhrase
   : CONVERTING (identifier | literal) inspectTo inspectBeforeAfter*
   ;

inspectFor
   : identifier FOR (inspectCharacters | inspectAllLeadings)+
   ;

inspectCharacters
   : (CHARACTER | CHARACTERS) inspectBeforeAfter*
   ;

inspectReplacingCharacters
   : (CHARACTER | CHARACTERS) inspectBy inspectBeforeAfter*
   ;

inspectAllLeadings
   : (ALL | LEADING) inspectAllLeading ({!opensInspectGroup($ctx)}? inspectAllLeading)*
   ;

inspectReplacingAllLeadings
   : (ALL | LEADING | FIRST) inspectReplacingAllLeading ({!opensInspectGroup($ctx)}? inspectReplacingAllLeading)*
   ;

inspectAllLeading
   : (identifier | literal) inspectBeforeAfter*
   ;

inspectReplacingAllLeading
   : (identifier | literal) inspectBy inspectBeforeAfter*
   ;

inspectBy
   : BY (identifier | literal)
   ;

inspectTo
   : TO (identifier | literal)
   ;

inspectBeforeAfter
   : (BEFORE | AFTER) INITIAL? (identifier | literal)
   ;

// merge statement

mergeStatement
   : MERGE fileName mergeOnKeyClause+ mergeCollatingSequencePhrase? mergeUsing* mergeOutputProcedurePhrase? mergeGivingPhrase*
   ;

mergeOnKeyClause
   : ON? (ASCENDING | DESCENDING) KEY? qualifiedDataName+
   ;

mergeCollatingSequencePhrase
   : COLLATING? SEQUENCE IS? alphabetName+ mergeCollatingAlphanumeric? mergeCollatingNational?
   ;

mergeCollatingAlphanumeric
   : FOR? ALPHANUMERIC IS alphabetName
   ;

mergeCollatingNational
   : FOR? NATIONAL IS? alphabetName
   ;

mergeUsing
   : USING fileName+
   ;

mergeOutputProcedurePhrase
   : OUTPUT PROCEDURE IS? procedureName mergeOutputThrough?
   ;

mergeOutputThrough
   : (THROUGH | THRU) procedureName
   ;

mergeGivingPhrase
   : GIVING mergeGiving+
   ;

mergeGiving
   : fileName (LOCK | SAVE | NO REWIND | CRUNCH | RELEASE | WITH REMOVE CRUNCH)?
   ;

// move statement

moveStatement
   : MOVE (ALL | {!ahead($ctx, ALL)}?) (moveToStatement | moveCorrespondingToStatement)
   ;

moveToStatement
   : moveToSendingArea TO identifier+
   ;

moveToSendingArea
   : identifier | literal
   ;

moveCorrespondingToStatement
   : (CORRESPONDING | CORR) moveCorrespondingToSendingArea TO identifier+
   ;

moveCorrespondingToSendingArea
   : identifier
   ;

// multiply statement

multiplyStatement
   : MULTIPLY (identifier | literal) BY (multiplyRegular | multiplyGiving) (onSizeErrorPhrase | {!startsPhrase($ctx, SIZE)}?) (notOnSizeErrorPhrase | {!startsNotPhrase($ctx, SIZE)}?) (END_MULTIPLY | {!ahead($ctx, END_MULTIPLY)}?)
   ;

multiplyRegular
   : multiplyRegularOperand+
   ;

multiplyRegularOperand
   : roundable
   ;

multiplyGiving
   : multiplyGivingOperand GIVING multiplyGivingResult+
   ;

multiplyGivingOperand
   : identifier | literal
   ;

multiplyGivingResult
   : roundable
   ;

// next sentence

nextSentenceStatement
   : NEXT SENTENCE
   ;

// open statement

openStatement
   : OPEN (openInputStatement | openOutputStatement | openIOStatement | openExtendStatement)+
   ;

openInputStatement
   : INPUT openInput+
   ;

openInput
   : fileName (REVERSED | WITH? NO REWIND)?
   ;

openOutputStatement
   : OUTPUT openOutput+
   ;

openOutput
   : fileName (WITH? NO REWIND)?
   ;

openIOStatement
   : I_O fileName+
   ;

openExtendStatement
   : EXTEND fileName+
   ;

// perform statement

performStatement
   : PERFORM (performInlineStatement | performProcedureStatement)
   ;

performInlineStatement
   : performType? statement* END_PERFORM
   ;

performProcedureStatement
   : procedureName ((THROUGH | THRU) procedureName)? performType?
   ;

performType
   : performTimes | performUntil | performVarying
   ;

performTimes
   : (identifier | integerLiteral) TIMES
   ;

performUntil
   : performTestClause? UNTIL condition
   ;

performVarying
   : performTestClause performVaryingClause | performVaryingClause performTestClause?
   ;

performVaryingClause
   : VARYING performVaryingPhrase performAfter*
   ;

performVaryingPhrase
   : (identifier | literal) performFrom performBy performUntil
   ;

performAfter
   : AFTER performVaryingPhrase
   ;

performFrom
   : FROM arithmeticExpression
   ;

performBy
   : BY arithmeticExpression
   ;

performTestClause
   : WITH? TEST (BEFORE | AFTER)
   ;

// purge statement

purgeStatement
   : PURGE cdName+
   ;

// read statement

readStatement
   : READ fileName NEXT? RECORD? readInto? readWith? readKey? (invalidKeyPhrase | {!startsPhrase($ctx, INVALID)}?) (notInvalidKeyPhrase | {!startsNotPhrase($ctx, INVALID)}?) (atEndPhrase | {!startsPhrase($ctx, END)}?) (notAtEndPhrase | {!startsNotPhrase($ctx, END)}?) (END_READ | {!ahead($ctx, END_READ)}?)
   ;

readInto
   : INTO identifier
   ;

readWith
   : WITH? ((KEPT | NO) LOCK | WAIT)
   ;

readKey
   : KEY IS? qualifiedDataName
   ;

// receive statement

receiveStatement
   : RECEIVE (receiveFromStatement | receiveIntoStatement) (onExceptionClause | {!startsPhrase($ctx, EXCEPTION)}?) (notOnExceptionClause | {!startsNotPhrase($ctx, EXCEPTION)}?) (END_RECEIVE | {!ahead($ctx, END_RECEIVE)}?)
   ;

receiveFromStatement
   : dataName FROM receiveFrom (receiveBefore | receiveWith | receiveThread | receiveSize | receiveStatus)*
   ;

receiveFrom
   : THREAD dataName | LAST THREAD | ANY THREAD
   ;

receiveIntoStatement
   : cdName (MESSAGE | SEGMENT) INTO? identifier (receiveNoData | {!ahead($ctx, NO, DATA)}?) (receiveWithData | {!ahead($ctx, WITH, DATA)}?)
   ;

receiveNoData
   : NO DATA imperativeStatements
   ;

receiveWithData
   : WITH DATA imperativeStatements
   ;

receiveBefore
   : BEFORE TIME? (numericLiteral | identifier)
   ;

receiveWith
   : WITH? NO WAIT
   ;

receiveThread
   : THREAD IN? dataName
   ;

receiveSize
   : SIZE IN? (numericLiteral | identifier)
   ;

receiveStatus
   : STATUS IN? (identifier)
   ;

// release statement

releaseStatement
   : RELEASE recordName (FROM qualifiedDataName)?
   ;

// return statement

returnStatement
   : RETURN fileName RECORD? returnInto? atEndPhrase (notAtEndPhrase | {!startsNotPhrase($ctx, END)}?) (END_RETURN | {!ahead($ctx, END_RETURN)}?)
   ;

returnInto
   : INTO qualifiedDataName
   ;

// rewrite statement

rewriteStatement
   : REWRITE recordName rewriteFrom? (invalidKeyPhrase | {!startsPhrase($ctx, INVALID)}?) (notInvalidKeyPhrase | {!startsNotPhrase($ctx, INVALID)}?) (END_REWRITE | {!ahead($ctx, END_REWRITE)}?)
   ;

rewriteFrom
   : FROM identifier
   ;

// search statement

searchStatement
   : SEARCH ALL? qualifiedDataName searchVarying? (atEndPhrase | {!startsPhrase($ctx, END)}?) searchWhen+ {_input.LA(1) != WHEN}? (END_SEARCH | {_input.LA(1) != END_SEARCH}?)
   ;

searchVarying
   : VARYING qualifiedDataName
   ;

searchWhen
   : WHEN condition (NEXT SENTENCE | imperativeStatements)
   ;

// send statement

sendStatement
   : SEND (sendStatementSync | sendStatementAsync) (onExceptionClause | {!startsPhrase($ctx, EXCEPTION)}?) (notOnExceptionClause | {!startsNotPhrase($ctx, EXCEPTION)}?)
   ;

sendStatementSync
   : (identifier | literal) sendFromPhrase? sendWithPhrase? sendReplacingPhrase? sendAdvancingPhrase?
   ;

sendStatementAsync
   : TO (TOP | BOTTOM) identifier
   ;

sendFromPhrase
   : FROM identifier
   ;

sendWithPhrase
   : WITH (EGI | EMI | ESI | identifier)
   ;

sendReplacingPhrase
   : REPLACING LINE?
   ;

sendAdvancingPhrase
   : (BEFORE | AFTER) ADVANCING? (sendAdvancingPage | sendAdvancingLines | sendAdvancingMnemonic)
   ;

sendAdvancingPage
   : PAGE
   ;

sendAdvancingLines
   : (identifier | literal) (LINE | LINES)?
   ;

sendAdvancingMnemonic
   : mnemonicName
   ;

// service statement

serviceStatement
   : SERVICE (LABEL | RELOAD identifier)
   ;

// set statement

setStatement
   : SET (setToStatement+ | setUpDownByStatement)
   ;

setToStatement
   : setTo+ TO setToValue+
   ;

setUpDownByStatement
   : setTo+ (UP BY | DOWN BY) setByValue
   ;

setTo
   : identifier
   ;

setToValue
   : ON | OFF | ENTRY (identifier | literal) | identifier | literal
   ;

setByValue
   : identifier | literal
   ;

// sort statement

sortStatement
   : SORT fileName sortOnKeyClause+ sortDuplicatesPhrase? sortCollatingSequencePhrase? sortInputProcedurePhrase? sortUsing* sortOutputProcedurePhrase? sortGivingPhrase*
   ;

sortOnKeyClause
   : ON? (ASCENDING | DESCENDING) KEY? qualifiedDataName+
   ;

sortDuplicatesPhrase
   : WITH? DUPLICATES IN? ORDER?
   ;

sortCollatingSequencePhrase
   : COLLATING? SEQUENCE IS? alphabetName+ sortCollatingAlphanumeric? sortCollatingNational?
   ;

sortCollatingAlphanumeric
   : FOR? ALPHANUMERIC IS alphabetName
   ;

sortCollatingNational
   : FOR? NATIONAL IS? alphabetName
   ;

sortInputProcedurePhrase
   : INPUT PROCEDURE IS? procedureName sortInputThrough?
   ;

sortInputThrough
   : (THROUGH | THRU) procedureName
   ;

sortUsing
   : USING fileName+
   ;

sortOutputProcedurePhrase
   : OUTPUT PROCEDURE IS? procedureName sortOutputThrough?
   ;

sortOutputThrough
   : (THROUGH | THRU) procedureName
   ;

sortGivingPhrase
   : GIVING sortGiving+
   ;

sortGiving
   : fileName (LOCK | SAVE | NO REWIND | CRUNCH | RELEASE | WITH REMOVE CRUNCH)?
   ;

// start statement

startStatement
   : START fileName startKey? (invalidKeyPhrase | {!startsPhrase($ctx, INVALID)}?) (notInvalidKeyPhrase | {!startsNotPhrase($ctx, INVALID)}?) (END_START | {!ahead($ctx, END_START)}?)
   ;

startKey
   : KEY IS? (EQUAL TO? | EQUALCHAR | GREATER THAN? | MORETHANCHAR | NOT LESS THAN? | NOT LESSTHANCHAR | GREATER THAN? OR EQUAL TO? | MORETHANOREQUAL) qualifiedDataName
   ;

// stop statement

stopStatement
   : STOP (RUN | literal | stopStatementGiving)
   ;

stopStatementGiving
   : RUN (GIVING | RETURNING) (identifier | integerLiteral)
   ;

// string statement

stringStatement
   : STRING stringSendingPhrase+ stringIntoPhrase stringWithPointerPhrase? (onOverflowPhrase | {!startsPhrase($ctx, OVERFLOW)}?) (notOnOverflowPhrase | {!startsNotPhrase($ctx, OVERFLOW)}?) (END_STRING | {!ahead($ctx, END_STRING)}?)
   ;

stringSendingPhrase
   : (stringSending COMMACHAR?)+ (stringDelimitedByPhrase | stringForPhrase)
   ;

stringSending
   : identifier | literal
   ;

stringDelimitedByPhrase
   : DELIMITED BY? (SIZE | identifier | literal)
   ;

stringForPhrase
   : FOR (identifier | literal)
   ;

stringIntoPhrase
   : INTO identifier
   ;

stringWithPointerPhrase
   : WITH? POINTER qualifiedDataName
   ;

// subtract statement

subtractStatement
   : SUBTRACT (subtractFromStatement | subtractFromGivingStatement | subtractCorrespondingStatement) (onSizeErrorPhrase | {!startsPhrase($ctx, SIZE)}?) (notOnSizeErrorPhrase | {!startsNotPhrase($ctx, SIZE)}?) (END_SUBTRACT | {!ahead($ctx, END_SUBTRACT)}?)
   ;

subtractFromStatement
   : subtractSubtrahend+ FROM subtractMinuend+
   ;

subtractFromGivingStatement
   : subtractSubtrahend+ FROM subtractMinuendGiving GIVING subtractGiving+
   ;

subtractCorrespondingStatement
   : (CORRESPONDING | CORR) qualifiedDataName FROM subtractMinuendCorresponding
   ;

subtractSubtrahend
   : identifier | literal
   ;

subtractMinuend
   : roundable
   ;

subtractMinuendGiving
   : identifier | literal
   ;

subtractGiving
   : roundable
   ;

subtractMinuendCorresponding
   : qualifiedDataName ROUNDED?
   ;

// terminate statement

terminateStatement
   : TERMINATE reportName
   ;

// unstring statement

unstringStatement
   : UNSTRING unstringSendingPhrase unstringIntoPhrase unstringWithPointerPhrase? unstringTallyingPhrase? (onOverflowPhrase | {!startsPhrase($ctx, OVERFLOW)}?) (notOnOverflowPhrase | {!startsNotPhrase($ctx, OVERFLOW)}?) (END_UNSTRING | {!ahead($ctx, END_UNSTRING)}?)
   ;

unstringSendingPhrase
   : identifier (unstringDelimitedByPhrase unstringOrAllPhrase*)?
   ;

unstringDelimitedByPhrase
   : DELIMITED BY? ALL? (identifier | literal)
   ;

unstringOrAllPhrase
   : OR ALL? (identifier | literal)
   ;

unstringIntoPhrase
   : INTO unstringInto+
   ;

unstringInto
   : identifier unstringDelimiterIn? unstringCountIn?
   ;

unstringDelimiterIn
   : DELIMITER IN? identifier
   ;

unstringCountIn
   : COUNT IN? identifier
   ;

unstringWithPointerPhrase
   : WITH? POINTER qualifiedDataName
   ;

unstringTallyingPhrase
   : TALLYING IN? qualifiedDataName
   ;

// use statement

useStatement
   : USE (useAfterClause | useDebugClause)
   ;

useAfterClause
   : GLOBAL? AFTER STANDARD? (EXCEPTION | ERROR) PROCEDURE ON? useAfterOn
   ;

useAfterOn
   : INPUT | OUTPUT | I_O | EXTEND | fileName+
   ;

useDebugClause
   : FOR? DEBUGGING ON? useDebugOn+
   ;

useDebugOn
   : ALL PROCEDURES | ALL REFERENCES? OF? identifier | procedureName | fileName
   ;

// write statement

writeStatement
   : WRITE recordName writeFromPhrase? writeAdvancingPhrase? (writeAtEndOfPagePhrase | {!startsPhrase($ctx, END_OF_PAGE) && !startsPhrase($ctx, EOP)}?) (writeNotAtEndOfPagePhrase | {!startsNotPhrase($ctx, END_OF_PAGE) && !startsNotPhrase($ctx, EOP)}?) (invalidKeyPhrase | {!startsPhrase($ctx, INVALID)}?) (notInvalidKeyPhrase | {!startsNotPhrase($ctx, INVALID)}?) (END_WRITE | {!ahead($ctx, END_WRITE)}?)
   ;

writeFromPhrase
   : FROM (identifier | literal)
   ;

writeAdvancingPhrase
   : (BEFORE | AFTER) ADVANCING? (writeAdvancingPage | writeAdvancingLines | writeAdvancingMnemonic)
   ;

writeAdvancingPage
   : PAGE
   ;

writeAdvancingLines
   : (identifier | literal) (LINE | LINES)?
   ;

writeAdvancingMnemonic
   : mnemonicName
   ;

writeAtEndOfPagePhrase
   : AT? (END_OF_PAGE | EOP) imperativeStatements
   ;

writeNotAtEndOfPagePhrase
   : NOT AT? (END_OF_PAGE | EOP) imperativeStatements
   ;

// xml parse statement

xmlParseStatement
   : XML PARSE identifier xmlEncodingPhrase? xmlReturningNationalPhrase? xmlValidatingPhrase? xmlProcessingProcedurePhrase (onExceptionClause | {!startsPhrase($ctx, EXCEPTION)}?) (notOnExceptionClause | {!startsNotPhrase($ctx, EXCEPTION)}?) (END_XML | {!ahead($ctx, END_XML)}?)
   ;

xmlEncodingPhrase
   : WITH? ENCODING (identifier | integerLiteral)
   ;

xmlReturningNationalPhrase
   : RETURNING NATIONAL
   ;

xmlValidatingPhrase
   : VALIDATING WITH? (identifier | FILE cobolWord)
   ;

xmlProcessingProcedurePhrase
   : PROCESSING PROCEDURE IS? procedureName ((THROUGH | THRU) procedureName)?
   ;

// JSON GENERATE, JSON PARSE and XML GENERATE convert between a document and a data item. Their phrases are read
// loosely, as the words and operands each may hold, in any order. Inside them NAME, ELEMENT and the other phrase
// words are keywords rather than data names, and a WHEN before a figurative constant is SUPPRESS's, not an EVALUATE's.

jsonXmlStatement
   : (JSON (GENERATE | PARSE) | XML GENERATE) identifier (FROM | INTO) identifier jsonXmlPhrase* (onExceptionClause | {!startsPhrase($ctx, EXCEPTION)}?) (notOnExceptionClause | {!startsNotPhrase($ctx, EXCEPTION)}?) (END_JSON | END_XML | {!ahead($ctx, END_JSON) && !ahead($ctx, END_XML)}?)
   ;

jsonXmlPhrase
   : COUNT IN? identifier
   | WITH? (DETAIL | XML_DECLARATION | ATTRIBUTES | ENCODING (identifier | integerLiteral))
   | (NAMESPACE | NAMESPACE_PREFIX) IS? (identifier | literal)
   | (NAME | TYPE) OF? ({!jsonXmlKeywordAhead($ctx)}? identifier IS? (literal | OMITTED | ATTRIBUTE | ELEMENT | CONTENT))+
   | SUPPRESS ({!jsonXmlKeywordAhead($ctx)}? identifier | EVERY | NUMERIC | NONNUMERIC | ATTRIBUTE | CONTENT | ELEMENT
       | (WHEN | OR) (ZERO | ZEROS | ZEROES | SPACE | SPACES | LOW_VALUE | LOW_VALUES | HIGH_VALUE | HIGH_VALUES))+ {!suppressWhenAhead($ctx)}?
   | CONVERTING ({!jsonXmlKeywordAhead($ctx)}? identifier | TO | FROM | JSON | BOOLEAN | USING | ALSO | literal)+
   ;

// allocate and free statements

allocateStatement
   : ALLOCATE arithmeticExpression CHARACTERS? INITIALIZED? (LOC integerLiteral)? (RETURNING identifier)?
   ;

freeStatement
   : FREE identifier+
   ;

// statement phrases ----------------------------------

atEndPhrase
   : AT? END imperativeStatements
   ;

notAtEndPhrase
   : NOT AT? END imperativeStatements
   ;

invalidKeyPhrase
   : INVALID KEY? imperativeStatements
   ;

notInvalidKeyPhrase
   : NOT INVALID KEY? imperativeStatements
   ;

onOverflowPhrase
   : ON? OVERFLOW imperativeStatements
   ;

notOnOverflowPhrase
   : NOT ON? OVERFLOW imperativeStatements
   ;

onSizeErrorPhrase
   : ON? SIZE ERROR imperativeStatements
   ;

notOnSizeErrorPhrase
   : NOT ON? SIZE ERROR imperativeStatements
   ;

// statement clauses ----------------------------------

onExceptionClause
   : ON? EXCEPTION imperativeStatements
   ;

notOnExceptionClause
   : NOT ON? EXCEPTION imperativeStatements
   ;

// arithmetic expression ----------------------------------

arithmeticExpression
   : multDivs plusMinus* {!signAhead($ctx)}?
   ;

plusMinus
   : (PLUSCHAR | MINUSCHAR) multDivs
   ;

multDivs
   : powers multDiv*
   ;

multDiv
   : (ASTERISKCHAR | SLASHCHAR) powers
   ;

powers
   : (PLUSCHAR | MINUSCHAR)? basis power*
   ;

power
   : DOUBLEASTERISKCHAR basis
   ;

basis
   : LPARENCHAR arithmeticExpression RPARENCHAR | identifier | literal
   ;

// condition ----------------------------------

condition
   : combinableCondition andOrCondition*
   ;

andOrCondition
   : (AND | OR) (combinableCondition | {!combinableAhead($ctx)}? abbreviation+)
   ;

combinableCondition
   : NOT? simpleCondition
   ;

simpleCondition
   : LPARENCHAR condition RPARENCHAR | relationCondition | classCondition | conditionNameReference
   ;

classCondition
   : identifier IS? NOT? (NUMERIC | ALPHABETIC | ALPHABETIC_LOWER | ALPHABETIC_UPPER | DBCS | KANJI | className)
   ;

conditionNameReference
   : conditionName inData* conditionNameSubscriptReference*
   ;

conditionNameSubscriptReference
   : LPARENCHAR (subscript COMMACHAR?)+ RPARENCHAR
   ;

// relation ----------------------------------

relationCondition
   : relationSignCondition | relationArithmeticComparison | relationCombinedComparison
   ;

relationSignCondition
   : arithmeticExpression IS? NOT? (POSITIVE | NEGATIVE | ZERO)
   ;

relationArithmeticComparison
   : arithmeticExpression relationalOperator arithmeticExpression
   ;

relationCombinedComparison
   : arithmeticExpression relationalOperator LPARENCHAR relationCombinedCondition RPARENCHAR
   ;

relationCombinedCondition
   : arithmeticExpression ((AND | OR) arithmeticExpression)+
   ;

relationalOperator
   : (IS | ARE)? (NOT? (GREATER THAN? | MORETHANCHAR | LESS THAN? | LESSTHANCHAR | EQUAL TO? | EQUALCHAR) | NOTEQUALCHAR | GREATER THAN? OR EQUAL TO? | MORETHANOREQUAL | LESS THAN? OR EQUAL TO? | LESSTHANOREQUAL)
   ;

abbreviation
   : NOT? relationalOperator? (arithmeticExpression | LPARENCHAR arithmeticExpression abbreviation RPARENCHAR)
   ;

// identifier ----------------------------------

identifier
   : qualifiedDataName tableCallSubscripts* {!startsSubscripts()}? referenceModifier? {_input.LA(1) != LPARENCHAR}? | functionCall | specialRegister
   ;

tableCallSubscripts
    : LPARENCHAR (subscript COMMACHAR?)+ RPARENCHAR
    ;

functionCall
   : FUNCTION functionName functionCallArguments* {!startsSubscripts()}? referenceModifier? {_input.LA(1) != LPARENCHAR}?
   ;

functionCallArguments
    : LPARENCHAR (argument COMMACHAR?)+ (LEADING | TRAILING)? RPARENCHAR
    ;

referenceModifier
   : LPARENCHAR characterPosition COLONCHAR length? RPARENCHAR
   ;

characterPosition
   : arithmeticExpression
   ;

length
   : arithmeticExpression
   ;

subscript
   : ALL
   | {plainSubscriptAhead($ctx)}? integerLiteral
   | {plainSubscriptAhead($ctx)}? qualifiedDataName (integerLiteral | {!startsIntegerLiteral($ctx)}?)
   | {!plainSubscriptAhead($ctx)}? arithmeticExpression
   ;

argument
   : arithmeticExpression
   ;

// qualified data name ----------------------------------

qualifiedDataName
   : qualifiedDataNameFormat1 | qualifiedDataNameFormat4
   ;

qualifiedDataNameFormat1
   : dataName qualifiedInData*
   ;

qualifiedDataNameFormat4
   : LINAGE_COUNTER inFile
   ;

qualifiedInData
   : inData
   ;

// in ----------------------------------

inData
   : (IN | OF) dataName
   ;

inFile
   : (IN | OF) fileName
   ;

inMnemonic
   : (IN | OF) mnemonicName
   ;

inSection
   : (IN | OF) sectionName
   ;

// names ----------------------------------

alphabetName
   : cobolWord
   ;

assignmentName
   : systemName
   ;

basisName
   : programName
   ;

cdName
   : cobolWord
   ;

className
   : cobolWord
   ;

computerName
   : systemName
   ;

conditionName
   : cobolWord
   ;

dataName
   : cobolWord
   ;

dataDescName
   : FILLER | CURSOR | dataName
   ;

environmentName
   : systemName
   ;

fileName
   : cobolWord
   ;

functionName
   : INTEGER | LENGTH | RANDOM | SUM | WHEN_COMPILED | {!namedFunctionAhead($ctx)}? cobolWord
   ;

indexName
   : cobolWord
   ;

languageName
   : systemName
   ;

libraryName
   : cobolWord
   ;

localName
   : cobolWord
   ;

mnemonicName
   : cobolWord
   ;

paragraphName
   : cobolWord | integerLiteral
   ;

procedureName
   : paragraphName inSection?
   ;

programName
   : NONNUMERICLITERAL | cobolWord
   ;

recordName
   : qualifiedDataName
   ;

reportName
   : qualifiedDataName
   ;

routineName
   : cobolWord
   ;

screenName
   : cobolWord
   ;

sectionName
   : cobolWord | integerLiteral
   ;

systemName
   : cobolWord
   ;

symbolicCharacter
   : cobolWord
   ;

// literal ----------------------------------

cobolWord
   : IDENTIFIER
   | ATTRIBUTES | ELEMENT | INITIALIZED | LOC | NAME | NAMESPACE | NAMESPACE_PREFIX | NONNUMERIC | XML_DECLARATION
   | ABORT | AS | ASCII | ASSOCIATED_DATA | ASSOCIATED_DATA_LENGTH | ATTRIBUTE | AUTO | AUTO_SKIP
   | BACKGROUND_COLOR | BACKGROUND_COLOUR | BEEP | BELL | BINARY | BIT | BLINK | BLOB | BOUNDS
   | CAPABLE | CCSVERSION | CHANGED | CHANNEL | CLOB | CLOSE_DISPOSITION | COBOL | COMMITMENT | CONTROL_POINT | CONVENTION | CRUNCH | CURSOR
   | DBCLOB | DEFAULT | DEFAULT_DISPLAY | DEFINITION | DISK | DONTCARE | DOUBLE
   | EBCDIC | EMPTY_CHECK | ENTER | ENTRY_PROCEDURE | EOL | EOS | ERASE | ESCAPE | EVENT | EXCLUSIVE | EXPORT | EXTENDED
   | FOREGROUND_COLOR | FOREGROUND_COLOUR | FULL | FUNCTIONNAME | FUNCTION_POINTER
   | GRID
   | HIGHLIGHT
   | IMPLICIT | IMPORT | INTEGER
   | KEPT | KEYBOARD
   | LANGUAGE | LB | LD | LEFTLINE | LENGTH_CHECK | LIBACCESS | LIBPARAMETER | LIBRARY | LIST | LOCAL | LONG_DATE | LONG_TIME | LOWER | LOWLIGHT
   | MMDDYYYY
   | NAMED | NATIONAL | NATIONAL_EDITED | NETWORK | NO_ECHO | NUMERIC_DATE | NUMERIC_TIME
   | ODT | ORDERLY | OVERLINE | OWN
   | PASSWORD | PORT | PRINTER | PRIVATE | PROCESS | PROGRAM | PROMPT
   | READER | REAL | RECEIVED | RECURSIVE | REF | REMOTE | REMOVE | REQUIRED | REVERSE_VIDEO
   | SAVE | SECURE | SHARED | SHAREDBYALL | SHAREDBYRUNUNIT | SHARING | SHORT_DATE | SQL | SYMBOL
   | TASK | THREAD | THREAD_LOCAL | TIMER | TODAYS_DATE | TODAYS_NAME | TRUNCATED | TYPEDEF
   | UNDERLINE
   | VIRTUAL
   | WAIT
   | YEAR | YYYYMMDD | YYYYDDD
   | ZERO_FILL
   ;

literal
   : NONNUMERICLITERAL | figurativeConstant | numericLiteral | booleanLiteral | cicsDfhRespLiteral | cicsDfhValueLiteral
   ;

booleanLiteral
   : TRUE | FALSE
   ;

numericLiteral
   : NUMERICLITERAL | integerLiteral
   ;

integerLiteral
   : INTEGERLITERAL | LEVEL_NUMBER_66 | LEVEL_NUMBER_77 | LEVEL_NUMBER_88
   ;

cicsDfhRespLiteral
   : DFHRESP LPARENCHAR (cobolWord | literal) RPARENCHAR
   ;

cicsDfhValueLiteral
   : DFHVALUE LPARENCHAR (cobolWord | literal) RPARENCHAR
   ;

// keywords ----------------------------------

figurativeConstant
   : ALL literal | HIGH_VALUE | HIGH_VALUES | LOW_VALUE | LOW_VALUES | NULL | NULLS | QUOTE | QUOTES | SPACE | SPACES | ZERO | ZEROS | ZEROES
   ;

specialRegister
   : ADDRESS OF identifier
   | DATE | DAY | DAY_OF_WEEK | DEBUG_CONTENTS | DEBUG_ITEM | DEBUG_LINE | DEBUG_NAME | DEBUG_SUB_1 | DEBUG_SUB_2 | DEBUG_SUB_3
   | LENGTH OF? identifier | LINAGE_COUNTER | LINE_COUNTER
   | PAGE_COUNTER
   | RETURN_CODE
   | SHIFT_IN | SHIFT_OUT | SORT_CONTROL | SORT_CORE_SIZE | SORT_FILE_SIZE | SORT_MESSAGE | SORT_MODE_SIZE | SORT_RETURN
   | TALLY | TIME
   | WHEN_COMPILED
   ;

// comment entry

commentEntry
   : COMMENTENTRYLINE+
   ;
