package tr.xip.prayertimes.model

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class County : Serializable {
    var databaseId: Int? = null
        internal set

    @SerializedName("IlceId")
    var id: String? = null

    @SerializedName("IlceAdi")
    var name: String? = null

    @SerializedName("error")
    val error: String? = null

    constructor(databaseId: Int, id: String, name: String) {
        this.databaseId = databaseId
        this.id = id
        this.name = name
    }

    constructor(id: String, name: String) {
        this.id = id
        this.name = name
    }
}
