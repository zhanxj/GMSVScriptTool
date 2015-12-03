grammar GMSV;

@header {
package cg.data.script.antlr;
}

STRING : ('a'..'z' | 'A'..'Z' | '0'..'9')+ ; 
BOOLEAN : 'true' | 'false' ;
NEWLINE:'\r' ? '\n' ; 
LOGIC : AMPERSAND AMPERSAND | BITWISEOR BITWISEOR;
DEFINE_VALUE : INT(',' INT)*;

NAME : (LETTER | UNDERLINE) (LETTER | UNDERLINE | DIGIT)* ;
LETTER : ('A'..'Z' | 'a'..'z');
CHINESECHAR : '\u4E00' .. '\u9FA5' | '\uF900' .. '\uFA2D';
INT : DIGIT+;
DIGIT : '0' .. '9';
COLON : ':' ;
COMMA : ',' ;
SEMICOLON : ';' ;
LPAREN : '(' ;
RPAREN : ')' ;
LSQUARE : '[' ;
RSQUARE : ']' ;
LCURLY : '{';
RCURLY : '}';
DOT : '.' ;
UNDERLINE : '_';
ASSIGNEQUAL : '=' ;
NOTEQUAL1 : '<>' ;
NOTEQUAL2 : '!=' ;
LESSTHANOREQUALTO1 : '<=' ;
LESSTHAN : '<' ;
GREATERTHANOREQUALTO1 : '>=' ;
GREATERTHAN : '>' ;
DIVIDE : '/' ;
PLUS : '+' ;
MINUS : '-' ;
STAR : '*' ;
MOD : '%' ;
AMPERSAND : '&' ;
TILDE : '~' ;
BITWISEOR : '|' ;
BITWISEXOR : '^' ;
POUND : '#';
DOLLAR : '$';
COMMENT : '/*' ()*? '*/' {setChannel(HIDDEN);} ;
LINE_COMMENT : ('#' | '//') ~ ('\n' | '\r') * '\r'? '\n' {setChannel(HIDDEN);} ;
WS : ( ' ' | '\t' | '\n' | '\r' ) + {skip();} ;

/* =========================================== */
/*               system function               */
/* =========================================== */

r : NAME DEFINE_VALUE;

/* =========================================== */
/*                game function                */
/* =========================================== */

