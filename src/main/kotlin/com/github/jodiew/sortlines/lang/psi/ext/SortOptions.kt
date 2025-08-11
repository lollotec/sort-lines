package com.github.jodiew.sortlines.lang.psi.ext

import com.github.jodiew.sortlines.SortInfo
import com.github.jodiew.sortlines.SortType
import com.github.jodiew.sortlines.toSortOrder
import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.SortTypes

val SortOptions.end: Boolean
    get() = node.findChildByType(SortTypes.END) != null

val SortOptions.order: String?
    get() = sort?.text

val SortOptions.sortInfo: SortInfo?
    get() = if (sort != null && pattern == null && index == null) {
        SortInfo(
            type = SortType.ORDER,
            order = sort!!.text.toSortOrder()
        )
    } else if (sort != null && pattern != null && index == null) {
        SortInfo(
            type = SortType.GROUP,
            order = sort!!.text.toSortOrder(),
            group = Regex(pattern!!.text)
        )
    } else if (sort != null && pattern != null && index != null) {
        SortInfo(
            type = SortType.SPLIT,
            order = sort!!.text.toSortOrder(),
            split = Regex(pattern!!.text),
            key = index!!.text.toInt()
        )
    } else {
        null
    }