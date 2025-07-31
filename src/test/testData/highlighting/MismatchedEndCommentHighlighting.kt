class MismatchedEndCommentHighlighting {
    <warning descr="No sort end comment">// sort: asc</warning>
    val apple = 1
    val banana = "2"

    // sort: desc
<warning descr="Lines not in specified order">    val ant = 3
    val beetle = "4"</warning>
    // sort: end
}