package com.github.jodiew.sortlines

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class SortLineActionTest : BasePlatformTestCase() {
    protected override fun getTestDataPath(): String =
        "src/test/testData/quickFix"

    private fun actionTest(name: String) {
        myFixture.configureByFile(testFileName(name, postfix = "_before.kt"))
        myFixture.performEditorAction("com.github.jodiew.sortlines.order.lines")
        myFixture.checkResultByFile(testFileName(name, postfix = "_after.kt"))
    }

    fun testSimpleLinesUnsorted() {
        actionTest(name)
    }

    fun testComplexLinesUnsorted() {
        actionTest(name)
    }

    fun testAltSortOrders() {
        actionTest(name)
    }

    fun testIndentChangeUnsorted() {
        actionTest(name)
    }

    fun testSortErrorUnchanged() {
        actionTest(name)
    }
}