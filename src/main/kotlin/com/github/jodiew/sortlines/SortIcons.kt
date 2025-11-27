package com.github.jodiew.sortlines

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object SortIcons {
    @JvmField
    val AscSort: Icon = IconLoader.getIcon("/icons/gutter/ascSort.svg", javaClass)
    @JvmField
    val DescSort: Icon = IconLoader.getIcon("/icons/gutter/descSort.svg", javaClass)
    @JvmField
    val NoSort: Icon = IconLoader.getIcon("/icons/gutter/noSort.svg", javaClass)
}
