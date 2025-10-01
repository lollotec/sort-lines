package com.github.jodiew.sortlines.lang

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class SortCompletionTest: BasePlatformTestCase() {
    override fun getTestDataPath(): String = "src/test/testData/completion"

    fun testCompletion() {
        myFixture.configureByFile("SimpleSortCompletion.kt")
        myFixture.complete(CompletionType.BASIC)
        val lookupElementStrings = myFixture.lookupElementStrings
        assertNotNull(lookupElementStrings)
        assertSameElements(lookupElementStrings!!, "asc", "desc", "ascending", "descending", "a-z", "z-a")
    }
}
