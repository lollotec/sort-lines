package com.github.jodiew.sortlines.lang.psi.ext

import com.github.jodiew.sortlines.lang.psi.SortElementFactory
import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.SortTypes

var SortOptions.sort: String?
    get() = node.findChildByType(SortTypes.SORT)?.text
    set(value) {
        val sortNode = node.findChildByType(SortTypes.SORT)
        if (sortNode != null) {
            val options = SortElementFactory.createSortOptions(project, value)
            val newSortNode = options.firstChild.node
            node.replaceChild(sortNode, newSortNode)
        }
    }

val SortOptions.isEnd: Boolean
    get() = node.findChildByType(SortTypes.END) != null

val SortOptions.isGroup: Boolean
    get() = node.findChildByType(SortTypes.GROUP) != null

val SortOptions.isSplit: Boolean
    get() = node.findChildByType(SortTypes.SPLIT) != null

val SortOptions.pattern: String?
    get() = node.findChildByType(SortTypes.PATTERN)?.text

val SortOptions.index: Int?
    get() = node.findChildByType(SortTypes.INDEX)?.text?.toInt()
