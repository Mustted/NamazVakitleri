package tr.xip.prayertimes.api.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class County implements Serializable {
    private int databaseId;

    @SerializedName("IlceId")
    private String id;

    @SerializedName("IlceAdi")
    private String name;

    @SerializedName("error")
    private String error;

    public County(int databaseId, String id, String name) {
        this.databaseId = databaseId;
        this.id = id;
        this.name = name;
    }

    public County(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getError() {
        return error;
    }
}
