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

    fun testSimpleLinesUnsorted() {
        myFixture.testHighlighting(
            true,
            false,
            false,
            "SimpleLinesUnsortedHighlighting.kt"
        )
    }
}