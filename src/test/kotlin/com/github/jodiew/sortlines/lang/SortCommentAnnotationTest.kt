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

    fun testInvalidSortOptionHighlighting() {
        myFixture.highlightingTestKotlin(name)
    }
}