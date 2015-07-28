lexer grammar GlobLexer;

OR_START: '{';
OR_SEPARATOR: ',';
OR_END: '}';


fragment LITERAL: .;
ESCAPE: '\\' LITERAL { setText(getText().substring(1)); };

CHARACTER: ESCAPE | LITERAL;
