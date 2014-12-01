package tr.xip.prayertimes.api.objects;

/**
 * Created by ix on 12/1/14.
 */
public class Location {
    int databaseId;
    String countryId;
    String cityId;
    String countyId;

    public Location(String countryId, String cityId, String countyId) {
        this.countryId = countryId;
        this.cityId = cityId;
        this.countyId = countyId;
    }

    public Location(int databaseId, String countryId, String cityId, String countyId) {
        this.databaseId = databaseId;
        this.countryId = countryId;
        this.cityId = cityId;
        this.countyId = countyId;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCountyId() {
        return countyId;
    }
}
