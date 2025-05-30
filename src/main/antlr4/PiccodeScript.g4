grammar PiccodeScript;

options {
  language = Java;
}

@header {
	package org.piccode.antlr4;
}

stmts: stmt*
	| EOF;

stmt:
	import_module
	| func
	| module
	| expr_stmt;

import_module:
	IMPORT ID COLON ID;

module: 
	MODULE ID LBRACE module_stmts RBRACE;

module_stmts:
	module_stmt*;

module_stmt:
	func
	| var_decl
	| module;

func: FUNCTION ID func_args ASSIGN expr ;

func_args: '(' arg_list? ')' ;

arg_list: arg (',' arg)* ;

arg: ID (ASSIGN literal_expr)? ; // only allow literals in default values


literal_expr :
	NUMBER                           
	| STRING                           
	|array
	| tuple
	| object
	;

expr_stmt: expr;
	// parser rules
expr
	: expr LPAREN call_expr_list? RPAREN
	| var_decl
	| expr DOT expr
	| expr PIPE expr         
  | expr OR expr           
	| expr AND expr          
	| expr EQ expr           
	| expr NE expr           
	| expr LT expr           
	| expr LE expr           
	| expr GT expr           
	| expr GE expr          
	| expr SHL expr          
	| expr SHR expr          
	| expr BOR expr          
	| expr BAND expr         
	| expr MUL expr         
	| expr DIV expr         
	| expr ADD expr          
	| expr SUB expr         
	| expr DOT expr
	| expr COLON expr
	| LPAREN expr? RPAREN
	| unary
	| if_expr
	| when_expr
	| do_expr
	| array
	| tuple
	| object
	| ID
	| NUMBER                           
	| STRING                           
	;


unary: 
	EXCLAIM expr
	| SUB expr
	| TILDE expr
	| BAND expr;

if_expr:
	IF expr LBRACE expr RBRACE ELSE LBRACE expr RBRACE;

when_expr: 
 WHEN expr LBRACE when_cases else_case? RBRACE;
	
when_cases: when_case*;

when_case: IS expr_list ARROW expr;

else_case: ELSE ARROW expr;

var_decl: LET ID ASSIGN expr;
tuple: LPAREN expr_list RPAREN;
array: LBRACKET expr_list? RBRACKET;
object: LBRACE key_val_pairs RBRACE;

expr_list: expr (COMMA expr)*;
call_expr_list: call_expr (COMMA call_expr)*;

call_expr: ID (ASSIGN expr)? 
	| expr;

key_val_pair: ID COLON expr;
key_val_pairs: key_val_pair (COMMA key_val_pair)*;

do_expr: DO LBRACE expr* RBRACE;

	// lexer rules
ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';

GT: '>';
GE: '>=';
LT: '<';
LE: '<=';
EQ: '==';
NE: '!=';

AND: '&&';
OR: '||';

SHL: '>>';
SHR: '<<';
BAND: '&';
BOR: '|';
EXCLAIM : '!';
PIPE: '|>';

LBRACE: '{';
RBRACE: '}';
LPAREN: '(';
RPAREN: ')';
LBRACKET: '[';
RBRACKET: ']';
COLON: ':';
COMMA: ',';
SEMI: ';';
ARROW: '->';
TILDE: '~';


ASSIGN: '=';

LET: 'let';
FUNCTION: 'function';
WHEN: 'when';
IMPORT: 'import';
IS: 'is';
IF: 'if';
ELSE: 'else';
MODULE: 'module';
DO: 'do';

// Matches both integers and floats (e.g., 123, 45.67)
NUMBER: [0-9]+ ('.' [0-9]+)?;

// Matches strings like "hello world" or 'hello world'
// Handles basic escaped quotes
STRING: '"' (~["\\] | '\\' .)* '"'
| '\'' (~['\\] | '\\' .)* '\'';

DOT: '.';

LINE_COMMENT: '//' ~[\r\n]* -> channel(HIDDEN);
BLOCK_COMMENT: '/*' .*? '*/' -> channel(HIDDEN);


ID: [a-zA-Z_][a-zA-Z0-9_]* ;
WS: [ \t\r\n]+ -> channel(HIDDEN); 
