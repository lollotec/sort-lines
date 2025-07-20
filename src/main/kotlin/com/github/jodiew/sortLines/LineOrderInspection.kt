package com.github.jodiew.sortLines

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.util.IntentionFamilyName
import com.intellij.codeInspection.util.IntentionName
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
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

    /**
     * Returns if this inspection is available for the given [file].
     */
    override fun isAvailableForFile(file: PsiFile): Boolean =
        file.fileType != PlainTextFileType.INSTANCE

    private val validSortOrders = listOf("asc", "↑", "desc", "↓")

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

                sortComments.windowed(2, 1, true) {
                    val curr = it[0]
                    val next = it.getOrNull(1)
                    val currSortOption = curr.text.substringAfter("sort:").trim()
                    if (currSortOption in validSortOrders + "end") {
                        val nextSortOption = next?.text?.substringAfter("sort:")?.trim()
                        if (currSortOption in validSortOrders) {
                            if (nextSortOption == "end") {
                                val sortRange = TextRange(curr.endOffset+1, next.prevSibling.startOffset)
                                val linesToCheck = document.getText(sortRange).lines()
                                if(!linesToCheck.isSorted(currSortOption)) {
                                    holder.registerProblem(
                                        file,
                                        sortRange,
                                        SortLinesBundle.message("inspection.line.order.problem.descriptor"),
                                        SortLinesQuickFix(currSortOption)
                                    )
                                }
                            } else {
                                holder.registerProblem(
                                    curr,
                                    SortLinesBundle.message("inspection.line.order.no.end.comment")
                                )
                            }
                        } else if (nextSortOption == "end") { // currSortOption == "end"
                            holder.registerProblem(
                                next,
                                SortLinesBundle.message("inspection.line.order.no.start.comment")
                            )
                        } else if (next == null && sortComments.size == 1) {
                            holder.registerProblem(
                                curr,
                                SortLinesBundle.message("inspection.line.order.no.start.comment")
                            )
                        }
                    } else {
                        holder.registerProblem(
                            curr,
                            SortLinesBundle.message("inspection.line.order.invalid.option")
                        )
                    }
                }
            }
        }
    }

    private fun <String: Comparable<String>> List<String>.isSorted(order: String): Boolean = when (order) {
        "asc" -> isSortedAscending()
        "↑" -> isSortedAscending()
        "desc" -> isSortedDescending()
        "↓" -> isSortedDescending()
        else -> error("invalid sort order: $order")
    }

    private fun <String: Comparable<String>> List<String>.isSortedAscending(): Boolean {
        if (size <= 1) return true
        return zipWithNext { a, b -> a <= b }.all { it }
    }

    private fun <String: Comparable<String>> List<String>.isSortedDescending(): Boolean {
        if (size <= 1) return true
        return zipWithNext { a, b -> a >= b }.all { it }
    }

    private class SortLinesQuickFix(val sortOptions: String) : LocalQuickFix {
        override fun getName(): @IntentionName String =
            SortLinesBundle.message("inspection.line.order.quickfix")

        override fun getFamilyName(): @IntentionFamilyName String = name

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val document: Document = PsiDocumentManager.getInstance(project).getDocument(descriptor.psiElement.containingFile)
                ?: error("no document")

            val sortRange = descriptor.textRangeInElement

            val unsortedLines = document.getText(descriptor.textRangeInElement).lines()

            val sortedLines = when (sortOptions) {
                "asc" -> unsortedLines.sorted()
                "↑" -> unsortedLines.sorted()
                "desc" -> unsortedLines.sortedDescending()
                "↓" -> unsortedLines.sortedDescending()
                else -> error("invalid sort options: $sortOptions")
            }
            WriteCommandAction.runWriteCommandAction(project) {
                document.replaceString(
                    sortRange.startOffset,
                    sortRange.endOffset,
                    sortedLines.joinToString("\n"))
            }
        }
    }
}