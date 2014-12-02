package tr.xip.prayertimes.api;

import java.util.List;

import retrofit.RestAdapter;
import tr.xip.prayertimes.api.objects.City;
import tr.xip.prayertimes.api.objects.Country;
import tr.xip.prayertimes.api.objects.County;
import tr.xip.prayertimes.api.objects.PrayerTimes;

/**
 * Created by ix on 11/30/14.
 */
public class DiyanetApi {
    private static final String API_HOST = "http://ahmeti-namaz-vakitleri.appspot.com";

    private List<Country> mCountriesList;

    private DiyanetService service;

    public DiyanetApi() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_HOST)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        service = restAdapter.create(DiyanetService.class);
    }

    public List<Country> getCountriesList() {
        if (mCountriesList != null)
            return mCountriesList;
        else
            return mCountriesList = ApiResources.getCountriesList();
    }

    public List<City> getCitiesForCountry(String countryId) {
        return service.getCitiesForCountry(countryId);
    }

    public List<County> getCountiesForCity(String cityId) {
        return service.getCountiesListForCity(cityId);
    }

    public List<PrayerTimes> getPrayerTimesForCity(String countryId, String cityId) {
        return service.getPrayerTimesForCity(countryId, cityId);
    }

    public List<PrayerTimes> getPrayerTimesForCounty(String countryId, String cityId, String countyId) {
        return service.getPrayerTimesForCounty(countryId, cityId, countyId);
    }
}
