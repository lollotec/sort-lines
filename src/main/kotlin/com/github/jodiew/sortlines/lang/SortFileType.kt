package com.github.jodiew.sortlines.lang

import com.intellij.openapi.fileTypes.LanguageFileType

object SortFileType : LanguageFileType(SortLanguage) {

    override fun getName() = "Sort File"

    override fun getDescription() = "Sort language file"

    override fun getDefaultExtension() = "sort"

    override fun getIcon() = null
}
