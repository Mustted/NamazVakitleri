package tr.xip.prayertimes.api.objects;

import java.io.Serializable;

/**
 * Created by ix on 11/30/14.
 */
public class Country implements Serializable {
    private String id;
    private String name;

    public Country(String code, String name) {
        this.id = code;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
