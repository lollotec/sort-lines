class InvalidSortOrderHighlighting {
    // sort: <error descr="Invalid sort order">error</error>
    val apple = "zebra"
    val pear = "bear"

    // sort: <error descr="Invalid sort order">{ order: error, group: /\(.*\)/ }</error>
    fun add(a: Int) = a
    fun add(a: Int, b: Int) = a.plus(b)
    fun add(a: Int, b: Int, c: Int) = a.plus(b).plus(c)

}
