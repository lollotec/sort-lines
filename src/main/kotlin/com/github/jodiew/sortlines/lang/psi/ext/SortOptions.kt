package com.github.jodiew.sortlines.lang.psi.ext

import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.SortTypes

val SortOptions.sort: String?
    get() = node.findChildByType(SortTypes.SORT)?.text

val SortOptions.isGroup: Boolean
    get() = node.findChildByType(SortTypes.GROUP) != null

val SortOptions.isSplit: Boolean
    get() = node.findChildByType(SortTypes.SPLIT) != null

val SortOptions.pattern: String?
    get() = node.findChildByType(SortTypes.PATTERN)?.text

val SortOptions.index: Int?
    get() = node.findChildByType(SortTypes.INDEX)?.text?.toInt()
