%{
#include<stdio.h>
void yyerror(char*);
int yylex();
FILE* yyin;	
%}

%token ASSIGN INTDEC FLOATDEC CHARDEC STRDEC BOOLDEC BOOLVAL INTVAL FLOATVAL CHARVAL STRVAL COMMA SEMICOL ID NL
%%
start: intdec|floatdec|chardec|strdec|booldec
;
// integer 
intdec:  INTDEC intvarlist SEMICOL NL {printf("valid INT Variable declaration\n"); return 0;}
;
// float  
floatdec:  FLOATDEC floatvarlist SEMICOL NL {printf("valid FLOAT Variable declaration\n"); return 0;}
;

// char  
chardec:  CHARDEC charvarlist SEMICOL NL {printf("valid CHAR Variable declaration\n"); return 0;}
;

// string
strdec:  STRDEC strvarlist SEMICOL NL {printf("valid STR Variable declaration\n"); return 0;}
;

// bool
booldec:  BOOLDEC boolvarlist SEMICOL NL {printf("valid BOOL Variable declaration\n"); return 0;}
;


intvarlist: ID | ID COMMA intvarlist | ID ASSIGN INTVAL COMMA intvarlist | ID ASSIGN INTVAL | 
;

floatvarlist: ID | ID COMMA floatvarlist | ID ASSIGN FLOATVAL COMMA floatvarlist | ID ASSIGN FLOATVAL | 
;

strvarlist: ID | ID COMMA strvarlist | ID ASSIGN STRVAL COMMA strvarlist | ID ASSIGN STRVAL | 
;

charvarlist: ID | ID COMMA charvarlist | ID ASSIGN CHARVAL COMMA charvarlist | ID ASSIGN CHARVAL | 
;

boolvarlist: ID | ID COMMA boolvarlist | ID ASSIGN BOOLVAL COMMA boolvarlist | ID ASSIGN BOOLVAL |
;
%% 

void yyerror(char *s)
{
	fprintf(stderr, "ERROR: %s\n",s);	
}
int main()
{
	yyin=fopen("input.txt","r");
	yyparse();
  fclose(yyin);
	return 0;
}


