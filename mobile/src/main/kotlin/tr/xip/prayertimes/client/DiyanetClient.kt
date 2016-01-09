package tr.xip.prayertimes.client

import retrofit.Call
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import tr.xip.prayertimes.model.City
import tr.xip.prayertimes.model.Country
import tr.xip.prayertimes.model.County
import tr.xip.prayertimes.model.PrayerTimes

object DiyanetClient {
    private val API_HOST = "http://namazvakitleri.ahmeti.net/"

    private var service: DiyanetService? = null

    fun init() {
        val retrofit = Retrofit.Builder()
                .baseUrl(API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(DiyanetService::class.java)
    }

    val countriesList: List<Country> = ClientResources.COUNTRIES

    fun getCitiesForCountry(countryId: String): Call<List<City>> {
        return service!!.getCitiesForCountry(countryId)
    }

    fun getCountiesForCity(cityId: String): Call<List<County>> {
        return service!!.getCountiesListForCity(cityId)
    }

    fun getPrayerTimesForCity(countryId: String, cityId: String): Call<List<PrayerTimes>> {
        return service!!.getPrayerTimesForCity(countryId, cityId, "aylik")
    }

    fun getPrayerTimesForCounty(countryId: String, cityId: String, countyId: String): Call<List<PrayerTimes>> {
        return service!!.getPrayerTimesForCounty(countryId, cityId, countyId, "aylik")
    }
}
