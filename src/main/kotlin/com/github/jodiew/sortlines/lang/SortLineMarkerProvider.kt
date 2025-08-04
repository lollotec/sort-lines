package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.DEFAULT_ASC
import com.github.jodiew.sortlines.DEFAULT_DESC
import com.github.jodiew.sortlines.SortBundle
import com.github.jodiew.sortlines.SortLineIcons
import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.ext.isEnd
import com.github.jodiew.sortlines.lang.psi.ext.sort
import com.intellij.codeInsight.daemon.GutterName
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import javax.swing.Icon

class SortLineMarkerProvider: LineMarkerProviderDescriptor() {
    override fun getName(): @GutterName String? = SortBundle.message("inspection.line.order.group.name")

    override fun getIcon(): Icon? = SortLineIcons.NoSort

    override fun getLineMarkerInfo(p0: PsiElement): LineMarkerInfo<*>? = null

    override fun collectSlowLineMarkers(elements: List<PsiElement?>, result: MutableCollection<in LineMarkerInfo<*>>) {
        for (element: PsiElement? in elements) {
            if (element == null) continue
            if (element !is SortOptions) continue
            if (element.isEnd) continue
            result.add(SortLineMarkerInfo(element))
        }
    }

    class SortLineMarkerInfo(element: SortOptions):
        LineMarkerInfo<PsiElement>(
            element,
            element.textRange,
            when (element.sort) {
                in DEFAULT_ASC -> SortLineIcons.AscSort
                in DEFAULT_DESC -> SortLineIcons.DescSort
                else -> SortLineIcons.NoSort
            },
            null,
            null,
            GutterIconRenderer.Alignment.CENTER,
            { SortBundle.message("inspection.line.order.group.name") }
        )
}