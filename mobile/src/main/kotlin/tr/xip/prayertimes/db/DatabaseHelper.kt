package tr.xip.prayertimes.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import tr.xip.prayertimes.db.table.LocationsTable
import tr.xip.prayertimes.db.table.PrayerTimesTable

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_LOCATIONS_TABLE)
        db.execSQL(SQL_CREATE_PRAYER_TIMES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_LOCATIONS_TABLE)
        db.execSQL(SQL_DELETE_PRAYER_TIMES_TABLE)
        Log.d(TAG, "Cleared database!")
        onCreate(db)
    }

    companion object {
        private val TAG = "Database Helper"

        val DATABASE_VERSION = 1
        val DATABASE_NAME = "database.db"

        private val TEXT_TYPE = " TEXT"
        private val INTEGER_TYPE = " INTEGER"
        private val INTEGER_PRIMARY_KEY_AUTOINCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT"
        private val COMMA_SEP = ", "

        val SQL_CREATE_LOCATIONS_TABLE = "CREATE TABLE " + LocationsTable.TABLE_NAME + " (" +
                LocationsTable.COLUMN_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA_SEP +
                LocationsTable.COLUMN_COUNTRY_ID + TEXT_TYPE + COMMA_SEP +
                LocationsTable.COLUMN_COUNTRY_NAME + TEXT_TYPE + COMMA_SEP +
                LocationsTable.COLUMN_CITY_ID + TEXT_TYPE + COMMA_SEP +
                LocationsTable.COLUMN_CITY_NAME + TEXT_TYPE + COMMA_SEP +
                LocationsTable.COLUMN_COUNTY_ID + TEXT_TYPE + COMMA_SEP +
                LocationsTable.COLUMN_COUNTY_NAME + TEXT_TYPE + COMMA_SEP +
                LocationsTable.COLUMN_NAME_NOTIF_FAJR + INTEGER_TYPE + COMMA_SEP +
                LocationsTable.COLUMN_NAME_NOTIF_SUNRISE + INTEGER_TYPE + COMMA_SEP +
                LocationsTable.COLUMN_NAME_NOTIF_DHUHR + INTEGER_TYPE + COMMA_SEP +
                LocationsTable.COLUMN_NAME_NOTIF_ASR + INTEGER_TYPE + COMMA_SEP +
                LocationsTable.COLUMN_NAME_NOTIF_MAGHRIB + INTEGER_TYPE + COMMA_SEP +
                LocationsTable.COLUMN_NAME_NOTIF_ISHA + INTEGER_TYPE +
                " )"

        val SQL_DELETE_LOCATIONS_TABLE = "DROP TABLE IF EXISTS " + LocationsTable.TABLE_NAME

        val SQL_CREATE_PRAYER_TIMES_TABLE = "CREATE TABLE " + PrayerTimesTable.TABLE_NAME + " (" +
                PrayerTimesTable.COLUMN_NAME_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA_SEP +
                PrayerTimesTable.COLUMN_NAME_LOCATION + INTEGER_TYPE + COMMA_SEP +
                PrayerTimesTable.COLUMN_NAME_DATE + INTEGER_TYPE + COMMA_SEP +
                PrayerTimesTable.COLUMN_NAME_FAJR + INTEGER_TYPE + COMMA_SEP +
                PrayerTimesTable.COLUMN_NAME_SUNRISE + INTEGER_TYPE + COMMA_SEP +
                PrayerTimesTable.COLUMN_NAME_DHUHR + INTEGER_TYPE + COMMA_SEP +
                PrayerTimesTable.COLUMN_NAME_ASR + INTEGER_TYPE + COMMA_SEP +
                PrayerTimesTable.COLUMN_NAME_MAGHRIB + INTEGER_TYPE + COMMA_SEP +
                PrayerTimesTable.COLUMN_NAME_ISHA + INTEGER_TYPE +
                " )"

        val SQL_DELETE_PRAYER_TIMES_TABLE = "DROP TABLE IF EXISTS " + PrayerTimesTable.TABLE_NAME
    }
}
