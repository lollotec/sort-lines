package com.github.jodiew.sortlines.lang.psi

import com.intellij.psi.tree.TokenSet

interface SortTokenSets {
    val sort: TokenSet
        get() = TokenSet.create(SortTypes.SORT)

    val end: TokenSet
        get() = TokenSet.create(SortTypes.END)
}
