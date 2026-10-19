lexer grammar BindLexer;

UTF_8_BOM : '\uFEFF' -> skip;

WS : [ \t\f]+ -> channel(HIDDEN);
EOL : (CR? LF | FORM_FEED) -> channel(HIDDEN);

// Hidden rather than skipped: a card continues the command above it only because that line ended in
// a dash, and only the line reader has looked there. Skipping these would throw away the one thing
// needed to group words back into commands.
CARD : '^^CARD^^' -> channel(HIDDEN), pushMode(INSIDE_CARD);
CARD_CONTINUATION : '^^CARD_CONT^^' -> channel(HIDDEN), pushMode(INSIDE_CARD);

// A card holding nothing but a comment, which neither opens a subcommand nor continues one. CardDemo
// opens each of its DSN decks with a licence block of them.
COMMENT_CARD : '^^COMMENT^^' ~[\r\n]* -> channel(HIDDEN);

fragment LF : '\n';
fragment CR : '\r';
fragment FORM_FEED : '\u000C';

mode INSIDE_CARD;
INSIDE_WS : WS -> type(WS), channel(HIDDEN);
INSIDE_EOL : EOL -> type(EOL), channel(HIDDEN), popMode;

// TSO reads a comment wherever a blank may stand. Written before TEXT so a comment closed on its own
// card wins the tie against the word rule.
COMMENT : '/*' ~[\r\n]*? '*/' -> channel(HIDDEN);

STRINGLITERAL : '\'' (~['\n\r] | '\'\'')* '\'';

TEXT : ~[ \t\f\r\n']+;
