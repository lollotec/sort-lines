package com.github.jodiew.sortlines

import com.intellij.psi.PsiComment

fun PsiComment.isSortComment() =
    text.contains(Regex("sort:", RegexOption.IGNORE_CASE))