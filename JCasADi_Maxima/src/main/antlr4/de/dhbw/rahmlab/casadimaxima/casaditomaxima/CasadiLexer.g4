lexer grammar CasadiLexer;

WS          : [ \t\r\n]+ -> skip ;

VAR         : '@' [0-9]+ ;
ARG         : 'arg' [0-9]+ '_' [0-9]+ ;
ID          : [a-zA-Z_] [a-zA-Z0-9_]* ;

NUMBER      : '-'? [0-9]+ ('.' [0-9]+)? ([eE] [+-]? [0-9]+)? ;

// Symbole
ASSIGN      : '=' ;
COMMA       : ',' ;
LPAREN      : '(' ;
RPAREN      : ')' ;
LBRACK      : '[' ;
RBRACK      : ']' ;
COLON       : ':' ;
QUESTION    : '?' ;

// Operatoren
MUL         : '*' ;
DIV         : '/' ;
PLUS        : '+' ;
MINUS       : '-' ;

// Relationale & Logische Operatoren
LE          : '<=' ;
GE          : '>=' ;
LT          : '<' ;
GT          : '>' ;
EQ          : '==' ;
NEQ         : '!=' ;
AND         : '&&' ;
OR          : '||' ;
NOT         : '!' ;

ANY         : . ;
