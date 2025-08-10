package com.github.jodiew.sortlines

import com.intellij.psi.PsiComment

const val PREFIX_STR = "sort"
const val SEPARATOR_STR = ":"
val DEFAULT_ASC = listOf("asc", "↑", "🐬")
val DEFAULT_DESC = listOf("desc", "↓", "🦜")
val VALID_SORTS = DEFAULT_ASC + DEFAULT_DESC

fun PsiComment.isSortComment(): Boolean = text.contains(Regex(PREFIX_STR + SEPARATOR_STR, RegexOption.IGNORE_CASE))

fun List<String>.isSorted(order: String): Boolean = when (order) {
    in DEFAULT_ASC -> isSortedAscending()
    in DEFAULT_DESC -> isSortedDescending()
    else -> error("Invalid sort order")
}

fun List<String>.isSortedAscending(): Boolean = if (size <= 1) { true } else { zipWithNext { a, b -> a <= b }.all { it } }

fun List<String>.isSortedDescending(): Boolean = if (size <= 1) { true } else { zipWithNext { a, b -> a >= b }.all { it } }
