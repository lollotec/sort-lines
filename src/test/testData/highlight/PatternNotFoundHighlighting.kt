class PatternNotFoundHighlighting {
    // sort: { order: asc, group: /: (\S+) =/ }
    val apple: String = "first"
<warning descr="Sort lines: group pattern not found">    val cicada = 3</warning>
    val beetle: String = "second"

    // sort: { order: asc, split: /:/, key: 1 }
    val deer: String = "fourth"
<warning descr="Sort lines: split pattern not found">    var earwig = "fifth"</warning>

    // sort: { order: asc, split: / /, key: 4 }
    val gear: String = "seventh"
<warning descr="Sort lines: key out of range">    var hearth = "eighth"</warning>
}