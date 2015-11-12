grammar GMSV;
ID : ('a'..'z' | 'A'..'Z')+ ; 
STRING : ('a'..'z' | 'A'..'Z' | '0'..'9')+ ; 
INT : '0'..'9' + ; 
BOOLEAN : true | false ;
NEWLINE:'\r' ? '\n' ; 
WS : (' ' |'\t' |'\n' |'\r' )+ {skip();} ;
LOGIC : '&' '&' | '|' '|';
DEFINE_VALUE : INT(',' INT)*;

/* =========================================== */
/*               system function               */
/* =========================================== */

r : ID DEFINE_VALUE;
block : ID (LOGIC ID)*;




/* =========================================== */
/*                game function                */
/* =========================================== */

