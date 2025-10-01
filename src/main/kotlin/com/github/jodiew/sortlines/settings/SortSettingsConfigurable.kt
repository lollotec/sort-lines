package com.github.jodiew.sortlines.settings

import com.github.jodiew.sortlines.SortBundle
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.options.BoundSearchableConfigurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.*
import com.intellij.util.FileContentUtil

internal class SortSettingsConfigurable(private val project: Project): BoundSearchableConfigurable(
    SortBundle.message("settings.com.github.jodiew.sortlines.name"),
    SortBundle.message("settings.com.github.jodiew.sortlines.name"),
    _id = ID
) {
    private val settings
        get() = SortSettings.getInstance(project)

    override fun apply() {
        super.apply()
        // Need this to have the correct highlighting and gutter icon shown in open files
        FileContentUtil.reparseOpenedFiles()
        FileEditorManager.getInstance(project).openFiles.forEach { it.refresh(true, false) }
    }

    override fun createPanel(): DialogPanel =
        panel {
            row(label = SortBundle.message("settings.com.github.jodiew.sortlines.order.asc")) {
                textField()
                    .bindText(settings::ascOrderList)
            }
            row(label = SortBundle.message("settings.com.github.jodiew.sortlines.order.desc")) {
                textField()
                    .bindText(settings::descOrderList)
            }
        }

    companion object {
        const val ID = "Settings.Sort"
    }
}