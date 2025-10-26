package com.github.jodiew.sortlines.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.github.jodiew.sortlines.lang.psi.SortTypes.*;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;

%%

%class SortLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{   return;
%eof}

// Whitespaces
WHITE_SPACE = \s+
STRING = [^:, \n\r\t{}\/]+
REGEX = ((\\\/)|[^\/])+
NUMBER = \d+

%s WAITING_KEY
%s WAITING_PATTERN

%%

<YYINITIAL> {
  "{"              { yybegin(WAITING_KEY); return LBRACE; }
  "}"              { return RBRACE; }
  ":"              { return COLON; }
  ","              { yybegin(WAITING_KEY); return COMMA; }
  "/"              { yybegin(WAITING_PATTERN); return FSLASH; }
  "end"            { return END; }
  {NUMBER}         { return NUMBER; }
  {STRING}         { return STRING; }
}

<WAITING_KEY> {
  "order"          { yybegin(YYINITIAL); return ORDER; }
  "group"          { yybegin(YYINITIAL); return GROUP; }
  "split"          { yybegin(YYINITIAL); return SPLIT; }
  "key"            { yybegin(YYINITIAL); return KEY; }
}

<WAITING_PATTERN> {
  {REGEX}          { return REGEX; }
  "/"              { yybegin(YYINITIAL); return FSLASH; }
}

{WHITE_SPACE}      { return WHITE_SPACE; }

[^]                { return BAD_CHARACTER; }
