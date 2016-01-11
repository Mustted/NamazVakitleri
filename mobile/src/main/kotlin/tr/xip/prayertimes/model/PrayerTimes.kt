package tr.xip.prayertimes.model

import com.google.gson.annotations.SerializedName

import java.io.Serializable
import java.util.ArrayList

class PrayerTimes(val date: Long, fajr: Long, sunrise: Long, dhuhr: Long, asr: Long, maghrib: Long, isha: Long) : Serializable {
    @SerializedName("tarih")
    private val dateString: String? = null

    @SerializedName("imsak")
    private val fajrString: String? = null

    @SerializedName("gunes")
    private val sunriseString: String? = null

    @SerializedName("ogle")
    private val dhuhrString: String? = null

    @SerializedName("ikindi")
    private val asrString: String? = null

    @SerializedName("aksam")
    private val maghribString: String? = null

    @SerializedName("yatsi")
    private val ishaString: String? = null

    @SerializedName("kible")
    val qibla: String? = null

    val fajrPrayerTime: PrayerTime?
    val sunrisePrayerTime: PrayerTime?
    val dhuhrPrayerTime: PrayerTime?
    val asrPrayerTime: PrayerTime?
    val maghribPrayerTime: PrayerTime?
    val ishaPrayerTime: PrayerTime?

    init {
        this.fajrPrayerTime = PrayerTime(PrayerTime.Type.FAJR, fajr)
        this.sunrisePrayerTime = PrayerTime(PrayerTime.Type.SUNRISE, sunrise)
        this.dhuhrPrayerTime = PrayerTime(PrayerTime.Type.DHUHR, dhuhr)
        this.asrPrayerTime = PrayerTime(PrayerTime.Type.ASR, asr)
        this.maghribPrayerTime = PrayerTime(PrayerTime.Type.MAGHRIB, maghrib)
        this.ishaPrayerTime = PrayerTime(PrayerTime.Type.ISHA, isha)
    }

    val fajr: PrayerTime
        get() = fajrPrayerTime ?: PrayerTime(PrayerTime.Type.FAJR, dateString!!, fajrString!!)

    val sunrise: PrayerTime
        get() = sunrisePrayerTime ?: PrayerTime(PrayerTime.Type.SUNRISE, dateString!!, sunriseString!!)

    val dhuhr: PrayerTime
        get() = dhuhrPrayerTime ?: PrayerTime(PrayerTime.Type.DHUHR, dateString!!, dhuhrString!!)

    val asr: PrayerTime
        get() = asrPrayerTime ?: PrayerTime(PrayerTime.Type.ASR, dateString!!, asrString!!)

    val maghrib: PrayerTime
        get() = maghribPrayerTime ?: PrayerTime(PrayerTime.Type.MAGHRIB, dateString!!, maghribString!!)

    val isha: PrayerTime
        get() = ishaPrayerTime ?: PrayerTime(PrayerTime.Type.ISHA, dateString!!, ishaString!!)

    val prayerTimesList: ArrayList<PrayerTime>
        get() {
            val list = ArrayList<PrayerTime>()
            list.add(fajr)
            list.add(sunrise)
            list.add(dhuhr)
            list.add(asr)
            list.add(maghrib)
            list.add(isha)
            return list
        }
}
