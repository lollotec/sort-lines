package com.github.jodiew.sortlines

fun testFileNameKt(name: String): String =
    testFileName(name, ".kt")

fun testFileName(name: String, postfix: String): String =
    name.substringAfter("test") + postfix
