package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.lang.psi.SortColor
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
    SortTypes.SORT -> SortColor.SORT
    TokenType.BAD_CHARACTER -> SortColor.BAD_CHARACTER
    else -> null
}
