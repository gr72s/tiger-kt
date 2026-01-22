grammar Tiger;

program: exp EOF;

exp
    : ID
    | INT
    | STRING
    | 'nil'
    | '(' (exp (';' exp)*)? ')'
    | exp '.' ID
    | exp '[' exp ']'
    | typeId '{' (ID '=' exp (',' ID '=' exp)*)? '}'
    | typeId '[' exp ']' 'of' exp
    | ID '(' (exp (',' exp)*)? ')'
    | '-' exp
    | exp ('*'|'/') exp
    | exp ('+'|'-') exp
    | exp ('='|'<>'|'>'|'<'|'>='|'<=') exp
    | exp '&' exp
    | exp '|' exp
    | exp ':=' exp
    | 'if' exp 'then' exp ('else' exp)?
    | 'while' exp 'do' exp
    | 'for' ID ':=' exp 'to' exp 'do' exp
    | 'break'
    | 'let' decs 'in' expseq 'end'
    ;

decs: dec+;

dec
    : tydec
    | vardec
    | fundec
    ;

tydec: 'type' typeId '=' ty;

ty
    : typeId
    | '{' tyfields '}'
    | 'array' 'of' typeId
    ;

tyfields
    :
    | typeDefine (',' typeDefine)*
    ;

typeDefine: ID ':' typeId;

vardec
    : 'var' ID ':=' exp
    | 'var' typeDefine ':=' exp
    ;

fundec
    : 'function' ID '(' tyfields ')' '=' exp
    | 'function' ID '(' tyfields ')' ':' typeId '=' exp
    ;

typeId: ID;

expseq: exp (';' exp)*;

INT
    : [0-9]+
    ;

STRING
    : '"' ( '\\' [btnfr"'\\] | ~["\\] )* '"'
    ;

ID
    : [a-zA-Z_] [a-zA-Z_0-9]*
    ;

REGULAR_COMMENT
    : '/*' .*? '*/' -> channel(HIDDEN)
    ;


WS
    : [ \t\r\n]+ -> channel(HIDDEN)
    ;