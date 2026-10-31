parser grammar BMSParser;

options { caseInsensitive = true; tokenVocab=BMSLexer; }

compilationUnit
    : statement* EOF
    ;

statement
    : bms
    | comment
    | unknown
    ;

bms
    : bmsWord
    ;

bmsWord
    : (BMS_TEXT | BMS_STRINGLITERAL) bmsSequenceArea?
    ;

bmsSequenceArea
    : BMS_CA_START
    ;

comment
    : commentWord
    ;

commentWord
    : COMMENT_TEXT commentSequenceArea?
    ;

commentSequenceArea
    : COMMENT_CA_START
    ;

unknown
    : unknownWord
    ;

unknownWord
    : (UNKNOWN_TEXT | UNKNOWN_STRINGLITERAL) unknownSequenceArea?
    | unknownSequenceArea
    ;

unknownSequenceArea
    : UNKNOWN_CA_START
    ;
