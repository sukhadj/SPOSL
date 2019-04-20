%{
	// program to generate the tokens
%}

digit [0-9]
integer {digit}*
floatingPoint {digit}*\.{digit}*(E|e)?{digit}+
identifier [_a-zA-Z]+[_a-zA-Z0-9]*
unaryOperator --|\+\+
binaryOperator \+|-|\*|\/|%|<<|>>|>>>
comparisonOperator >|<|==|<=|>=|!=
bitwiseOperator \||&|\^
logicalOperator \|\||&&
assignmentOperator =|\+=|-=|\*=|\/=|%=
loopKeyword for|while|do
conditionalKeyword if|else|else\sif
exceptionKeyword try|catch|throw|throws|finally
classTokenKeyword class|abstract|interface|extends|implements|new
datatypeKeyword int|float|boolean|String|char
nullKeyword null
booleanKeyword true|false
accessSpecifier private|public|protected
importToken ^(import)
comment (\/\/.*|\/\*(.|\n)*\*\/)

%%
{comment}	{printf("(Comment:%s)", yytext);}
{nullKeyword}	{printf("(Null:%s)", yytext);}
{importToken}	{printf("(Import token:%s)", yytext);}
{unaryOperator}	{printf("(Unary Operator:%s)", yytext);}
{binaryOperator}	{printf("(Binary Operator:%s)", yytext);}
{comparisonOperator}	{printf("(Comparision Operator:%s)", yytext);}
{bitwiseOperator}	{printf("(Bitwise Operator:%s)", yytext);}
{logicalOperator}	{printf("(Logical Operator:%s)", yytext);}
{assignmentOperator}	{printf("(Assignment Operator:%s)", yytext);}
{booleanKeyword}	{printf("(Boolean:%s)", yytext);}
{datatypeKeyword}	{printf("(Datatype:%s)", yytext);}
{loopKeyword}	{printf("(Loop Keyword:%s)", yytext);}
{conditionalKeyword}	{printf("(Conditional:%s)", yytext);}
{exceptionKeyword}	{printf("(Exception keyword:%s)", yytext);}
{accessSpecifier}	{printf("(Access Specifier:%s)", yytext);}
{classTokenKeyword}	{printf("(Class Token:%s)", yytext);}
{identifier}	{printf("(Identifier:%s)", yytext);}
{integer}	{printf("(Integer:%s)", yytext);}
{floatingPoint}	{printf("(Float:%s)", yytext);}
%%

int yywrap(){
	return 1;
}

int main()
{
	extern FILE *yyin;
	yyin = fopen("sample.java", "r");
	yylex();
	return 0;
}
