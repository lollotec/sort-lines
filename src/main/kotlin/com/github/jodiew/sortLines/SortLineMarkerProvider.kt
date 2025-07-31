package com.github.jodiew.sortLines

import com.intellij.codeInsight.daemon.GutterName
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement

class SortLineMarkerProvider : LineMarkerProviderDescriptor() {

    override fun getName(): @GutterName String? = SortLinesBundle.message("inspection.line.order.group.name")

    override fun getLineMarkerInfo(p0: PsiElement): LineMarkerInfo<*>? = null

    override fun collectSlowLineMarkers(elements: List<PsiElement?>, result: MutableCollection<in LineMarkerInfo<*>>) {
        for (element: PsiElement? in elements) {
            if (element == null) continue
            if (element !is PsiComment) continue
            if (!element.isSortComment()) continue
            val sortOptions = element.getSortOptions()
            if (sortOptions == "end") continue
            val sortOrder = sortOptions.toSortOrder()
            when (sortOrder) {
                SortOrder.ASC -> result.add(SortLineMarkerInfo(element, order = SortOrder.ASC))
                SortOrder.DESC -> result.add(SortLineMarkerInfo(element, order = SortOrder.DESC))
                else -> result.add(SortLineMarkerInfo(element, order = null))
            }
        }
    }

    class SortLineMarkerInfo(element: PsiElement, order: SortOrder?):
        LineMarkerInfo<PsiElement>(
            element,
            element.textRange,
            when (order) {
                SortOrder.ASC -> SortLineIcons.AscSort
                SortOrder.DESC -> SortLineIcons.DescSort
                else -> SortLineIcons.NoSort
            },
            null,
            null,
            GutterIconRenderer.Alignment.CENTER,
            { SortLinesBundle.message("inspection.line.order.group.name") }
        ) {
//            override fun createGutterRenderer(): GutterIconRenderer? = SortLineMarkerGutterRenderer(this)
        }

    class SortLineMarkerGutterRenderer(info: LineMarkerInfo<PsiElement>): LineMarkerInfo.LineMarkerGutterIconRenderer<PsiElement>(info) {
//        override fun getClickAction(): AnAction? {
//            TODO("Sort direction switch not implemented")
//        }
    }
}
