package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.lang.parser.SortParser
import com.github.jodiew.sortlines.lang.psi.SortFile
import com.github.jodiew.sortlines.lang.psi.SortTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class SortParserDefinition : ParserDefinition {
    companion object {
        @JvmField val FILE = IFileElementType(SortLanguage)
    }

    override fun createLexer(p0: Project?): Lexer = SortLexerAdapter()

    override fun getCommentTokens(): TokenSet = TokenSet.EMPTY

    override fun getStringLiteralElements(): TokenSet  = TokenSet.EMPTY

    override fun createParser(p0: Project?): PsiParser = SortParser()

    override fun getFileNodeType(): IFileElementType = FILE

    override fun createFile(viewProvider: FileViewProvider): PsiFile = SortFile(viewProvider)

    override fun createElement(node: ASTNode?): PsiElement = SortTypes.Factory.createElement(node)
}
