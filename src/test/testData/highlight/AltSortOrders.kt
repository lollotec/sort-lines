/**
 * Test sort highlighting with unicode arrows
 */
class AltSortOrders {
    // sort: ascending
<warning descr="Lines not in specified order">    val banana = 2
    val apple = "1"</warning>
    // sort: end

    // sort: z-a
<warning descr="Lines not in specified order">    val ant = "4"
    val beetle = 3</warning>
    // sort: end
}
