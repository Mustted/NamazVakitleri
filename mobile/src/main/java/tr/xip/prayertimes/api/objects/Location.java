package tr.xip.prayertimes.api.objects;

import java.io.Serializable;

public class Location implements Serializable {
    int databaseId;
    String countryId;
    String countryName;
    String cityId;
    String cityName;
    String countyId;
    String countyName;

    int fajrNotification;
    int sunriseNotiication;
    int dhuhrNotification;
    int asrNotification;
    int maghribNotification;
    int ishaNotification;

    public Location(String countryId, String countryName, String cityId, String cityName, String countyId, String countyName) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.cityId = cityId;
        this.cityName = cityName;
        this.countyId = countyId;
        this.countyName = countyName;
    }

    public Location(int databaseId, String countryId, String countryName, String cityId,
                    String cityName, String countyId, String countyName, int fajrNotification,
                    int sunriseNotiication, int dhuhrNotification, int asrNotification,
                    int maghribNotification, int ishaNotification) {
        this.databaseId = databaseId;
        this.countryId = countryId;
        this.countryName = countryName;
        this.cityId = cityId;
        this.cityName = cityName;
        this.countyId = countyId;
        this.countyName = countyName;
        this.fajrNotification = fajrNotification;
        this.sunriseNotiication = sunriseNotiication;
        this.dhuhrNotification = dhuhrNotification;
        this.asrNotification = asrNotification;
        this.maghribNotification = maghribNotification;
        this.ishaNotification = ishaNotification;
    }

    public int getDatabaseId() {
        return databaseId;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getCountryName() {
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

    public String getName() {
        return countyName != null ? countyName : cityName;
    }

    public void setFajrNotification(int notification) {
        fajrNotification = notification;
    }

    public void setSunriseNotiication(int notification) {
        sunriseNotiication = notification;
    }

    public void setDhuhrNotification(int notification) {
        dhuhrNotification = notification;
    }

    public void setAsrNotification(int notification) {
        asrNotification = notification;
    }

    public void setMaghribNotification(int notification) {
        maghribNotification = notification;
    }

    public void setIshaNotification(int notification) {
        ishaNotification = notification;
    }

    public int getFajrNotification() {
        return fajrNotification;
    }

    public int getSunriseNotiication() {
        return sunriseNotiication;
    }

    public int getDhuhrNotification() {
        return dhuhrNotification;
    }

    public int getAsrNotification() {
        return asrNotification;
    }

    public int getMaghribNotification() {
        return maghribNotification;
    }

    public int getIshaNotification() {
        return ishaNotification;
    }
}
