package com.github.jodiew.sortlines.lang.psi.ext

import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.SortTypes

val SortOptions.end: Boolean
    get() = node.findChildByType(SortTypes.END) != null

val SortOptions.order: String?
    get() = sort?.text

val SortOptions.group: String?
    get() = groupPattern?.text

val SortOptions.split: String?
    get() = splitPattern?.text

val SortOptions.key: String?
    get() = index?.text
