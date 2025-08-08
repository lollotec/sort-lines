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
EOL_WS           = \n | \r | \r\n
LINE_WS          = [\ \t]
WHITE_SPACE_CHAR = {EOL_WS} | {LINE_WS}
WHITE_SPACE      = {WHITE_SPACE_CHAR}+
SORT_CHAR        = [^ \n\f\\{},/] | "\\"{EOL_WS} | "\\".
PATTERN_CHAR     = [^\n\r/] | "\\"{EOL_WS} | "\\".
INDEX_CHAR       = [0-9]

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
  "order"          { return ORDER; }
  {INDEX_CHAR}+    { return INDEX; }
  {SORT_CHAR}+     { return SORT; }
}

<WAITING_KEY> {
  "order"          { yybegin(YYINITIAL); return ORDER; }
  "group"          { yybegin(YYINITIAL); return GROUP; }
  "split"          { yybegin(YYINITIAL); return SPLIT; }
  "key"            { yybegin(YYINITIAL); return KEY; }
}

<WAITING_PATTERN> {
  {PATTERN_CHAR}+  { return PATTERN; }
  "/"              { yybegin(YYINITIAL); return FSLASH; }
}

{WHITE_SPACE}      { return WHITE_SPACE; }

[^]                { return BAD_CHARACTER; }
