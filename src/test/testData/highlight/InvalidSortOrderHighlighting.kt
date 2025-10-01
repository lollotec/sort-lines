package test

import kotlin.plus

class InvalidSortOrderHighlighting {
    // sort: <error descr="Invalid sort order">error</error>
    val apple = "zebra"
    val pear = "bear"

    // sort: <error descr="Invalid sort order">{ order: error, group: /\(.*\)/ }</error>
    val notFirst = arrayOf<String>("apple")
    val isSecond = arrayOf<String>("apple", "beetle")
    val notLast = arrayOf<String>("apple", "beetle", "bee")

}
