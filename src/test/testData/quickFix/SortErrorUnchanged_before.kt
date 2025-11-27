class SortErrorUnchanged {
    // sort: { order: asc, group: /: (\S+) =/ }
    val apple: String = "first"
    val cicada = 3
    val beetle: String = "second"

    // sort: { order: asc, split: /:/, key: 1 }
    val deer: String = "fourth"
    var earwig = "fifth"

    // sort: { order: asc, split: / /, key: 4 }
    val foal: String = "sixth"
    var gear = "seventh"

    // sort: error
    val hearth = "eighth"
    val ingest = "ninth"
}