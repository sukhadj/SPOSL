%{
	// program to generate tokens for yacc
#include<stdio.h>
#include"y.tab.h"
%}

article a|an|the
verb 	run|go|come|walk|sing
adverb	very|simply|gently
conjuction and|or|also|but|then
pronoun he|she|it|they|you|this
preposition in|on|into
noun [a-zA-Z]+
adjective fast|good|honest
%%
\t ;
article {return ARTICLE;}
verb {return VERB;}
adverb {return ADVERB;}
conjuction {return CONJ;}
pronoun {return PRO;}
preposition {return PREPOSITION;}
noun {return NOUN;}
adjective {return ADJ;}
. ;
%%

int yywrap()
{
	return 1;
}
