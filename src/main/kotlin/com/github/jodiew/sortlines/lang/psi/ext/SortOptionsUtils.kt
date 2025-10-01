package com.github.jodiew.sortlines.lang.psi.ext

import com.github.jodiew.sortlines.SortInfo
import com.github.jodiew.sortlines.SortType
import com.github.jodiew.sortlines.toSortOrder
import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.SortTypes
import com.github.jodiew.sortlines.settings.SortSettings

val SortOptions.end: Boolean
    get() = node.findChildByType(SortTypes.END) != null

val SortOptions.order: String?
    get() = sort?.text

val SortOptions.sortInfo: SortInfo?
    get() = if (sort != null && pattern == null && index == null) {
        SortInfo(
            type = SortType.ORDER,
            order = sort!!.text.toSortOrder(SortSettings.getInstance(project))
        )
    } else if (sort != null && pattern != null && index == null) {
        SortInfo(
            type = SortType.GROUP,
            order = sort!!.text.toSortOrder(SortSettings.getInstance(project)),
            group = Regex(pattern!!.text)
        )
    } else if (sort != null && pattern != null && index != null) {
        SortInfo(
            type = SortType.SPLIT,
            order = sort!!.text.toSortOrder(SortSettings.getInstance(project)),
            split = Regex(pattern!!.text),
            key = index!!.text.toInt()
        )
    } else {
        null
    }