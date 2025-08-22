package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.lang.psi.SortTypes
import com.github.jodiew.sortlines.settings.SortSettings
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
                    SortSettings.getInstance(parameters.position.project).allOrders.forEach { sort ->
                        resultSet.addElement(LookupElementBuilder.create(sort))
                    }
                }
            }
        )
    }
}