package com.github.jodiew.sortlines.actions

import com.github.jodiew.sortlines.SortBundle
import com.github.jodiew.sortlines.SortNotifier
import com.github.jodiew.sortlines.lang.psi.forEachSort
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.fileTypes.PlainTextFileType
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.psi.util.PsiEditorUtil

internal class OrderLinesAction : DumbAwareAction() {
    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        val project = e.getData(CommonDataKeys.PROJECT)
        val editor = e.getData(CommonDataKeys.EDITOR)
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)

        e.presentation.isEnabledAndVisible =
            project != null &&
            editor != null &&
            psiFile != null &&
            psiFile.fileType != PlainTextFileType.INSTANCE
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(CommonDataKeys.PROJECT) ?: error("no project")
        val editor = e.getData(CommonDataKeys.EDITOR) ?: error("no editor")
        val psiFile = e.getData(CommonDataKeys.PSI_FILE) ?: error("no psi file")

        val document = editor.document

        WriteCommandAction.runWriteCommandAction(project) {
            psiFile.forEachSort(document) { info, range ->
                val sortedLines = info.sorted(document.getText(range).lines())?.joinToString("\n")
                if (sortedLines == null) {
                    thisLogger().warn("The sort $info couldn't be completed for the range $range in ${psiFile.name}")
                    SortNotifier.notifyError(project, SortBundle.message("notification.com.github.jodiew.action.error")) {
                        val editor: Editor? = PsiEditorUtil.findEditor(psiFile)

                        if (editor != null) {
                            editor.caretModel.primaryCaret.moveToOffset(range.startOffset)
                            editor.scrollingModel.scrollToCaret(ScrollType.CENTER)
                        }
                    }
                    return@forEachSort
                }
                document.replaceString(
                    range.startOffset,
                    range.endOffset,
                    sortedLines
                )
            }
        }
    }
}