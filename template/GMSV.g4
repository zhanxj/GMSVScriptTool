grammar GMSV;
@header {
package cg.data.script.antlr;
}
ID : ('a'..'z' | 'A'..'Z')+ ; 
STRING : ('a'..'z' | 'A'..'Z' | '0'..'9')+ ; 
INT : '0'..'9' + ; 
BOOLEAN : 'true' | 'false' ;
NEWLINE:'\r' ? '\n' ; 
WS : (' ' | '\t' |'\n' |'\r' )+ {skip();} ;
LOGIC : '&' '&' | '|' '|';
DEFINE_VALUE : INT(',' INT)*;
COMMENT : '/*' (.)*? '*/' {skip();} ;
LINE_COMMENT : ('#' | '//') ~ ('\n' | '\r')* '\r'? '\n' {skip();} ;

/* =========================================== */
/*               system function               */
/* =========================================== */

r : ID DEFINE_VALUE;

/* =========================================== */
/*                game function                */
/* =========================================== */

