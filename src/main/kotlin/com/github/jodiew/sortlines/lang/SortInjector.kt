package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.isSortComment
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost

class SortInjector : MultiHostInjector {

    override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
        if (context is PsiComment && context.isSortComment()) {
            registrar.startInjecting(SortLanguage)
                .addPlace(null, null,
                    context as PsiLanguageInjectionHost,
                    rangeForSortComment(context))
                .doneInjecting()
        }
    }

    override fun elementsToInjectIn(): List<Class<out PsiElement?>?> =
        listOf(PsiComment::class.java)

    private fun rangeForSortComment(comment: PsiComment): TextRange {
        with (comment) {
            val startOffset = text.indexOf(string = "sort:", ignoreCase = true) + "sort:".length
            val endOffset = text.length
            return TextRange(startOffset, endOffset)
        }
    }
}
