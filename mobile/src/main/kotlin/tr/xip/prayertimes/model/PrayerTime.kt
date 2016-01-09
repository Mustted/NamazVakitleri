package tr.xip.prayertimes.model

import android.graphics.drawable.Drawable

import java.io.Serializable

import tr.xip.prayertimes.R
import tr.xip.prayertimes.util.getContext
import tr.xip.prayertimes.util.getTimestampFromDateTime

class PrayerTime : Serializable {
    var time: Long = 0
    var type: Type? = null

    constructor(type: PrayerTime.Type, time: Long) {
        this.time = time
        this.type = type
    }

    constructor(type: PrayerTime.Type, date: String, time: String) {
        this.time = getTimestampFromDateTime(date, time)
        this.type = type
    }

    val title: String
        get() {
            when (type) {
                Type.FAJR -> return getContext().getString(R.string.prayer_time_fajr)
                Type.SUNRISE -> return getContext().getString(R.string.prayer_time_sunrise)
                Type.DHUHR -> return getContext().getString(R.string.prayer_time_dhuhr)
                Type.ASR -> return getContext().getString(R.string.prayer_time_asr)
                Type.MAGHRIB -> return getContext().getString(R.string.prayer_time_maghrib)
                Type.ISHA -> return getContext().getString(R.string.prayer_time_isha)
                else -> return "?"
            }
        }

    val smallImage: Drawable?
        get() {
            when (type) {
                Type.FAJR -> return getContext().resources.getDrawable(R.drawable.prayer_time_fajr_small, null)
                Type.SUNRISE -> return getContext().resources.getDrawable(R.drawable.prayer_time_sunrise_small, null)
                Type.DHUHR -> return getContext().resources.getDrawable(R.drawable.prayer_time_dhuhr_small, null)
                Type.ASR -> return getContext().resources.getDrawable(R.drawable.prayer_time_asr_small, null)
                Type.MAGHRIB -> return getContext().resources.getDrawable(R.drawable.prayer_time_maghrib_small, null)
                Type.ISHA -> return getContext().resources.getDrawable(R.drawable.prayer_time_isha_small, null)
                else -> return null
            }
        }

    val largeImage: Drawable?
        get() {
            when (type) {
                Type.FAJR -> return getContext().resources.getDrawable(R.drawable.prayer_time_fajr_large, null)
                Type.SUNRISE -> return getContext().resources.getDrawable(R.drawable.prayer_time_sunrise_large, null)
                Type.DHUHR -> return getContext().resources.getDrawable(R.drawable.prayer_time_dhuhr_large, null)
                Type.ASR -> return getContext().resources.getDrawable(R.drawable.prayer_time_asr_large, null)
                Type.MAGHRIB -> return getContext().resources.getDrawable(R.drawable.prayer_time_maghrib_large, null)
                Type.ISHA -> return getContext().resources.getDrawable(R.drawable.prayer_time_isha_large, null)
                else -> return null
            }
        }

    enum class Type {
        FAJR, SUNRISE, DHUHR, ASR, MAGHRIB, ISHA
    }
}
