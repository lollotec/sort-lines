class InvalidGroupOptionHighlighting {
    //sort: { order: asc, group: <error descr="'/' expected, got '}'">}</error>
    val a = "first"
    val b = "second"

    //sort: { order: asc, group: /<error descr="REGEX expected, got '/'">/</error> }
    val c = "third"
    val d = "fourth"

    // sort: { order: asc, group: /<error descr="Invalid group pattern">[</error>/ }
    val e = "fifth"
    val f = "sixth"
}
