package tr.xip.prayertimes.model

import java.io.Serializable

class Country : Serializable {
    var databaseId: Int = 0
        internal set
    var id: String? = null
    var name: String? = null

    constructor(id: String, name: String) {
        this.id = id
        this.name = name
    }

    constructor(databaseId: Int, id: String, name: String) {
        this.databaseId = databaseId
        this.id = id
        this.name = name
    }
}
