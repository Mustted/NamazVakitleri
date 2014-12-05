package tr.xip.prayertimes.api.objects;

import java.io.Serializable;

/**
 * Created by ix on 12/1/14.
 */
public class Location implements Serializable {
    int databaseId;
    String countryId;
    String countryName;
    String cityId;
    String cityName;
    String countyId;
    String countyName;

    public Location(String countryId, String countryName, String cityId, String cityName, String countyId, String countyName) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.cityId = cityId;
        this.cityName = cityName;
        this.countyId = countyId;
        this.countyName = countyName;
    }

    public Location(int databaseId, String countryId, String countryName, String cityId, String cityName, String countyId, String countyName) {
        this.databaseId = databaseId;
        this.countryId = countryId;
        this.countryName = countryName;
        this.cityId = cityId;
        this.cityName = cityName;
        this.countyId = countyId;
        this.countyName = countyName;
    }

    public int getDatabaseId() {
        return databaseId;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getcountryNama() {
        return countryName;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountyId() {
        return countyId;
    }

    public String getCountyName() {
        return countyName;
    }
}
