package com.github.jodiew.sortLines

import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath($$"$CONTENT_ROOT/testData")
class LineOrderInspectionHighlightingTests: BasePlatformTestCase() {

    protected override fun setUp() {
        super.setUp()
        myFixture.enableInspections(LineOrderInspection())
    }

    protected override fun getTestDataPath(): String =
        "src/test/testData/highlighting"

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

    fun testNoStartCommentHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testNoEndMultipleCommentHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testNoStartMultipleCommentHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testMismatchedEndCommentHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testInvalidSortOptionHighlighting() {
        highlightingTestKotlin(name)
    }

    fun testUnicodeArrows() {
        highlightingTestKotlin(name)
    }
}
