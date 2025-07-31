class NoStartMultipleCommentHighlighting {
    // sort: asc
    val apple = 1
    val banana = "2"
    // sort: end

    val beetle = "4"
    val ant = 3
    <warning descr="No sort start comment">// sort: end</warning>
}