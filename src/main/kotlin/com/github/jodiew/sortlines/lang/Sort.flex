package com.github.jodiew.sortlines.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.github.jodiew.sortlines.lang.psi.SortTypes;
import com.intellij.psi.TokenType;

%%

%class SortLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{   return;
%eof}

CRLF=\R
WHITE_SPACE=[\ \n\t\f]
KEY_CHARACTER=[^:=\ \n\t\f\\] | "\\ "

%state WAITING_VALUE

%%

<YYINITIAL> {KEY_CHARACTER}+                                { yybegin(YYINITIAL); return SortTypes.KEY; }

<WAITING_VALUE> {CRLF}({CRLF}|{WHITE_SPACE})+               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }

({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

[^]                                                         { return TokenType.BAD_CHARACTER; }
