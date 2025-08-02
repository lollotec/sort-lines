package com.github.jodiew.sortlines.lang.psi

import com.github.jodiew.sortlines.lang.SortFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory

object SortElementFactory {

    fun createSortOptions(project: Project, sort: String): SortOptions {
        val file: SortFile = createFile(project, sort)
        return file.firstChild as SortOptions
    }

    fun createFile(project: Project, text: String): SortFile =
        PsiFileFactory.getInstance(project).createFileFromText("dummy.sort", SortFileType, text) as SortFile
}