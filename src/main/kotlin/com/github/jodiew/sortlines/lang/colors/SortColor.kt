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
    PREFIX(SortBundle.messagePointer("settings.sort.color.prefix"), Default.KEYWORD),

    ORDER(SortBundle.messagePointer("settings.sort.color.order"), Default.IDENTIFIER),
    GROUP(SortBundle.messagePointer("settings.sort.color.group"), Default.IDENTIFIER),
    SPLIT(SortBundle.messagePointer("settings.sort.color.split"), Default.IDENTIFIER),
    KEY(SortBundle.messagePointer("settings.sort.color.key"), Default.IDENTIFIER),

    SORT(SortBundle.messagePointer("settings.sort.color.sort"), Default.STRING),
    END(SortBundle.messagePointer("settings.sort.color.end"), Default.STRING),
    PATTERN(SortBundle.messagePointer("settings.sort.color.pattern"), Default.STRING),
    INDEX(SortBundle.messagePointer("settings.sort.color.index"), Default.NUMBER),

    BRACES(OptionsBundle.messagePointer("options.language.defaults.braces"), Default.BRACES),
    COMMA(OptionsBundle.messagePointer("options.language.defaults.comma"), Default.COMMA),
    COLON(SortBundle.messagePointer("settings.sort.color.colon"), Default.OPERATION_SIGN),

    BAD_CHARACTER(SortBundle.messagePointer("settings.sort.color.bad.character"), HighlighterColors.BAD_CHARACTER)
    ;

    val textAttributesKey = TextAttributesKey.createTextAttributesKey("com.github.jodiew.sortlines.$name", default)
    val attributeDescriptor = AttributesDescriptor(humanName, textAttributesKey)
//    val testSeverity = HighlightSeverity(name, HighlightSeverity.INFORMATION.myVal)
}