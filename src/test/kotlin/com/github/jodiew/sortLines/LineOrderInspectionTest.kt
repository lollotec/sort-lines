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
}
