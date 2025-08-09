package com.github.jodiew.sortlines.lang.psi

import com.github.jodiew.sortlines.lang.SortLanguage
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import com.github.jodiew.sortlines.lang.psi.SortTypes.*

open class SortTokenType(debugName: String) : IElementType(debugName, SortLanguage)

fun tokenSetOf(vararg tokens: IElementType) = TokenSet.create(*tokens)

val SORT_KEYWORDS = tokenSetOf(
    ORDER, GROUP, SPLIT, KEY
)

val SORT_VALUES = tokenSetOf(
    STRING, REGEX, NUMBER
)

val SORT_OPERATORS = tokenSetOf(
    LBRACE, RBRACE, COLON, COMMA, FSLASH
)
