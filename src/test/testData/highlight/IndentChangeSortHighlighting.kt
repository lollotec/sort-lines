class IndentChangeSortHighlighting {
    fun aFunction( // sort: asc
<warning descr="Lines not in specified order">        doodle: String,
        labrador: Int,
        poodle: Double,
        colie: Boolean,</warning>
    ): String {
        return doodle
    }

    // sort: asc
<warning descr="Lines not in specified order">    val tuna = "wow"
    val anchovy =</warning>
        "This is longer and got moved to the next line"

    companion object {
        // sort: z-a
<warning descr="Lines not in specified order">        val apple = "last"
        val agave = "second"
        val banana = "first"</warning>
    }
}