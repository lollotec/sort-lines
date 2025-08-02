package com.github.jodiew.sortlines.highlight

import com.github.jodiew.sortlines.lang.SortLexerAdapter
import com.github.jodiew.sortlines.lang.colors.SortColor
import com.github.jodiew.sortlines.lang.psi.SortTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.intellij.psi.TokenType

class SortSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer = SortLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> =
        pack(map(tokenType)?.textAttributesKey)
}

private fun map(tokenType: IElementType?): SortColor? = when (tokenType) {
    SortTypes.ORDER -> SortColor.ORDER
    SortTypes.GROUP -> SortColor.GROUP
    SortTypes.SPLIT -> SortColor.SPLIT
    SortTypes.KEY -> SortColor.KEY

    SortTypes.SORT -> SortColor.SORT
    SortTypes.END -> SortColor.END
    SortTypes.PATTERN -> SortColor.PATTERN
    SortTypes.INDEX -> SortColor.INDEX

    SortTypes.LBRACE -> SortColor.BRACES
    SortTypes.RBRACE -> SortColor.BRACES
    SortTypes.COMMA -> SortColor.COMMA
    SortTypes.COLON -> SortColor.COLON
    SortTypes.FSLASH -> SortColor.PATTERN

    TokenType.BAD_CHARACTER -> SortColor.BAD_CHARACTER
    else -> null
}
