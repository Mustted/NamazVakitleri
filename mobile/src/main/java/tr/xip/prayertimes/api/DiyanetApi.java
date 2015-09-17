package tr.xip.prayertimes.api;

import com.google.gson.Gson;

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

    private List<Country> mCountriesList;

    private DiyanetService service;

    public DiyanetApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(DiyanetService.class);
    }

    public List<Country> getCountriesList() {
        if (mCountriesList != null)
            return mCountriesList;
        else
            return mCountriesList = ApiResources.getCountriesList();
    }

    public Call<List<City>> getCitiesForCountry(String countryId) {
        return service.getCitiesForCountry(countryId);
    }

    public Call<List<County>> getCountiesForCity(String cityId) {
        return service.getCountiesListForCity(cityId);
    }

    public Call<List<PrayerTimes>> getPrayerTimesForCity(String countryId, String cityId) {
        return service.getPrayerTimesForCity(countryId, cityId, "aylik");
    }

    public Call<List<PrayerTimes>> getPrayerTimesForCounty(String countryId, String cityId, String countyId) {
        return service.getPrayerTimesForCounty(countryId, cityId, countyId, "aylik");
    }
}
