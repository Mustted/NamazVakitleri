package tr.xip.prayertimes.client

import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query
import tr.xip.prayertimes.model.City
import tr.xip.prayertimes.model.County
import tr.xip.prayertimes.model.PrayerTimes

interface DiyanetService {

    @GET("index.php?islem=getSehirList")
    fun getCitiesForCountry(
            @Query("ulke_id") id: String): Call<List<City>>

    @GET("index.php?islem=getIlceList")
    fun getCountiesListForCity(
            @Query("sehir_id") id: String): Call<List<County>>

    @GET("index.php?islem=getNamazVakitleri")
    fun getPrayerTimesForCity(
            @Query("ulke_id") countryId: String,
            @Query("sehir_id") cityId: String,
            @Query("periyot") period: String): Call<List<PrayerTimes>>

    @GET("index.php?islem=getNamazVakitleri")
    fun getPrayerTimesForCounty(
            @Query("ulke_id") countryId: String,
            @Query("sehir_id") cityId: String,
            @Query("ilce_id") countyId: String,
            @Query("periyot") period: String): Call<List<PrayerTimes>>
}
