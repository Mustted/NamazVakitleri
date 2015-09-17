package tr.xip.prayertimes.api;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import tr.xip.prayertimes.api.objects.City;
import tr.xip.prayertimes.api.objects.Country;
import tr.xip.prayertimes.api.objects.County;
import tr.xip.prayertimes.api.objects.PrayerTimes;

/**
 * Created by ix on 11/30/14.
 */
public class DiyanetApi {
    private static final String API_HOST = "http://namazvakitleri.ahmeti.net/";

    private static List<Country> sCountriesList;

    private static DiyanetService sService;

    private DiyanetApi() {}

    public static void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sService = retrofit.create(DiyanetService.class);
    }

    public static List<Country> getCountriesList() {
        if (sCountriesList != null)
            return sCountriesList;
        else
            return sCountriesList = ApiResources.getCountriesList();
    }

    public static Call<List<City>> getCitiesForCountry(String countryId) {
        return sService.getCitiesForCountry(countryId);
    }

    public static Call<List<County>> getCountiesForCity(String cityId) {
        return sService.getCountiesListForCity(cityId);
    }

    public static Call<List<PrayerTimes>> getPrayerTimesForCity(String countryId, String cityId) {
        return sService.getPrayerTimesForCity(countryId, cityId, "aylik");
    }

    public static Call<List<PrayerTimes>> getPrayerTimesForCounty(String countryId, String cityId, String countyId) {
        return sService.getPrayerTimesForCounty(countryId, cityId, countyId, "aylik");
    }
}
