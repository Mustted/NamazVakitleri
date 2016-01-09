package tr.xip.prayertimes.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log

import tr.xip.prayertimes.model.Location
import tr.xip.prayertimes.model.PrayerTimes
import tr.xip.prayertimes.db.table.LocationsTable
import tr.xip.prayertimes.db.table.PrayerTimesTable
import tr.xip.prayertimes.util.LocationsList

import tr.xip.prayertimes.util.getContext

object DatabaseManager {
    val SORT_ASCN = " ASC"
    val SORT_DESC = " DESC"

    private val TAG = "Database Manager"

    private var db: SQLiteDatabase? = null

    var prayerTimesProjection = arrayOf(
            PrayerTimesTable.COLUMN_NAME_ID,
            PrayerTimesTable.COLUMN_NAME_LOCATION,
            PrayerTimesTable.COLUMN_NAME_DATE,
            PrayerTimesTable.COLUMN_NAME_FAJR,
            PrayerTimesTable.COLUMN_NAME_SUNRISE,
            PrayerTimesTable.COLUMN_NAME_DHUHR,
            PrayerTimesTable.COLUMN_NAME_ASR,
            PrayerTimesTable.COLUMN_NAME_MAGHRIB,
            PrayerTimesTable.COLUMN_NAME_ISHA
    )

    var locationsProjection = arrayOf(
            LocationsTable.COLUMN_ID,
            LocationsTable.COLUMN_COUNTRY_ID,
            LocationsTable.COLUMN_COUNTRY_NAME,
            LocationsTable.COLUMN_CITY_ID,
            LocationsTable.COLUMN_CITY_NAME,
            LocationsTable.COLUMN_COUNTY_ID,
            LocationsTable.COLUMN_COUNTY_NAME
    )

    fun init() {
        db = DatabaseHelper(getContext()).writableDatabase
    }

    fun addLocation(location: Location): Long {
        val values = ContentValues()
        values.put(LocationsTable.COLUMN_COUNTRY_ID, location.countryId)
        values.put(LocationsTable.COLUMN_COUNTRY_NAME, location.countryName)
        values.put(LocationsTable.COLUMN_CITY_ID, location.cityId)
        values.put(LocationsTable.COLUMN_CITY_NAME, location.cityName)
        values.put(LocationsTable.COLUMN_COUNTY_ID, location.countyId)
        values.put(LocationsTable.COLUMN_COUNTY_NAME, location.countyName)
        values.put(LocationsTable.COLUMN_NAME_NOTIF_FAJR, location.fajrNotification)
        values.put(LocationsTable.COLUMN_NAME_NOTIF_SUNRISE, location.sunriseNotification)
        values.put(LocationsTable.COLUMN_NAME_NOTIF_DHUHR, location.dhuhrNotification)
        values.put(LocationsTable.COLUMN_NAME_NOTIF_ASR, location.asrNotification)
        values.put(LocationsTable.COLUMN_NAME_NOTIF_MAGHRIB, location.maghribNotification)
        values.put(LocationsTable.COLUMN_NAME_NOTIF_ISHA, location.ishaNotification)

        return db!!.insert(LocationsTable.TABLE_NAME, LocationsTable.COLUMN_NULLABLE, values)
    }

    fun updateLocation(location: Location): Int {
        val values = ContentValues()
        values.put(LocationsTable.COLUMN_COUNTRY_ID, location.countryId)
        values.put(LocationsTable.COLUMN_COUNTRY_NAME, location.countryName)
        values.put(LocationsTable.COLUMN_CITY_ID, location.cityId)
        values.put(LocationsTable.COLUMN_CITY_NAME, location.cityName)
        values.put(LocationsTable.COLUMN_COUNTY_ID, location.countyId)
        values.put(LocationsTable.COLUMN_COUNTY_NAME, location.countyName)
        values.put(LocationsTable.COLUMN_NAME_NOTIF_FAJR, location.fajrNotification)
        values.put(LocationsTable.COLUMN_NAME_NOTIF_SUNRISE, location.sunriseNotification)
        values.put(LocationsTable.COLUMN_NAME_NOTIF_DHUHR, location.dhuhrNotification)
        values.put(LocationsTable.COLUMN_NAME_NOTIF_ASR, location.asrNotification)
        values.put(LocationsTable.COLUMN_NAME_NOTIF_MAGHRIB, location.maghribNotification)
        values.put(LocationsTable.COLUMN_NAME_NOTIF_ISHA, location.ishaNotification)

        val where = LocationsTable.COLUMN_ID + " = ?"
        val whereArgs = arrayOf(location.databaseId.toString())

        return db!!.update(LocationsTable.TABLE_NAME, values, where, whereArgs)
    }

    fun removeLocation(locationId: Int) {
        var selection = LocationsTable.COLUMN_ID + " = ?"
        val selectionArgs = arrayOf(locationId.toString())

        db!!.delete(LocationsTable.TABLE_NAME, selection, selectionArgs)

        selection = PrayerTimesTable.COLUMN_NAME_LOCATION + " = ?"

        db!!.delete(PrayerTimesTable.TABLE_NAME, selection, selectionArgs)
    }

    fun getLocations(): LocationsList {
        val list = LocationsList()

        val selectQuery = "SELECT * FROM " + LocationsTable.TABLE_NAME

        val c = db!!.rawQuery(selectQuery, null)

        if (c != null && c.moveToFirst()) {
            do {
                val databaseId = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_ID))
                val countryId = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTRY_ID))
                val countryName = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTRY_NAME))
                val cityId = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_CITY_ID))
                val cityName = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_CITY_NAME))
                val countyId = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTY_ID))
                val countyName = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTY_NAME))
                val fajrNotif = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_NAME_NOTIF_FAJR))
                val sunriseNotif = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_NAME_NOTIF_SUNRISE))
                val dhuhrNotif = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_NAME_NOTIF_DHUHR))
                val asrNotif = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_NAME_NOTIF_ASR))
                val maghribNotif = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_NAME_NOTIF_MAGHRIB))
                val ishaNotif = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_NAME_NOTIF_ISHA))

                val location = Location(
                        databaseId,
                        countryId,
                        countryName,
                        cityId,
                        cityName,
                        countyId,
                        countyName,
                        fajrNotif,
                        sunriseNotif,
                        dhuhrNotif,
                        asrNotif,
                        maghribNotif,
                        ishaNotif
                )
                list.add(location)
            } while (c.moveToNext())
        } else {
            Log.e(TAG, "No locations found; returning empty list.")
        }

        return list
    }

    fun getPrayerTimes(locationId: Int, date: Long): PrayerTimes? {

        val sortOrder = PrayerTimesTable.COLUMN_NAME_DATE + SORT_ASCN

        val selection = PrayerTimesTable.COLUMN_NAME_LOCATION + " = ?" + " AND " + PrayerTimesTable.COLUMN_NAME_DATE + " = ?"
        val selectionArgs = arrayOf(locationId.toString(), date.toString())

        val c = db!!.query(
                PrayerTimesTable.TABLE_NAME,
                prayerTimesProjection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder)

        c.moveToFirst()
        try {
            return PrayerTimes(
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_DATE)),
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_FAJR)),
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_SUNRISE)),
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_DHUHR)),
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_ASR)),
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_MAGHRIB)),
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_ISHA)))
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    fun addPrayerTimes(list: List<PrayerTimes>, locationId: Int) {
        for (prayerTimes in list) {
            val values = ContentValues()
            values.put(PrayerTimesTable.COLUMN_NAME_LOCATION, locationId)
            values.put(PrayerTimesTable.COLUMN_NAME_DATE, prayerTimes.date)
            values.put(PrayerTimesTable.COLUMN_NAME_FAJR, prayerTimes.fajr.time)
            values.put(PrayerTimesTable.COLUMN_NAME_SUNRISE, prayerTimes.sunrise.time)
            values.put(PrayerTimesTable.COLUMN_NAME_DHUHR, prayerTimes.dhuhr.time)
            values.put(PrayerTimesTable.COLUMN_NAME_ASR, prayerTimes.asr.time)
            values.put(PrayerTimesTable.COLUMN_NAME_MAGHRIB, prayerTimes.maghrib.time)
            values.put(PrayerTimesTable.COLUMN_NAME_ISHA, prayerTimes.isha.time)

            db!!.insert(PrayerTimesTable.TABLE_NAME, PrayerTimesTable.COLUMN_NULLABLE, values)
        }
    }
}
