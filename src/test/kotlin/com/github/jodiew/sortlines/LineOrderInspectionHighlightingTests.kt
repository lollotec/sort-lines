package com.github.jodiew.sortlines

import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath($$"$CONTENT_ROOT/testData/highlight")
class LineOrderInspectionHighlightingTests: BasePlatformTestCase() {

    protected override fun setUp() {
        super.setUp()
        myFixture.enableInspections(LineOrderInspection())
    }

    protected override fun getTestDataPath(): String =
        "src/test/testData/highlight"

    fun testNoSortHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testSimpleLinesUnsortedHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testSimpleLinesSortedNoHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testMultipleLinesUnsortedHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testNoEndCommentHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testNoEndMultipleCommentHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testNoEndCommentToEofHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testComplexGroupHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testComplexSplitHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testComplexNoHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testMultipleNoEndComment() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testMixedEndCommentHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testIndentChangeSortHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }
}
