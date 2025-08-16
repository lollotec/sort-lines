package com.github.jodiew.sortlines.lang.colors

import com.github.jodiew.sortlines.SortBundle
import com.github.jodiew.sortlines.SortIcons
import com.github.jodiew.sortlines.highlight.SortSyntaxHighlighter
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class SortColorSettingsPage : ColorSettingsPage {

    override fun getIcon(): Icon = SortIcons.FILE

    override fun getHighlighter(): SyntaxHighlighter = SortSyntaxHighlighter()

    override fun getDemoText(): String = "{ order: asc, split: /\\./, key: 2 }"

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String?, TextAttributesKey?>? = null

    override fun getAttributeDescriptors(): Array<out AttributesDescriptor?> = ATTRS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String = SortBundle.message("settings.sort.color.name")

}

private val ATTRS: Array<AttributesDescriptor> = SortColor.entries.map { it.attributeDescriptor }.toTypedArray()
