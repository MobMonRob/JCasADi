parser grammar MaximaParser;

options { tokenVocab=MaximaLexer; }

root : content;

content
    : arrayExpr                          # SimpleArray
    | BLOCK LPAR varList COMMA definitions COMMA arrayExpr RPAR # FullBlock
    ;

varList : LBRACK (ID (COMMA ID)*)? RBRACK ;

definitions : assignment (COMMA assignment)* ;

assignment : ID ASSIGN expression ;

arrayExpr : LBRACK (expression (COMMA expression)*)? RBRACK ;

// precedence / binding strength: https://maxima.sourceforge.io/docs/manual/Operators.html#Introduction-to-operators
expression
	: LPAR expression RPAR                                          # ParenExpr
    | ID LPAR (expression (COMMA expression)*)? RPAR                # FunctionCall
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
	| expression op=(AND|OR) expression                             # LogicalExpr
	| IF expression THEN expression ELSE expression                 # IfExpr
    ;

