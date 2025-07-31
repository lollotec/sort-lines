package com.github.jodiew.sortlines

import com.intellij.psi.PsiComment

object Sort {
    const val PREFIX_STR = "sort"
    const val SEPARATOR_STR = ":"
    val DEFAULT_ASC = listOf("asc", "↑", "🐬")
    val DEFAULT_DESC = listOf("desc", "↓", "🦜")
    val VALID_SORTS = DEFAULT_ASC + DEFAULT_DESC
}

fun PsiComment.isSortComment() =
    text.contains(Regex(Sort.PREFIX_STR + Sort.SEPARATOR_STR, RegexOption.IGNORE_CASE))
