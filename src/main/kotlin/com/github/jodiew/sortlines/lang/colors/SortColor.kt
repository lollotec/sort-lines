package com.github.jodiew.sortlines.lang.colors

import com.github.jodiew.sortlines.SortBundle
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.OptionsBundle
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.util.NlsContexts
import java.util.function.Supplier

enum class SortColor(humanName: Supplier<@NlsContexts.AttributeDescriptor String>, default: TextAttributesKey? = null) {
    PREFIX(SortBundle.messagePointer("settings.com.github.jodiew.sortlines.color.prefix"), Default.KEYWORD),

    ORDER(SortBundle.messagePointer("settings.com.github.jodiew.sortlines.color.order"), Default.IDENTIFIER),
    GROUP(SortBundle.messagePointer("settings.com.github.jodiew.sortlines.color.group"), Default.IDENTIFIER),
    SPLIT(SortBundle.messagePointer("settings.com.github.jodiew.sortlines.color.split"), Default.IDENTIFIER),
    KEY(SortBundle.messagePointer("settings.com.github.jodiew.sortlines.color.key"), Default.IDENTIFIER),

    SORT(SortBundle.messagePointer("settings.com.github.jodiew.sortlines.color.sort"), Default.STRING),
    END(SortBundle.messagePointer("settings.com.github.jodiew.sortlines.color.end"), Default.STRING),
    PATTERN(SortBundle.messagePointer("settings.com.github.jodiew.sortlines.color.pattern"), Default.STRING),
    INDEX(SortBundle.messagePointer("settings.com.github.jodiew.sortlines.color.index"), Default.NUMBER),

    BRACES(OptionsBundle.messagePointer("options.language.defaults.braces"), Default.BRACES),
    COMMA(OptionsBundle.messagePointer("options.language.defaults.comma"), Default.COMMA),
    COLON(SortBundle.messagePointer("settings.com.github.jodiew.sortlines.color.colon"), Default.OPERATION_SIGN),

    BAD_CHARACTER(SortBundle.messagePointer("settings.com.github.jodiew.sortlines.color.bad.character"), HighlighterColors.BAD_CHARACTER)
    ;

    val textAttributesKey = TextAttributesKey.createTextAttributesKey("com.github.jodiew.sortlines.$name", default)
    val attributeDescriptor = AttributesDescriptor(humanName, textAttributesKey)
}