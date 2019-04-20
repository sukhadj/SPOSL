%{
	// program to generate tokens for yacc
#include <stdio.h>
#include "scanner.tab.h"
%}

digit [0-9]
int {digit}+
float {digit}+(\.{digit}+)?((e|E){digit}+)?
char '.'
string 	\".*\"

%%
=	{return ASSIGN;}
int	{return INTDEC;}
float	{return FLOATDEC;}
char	{return CHARDEC;}
String	{return STRDEC;}
boolean {return BOOLDEC;}
true|false	{return BOOLVAL;}
{int} 	{return INTVAL;}
{float} {return FLOATVAL;}
{char} 	{return CHARVAL;}
{string} {return STRVAL;}
,	{return COMMA;}
; 	{return SEMICOL;}
[a-zA-Z][a-zA-Z0-9]*	{return ID;}
\n 	{return NL;}
.	;
%%

int yywrap()
{
	return 1;
}

