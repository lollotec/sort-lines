package test
class BlankLines {
    // sort: asc
<warning descr="Lines not in specified order">    val b0 = "b"
    val a0 = "a"</warning>

<warning descr="Lines not in specified order">    val c1 = "c"
    val e1 = "e"
    val d1 = "d"</warning>

    val xn = "x"
    val yn = "y"
    val zn = "z"
    // sort: end
    val noSort = "stay"
}
