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
import com.intellij.psi.util.nextLeaf
import com.intellij.psi.util.prevLeaf
import com.intellij.psi.util.startOffset
import kotlin.math.min

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

                    val nextEmptyLine = currSortComment.nextLeaf { element ->
                        // ruby lang doesn't use the PsiWhitespace interface so have to search every element for \n\n
                        element.text.contains(Regex("\n\\h*\n"))
                    }

                    val endOffset = if (nextEmptyLine == null && nextSortComment == null) {
                        // There is no empty line or sort comment before the end of the file
                        file.lastLeaf().endOffset
                    } else if (nextSortComment != null && nextSortOptions != null && nextSortOptions.end) {
                        // The next sort comment is an end comment
                        nextSortComment.prevLeaf()!!.startOffset
                    } else if (nextEmptyLine == null) { // nextSortComment != null
                        // the next comment isn't an end comment but there is no remaining empty lines
                       nextSortComment!!.prevLeaf()!!.startOffset
                    } else if (nextSortComment == null) { // nextEmptyLine != null
                        // there are no more sort comments, but there is an empty line
                        nextEmptyLine.startOffset
                    } else { // nextEmptyLine != null && nextSortComment != null
                        // there is an empty line and a sort comment, whichever is first
                        min(nextEmptyLine.startOffset, nextSortComment.prevLeaf()!!.startOffset)
                    }

                    val sortRange = TextRange(currSortComment.endOffset+1, endOffset)

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

    private class SortLinesQuickFix(val sortInfo: SortInfo) : LocalQuickFix {
        override fun getName(): @IntentionName String =
            SortBundle.message("inspection.line.order.quickfix")

        override fun getFamilyName(): @IntentionFamilyName String = name

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