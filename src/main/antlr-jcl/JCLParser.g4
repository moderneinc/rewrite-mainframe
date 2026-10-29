parser grammar JCLParser;

options { caseInsensitive = true; tokenVocab=JCLLexer; }

@members {
    /**
     * A word takes the comment area that follows it, which a statement of its own could also take. The rule's
     * context makes ANTLR treat the predicate as context-dependent, so each word rule's predicate stays its own.
     */
    boolean ahead(ParserRuleContext rule, int type) {
        return _input.LA(1) == type;
    }
}

compilationUnit
    : statement* EOF
    ;

statement
    : jcl
    | jes2
    | jes3
    | stream
    | controlM
    | comment
    | unknown
    ;

jcl
    : jclWord
    | jclTrailingComment
    ;

jclWord
    : (JCL_TEXT | JCL_STRINGLITERAL) (commentArea | {!ahead($ctx, CA_START)}?)
    ;

jclTrailingComment
    : jclWord JCL_TC_START jclWord* JCL_TC_STOP  (commentArea | {!ahead($ctx, CA_START)}?)
    ;

jes2
    : jes2Word
    ;

jes2Word
    : (JES2_TEXT | JES2_STRINGLITERAL) (commentArea | {!ahead($ctx, CA_START)}?)
    ;

jes3
    : jes3Word
    ;

jes3Word
    : (JES3_TEXT | JES3_STRINGLITERAL) (commentArea | {!ahead($ctx, CA_START)}?)
    ;

stream
    : streamWord
    ;

streamWord
    : (STREAM_TEXT | STREAM_STRINGLITERAL) (commentArea | {!ahead($ctx, CA_START)}?)
    ;

controlM
    : controlMWord
    ;

controlMWord
    : (CM_TEXT | CM_STRINGLITERAL) (commentArea | {!ahead($ctx, CA_START)}?)
    ;

comment
    : commentWord
    ;

commentWord
    : (COMMENT_TEXT | COMMENT_STRINGLITERAL) (commentArea | {!ahead($ctx, CA_START)}?)
    ;

unknown
    : unknownWord
    ;

unknownWord
    : (UNKNOWN_TEXT | UNKNOWN_STRINGLITERAL) (commentArea | {!ahead($ctx, CA_START)}?)
    | commentArea
    ;

// Columns 73-80, the sequence field. One token however many words are written there: the line ending
// that bounds it is on the hidden channel, so a parser repetition would run straight through it into
// the next line.
commentArea
    : CA_START
    ;
