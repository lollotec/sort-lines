package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.DEFAULT_ASC
import com.github.jodiew.sortlines.DEFAULT_DESC
import com.github.jodiew.sortlines.lang.psi.SortTypes
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext

class SortCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(SortTypes.STRING),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    resultSet: CompletionResultSet,
                ) {
                    (DEFAULT_ASC + DEFAULT_DESC).forEach { sort ->
                        resultSet.addElement(LookupElementBuilder.create(sort))
                    }
                }
            }
        )
    }
}