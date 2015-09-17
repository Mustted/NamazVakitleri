package tr.xip.prayertimes.api.objects;

import java.io.Serializable;

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
