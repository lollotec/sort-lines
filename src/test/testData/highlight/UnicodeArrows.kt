/**
 * Test sort highlighting with unicode arrows
 */
class UnicodeArrows {
    // sort: ↑
<warning descr="Lines not in specified order">    val banana = 2
    val apple = "1"</warning>
    // sort: end

    // sort: ↓
<warning descr="Lines not in specified order">    val ant = "4"
    val beetle = 3</warning>
    // sort: end
}
