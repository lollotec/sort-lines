package com.github.jodiew.sortlines.lang

import com.intellij.lang.Language

object SortLanguage : Language("Sort") {
    @Suppress("unused")
    private fun readResolve(): Any = SortLanguage
}
