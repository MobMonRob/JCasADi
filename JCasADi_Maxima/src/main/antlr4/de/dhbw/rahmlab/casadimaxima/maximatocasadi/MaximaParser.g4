parser grammar MaximaParser;

options { tokenVocab=MaximaLexer; }

// Einstiegspunkt
root : QUOTE content QUOTE ;

content
    : arrayExpr                          # SimpleArray
    | BLOCK LPAR varList COMMA definitions COMMA arrayExpr RPAR # FullBlock
    ;

// Deklarationsliste am Anfang eines Blocks: [%1, %2, ...]
varList : LBRACK (ID (COMMA ID)*)? RBRACK ;

// Komma-separierte Zuweisungen: %1:a+b, %2:c*d
definitions : assignment (COMMA assignment)* ;

assignment : ID ASSIGN expression ;

// Das finale Array am Ende
arrayExpr : LBRACK (expression (COMMA expression)*)? RBRACK ;

// Die mathematische Kern-Logik
expression
    : LPAR expression RPAR              # ParenExpr
    | IF expression THEN expression ELSE expression # IfExpr
    | NOT expression                    # NotExpr
    | expression op=(MUL|DIV) expression # MulDivExpr
    | expression op=(ADD|SUB) expression # AddSubExpr
    | expression op=POW expression     # PowerExpr
    | expression compOp expression      # CompareExpr
    | expression op=(AND|OR) expression # LogicalExpr
    | SUB expression                    # UnaryMinus
    | ID LPAR (expression (COMMA expression)*)? RPAR # FunctionCall
    | ID                                # Variable
    | NUMBER                            # Number
    | E_CONST                           # ConstantE
    | PI_CONST                          # ConstantPi
    ;

compOp : EQ | NEQ | LT | GT | LTE | GTE ;
