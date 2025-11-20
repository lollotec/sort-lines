package com.github.jodiew.sortlines.lang.psi

import com.github.jodiew.sortlines.PREFIX_STR
import com.github.jodiew.sortlines.SortInfo
import com.github.jodiew.sortlines.findIndentChangeOffset
import com.github.jodiew.sortlines.lang.psi.ext.*
import com.github.jodiew.sortlines.toSortOrder
import com.github.jodiew.sortlines.toSortRegex
import com.intellij.lang.injection.InjectedLanguageManager
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.endOffset
import com.intellij.psi.util.lastLeaf
import com.intellij.psi.util.prevLeaf
import com.intellij.psi.util.startOffset

/** The list of sort comments in the [PsiFile] */
val PsiFile.sortComments: List<PsiComment>
    @Suppress("UNCHECKED_CAST")
    get() = PsiTreeUtil.collectElementsOfType(
        this,
        PsiLanguageInjectionHost::class.java).filter{ it is PsiComment && it.isSortComment } as List<PsiComment>

/** If the [PsiComment] contains the sort prefix */
val PsiComment.isSortComment: Boolean
    get() = text.contains(Regex(PREFIX_STR, RegexOption.IGNORE_CASE))

/** The [SortOptions] for the [PsiComment], if it isn't a valid sort comment returns null */
val PsiComment.sortOptions: SortOptions?
    get() {
        if (!isSortComment) return null
        val injected = InjectedLanguageManager.getInstance(project).getInjectedPsiFiles(this)
        val sortFile = injected?.getOrNull(0)?.first ?: return null
        return PsiTreeUtil.findChildOfType(sortFile, SortOptions::class.java)
    }

/** Runs [action] for each sort block in the [PsiFile] */
fun PsiFile.forEachSort(document: Document, action: (SortInfo, TextRange) -> Unit) {
    val sortComments = this.sortComments

    if (sortComments.isEmpty()) return

    // TODO: Something about this is weird, you have to collect all the sort options first if you are calling this from OrderLinesAction
    // I think its something about the document write action makes the psi tree break
    sortComments.map { psiComment ->
        Pair(psiComment, psiComment.sortOptions)
    }.windowed(2, 1, true) {
        val (currSortComment, currSortOptions) = it[0]
        val (nextSortComment, nextSortOptions) = it.getOrNull(1) ?: Pair(null, null)

        if (currSortOptions == null) {
            action(SortInfo(null), currSortComment.textRange)
            return@windowed
        }

        if (currSortOptions.end) return@windowed

        val sortOrder = currSortOptions.order?.toSortOrder(project)

        val groupRegex = currSortOptions.group?.toSortRegex()

        val splitRegex = currSortOptions.split?.toSortRegex()

        val keyInt = currSortOptions.key?.toIntOrNull()

        val sortInfo = SortInfo(sortOrder, groupRegex, splitRegex, keyInt)

        if (sortOrder == null) {
            action(sortInfo, currSortComment.textRange)
            return@windowed
        }

        val startOffset = currSortComment.endOffset+1
        // Find the next sort comment or the end of the file
        val initialEndOffset = if (nextSortComment != null && nextSortOptions != null) {
            // this assumes that the previous leaf is the whitespace element containing the new line
            nextSortComment.prevLeaf()!!.startOffset
        } else {
            // this handles a trailing newline at the end of the document
            val lastLeaf = lastLeaf()
            if (lastLeaf.text == "\n") {
                lastLeaf.endOffset.minus(1)
            } else {
                lastLeaf.endOffset
            }
        }

        if (initialEndOffset <= startOffset) return@windowed

        val endOffset = if (nextSortOptions != null && nextSortOptions.end) {
            // the next sort option is "end"
            initialEndOffset
        } else {
            findIndentChangeOffset(document.text, startOffset, initialEndOffset)
        }

        if (endOffset <= startOffset) return@windowed

        val sortRange = TextRange(startOffset, endOffset)

        action(sortInfo, sortRange)
    }
}
