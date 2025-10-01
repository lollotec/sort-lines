package com.github.jodiew.sortlines.lang

import com.intellij.testFramework.ParsingTestCase

class SimpleParsingTest : ParsingTestCase("", "sort", SortParserDefinition()) {
    fun testAscParsingTestData() {
        doTest(true)
    }

    fun testDescParsingTestData() {
        doTest(true)
    }

    fun testUnknownParsingTestData() {
        doTest(true)
    }

    fun testOrderParsingTestData() {
        doTest(true)
    }

    fun testGroupParsingTestData() {
        doTest(true)
    }

    fun testSplitParsingTestData() {
        doTest(true)
    }

    override fun getTestDataPath(): String = "src/test/testData/parsing"

    override fun includeRanges(): Boolean = true
}
