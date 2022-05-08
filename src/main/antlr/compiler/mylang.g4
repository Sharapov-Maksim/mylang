grammar mylang;

@header {
package compiler;
}

// PARSER RULES
program : block EOF;

block : statement*;

statement : let | show | if_stat | while_stat;
let : VAR '=' (NUMBER | STRING) ;
show : 'show' (NUMBER | STRING | VAR) ;

if_stat
 : IF condition_block (ELSE IF condition_block)* (ELSE statement_block)?
 ;

//          condition, statements if condition is true
condition_block : expr statement_block;

statement_block
 : OBRACE block CBRACE
 | statement
 ;

while_stat
 : WHILE expr statement_block
 ;

expr
 : MINUS expr                           #unaryMinusExpr
 | expr op=(MULT | DIV) expr            #multiplicationExpr
 | expr op=(PLUS | MINUS) expr          #additiveExpr
 | expr op=(LTEQ | GTEQ | LT | GT) expr #relationalExpr
 | expr op=(EQ | NEQ) expr              #equalityExpr
 | atom                                 #atomExpr
 ;

atom
 : OPAR expr CPAR #parExpr
 | NUMBER         #numberAtom
 | VAR            #varAtom
 | STRING         #stringAtom
 ;

// LEXER RULES
EQ : '==';
NEQ : '!=';
GT : '>';
LT : '<';
GTEQ : '>=';
LTEQ : '<=';
PLUS : '+';
MINUS : '-';
MULT : '*';
DIV : '/';

TRUE : 'true';
FALSE : 'false';
IF : 'if';
ELSE : 'else';
WHILE : 'while';

VAR : LETTER (LETTER | DIGIT)* ;

NUMBER : DIGIT+ ;
// STRING : '"' (LETTER | DIGIT | SPECSIMBOLS | WS)* '"';
STRING : '"' (~["\r\n] | '""')* '"';

DIGIT : '0'..'9';
LETTER : LOWER | UPPER ;
LOWER : ('a'..'z') ;
UPPER : ('A'..'Z') ;

OPAR : '(';
CPAR : ')';
OBRACE : '{';
CBRACE : '}';

SPECSIMBOLS: [.,;!?()@#№$%^:&*];
WS : [ \n\t\r]+ -> skip;