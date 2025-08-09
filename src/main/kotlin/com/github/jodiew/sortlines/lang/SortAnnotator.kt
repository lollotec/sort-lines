package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.PREFIX_STR
import com.github.jodiew.sortlines.SEPARATOR_STR
import com.github.jodiew.sortlines.VALID_SORTS
import com.github.jodiew.sortlines.isSortComment
import com.github.jodiew.sortlines.lang.colors.SortColor
import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.ext.end
import com.github.jodiew.sortlines.lang.psi.ext.order
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement

class SortAnnotator: Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        // Check if the PSI element is a comment and contains the sort prefix
        if (element is PsiComment && element.isSortComment()) {
            // Define the text ranges (start is inclusive, end is exclusive)
            val prefixRange = TextRange.from(
                element.textRange.startOffset + element.text.indexOf(PREFIX_STR),
                PREFIX_STR.length
            )
            val separatorRange = TextRange.from(prefixRange.endOffset, SEPARATOR_STR.length)

            // Highlight the "sort" prefix and ":" separator
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(prefixRange).textAttributes(SortColor.PREFIX.textAttributesKey).create()
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(separatorRange).textAttributes(SortColor.COLON.textAttributesKey).create()
        }

        // Check if the psi element is SortOptions
        if (element is SortOptions) {
            // Check if it's a block end
            if (element.end) return

            // Check the sort order
            if (element.order !in VALID_SORTS) {
                holder.newAnnotation(HighlightSeverity.ERROR, "Invalid sort order")
                    .range(element.textRange)
                    .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                    // TODO: Add a quick fix for the sort order
                    // .withFix()
                    .create()
            }
        }
    }
}