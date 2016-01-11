package tr.xip.prayertimes.client

import retrofit.Call
import retrofit.http.GET
import retrofit.http.Path
import tr.xip.prayertimes.model.State
import tr.xip.prayertimes.model.City
import tr.xip.prayertimes.model.PrayerTimes

interface DiyanetService {

    @GET("country")
    fun getCountries()

    @GET("state/{id_country}")
    fun getStatesForCountry(
            @Path("id_country") id: String): Call<List<State>>

    @GET("city/{id_state}")
    fun getCitiesForState(
            @Path("id_state") id: String): Call<List<City>>

    @GET("day/{id_country}/{id_state}/{id_city}")
    fun getPrayerTimesForDay(
            @Path("id_country") countryId: String,
            @Path("id_state") stateId: String,
            @Path("id_city") cityId: String): Call<List<PrayerTimes>>

    @GET("week/{id_country}/{id_state}/{id_city}")
    fun getPrayerTimesForWeek(
            @Path("id_country") countryId: String,
            @Path("id_state") stateId: String,
            @Path("id_city") cityId: String): Call<List<PrayerTimes>>

    @GET("month/{id_country}/{id_state}/{id_city}")
    fun getPrayerTimesForMonth(
            @Path("id_country") countryId: String,
            @Path("id_state") stateId: String,
            @Path("id_city") cityId: String): Call<List<PrayerTimes>>
}
