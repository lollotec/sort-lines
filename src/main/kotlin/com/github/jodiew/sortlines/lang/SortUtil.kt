package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.SortTypes
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.GlobalSearchScopes
import com.intellij.psi.util.PsiTreeUtil

object SortUtil {
//    fun SortOptions.getSort(): String? =
//        node.findChildByType(SortTypes.SORT)?.text
//
//    fun SortOptions.isEnd(): Boolean =
//        node.findChildByType(SortTypes.END) !== null

    fun findSortOptions(project: Project): List<SortOptions> =
        FileTypeIndex.getFiles(SortFileType, GlobalSearchScope.allScope(project))
            .fold(emptyList()) { result, virtualFile ->
                val simpleFile = PsiManager.getInstance(project).findFile(virtualFile)
                return if (simpleFile != null) {
                    val properties = PsiTreeUtil.getChildrenOfType(simpleFile, SortOptions::class.java)
                    if (properties != null) {
                        result + properties
                    } else {
                        result
                    }
                } else {
                    result
                }
            }
}