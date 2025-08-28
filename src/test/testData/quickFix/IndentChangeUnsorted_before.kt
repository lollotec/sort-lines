class IndentChangeUnsorted {
    fun aFunction( // sort: asc
        doodle: String,
        labrador: Int,
        poodle: Double,
        colie: Boolean,
    ): String {
        return doodle
    }

    // sort: asc
    val tuna = "wow"
    val anchovy =
        "This is longer and got moved to the next line"

    companion object {
        // sort: z-a
        val apple = "last"
        val agave = "second"
        val banana = "first"
    }
}