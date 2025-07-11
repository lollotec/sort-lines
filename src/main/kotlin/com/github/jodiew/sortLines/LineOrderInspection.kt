package com.github.jodiew.sortLines

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.util.IntentionFamilyName
import com.intellij.codeInspection.util.IntentionName
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.endOffset
import com.intellij.psi.util.startOffset

/**
 * Implements an inspection to detect when lines are out of order in sort blocks.
 * The quick fix sorts the lines in the block.
 */
class LineOrderInspection: LocalInspectionTool() {

    private val quickFix = SortLinesQuickFix()

    /**
     * Returns if this inspection is available for the given [file].
     */
    override fun isAvailableForFile(file: PsiFile): Boolean =
        file.fileType != PlainTextFileType.INSTANCE

    /**
     * Provides a custom psi visitor that inspects the order lines between sort comments.
     * Registers problems found to [holder] and [isOnTheFly] is true if inspection was run in non-batch mode.
     * The visitor must not be recursive and must be thread-safe.
     */
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            override fun visitFile(file: PsiFile) {
                super.visitFile(file)

                val sortComments = PsiTreeUtil.findChildrenOfType(file, PsiComment::class.java)
                    .filter { it.text.contains(Regex("\\Wsort:\\W", RegexOption.IGNORE_CASE)) }

                if (sortComments.isEmpty()) return

                val document = PsiDocumentManager.getInstance(file.project).getDocument(file) ?: error("no document")

                sortComments.chunked(2) { (start, end) ->
                    val sortRange = TextRange(start.endOffset+1, end.startOffset-1)
                    val linesToCheck = document.getText(sortRange).lines()

                    val order = start.text.substringAfter("sort:").trim()

                    if(!linesToCheck.isSorted(order)) {
                        holder.registerProblem(
                            file,
                            sortRange,
                            SortLinesBundle.message("inspection.line.order.problem.descriptor"),
                            quickFix
                        )
                    }
                }
            }
        }
    }

    private fun <T: Comparable<T>> List<T>.isSorted(order: String): Boolean = when (order) {
        "asc" -> isSortedAscending()
        "desc" -> isSortedDescending()
        else -> error("invalid sort order: $order")
    }

    private fun <T: Comparable<T>> List<T>.isSortedAscending(): Boolean {
        if (size <= 1) return true
        return zipWithNext { a, b -> a <= b }.all { it }
    }

    private fun <T: Comparable<T>> List<T>.isSortedDescending(): Boolean {
        if (size <= 1) return true
        return zipWithNext { a, b -> a >= b }.all { it }
    }

    private class SortLinesQuickFix() : LocalQuickFix {
        override fun getName(): @IntentionName String =
            SortLinesBundle.message("inspection.line.order.quickfix")

        override fun getFamilyName(): @IntentionFamilyName String = name

        override fun applyFix(p0: Project, p1: ProblemDescriptor) {
            TODO("Not yet implemented")
        }
    }
}