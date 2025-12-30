grammar tiger;

decs: dec;

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

ID
    : [a-zA-Z_] [a-zA-Z_0-9]*
    ;

REGULAR_COMMENT
    : '/*' .*? '*/'
    ;