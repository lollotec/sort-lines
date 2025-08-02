package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.lang.psi.SortColor
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class SortColorSettingsPage : ColorSettingsPage {

    override fun getIcon(): Icon? = SortIcons.FILE

    override fun getHighlighter(): SyntaxHighlighter = SortSyntaxHighlighter()

    override fun getDemoText(): String = "asc"

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String?, TextAttributesKey?>? = null

    override fun getAttributeDescriptors(): Array<out AttributesDescriptor?> = ATTRS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String ="Sort"

}

private val ATTRS: Array<AttributesDescriptor> = SortColor.entries.map { it.attributeDescriptor }.toTypedArray()
