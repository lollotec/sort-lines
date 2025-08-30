package com.github.jodiew.sortlines

import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.ext.end
import com.github.jodiew.sortlines.lang.psi.ext.sortInfo
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.util.IntentionFamilyName
import com.intellij.codeInspection.util.IntentionName
import com.intellij.lang.injection.InjectedLanguageManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.endOffset
import com.intellij.psi.util.lastLeaf
import com.intellij.psi.util.prevLeaf
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
     * Provides a custom psi visitor that inspects the line orders for sort comments.
     * Registers problems found to [holder] and [isOnTheFly] is true if inspection was run in non-batch mode.
     * The visitor must not be recursive and must be thread-safe.
     */
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : PsiElementVisitor() {
            override fun visitFile(file: PsiFile) {
                super.visitFile(file)
                val sortComments = PsiTreeUtil.collectElementsOfType(
                    file,
                    PsiLanguageInjectionHost::class.java).filter{ it is PsiComment && it.isSortComment() }
                if (sortComments.isEmpty()) return

                val sortCommentOptions = sortComments.fold(listOf<Pair<PsiComment, SortOptions>>()) { result, languageInjectionHost ->
                    val injected = InjectedLanguageManager.getInstance(file.project).getInjectedPsiFiles(languageInjectionHost)
                    val sortFile = injected?.getOrNull(0)?.first ?: return
                    val sortOptions = PsiTreeUtil.findChildOfType(sortFile, SortOptions::class.java) ?: return
                    return@fold result + Pair(languageInjectionHost as PsiComment, sortOptions)
                }

                val document = PsiDocumentManager.getInstance(file.project).getDocument(file) ?: error("No document")

                sortCommentOptions.windowed(2, 1, true) {
                    val (currSortComment, currSortOptions) = it[0]
                    val (nextSortComment, nextSortOptions) = it.getOrNull(1) ?: Pair(null, null)

                    if (currSortOptions.end) return@windowed

                    val sortInfo = currSortOptions.sortInfo ?: return@windowed

                    if (sortInfo.order == null) return@windowed

                    val startOffset = currSortComment.endOffset+1
                    // Find the next sort comment or the end of the file
                    val initialEndOffset = if (nextSortComment != null && nextSortOptions != null) {
                        // this assumes that the previous leaf is the whitespace element containing the new line
                        nextSortComment.prevLeaf()!!.startOffset
                    } else {
                        file.lastLeaf().endOffset
                    }

                    val endOffset = findIndentChangeOffset(document.text, startOffset, initialEndOffset)

                    if (endOffset <= startOffset) return@windowed

                    val sortRange = TextRange(startOffset, endOffset)
                    val linesToCheck = document.getText(sortRange).lines()

                    if(!sortInfo.isSorted(linesToCheck)) {
                        holder.registerProblem(
                            file,
                            sortRange,
                            SortBundle.message("inspection.line.order.problem.descriptor"),
                            SortLinesQuickFix(sortInfo)
                        )
                    }
                }
            }
        }
    }

    /**
     * Returns the offset of end of the line before an indent change in [text] after [startOffset].
     * If there is no indent change before [initialEndOffset] then that offset is returned.
     */
    private fun findIndentChangeOffset(text: String, startOffset: Int, initialEndOffset: Int): Int {
        val initialMatch = Regex("^.*$", RegexOption.MULTILINE).find(text, startOffset) ?: return initialEndOffset
        val initialIndent = text.substring(startOffset).takeWhile { it.isWhitespace() }

        return (generateSequence(initialMatch.next()) { it.next() }
            .takeWhile { it.range.first < initialEndOffset }
            .firstOrNull { lineMatch ->
                val currentIndent = lineMatch.value.takeWhile { it.isWhitespace() }
                currentIndent != initialIndent
            }?.range?.first?.minus(1)) ?: initialEndOffset
    }

    /**
     * Quick fix to sort lines based on a [sortInfo]
     */
    private class SortLinesQuickFix(val sortInfo: SortInfo) : LocalQuickFix {
        override fun getName(): @IntentionName String =
            SortBundle.message("inspection.line.order.quickfix")

        override fun getFamilyName(): @IntentionFamilyName String = name

        /**
         * Applies the sort lines fix to the using information from [project] and [descriptor]
         */
        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val document: Document = PsiDocumentManager.getInstance(project).getDocument(descriptor.psiElement as PsiFile)
                ?: error("No document to apply fix to")

            val sortRange = descriptor.textRangeInElement
            val unsortedLines = document.getText(descriptor.textRangeInElement).lines()

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