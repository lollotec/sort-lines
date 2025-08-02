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
  "{"              { yybegin(WAITING_KEY); return SortTypes.LBRACE; }
  "}"              { return SortTypes.RBRACE; }
  ":"              { return SortTypes.COLON; }
  ","              { yybegin(WAITING_KEY); return SortTypes.COMMA; }
  "/"              { yybegin(WAITING_PATTERN); return SortTypes.FSLASH; }
  "end"            { return SortTypes.END; }
  "order"          { return SortTypes.ORDER; }
  {INDEX_CHAR}+    { return SortTypes.INDEX; }
  {SORT_CHAR}+     { return SortTypes.SORT; }
}

<WAITING_KEY> {
  "order"          { yybegin(YYINITIAL); return SortTypes.ORDER; }
  "group"          { yybegin(YYINITIAL); return SortTypes.GROUP; }
  "split"          { yybegin(YYINITIAL); return SortTypes.SPLIT; }
  "key"            { yybegin(YYINITIAL); return SortTypes.KEY; }
}

<WAITING_PATTERN> {
  {PATTERN_CHAR}+  { return SortTypes.PATTERN; }
  "/"              { yybegin(YYINITIAL); return SortTypes.FSLASH; }
}

{WHITE_SPACE}      { return TokenType.WHITE_SPACE; }

[^]                { return TokenType.BAD_CHARACTER; }
