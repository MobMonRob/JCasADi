parser grammar MaximaParser;

options { tokenVocab=MaximaLexer; }

root : content EOF;

content
    : arrayExpr                          # SimpleArray
    | BLOCK LPAR varList COMMA definitions COMMA arrayExpr RPAR # FullBlock
    ;

varList : LBRACK (CSE_VAR (COMMA CSE_VAR)*)? RBRACK ;

definitions : assignment (COMMA assignment)* ;

assignment : CSE_VAR ASSIGN expression ;

arrayExpr : LBRACK (expression (COMMA expression)*)? RBRACK ;

// precedence / binding strength: https://maxima.sourceforge.io/docs/manual/Operators.html#Introduction-to-operators
expression
	: LPAR expression RPAR                                          # ParenExpr
    | ID LPAR (expression (COMMA expression)*)? RPAR                # FunctionCall
    | CSE_VAR                                                       # CseVariable
    | ID                                                            # Variable
	| NUMBER                                                        # Number
	| E_CONST                                                       # ConstantE
	| PI_CONST                                                      # ConstantPi
	| <assoc=right> expression op=POW expression                    # PowerExpr
	| SUB expression                                                # UnaryMinus
	| expression op=(MUL|DIV) expression                            # MulDivExpr
	| expression op=(ADD|SUB) expression                            # AddSubExpr
	| expression op=(EQ|NEQ|LT|GT|LTE|GTE) expression               # CompareExpr
	| NOT expression                                                # NotExpr
	| expression AND expression                                     # LogicalAndExpr
	| expression OR expression                                      # LogicalOrExpr
	| IF expression THEN expression ELSE expression                 # IfExpr
    ;
