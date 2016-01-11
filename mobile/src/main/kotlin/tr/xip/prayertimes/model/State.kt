package tr.xip.prayertimes.model

import java.io.Serializable

class State : Serializable {
    var databaseId: Int? = null
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
