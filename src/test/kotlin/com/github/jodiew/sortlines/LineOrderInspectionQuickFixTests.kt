package com.github.jodiew.sortlines

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class LineOrderInspectionQuickFixTests : BasePlatformTestCase() {
    companion object {
        private val QUICK_FIX_NAME = SortBundle.message("inspection.line.order.quickfix")
    }

    protected override fun setUp() {
        super.setUp()
        myFixture.enableInspections(LineOrderInspection())
    }

    protected override fun getTestDataPath(): String =
        "src/test/testData/quickFix"

    private fun quickFixTest(name: String) {
        myFixture.configureByFile(testFileName(name, postfix = "_before.kt"))
        val sortFixes = myFixture.getAllQuickFixes().filter { it.text == QUICK_FIX_NAME }
        assertFalse(sortFixes.isEmpty())
        sortFixes.forEach { myFixture.launchAction(it) }
        myFixture.checkResultByFile(testFileName(name, postfix = "_after.kt"))
    }

    fun testSimpleLinesUnsorted() {
        quickFixTest(name)
    }

    fun testAltSortOrders() {
        quickFixTest(name)
    }

    fun testComplexLinesUnsorted() {
        quickFixTest(name)
    }

    fun testIndentChangeUnsorted() {
        quickFixTest(name)
    }
}