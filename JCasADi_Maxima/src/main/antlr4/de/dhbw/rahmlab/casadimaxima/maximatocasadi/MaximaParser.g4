parser grammar MaximaParser;

options { tokenVocab=MaximaLexer; }

root : QUOTE content QUOTE ;

content
    : arrayExpr                          # SimpleArray
    | BLOCK LPAR varList COMMA definitions COMMA arrayExpr RPAR # FullBlock
    ;

varList : LBRACK (ID (COMMA ID)*)? RBRACK ;

definitions : assignment (COMMA assignment)* ;

assignment : ID ASSIGN expression ;

arrayExpr : LBRACK (expression (COMMA expression)*)? RBRACK ;

expression
    : LPAR expression RPAR              # ParenExpr
    | IF expression THEN expression ELSE expression # IfExpr
    | NOT expression                    # NotExpr
    | expression op=(MUL|DIV) expression # MulDivExpr
    | expression op=(ADD|SUB) expression # AddSubExpr
    | expression op=POW expression     # PowerExpr
    | expression op=(EQ | NEQ | LT | GT | LTE | GTE) expression      # CompareExpr
    | expression op=(AND|OR) expression # LogicalExpr
    | SUB expression                    # UnaryMinus
    | ID LPAR (expression (COMMA expression)*)? RPAR # FunctionCall
    | ID                                # Variable
    | NUMBER                            # Number
    | E_CONST                           # ConstantE
    | PI_CONST                          # ConstantPi
    ;

