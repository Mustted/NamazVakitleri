package tr.xip.prayertimes.db.table

import android.provider.BaseColumns

object LocationsTable : BaseColumns {
    val TABLE_NAME = "locations"
    val COLUMN_ID = "_id"
    val COLUMN_COUNTRY_ID = "country"
    val COLUMN_COUNTRY_NAME = "countryName"
    val COLUMN_CITY_ID = "city"
    val COLUMN_CITY_NAME = "cityName"
    val COLUMN_COUNTY_ID = "county"
    val COLUMN_COUNTY_NAME = "countyName"
    val COLUMN_NAME_NOTIF_FAJR = "fajrNotification"
    val COLUMN_NAME_NOTIF_SUNRISE = "sunriseNotification"
    val COLUMN_NAME_NOTIF_DHUHR = "dhuhrNotification"
    val COLUMN_NAME_NOTIF_ASR = "asrNotification"
    val COLUMN_NAME_NOTIF_MAGHRIB = "maghribNotification"
    val COLUMN_NAME_NOTIF_ISHA = "ishaNotification"
    val COLUMN_NULLABLE = "nullable"
}
