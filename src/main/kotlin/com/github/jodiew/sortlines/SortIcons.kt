package com.github.jodiew.sortlines

import com.intellij.openapi.util.IconLoader

object SortIcons {
    @JvmField
    val FILE = IconLoader.getIcon("/icons/jar-gray.png", javaClass)
    @JvmField
    val AscSort = IconLoader.getIcon("/icons/gutter/ascSort.svg", javaClass)
    @JvmField
    val DescSort = IconLoader.getIcon("/icons/gutter/descSort.svg", javaClass)
    @JvmField
    val NoSort = IconLoader.getIcon("/icons/gutter/noSort.svg", javaClass)
}