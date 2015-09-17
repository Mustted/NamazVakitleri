package tr.xip.prayertimes.api;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import tr.xip.prayertimes.api.objects.City;
import tr.xip.prayertimes.api.objects.County;
import tr.xip.prayertimes.api.objects.PrayerTimes;

public interface DiyanetService {

    @GET("index.php?islem=getSehirList")
    Call<List<City>> getCitiesForCountry(
            @Query("ulke_id") String id
    );

    @GET("index.php?islem=getIlceList")
    Call<List<County>> getCountiesListForCity(
            @Query("sehir_id") String id
    );

    @GET("index.php?islem=getNamazVakitleri")
    Call<List<PrayerTimes>> getPrayerTimesForCity(
            @Query("ulke_id") String countryId,
            @Query("sehir_id") String cityId,
            @Query("periyot") String period
    );

    @GET("index.php?islem=getNamazVakitleri")
    Call<List<PrayerTimes>> getPrayerTimesForCounty(
            @Query("ulke_id") String countryId,
            @Query("sehir_id") String cityId,
            @Query("ilce_id") String countyId,
            @Query("periyot") String period
    );
}
