class NoEndMultipleCommentHighlighting {
    // sort: asc
    val apple = 1
    val banana = "2"
    // sort: end

    <warning descr="No sort end comment">// sort: desc</warning>
    val beetle = "4"
    val ant = 3
}