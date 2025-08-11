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
) {
    fun isSorted(lines: List<String>): Boolean = if (lines.size <= 1) { true } else {
        lines.zipWithNext { a, b -> comp(transform(a), transform(b)) }.all { it }
    }

    private fun String.getGroup(group: Regex): String = group.find(this)?.groupValues?.getOrNull(1) ?: error("Group pattern not found")

    private fun String.getSplit(splitPattern: Regex, key: Int): String = trim().split(splitPattern).getOrNull(key) ?: error("Split pattern and key combo not found")

    private val comp: (String, String) -> Boolean = when (order) {
        SortOrder.ASC -> { a, b -> a <= b }
        SortOrder.DESC -> { a, b -> a >= b }
        else -> { _, _ -> true }
    }

    private val transform: (String) -> String = when (type) {
        SortType.ORDER -> { a -> a }
        SortType.GROUP -> { a -> if (group != null) { a.getGroup(group) } else { a } }
        SortType.SPLIT -> { a -> if (split != null && key != null) { a.getSplit(split, key) } else { a } }
    }
}
