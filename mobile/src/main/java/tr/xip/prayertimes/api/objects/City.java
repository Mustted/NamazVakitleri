package tr.xip.prayertimes.api.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ix on 11/30/14.
 */
public class City implements Serializable {

    @SerializedName("SehirId")
    private String id;

    @SerializedName("SehirAdi")
    private String name;

    public City(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
