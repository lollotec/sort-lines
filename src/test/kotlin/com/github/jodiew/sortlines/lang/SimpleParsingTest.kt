package com.github.jodiew.sortlines.lang

import com.intellij.testFramework.ParsingTestCase

class SimpleParsingTest : ParsingTestCase("", "sort", SortParserDefinition()) {
    fun testAscParsingTestData() {
        doTest(true)
    }

    fun testDescParsingTestData() {
        doTest(true)
    }

    fun testOrderParsingTestData() {
        doTest(true)
    }

    fun testGroupParsingTestData() {
        doTest(true)
    }

    override fun getTestDataPath(): String = "src/test/testData"

    override fun includeRanges(): Boolean = true
}