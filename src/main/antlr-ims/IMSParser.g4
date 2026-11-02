parser grammar IMSParser;

options { caseInsensitive = true; tokenVocab=IMSLexer; }

compilationUnit
    : statement* EOF
    ;

statement
    : ims
    | comment
    | unknown
    ;

ims
    : imsWord
    ;

imsWord
    : (IMS_TEXT | IMS_STRINGLITERAL) imsSequenceArea?
    ;

imsSequenceArea
    : IMS_SEQUENCE_AREA
    ;

comment
    : commentWord
    ;

commentWord
    : COMMENT_TEXT commentSequenceArea?
    ;

commentSequenceArea
    : COMMENT_SEQUENCE_AREA
    ;

unknown
    : unknownWord
    ;

unknownWord
    : (UNKNOWN_TEXT | UNKNOWN_STRINGLITERAL) unknownSequenceArea?
    | unknownSequenceArea
    ;

unknownSequenceArea
    : UNKNOWN_SEQUENCE_AREA
    ;
