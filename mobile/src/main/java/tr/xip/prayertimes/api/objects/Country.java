package tr.xip.prayertimes.api.objects;

import java.io.Serializable;

/**
 * Created by ix on 11/30/14.
 */
public class Country implements Serializable {
    int databaseId;
    private String id;
    private String name;

    public Country(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Country(int databaseId, String id, String name) {
        this.databaseId = databaseId;
        this.id = id;
        this.name = name;
    }

    public int getDatabaseId() {
        return databaseId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
