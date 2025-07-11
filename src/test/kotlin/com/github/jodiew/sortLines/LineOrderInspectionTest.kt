package com.github.jodiew.sortLines

import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath($$"$CONTENT_ROOT/testData")
class LineOrderInspectionTest: BasePlatformTestCase() {
    protected override fun setUp() {
        super.setUp()
        myFixture.enableInspections(LineOrderInspection())
    }

    protected override fun getTestDataPath(): String? =
        "src/test/testData"

    fun `test no sort comments`() {
        myFixture.testHighlighting(
            true,
            false,
            false,
            "NoSortHighlighting.kt"
        )
    }

    fun `test simple unsorted highlighting`() {
        myFixture.testHighlighting(
            true,
            false,
            false,
            "SimpleLinesUnsortedHighlighting.kt"
        )
    }

    fun `test simple sorted no highlighting`() {
        myFixture.testHighlighting(
            true,
            false,
            false,
            "SimpleLinesSortedNoHighlighting.kt"
        )
    }

    fun `test multiple unsorted highlighting`() {
        myFixture.testHighlighting(
            true,
            false,
            false,
            "MultipleLinesUnsortedHighlighting.kt"
        )
    }

    fun `test no end comment highlighting`() {
        myFixture.testHighlighting(
            true,
            false,
            false,
            "NoEndCommentHighlighting.kt"
        )
    }

    fun `test no start comment highlighting`() {
        myFixture.testHighlighting(
            true,
            false,
            false,
            "NoStartCommentHighlighting.kt"
        )
    }

    fun `test no end multiple comment highlighting`() {
        myFixture.testHighlighting(
            true,
            false,
            false,
            "NoEndMultipleCommentHighlighting.kt"
        )
    }

    fun `test no start multiple comment highlighting`() {
        myFixture.testHighlighting(
            true,
            false,
            false,
            "NoStartMultipleCommentHighlighting.kt"
        )
    }

    fun `test mismatched end comment highlighting`() {
        myFixture.testHighlighting(
            true,
            false,
            false,
            "MismatchedEndCommentHighlighting.kt"
        )
    }

    fun `test invalid sort option highlighting`() {
        myFixture.testHighlighting(
            true,
            false,
            false,
            "InvalidSortOptionHighlighting.kt"
        )
    }
}
