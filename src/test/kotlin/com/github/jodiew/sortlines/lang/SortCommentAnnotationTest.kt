package com.github.jodiew.sortlines.lang

import com.github.jodiew.sortlines.highlightingTestKotlin
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class SortCommentAnnotationTest: BasePlatformTestCase() {
    protected override fun getTestDataPath(): String =
        "src/test/testData/annotation"

    fun testInvalidSortOrderHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testClosingCommentCharsHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testAltSortOrders() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testEmptySortOptionHighlighting() {
        // TODO: The error for this should be more informative, right now it's just "<options> expected" from the parser
        myFixture.highlightingTestKotlin(name)
    }

    fun testInvalidGroupOptionHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testInvalidSplitOptionHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }

    fun testNoEndBracketHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }
}