package com.github.jodiew.sortlines

import com.intellij.psi.PsiComment

const val PREFIX_STR = "sort"
const val SEPARATOR_STR = ":"
val DEFAULT_ASC = listOf("asc", "↑", "🐬")
val DEFAULT_DESC = listOf("desc", "↓", "🦜")
val VALID_SORTS = DEFAULT_ASC + DEFAULT_DESC

fun PsiComment.isSortComment(): Boolean = text.contains(Regex(PREFIX_STR + SEPARATOR_STR, RegexOption.IGNORE_CASE))

enum class SortType {
    ORDER,
    GROUP,
    SPLIT,
}

enum class SortOrder {
    ASC,
    DESC,
}

fun String.toSortOrder(): SortOrder? = when (this) {
    in DEFAULT_ASC -> SortOrder.ASC
    in DEFAULT_DESC -> SortOrder.DESC
    else -> null
}

data class SortInfo(
    val type: SortType,
    val order: SortOrder?,
    val group: Regex? = null,
    val split: Regex? = null,
    val key: Int? = null,
)

// TODO Clean up all the sorts into a isSorted(sortInfo) -> SortType and a passed function

fun List<String>.isSorted(order: SortOrder): Boolean = when (order) {
    SortOrder.ASC -> isSortedAscending()
    SortOrder.DESC -> isSortedDescending()
}

fun List<String>.isSorted(info: SortInfo): Boolean = when (info.type) {
    SortType.ORDER -> isSorted(info.order!!)
    SortType.GROUP -> isSorted(info.order!!, info.group!!)
    SortType.SPLIT -> isSorted(info.order!!, info.split!!, info.key!!)
}

fun List<String>.isSorted(order: SortOrder, group: Regex): Boolean = when (order) {
    SortOrder.ASC -> isSortedAscending(group)
    SortOrder.DESC -> isSortedDescending(group)
}

fun List<String>.isSorted(order: SortOrder, split: Regex, key: Int): Boolean = when (order) {
    SortOrder.ASC -> isSortedAscending(split, key)
    SortOrder.DESC -> isSortedDescending(split, key)
}

fun List<String>.isSortedAscending(): Boolean = if (size <= 1) { true } else { zipWithNext { a, b -> a <= b }.all { it } }

fun List<String>.isSortedAscending(group: Regex) = if (size <= 1) { true } else {
    zipWithNext { a, b -> a.getGroup(group) <= b.getGroup(group) }.all { it }
}

fun List<String>.isSortedAscending(split: Regex, key: Int) = if (size <= 1) { true } else {
    zipWithNext { a, b -> a.getSplit(split, key) <= b.getSplit(split, key) }.all { it }
}

fun List<String>.isSortedDescending(): Boolean = if (size <= 1) { true } else { zipWithNext { a, b -> a >= b }.all { it } }

fun List<String>.isSortedDescending(group: Regex) = if (size <= 1) { true } else {
    zipWithNext { a, b -> a.getGroup(group) >= b.getGroup(group) }.all { it }
}

fun List<String>.isSortedDescending(split: Regex, key: Int) = if (size <= 1) { true } else {
    zipWithNext { a, b -> a.getSplit(split, key) >= b.getSplit(split, key) }.all { it }
}

fun String.getGroup(group: Regex): String = group.find(this)?.groupValues?.getOrNull(1) ?: error("Group pattern not found")

fun String.getSplit(splitPattern: Regex, key: Int): String = trim().split(splitPattern).getOrNull(key) ?: error("Split pattern and key combo not found")
