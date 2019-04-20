%{
	#include<stdio.h>
	FILE *yyin;
	void yyerror(char *s);
	int yylex();
%}

%token ADVERB VERB ARTICLE CONJ PRO PREPOSITION NOUN ADJ

%%
sentence: simple {printf("SIMPLE SENTENCE \n");} |
	compound {printf("COMPOUND SENTENCE \n");}
;
simple: subject VERB ARTICLE object;

compound: subject VERB ARTICLE object CONJ subject VERB ARTICLE object| subject VERB ARTICLE object CONJ ARTICLE object;

subject: PRO|NOUN
;
object: NOUN|ADJ NOUN|ADVERB NOUN|PREPOSITION NOUN
;
%%

void yyerror(char *s)
{
}

int main()
{
	yyin = fopen("input.txt","r");
	yyparse();
	fclose(yyin);
	return 0;
}
