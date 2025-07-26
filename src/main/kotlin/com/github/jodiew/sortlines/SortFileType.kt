package com.github.jodiew.sortlines

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.util.NlsSafe
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

class SortFileType : LanguageFileType(SortLanguage.INSTANCE) {
    companion object {
        val INSTANCE = SortFileType()
    }

    override fun getName(): @NonNls String = "Sort File"

    override fun getDescription(): @NlsContexts.Label String = "Sort language file"

    override fun getDefaultExtension(): @NlsSafe String = "sort"

    override fun getIcon(): Icon? = SortIcons.FILE
}