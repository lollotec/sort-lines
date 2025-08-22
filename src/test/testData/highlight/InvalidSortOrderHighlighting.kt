class InvalidSortOrderHighlighting {
    // sort: <error descr="Invalid sort order">error</error>
    val apple = "zebra"
    val pear = "bear"

    // sort: <error descr="Invalid sort order">{ order: error, group: /\(.*\)/ }</error>
    fun add(a: Int) = 1
    fun add(a: Int, b: Int) = 2
    fun add(a: Int, b: Int, c: Int) = 3

}