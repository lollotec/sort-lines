package com.github.jodiew.sortLines

import com.intellij.psi.PsiComment

enum class SortOrder {
    ASC,
    DESC,
}

val ascSortOrders = listOf("asc", "↑", "🦜")
val descSortOrders = listOf("desc", "↓", "🐬")
val validSortOrders = ascSortOrders + descSortOrders
val validSortOptions = validSortOrders + "end"

fun PsiComment.isSortComment(): Boolean = text.contains(Regex("\\Wsort:\\W", RegexOption.IGNORE_CASE))
fun PsiComment.getSortOptions(): String = text.substringAfter("sort:").trim()

fun String.isValidSortOrder(): Boolean = this in validSortOrders
fun String.isValidSortOption(): Boolean = this in validSortOptions
fun String.toSortOrder(): SortOrder? = when (this) {
    in ascSortOrders -> SortOrder.ASC
    in descSortOrders -> SortOrder.DESC
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
