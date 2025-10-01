package com.github.jodiew.sortlines.actions

import com.github.jodiew.sortlines.SortBundle
import com.github.jodiew.sortlines.lang.psi.forEachSort
import com.github.jodiew.sortlines.settings.OrderLinesActionOnSaveInfoProvider
import com.intellij.ide.actionsOnSave.impl.ActionsOnSaveFileDocumentManagerListener
import com.intellij.openapi.application.readAndEdtWriteAction
import com.intellij.openapi.command.executeCommand
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager

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
                        thisLogger().warn("Something went wrong with the sort")
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
