package com.github.jodiew.sortlines.lang.psi

import com.github.jodiew.sortlines.lang.SortFileType
import com.github.jodiew.sortlines.lang.SortLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider

class SortFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, SortLanguage) {

    override fun getFileType() = SortFileType

    override fun toString() = "Sort File"
}
