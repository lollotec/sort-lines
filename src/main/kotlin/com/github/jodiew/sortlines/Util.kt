package com.github.jodiew.sortlines

import com.github.jodiew.sortlines.settings.SortSettings
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import java.util.regex.PatternSyntaxException

const val PREFIX_STR = "sort:"

enum class SortType {
    ORDER,
    GROUP,
    SPLIT,
}

enum class SortOrder {
    ASC,
    DESC,
}

/** Returns the [SortOrder] of the string, otherwise null */
fun String.toSortOrder(project: Project): SortOrder? {
    val settings = SortSettings.getInstance(project)
    return when (this) {
        in settings.ascOrderList.split(", ") -> SortOrder.ASC
        in settings.descOrderList.split(", ") -> SortOrder.DESC
        else -> null
    }
}

fun String.toSortRegex(): Regex? {
   try {
       val regex = Regex(this)
       return regex
   } catch (e: PatternSyntaxException) {
       thisLogger().warn("Error converting to regex \"$this\": $e")
       return null
   }
}

data class SortInfo(
    val order: SortOrder?,
    val group: Regex? = null,
    val split: Regex? = null,
    val key: Int? = null,
) {
    /** Returns true if the [lines] are sorted according to the [SortInfo], otherwise false */
    fun isSorted(lines: List<String>): Boolean = if (lines.size <= 1) { true } else {
        lines.zipWithNext { a, b -> comp(selector(a), selector(b)) }.all { it }
    }

    /** Returns the [lines] in the order specified by the [SortInfo] or null if the [SortInfo.order] is null */
    fun sorted(lines: List<String>): List<String>? = when (order) {
        SortOrder.ASC -> lines.sortedBy(selector)
        SortOrder.DESC -> lines.sortedByDescending(selector)
        else -> null
    }

    private val type: SortType
        get() = if (split != null && key != null) {
            SortType.SPLIT
        } else if (group != null) {
            SortType.GROUP
        } else {
            SortType.ORDER
        }

    private val comp: (String, String) -> Boolean = when (order) {
        SortOrder.ASC -> { a, b -> a <= b }
        SortOrder.DESC -> { a, b -> a >= b }
        else -> { _, _ -> true }
    }

    private val selector: (String) -> String = when (type) {
        SortType.ORDER -> { a -> a }
        SortType.GROUP -> { a -> if (group != null) { a.getGroup(group) } else { a } }
        SortType.SPLIT -> { a -> if (split != null && key != null) { a.getSplit(split, key) } else { a } }
    }

    private fun String.getGroup(group: Regex): String = group.find(this)?.groupValues?.getOrNull(1) ?: error("Group pattern not found")

    private fun String.getSplit(splitPattern: Regex, key: Int): String = trim().split(splitPattern).getOrNull(key) ?: error("Split pattern and key combo not found")
}

/**
 * Returns the offset of end of the line before an indent change in [text] after [startOffset].
 * If there is no indent change before [initialEndOffset] then that offset is returned.
 */
fun findIndentChangeOffset(text: String, startOffset: Int, initialEndOffset: Int): Int {
    val initialMatch = Regex("^\\s*", RegexOption.MULTILINE)
        .find(text, startOffset) ?: return initialEndOffset

    return generateSequence(initialMatch) { it.next() }
        .firstOrNull { match ->
            match.range.first < initialEndOffset && match.value != initialMatch.value
        }?.range?.first?.minus(1) ?: initialEndOffset
}
