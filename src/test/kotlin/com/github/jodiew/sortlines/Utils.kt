package com.github.jodiew.sortlines

import com.intellij.testFramework.fixtures.CodeInsightTestFixture

fun testFileNameKt(name: String): String =
    testFileName(name, ".kt")

fun testFileName(name: String, postfix: String): String =
    name.substringAfter("test") + postfix

fun CodeInsightTestFixture.highlightingTestKotlin(name: String) {
    testHighlighting(
        true,
        false,
        false,
        testFileNameKt(name)
    )
}