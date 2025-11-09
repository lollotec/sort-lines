package com.github.jodiew.sortlines.actions

import com.github.jodiew.sortlines.SortBundle
import com.github.jodiew.sortlines.SortNotifier
import com.github.jodiew.sortlines.lang.psi.forEachSort
import com.github.jodiew.sortlines.settings.OrderLinesActionOnSaveInfoProvider
import com.intellij.ide.actionsOnSave.impl.ActionsOnSaveFileDocumentManagerListener
import com.intellij.openapi.application.readAndEdtWriteAction
import com.intellij.openapi.command.executeCommand
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.util.PsiEditorUtil

class OrderLinesActionOnSave: ActionsOnSaveFileDocumentManagerListener.DocumentUpdatingActionOnSave() {
    override val presentableName: String
        get() = SortBundle.message("action.com.github.jodiew.sortlines.on.save.name.sentence")

    override fun isEnabledForProject(project: Project): Boolean =
        OrderLinesActionOnSaveInfoProvider().enabledOnSave(project)

    override suspend fun updateDocument(project: Project, document: Document) {
        readAndEdtWriteAction {
            val psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document)
            if (psiFile == null) {
                thisLogger().warn("no psi file")
                return@readAndEdtWriteAction writeAction { }
            }

            writeAction {
                psiFile.forEachSort(document) { info, range ->
                    val sortedLines = info.sorted(document.getText(range).lines())?.joinToString("\n")
                    if (sortedLines == null) {
                        thisLogger().warn("The sort $info couldn't be completed for the range $range in ${psiFile.name}")
                        SortNotifier.notifyError(project, SortBundle.message("notification.com.github.jodiew.action.on.save.error")) {
                            val fileEditorManager = FileEditorManager.getInstance(project)
                            val virtualFile = psiFile.virtualFile

                            fileEditorManager.openFile(virtualFile, true)

                            val selectedEditor = fileEditorManager.getSelectedEditor(virtualFile) as? TextEditor
                            val editor = selectedEditor?.editor ?: PsiEditorUtil.findEditor(psiFile)

                            if (editor != null) {
                                editor.caretModel.primaryCaret.moveToOffset(range.startOffset)
                                editor.scrollingModel.scrollToCaret(ScrollType.CENTER)
                            }
                        }
                        return@forEachSort
                    }

                    executeCommand(
                        project,
                        SortBundle.message(
                            "action.com.github.jodiew.sortlines.on.save.name.title"
                        )
                    ) {
                        document.replaceString(
                            range.startOffset,
                            range.endOffset,
                            sortedLines
                        )
                    }
                }
            }
        }
    }
}
