package com.github.jodiew.sortLines

import com.intellij.psi.PsiComment

fun PsiComment.isSortComment(): Boolean = text.contains(Regex("\\Wsort:\\W", RegexOption.IGNORE_CASE))

fun PsiComment.getSortOptions(): String = text.substringAfter("sort:").trim()

enum class SortOrder {
    ASC,
    DESC,
}
