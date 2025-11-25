package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.SortBundle
import com.github.jodiew.sortlines.SortIcons
import com.github.jodiew.sortlines.SortOrder
import com.github.jodiew.sortlines.lang.psi.SortFile
import com.github.jodiew.sortlines.lang.psi.SortOptions
import com.github.jodiew.sortlines.lang.psi.ext.end
import com.github.jodiew.sortlines.toSortOrder
import com.intellij.codeInsight.daemon.GutterName
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.util.firstLeaf
import javax.swing.Icon

class SortLineMarkerProvider: LineMarkerProviderDescriptor() {
    override fun getName(): @GutterName String = SortBundle.message("inspection.com.github.jodiew.sortlines.line.order.group.name")

    override fun getIcon(): Icon = SortIcons.NoSort

    override fun getLineMarkerInfo(p0: PsiElement): LineMarkerInfo<*>? = null

    override fun collectSlowLineMarkers(elements: List<PsiElement?>, result: MutableCollection<in LineMarkerInfo<*>>) {
        for (element: PsiElement? in elements) {
            if (element == null) continue
            if (element !is SortFile) continue
            val sortOptions: SortOptions? = element.sortOptions
            if (sortOptions?.end ?: false) continue
            result.add(SortLineMarkerInfo(sortOptions?.sort?.firstLeaf() ?: element.firstLeaf()))
        }
    }

    class SortLineMarkerInfo(element: PsiElement):
        LineMarkerInfo<PsiElement>(
            element,
            element.textRange,
            when (element.text.toSortOrder(element.project)) {
                SortOrder.ASC -> SortIcons.AscSort
                SortOrder.DESC -> SortIcons.DescSort
                else -> SortIcons.NoSort
            },
            null,
            null,
            GutterIconRenderer.Alignment.CENTER,
            { SortBundle.message("inspection.com.github.jodiew.sortlines.line.order.group.name") }
        ) {
//        override fun createGutterRenderer(): GutterIconRenderer? = SortLineMarkerGutterRenderer(this)
    }

//    class SortLineMarkerGutterRenderer(info: LineMarkerInfo<PsiElement>): LineMarkerInfo.LineMarkerGutterIconRenderer<PsiElement>(info) {
//        override fun getClickAction(): AnAction? {
//            TODO("Sort direction switch not implemented")
//        }
//    }
}