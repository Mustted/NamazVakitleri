package tr.xip.prayertimes.client

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.logging.HttpLoggingInterceptor
import retrofit.Call
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import tr.xip.prayertimes.model.State
import tr.xip.prayertimes.model.Country
import tr.xip.prayertimes.model.City
import tr.xip.prayertimes.model.PrayerTimes

object DiyanetClient {
    private val API_HOST = "http://bluedream.info/prayertimes/"

    private var service: DiyanetService? = null

    fun init() {
        val client: OkHttpClient = OkHttpClient()
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.interceptors().add(interceptor)

        val retrofit = Retrofit.Builder()
                .baseUrl(API_HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(DiyanetService::class.java)
    }

    val countriesList: List<Country> = ClientResources.COUNTRIES

    fun getCountries() = service!!.getCountries()

    fun getStatesForCountry(countryId: String): Call<List<State>> = service!!.getStatesForCountry(countryId)

    fun getCitiesForState(cityId: String): Call<List<City>> = service!!.getCitiesForState(cityId)

    fun getPrayerTimesForDay(countryId: String, stateId: String, cityId: String?): Call<List<PrayerTimes>> {
        return service!!.getPrayerTimesForDay(countryId, stateId, cityId ?: "")
    }

    fun getPrayerTimesForWeek(countryId: String, stateId: String, cityId: String?): Call<List<PrayerTimes>> {
        return service!!.getPrayerTimesForWeek(countryId, stateId, cityId ?: "")
    }

    fun getPrayerTimesForMonth(countryId: String, stateId: String, cityId: String?): Call<List<PrayerTimes>> {
        return service!!.getPrayerTimesForMonth(countryId, stateId, cityId ?: "")
    }
}
