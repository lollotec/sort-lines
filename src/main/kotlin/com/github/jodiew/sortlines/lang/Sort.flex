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
SORT_CHAR   = [^ \n\f\\\{\}] | "\\"{EOL_WS} | "\\".

%%

<YYINITIAL> {
  {SORT_CHAR}+  { return SortTypes.SORT; }
  {WHITE_SPACE} { return TokenType.WHITE_SPACE; }
}

[^]             { return TokenType.BAD_CHARACTER; }
