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
        else -> {
            thisLogger().warn("Couldn't convert \"$this\" to SortOrder - available a-z sorts: ${settings.ascOrderList}, available z-a sorts: ${settings.descOrderList}")
            null
        }
    }
}

fun String.toSortRegex(): Regex? {
   try {
       val regex = Regex(this)
       return regex
   } catch (e: PatternSyntaxException) {
       thisLogger().warn("Error converting to regex \"$this\": ${e.message}")
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
    fun isSorted(lines: List<String>): Boolean = if (type == null) {
        thisLogger().warn("The sort type couldn't be determined for $this starting on the line \"${lines.first()}\"")
        true
    } else {
        try { if (lines.size <= 1) { true } else {
            lines.zipWithNext { a, b ->
                comp(
                    selector(a),
                    selector(b)
                )
            }.all { it }
        } } catch (e: SortOrderException) {
            thisLogger().warn("Exception encountered when checking line order: \"${e.message}\" for line \"${e.line}\"")
            throw e
        }
    }

    /** Returns the [lines] in the order specified by the [SortInfo] or null if the [SortInfo.order] is null */
    fun sorted(lines: List<String>): List<String>? = if (type == null) {
        thisLogger().warn("The sort type couldn't be determined for $this starting on the line \"${lines.first()}\"")
        lines
    } else {
        try {
            when (order) {
                SortOrder.ASC -> lines.sortedBy(selector)
                SortOrder.DESC -> lines.sortedByDescending(selector)
                else -> error("The order should never be null here: $type $this ${lines.first()}")
            }
        } catch (e: SortOrderException) {
            thisLogger().warn("The sort $this caused a SortOrderException: \"${e.localizedMessage}\" for the lines starting with \"${lines.first()}\"")
            null
        }
    }

    val valid: Boolean
        get() = type != null

    private val type: SortType?
        get() = if (order != null && split != null && key != null) {
            SortType.SPLIT
        } else if (order != null && group != null) {
            SortType.GROUP
        } else if (order != null) {
            SortType.ORDER
        } else {
            null
        }

    private val comp: (String, String) -> Boolean = when (order) {
        SortOrder.ASC -> { a, b -> a <= b }
        SortOrder.DESC -> { a, b -> a >= b }
        else -> { a, b -> error("The order should never be null here: $type $this $a $b ") }
    }

    private val selector: (String) -> String = try { when (type) {
        SortType.ORDER -> { a -> a }
        SortType.GROUP -> { a -> a.getGroup(group!!) }
        SortType.SPLIT -> { a -> a.getSplit(split!!, key!!) }
        else -> { a -> error("The type should never be null here: $type $this $a") }
    } } catch (e: SortOrderException) {
        throw e
    }

    private fun String.getGroup(group: Regex): String =
        group.find(this)?.groupValues?.getOrNull(1)
            ?: throw SortOrderException(SortBundle.message("inspection.com.github.jodiew.sortlines.error.group"), this)

    private fun String.getSplit(splitPattern: Regex, key: Int): String {
        val splitGroups = trim().split(splitPattern)
        if (splitGroups.size <= 1) {
            throw SortOrderException(SortBundle.message("inspection.com.github.jodiew.sortlines.error.split"), this)
        } else {
            return splitGroups.getOrNull(key) ?: throw SortOrderException(SortBundle.message("inspection.com.github.jodiew.sortlines.error.key"), this)
        }
    }
}

class SortOrderException(message: String, val line: String): IllegalStateException(message)

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

/**
 * Returns the offset of the start of the next line in the [text] after [startOffset] that has non-whitespace characters.
 * If that can't be found the [startOffset] is returned
 */
fun findLastNewline(text: String, startOffset: Int): Int =
    Regex("\n(?=[^\n]*\\S)", RegexOption.MULTILINE)
        .find(text, startOffset)?.range?.first?.inc() ?: startOffset
