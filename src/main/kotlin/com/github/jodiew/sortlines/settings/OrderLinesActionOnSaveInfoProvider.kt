package com.github.jodiew.sortlines.settings

import com.github.jodiew.sortlines.SortBundle
import com.intellij.codeInsight.actions.onSave.ActionOnSaveInfoBase
import com.intellij.ide.actionsOnSave.ActionOnSaveContext
import com.intellij.ide.actionsOnSave.ActionOnSaveInfo
import com.intellij.ide.actionsOnSave.ActionOnSaveInfoProvider
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project

class OrderLinesActionOnSaveInfoProvider: ActionOnSaveInfoProvider() {
    override fun getActionOnSaveInfos(context: ActionOnSaveContext): Collection<ActionOnSaveInfo?> =
        listOf(object : ActionOnSaveInfoBase(
            context,
            SortBundle.message("action.com.github.jodiew.sortlines.on.save.name.sentence"),
            SORT_LINES_ON_SAVE,
            SORT_LINES_BY_DEFAULT,
        ){})

    fun enabledOnSave(project: Project): Boolean =
        PropertiesComponent.getInstance(project).getBoolean(SORT_LINES_ON_SAVE, SORT_LINES_BY_DEFAULT)

    companion object {
        private const val SORT_LINES_ON_SAVE = "sort.lines.on.save"
        private const val SORT_LINES_BY_DEFAULT = true
    }
}
