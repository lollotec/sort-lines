class InvalidSortOrderHighlighting {
    // sort: <error descr="Invalid sort order">error</error>
    val apple = "zebra"
    val pear = "bear"

    // sort: { order: <error descr="Invalid sort order">error</error>, group: /\(.*\)/ }
    val notFirst = arrayOf<String>("apple")
    val isSecond = arrayOf<String>("apple", "beetle")
    val notLast = arrayOf<String>("apple", "beetle", "bee")
}
