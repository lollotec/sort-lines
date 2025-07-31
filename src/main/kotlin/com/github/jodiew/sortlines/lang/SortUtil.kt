package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.SortTypes

object SortUtil {
    fun SortOptions.getSort(): String? =
        node.findChildByType(SortTypes.SORT)?.text
}