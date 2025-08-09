package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.DEFAULT_ASC
import com.github.jodiew.sortlines.DEFAULT_DESC
import com.github.jodiew.sortlines.SortBundle
import com.github.jodiew.sortlines.SortIcons
import com.github.jodiew.sortlines.lang.psi.SortSort
import com.intellij.codeInsight.daemon.GutterName
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import javax.swing.Icon

class SortLineMarkerProvider: LineMarkerProviderDescriptor() {
    override fun getName(): @GutterName String = SortBundle.message("inspection.line.order.group.name")

    override fun getIcon(): Icon = SortIcons.NoSort

    override fun getLineMarkerInfo(p0: PsiElement): LineMarkerInfo<*>? = null

    override fun collectSlowLineMarkers(elements: List<PsiElement?>, result: MutableCollection<in LineMarkerInfo<*>>) {
        for (element: PsiElement? in elements) {
            if (element == null) continue
            if (element.parent !is SortSort) continue
            result.add(SortLineMarkerInfo(element))
        }
    }

    class SortLineMarkerInfo(element: PsiElement):
        LineMarkerInfo<PsiElement>(
            element,
            element.textRange,
            when (element.text) {
                in DEFAULT_ASC -> SortIcons.AscSort
                in DEFAULT_DESC -> SortIcons.DescSort
                else -> SortIcons.NoSort
            },
            null,
            null,
            GutterIconRenderer.Alignment.CENTER,
            { SortBundle.message("inspection.line.order.group.name") }
        )
}