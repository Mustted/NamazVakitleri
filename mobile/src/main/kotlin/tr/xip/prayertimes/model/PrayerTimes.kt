package tr.xip.prayertimes.model

import com.google.gson.annotations.SerializedName

import java.io.Serializable
import java.util.ArrayList

import tr.xip.prayertimes.model.PrayerTime

class PrayerTimes(val date: Long, fajr: Long, sunrise: Long, dhuhr: Long, asr: Long, maghrib: Long, isha: Long) : Serializable {
    @SerializedName("Tarih")
    private val dateString: String? = null

    @SerializedName("Imsak")
    private val fajrString: String? = null

    @SerializedName("Gunes")
    private val sunriseString: String? = null

    @SerializedName("Ogle")
    private val dhuhrString: String? = null

    @SerializedName("Ikindi")
    private val asrString: String? = null

    @SerializedName("Aksam")
    private val maghribString: String? = null

    @SerializedName("Yatsi")
    private val ishaString: String? = null

    @SerializedName("Kible")
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
