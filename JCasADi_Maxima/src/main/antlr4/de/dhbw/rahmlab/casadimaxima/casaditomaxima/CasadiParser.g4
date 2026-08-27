parser grammar CasadiParser;

options { tokenVocab=CasadiLexer; }

file        : (assignment (COMMA assignment)* COMMA)? array EOF ;

assignment  : VAR ASSIGN expr ;

array       : LBRACK expr (COMMA expr)* RBRACK ;

expr        : LPAREN expr RPAREN                        # Parentheses
            | ID LPAREN expr (COMMA expr)* RPAREN       # FunctionCall
            | (MINUS|NOT) expr                          # UnaryOp
            | expr op=(MUL|DIV) expr                    # Multiplicative
            | expr op=(PLUS|MINUS) expr                 # Additive
            | expr op=(LT|LE|GT|GE|EQ|NEQ) expr         # RelationalOps
            | expr AND expr                              # LogicalAnd
            | expr OR expr                               # LogicalOr
            | expr QUESTION expr COLON expr             # TernaryOp
            | atom                                      # Primary
            ;

atom        : NUMBER 
            | VAR 
            | ARG 
            | ID 
            ;
