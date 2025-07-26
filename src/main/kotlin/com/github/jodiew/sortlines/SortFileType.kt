package com.github.jodiew.sortlines

import com.intellij.openapi.fileTypes.LanguageFileType

object SortFileType : LanguageFileType(SortLanguage) {

    override fun getName() = "Sort File"

    override fun getDescription() = "Sort language file"

    override fun getDefaultExtension() = "sort"

    override fun getIcon() = SortIcons.FILE
}