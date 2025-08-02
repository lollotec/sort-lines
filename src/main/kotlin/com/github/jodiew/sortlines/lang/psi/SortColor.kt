package com.github.jodiew.sortlines.lang.psi

import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default

enum class SortColor(humanName: @NlsContexts.AttributeDescriptor String, default: TextAttributesKey? = null) {
    SORT("sort", Default.STRING),
    END("end", Default.STRING),
    BAD_CHARACTER("badCharacter", HighlighterColors.BAD_CHARACTER),
    PREFIX("prefix", Default.KEYWORD),
    COLON("colon", Default.OPERATION_SIGN),
    ;

    val textAttributesKey = TextAttributesKey.createTextAttributesKey("com.github.jodiew.sortlines.$name", default)
    val attributeDescriptor = AttributesDescriptor(humanName, textAttributesKey)
//    val testSeverity = HighlightSeverity(name, HighlightSeverity.INFORMATION.myVal)
}