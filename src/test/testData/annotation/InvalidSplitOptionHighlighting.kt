class InvalidSplitOptionHighlighting {
    // sort: { order: asc, split: <error descr="'/' expected, got '}'">}</error>
    val a = "first"
    val b = "second"

    // sort: { order: asc, split: <error descr="'/' expected, got 'error'">error</error> }
    val c = "third"
    val d = "fourth"

    // sort: { order: asc, split: / /, key <error descr="':' expected, got '}'">}</error>
    val e = "fifth"
    val f = "sixth"

    // sort: { order: asc, split: / /, key: <error descr="NUMBER expected, got 'error'">error</error> }
    val g = "seventh"
    val h = "eighth"

    // sort: { order: asc, split: //, key: 5 }
    val i = "ninth"
    val j = "tenth"

    // sort: { order: asc, split: // <error descr="',' expected, got '}'">}</error>
    val k = "eleventh"
    val l = "twelfth"

    // sort: { order: asc, split: /<error descr="Invalid split pattern">[</error>/, key: 0 }
    val m = "thirteenth"
    val n = "fourteenth"
}