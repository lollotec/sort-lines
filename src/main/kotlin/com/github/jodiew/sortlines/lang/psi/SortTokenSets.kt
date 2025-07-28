package com.github.jodiew.sortlines.lang.psi

import com.intellij.psi.tree.TokenSet

interface SortTokenSets {
    val SORT: TokenSet
        get() = TokenSet.create(SortTypes.SORT)
}
