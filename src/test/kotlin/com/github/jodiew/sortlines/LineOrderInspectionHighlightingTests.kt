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

    private fun highlightingTestKotlin(name: String) {
        myFixture.testHighlighting(
            true,
            false,
            false,
            testFileNameKt(name)
        )
    }

    fun testNoSortHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testSimpleLinesUnsortedHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testSimpleLinesSortedNoHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testMultipleLinesUnsortedHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testNoEndCommentHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testNoEndMultipleCommentHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testNoEndCommentToEofHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testInvalidSortOptionHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testUnicodeArrows() {
        highlightingTestKotlin(name)
    }

    fun testComplexGroupHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testComplexSplitHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testComplexNoHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testMultipleNoEndComment() {
        highlightingTestKotlin(name)
    }

    fun testMixedEndCommentHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testInvalidSortOrderHighlighting() {
        highlightingTestKotlin(name)
    }
}
