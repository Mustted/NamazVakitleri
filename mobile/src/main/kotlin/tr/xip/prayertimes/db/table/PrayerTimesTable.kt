package tr.xip.prayertimes.db.table

import android.provider.BaseColumns

object PrayerTimesTable : BaseColumns {
    val TABLE_NAME = "prayer_times"
    val COLUMN_NAME_ID = "_id"
    val COLUMN_NAME_LOCATION = "location"
    val COLUMN_NAME_DATE = "date"
    val COLUMN_NAME_FAJR = "fajr"
    val COLUMN_NAME_SUNRISE = "sunrise"
    val COLUMN_NAME_DHUHR = "dhuhr"
    val COLUMN_NAME_ASR = "asr"
    val COLUMN_NAME_MAGHRIB = "maghrib"
    val COLUMN_NAME_ISHA = "isha"
    val COLUMN_NULLABLE = "nullable"
}
