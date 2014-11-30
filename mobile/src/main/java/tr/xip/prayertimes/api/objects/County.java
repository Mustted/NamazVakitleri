package tr.xip.prayertimes.api.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ix on 11/30/14.
 */
public class County implements Serializable {

    @SerializedName("IlceId")
    private String id;

    @SerializedName("IlceAdi")
    private String name;

    @SerializedName("error")
    private String error;

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
