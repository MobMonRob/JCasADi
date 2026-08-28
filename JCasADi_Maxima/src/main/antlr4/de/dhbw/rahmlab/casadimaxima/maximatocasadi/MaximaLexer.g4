lexer grammar MaximaLexer;

// Whitespace
WS : [ \t\r\n]+ -> skip ;

// Keywords
BLOCK : 'block' ;
IF    : 'if' ;
THEN  : 'then' ;
ELSE  : 'else' ;
AND   : 'and' ;
OR    : 'or' ;
NOT   : 'not' ;

// Konstanten & Symbole
E_CONST  : '%e' ;
PI_CONST : '%pi' ;
ASSIGN   : ':' ;
QUOTE    : '"' ;
COMMA    : ',' ;
LPAR     : '(' ;
RPAR     : ')' ;
LBRACK   : '[' ;
RBRACK   : ']' ;

// Operatoren
POW   : '^' ;
MUL   : '*' ;
DIV   : '/' ;
ADD   : '+' ;
SUB   : '-' ;
EQ    : '=' ;
NEQ   : '#' ;
LTE   : '<=' ;
GTE   : '>=' ;
LT    : '<' ;
GT    : '>' ;

// Literale
NUMBER : [0-9]+ ('.' [0-9]*)? ([eE] [+-]? [0-9]+)? ;
ID     : [a-zA-Z%][a-zA-Z0-9_]* ;
