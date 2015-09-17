package tr.xip.prayertimes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import tr.xip.prayertimes.db.table.LocationsTable;
import tr.xip.prayertimes.db.table.PrayerTimesTable;

/**
 * Created by ix on 12/1/14.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "prayer_times_database.sDb";

    private final String TAG = "Database Helper";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String INTEGER_PRIMARY_KEY_AUTOINCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String COMMA_SEP = ", ";

    public static final String SQL_CREATE_LOCATIONS_TABLE = "CREATE TABLE "
            + LocationsTable.TABLE_NAME + " ("
            + LocationsTable.COLUMN_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA_SEP
            + LocationsTable.COLUMN_COUNTRY_ID + TEXT_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_COUNTRY_NAME + TEXT_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_CITY_ID + TEXT_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_CITY_NAME + TEXT_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_COUNTY_ID + TEXT_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_COUNTY_NAME + TEXT_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_NAME_NOTIF_FAJR + INTEGER_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_NAME_NOTIF_SUNRISE + INTEGER_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_NAME_NOTIF_DHUHR + INTEGER_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_NAME_NOTIF_ASR + INTEGER_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_NAME_NOTIF_MAGHRIB + INTEGER_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_NAME_NOTIF_ISHA + INTEGER_TYPE + " )";

    public static final String SQL_DELETE_LOCATIONS_TABLE = "DROP TABLE IF EXISTS "
            + LocationsTable.TABLE_NAME;

    public static final String SQL_CREATE_PRAYER_TIMES_TABLE = "CREATE TABLE "
            + PrayerTimesTable.TABLE_NAME + " ("
            + PrayerTimesTable.COLUMN_NAME_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA_SEP
            + PrayerTimesTable.COLUMN_NAME_LOCATION + INTEGER_TYPE + COMMA_SEP
            + PrayerTimesTable.COLUMN_NAME_DATE + INTEGER_TYPE + COMMA_SEP
            + PrayerTimesTable.COLUMN_NAME_FAJR + INTEGER_TYPE + COMMA_SEP
            + PrayerTimesTable.COLUMN_NAME_SUNRISE + INTEGER_TYPE + COMMA_SEP
            + PrayerTimesTable.COLUMN_NAME_DHUHR + INTEGER_TYPE + COMMA_SEP
            + PrayerTimesTable.COLUMN_NAME_ASR + INTEGER_TYPE + COMMA_SEP
            + PrayerTimesTable.COLUMN_NAME_MAGHRIB + INTEGER_TYPE + COMMA_SEP
            + PrayerTimesTable.COLUMN_NAME_ISHA + INTEGER_TYPE + " )";

    public static final String SQL_DELETE_PRAYER_TIMES_TABLE = "DROP TABLE IF EXISTS "
            + PrayerTimesTable.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LOCATIONS_TABLE);
        db.execSQL(SQL_CREATE_PRAYER_TIMES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_LOCATIONS_TABLE);
        db.execSQL(SQL_DELETE_PRAYER_TIMES_TABLE);
        Log.d(TAG, "Cleared database!");
        onCreate(db);
    }
}
