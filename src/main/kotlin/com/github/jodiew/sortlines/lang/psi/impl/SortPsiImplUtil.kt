package com.github.jodiew.sortlines.lang.psi.impl

import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.SortTypes
import com.intellij.lang.ASTNode


object SortPsiImplUtil {

    fun getSort(element: SortOptions): String? {
        val sortNode: ASTNode? = element.node.findChildByType(SortTypes.SORT)
        return sortNode?.text
    }
}
