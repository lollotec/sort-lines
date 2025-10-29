package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.PREFIX_STR
import com.github.jodiew.sortlines.lang.colors.SortColor
import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.ext.*
import com.github.jodiew.sortlines.lang.psi.isSortComment
import com.github.jodiew.sortlines.toSortOrder
import com.github.jodiew.sortlines.toSortRegex
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement

class SortAnnotator: Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        val project = element.project

        // Check if the PSI element is a comment and contains the sort prefix
        if (element is PsiComment && element.isSortComment) {
            // Define the text ranges (start is inclusive, end is exclusive)
            val prefixRange = TextRange.from(
                element.textRange.startOffset + element.text.indexOf(PREFIX_STR),
                PREFIX_STR.length
            )

            // Highlight the "sort" prefix and ":" separator
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(prefixRange).textAttributes(SortColor.PREFIX.textAttributesKey).create()
        }

        // Check if the psi element is SortOptions
        if (element is SortOptions) {
            // Check if it's a block end
            if (element.end) return

            with (holder) {
                validateOption(
                    element.order != null && element.order!!.toSortOrder(project) == null,
                    "Invalid sort order",
                    element.sort ?: element,
                )
                validateOption(
                    element.group != null && element.group!!.toSortRegex() == null,
                    "Invalid group regex",
                    element.groupPattern ?: element,
                )
                validateOption(
                    element.split != null && element.split!!.toSortRegex() == null,
                    "Invalid split regex",
                    element.splitPattern ?: element,
                )
                // Don't need to check the key, it's covered by the lexer
            }
        }
    }

    private fun AnnotationHolder.validateOption(check: Boolean, message: String, element: PsiElement) {
        if (check) {
            newAnnotation(HighlightSeverity.ERROR, message)
                .range(element)
                .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                .create()
        }
    }
}