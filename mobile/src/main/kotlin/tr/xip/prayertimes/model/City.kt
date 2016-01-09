package tr.xip.prayertimes.model

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class City : Serializable {
    var databaseId: Int? = null
        internal set

    @SerializedName("SehirId")
    var id: String? = null

    @SerializedName("SehirAdi")
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
