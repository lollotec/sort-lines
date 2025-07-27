package com.github.jodiew.sortlines.lang

import com.intellij.testFramework.ParsingTestCase

class SimpleParsingTest : ParsingTestCase("", "sort", SortParserDefinition()) {
    fun testParsingTestData() {
        doTest(true)
    }

    override fun getTestDataPath(): String = "src/test/testData"

    override fun includeRanges(): Boolean = true
}
