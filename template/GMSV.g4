grammar GMSV;

@header {
package cg.data.script.antlr;
}

STRING : ('a'..'z' | 'A'..'Z' | '0'..'9')+ ; 
BOOLEAN : 'true' | 'false' ;
//NEWLINE:'\r' ? '\n' ; 
LOGIC : AMPERSAND AMPERSAND | BITWISEOR BITWISEOR;
DEFINE_VALUE : INT(',' INT)*;

fragment LETTER : ('A'..'Z' | 'a'..'z');
fragment CHINESECHAR : '\u4E00' .. '\u9FA5' | '\uF900' .. '\uFA2D';
fragment DIGIT : '0' .. '9';
NAME : (LETTER | UNDERLINE) (LETTER | UNDERLINE | DIGIT)* ;
INT : DIGIT+;
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
COMMENT : '/*' ( options {} : ()*? ) * '*/' {setChannel(HIDDEN);} ;
LINE_COMMENT : ('#' | '//') ~ ('\n' | '\r') * '\r'? '\n' {setChannel(HIDDEN);} ;
WS : ( ' ' | '\t' | '\n' | '\r' ) + {skip();} ;
EQUAL : '==' ;

/* =========================================== */
/*               system function               */
/* =========================================== */

r : NAME DEFINE_VALUE;

/* =========================================== */
/*                game function                */
/* =========================================== */

