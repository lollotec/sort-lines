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
                    .filter { it.isSortComment() }

                if (sortComments.isEmpty()) return

                val document = PsiDocumentManager.getInstance(file.project).getDocument(file) ?: error("no document")

                sortComments.windowed(2, 1, true) {
                    val curr = it[0]
                    val next = it.getOrNull(1)
                    val currSortOption = curr.getSortOptions()
                    if (currSortOption.isValidSortOption()) {
                        val nextSortOption = next?.getSortOptions()
                        if (currSortOption.isValidSortOrder()) {
                            val currSortOrder = currSortOption.toSortOrder()
                            if (nextSortOption == "end") {
                                val sortRange = TextRange(curr.endOffset+1, next.prevSibling.startOffset)
                                val linesToCheck = document.getText(sortRange).lines()
                                if(!linesToCheck.isSorted(currSortOrder)) {
                                    holder.registerProblem(
                                        file,
                                        sortRange,
                                        SortLinesBundle.message("inspection.line.order.problem.descriptor"),
                                        SortLinesQuickFix(currSortOrder)
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

    private class SortLinesQuickFix(val sortOrder: SortOrder?) : LocalQuickFix {
        override fun getName(): @IntentionName String =
            SortLinesBundle.message("inspection.line.order.quickfix")

        override fun getFamilyName(): @IntentionFamilyName String = name

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val document: Document = PsiDocumentManager.getInstance(project).getDocument(descriptor.psiElement.containingFile)
                ?: TODO("Something about invoking the intention on a copy of the file?")

            val sortRange = descriptor.textRangeInElement

            val unsortedLines = document.getText(descriptor.textRangeInElement).lines()

            val sortedLines = when (sortOrder) {
                SortOrder.ASC -> unsortedLines.sorted()
                SortOrder.DESC -> unsortedLines.sortedDescending()
                else -> error("invalid sort order")
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