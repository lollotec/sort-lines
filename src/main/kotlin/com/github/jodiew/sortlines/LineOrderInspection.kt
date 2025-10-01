package com.github.jodiew.sortlines

import com.github.jodiew.sortlines.lang.psi.forEachSort
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
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile

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
     * Provides a custom psi visitor that inspects the line orders for sort comments.
     * Registers problems found to [holder] and [isOnTheFly] is true if inspection was run in non-batch mode.
     * The visitor must not be recursive and must be thread-safe.
     */
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            override fun visitFile(file: PsiFile) {
                super.visitFile(file)

                val document = PsiDocumentManager.getInstance(file.project).getDocument(file) ?: error("No document")

                file.forEachSort(document) { sortInfo, sortRange ->
                    val linesToCheck = document.getText(sortRange).lines()

                    if(!sortInfo.isSorted(linesToCheck)) {
                        holder.registerProblem(
                            file,
                            sortRange,
                            SortBundle.message("inspection.com.github.jodiew.sortlines.line.order.problem.descriptor"),
                            SortLinesQuickFix(sortInfo)
                        )
                    }
                }
            }
        }
    }

    /**
     * Quick fix to sort lines based on a [sortInfo]
     */
    private class SortLinesQuickFix(val sortInfo: SortInfo) : LocalQuickFix {
        override fun getName(): @IntentionName String =
            SortBundle.message("inspection.com.github.jodiew.sortlines.line.order.quickfix")

        override fun getFamilyName(): @IntentionFamilyName String = name

        /**
         * Applies the sort lines fix to the using information from [project] and [descriptor]
         */
        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val document: Document = PsiDocumentManager.getInstance(project).getDocument(descriptor.psiElement as PsiFile)
                ?: error("No document to apply fix to")

            val sortRange = descriptor.textRangeInElement
            val unsortedLines = document.getText(sortRange).lines()

            val sortedLines = sortInfo.sorted(unsortedLines) ?: error("invalid sort")

            WriteCommandAction.runWriteCommandAction(project) {
                document.replaceString(
                    sortRange.startOffset,
                    sortRange.endOffset,
                    sortedLines.joinToString("\n"))
            }
        }
    }
}