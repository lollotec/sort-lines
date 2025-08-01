package com.github.jodiew.sortlines

import com.intellij.psi.PsiComment

enum class SortOrder {
    ASC,
    DESC,
}

const val PREFIX_STR = "sort"
const val SEPARATOR_STR = ":"
val DEFAULT_ASC = listOf("asc", "↑", "🐬")
val DEFAULT_DESC = listOf("desc", "↓", "🦜")
val VALID_SORTS = DEFAULT_ASC + DEFAULT_DESC
val VALID_OPTIONS = VALID_SORTS + "end"

//val ascSortOrders = listOf("asc", "↑", "🦜")
//val descSortOrders = listOf("desc", "↓", "🐬")
//val validSortOrders = ascSortOrders + descSortOrders
//val validSortOptions = validSortOrders + "end"

fun PsiComment.isSortComment(): Boolean = text.contains(Regex(PREFIX_STR + SEPARATOR_STR, RegexOption.IGNORE_CASE))
fun PsiComment.getSortOptions(): String = text.substringAfter("sort:").trim()

fun String.isValidSortOrder(): Boolean = this in VALID_SORTS
fun String.isValidSortOption(): Boolean = this in VALID_OPTIONS
fun String.toSortOrder(): SortOrder? = when (this) {
    in DEFAULT_ASC -> SortOrder.ASC
    in DEFAULT_DESC -> SortOrder.DESC
    else -> null
}

fun <String: Comparable<String>> List<String>.isSorted(order: SortOrder?): Boolean = when (order) {
    SortOrder.ASC -> isSortedAscending()
    SortOrder.DESC -> isSortedDescending()
    else -> error("Invalid sort order")
}

fun <String: Comparable<String>> List<String>.isSortedAscending(): Boolean {
    if (size <= 1) return true
    return zipWithNext { a, b -> a <= b }.all { it }
}

fun <String: Comparable<String>> List<String>.isSortedDescending(): Boolean {
    if (size <= 1) return true
    return zipWithNext { a, b -> a >= b }.all { it }
}
