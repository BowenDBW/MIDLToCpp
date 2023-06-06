lexer grammar MidlLexer;

INTEGER : ('0' | [1-9] DIGIT*) INTEGER_TYPE_SUFFIX?;
FLOATING_PT:    DIGIT+'.'DIGIT*  EXPONENT?  FLOAT_TYPE_SUFFIX?
   				|  '.'DIGIT+  EXPONENT?  FLOAT_TYPE_SUFFIX?
   				|  DIGIT+  EXPONENT  FLOAT_TYPE_SUFFIX?
   				|  DIGIT+  EXPONENT?  FLOAT_TYPE_SUFFIX
   				;
CHAR: '\''(ESCAPE_SEQUENCE |  ~('\\' | '\'') ) '\'';
STRING :  '"' (ESCAPE_SEQUENCE |  ~('\\' | '"') )* '"';
BOOLEAN :  'TRUE' | 'true' | 'FALSE' | 'false';
ID :  LETTER ((UNDERLINE)?( LETTER | DIGIT))* ;  //问号表示非贪婪模式
WS : [ \t\r\n]+ -> skip;    // 忽略空格、Tab、换行以及\r （Windows）

fragment LETTER : [a-z]
       | [A-Z]
       ;
fragment DIGIT : [0-9];
fragment UNDERLINE: '_';
fragment INTEGER_TYPE_SUFFIX :   'l' | 'L';
fragment EXPONENT:( 'e' | 'E') ( '+' | '-' )? DIGIT+;
fragment FLOAT_TYPE_SUFFIX:  'f' | 'F' | 'd' | 'D';
fragment ESCAPE_SEQUENCE :  '\\'( 'b' | 't' | 'n' | 'f' | 'r' | '"' | '\'' | '\\');
