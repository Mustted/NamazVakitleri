package tr.xip.prayertimes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tr.xip.prayertimes.api.objects.Location;
import tr.xip.prayertimes.api.objects.PrayerTimes;
import tr.xip.prayertimes.db.table.LocationsTable;
import tr.xip.prayertimes.db.table.PrayerTimesTable;

import static tr.xip.prayertimes.ui.app.NamazVakitleriApplication.getContext;

public class DatabaseManager {
    public static final String SORT_ASCN = " ASC";
    public static final String SORT_DESC = " DESC";

    private static final String TAG = "Database Manager";

    private static SQLiteDatabase sDb;

    public static String[] prayerTimesProjection = {
            PrayerTimesTable.COLUMN_NAME_ID,
            PrayerTimesTable.COLUMN_NAME_LOCATION,
            PrayerTimesTable.COLUMN_NAME_DATE,
            PrayerTimesTable.COLUMN_NAME_FAJR,
            PrayerTimesTable.COLUMN_NAME_SUNRISE,
            PrayerTimesTable.COLUMN_NAME_DHUHR,
            PrayerTimesTable.COLUMN_NAME_ASR,
            PrayerTimesTable.COLUMN_NAME_MAGHRIB,
            PrayerTimesTable.COLUMN_NAME_ISHA
    };

    public static String[] locationsProjection = {
            LocationsTable.COLUMN_ID,
            LocationsTable.COLUMN_COUNTRY_ID,
            LocationsTable.COLUMN_COUNTRY_NAME,
            LocationsTable.COLUMN_CITY_ID,
            LocationsTable.COLUMN_CITY_NAME,
            LocationsTable.COLUMN_COUNTY_ID,
            LocationsTable.COLUMN_COUNTY_NAME,
    };

    private DatabaseManager() {}

    public static void init() {
        sDb = new DatabaseHelper(getContext()).getWritableDatabase();
    }

    public static long addLocation(Location location) {
        ContentValues values = new ContentValues();
        values.put(LocationsTable.COLUMN_COUNTRY_ID, location.getCountryId());
        values.put(LocationsTable.COLUMN_COUNTRY_NAME, location.getCountryName());
        values.put(LocationsTable.COLUMN_CITY_ID, location.getCityId());
        values.put(LocationsTable.COLUMN_CITY_NAME, location.getCityName());
        values.put(LocationsTable.COLUMN_COUNTY_ID, location.getCountyId());
        values.put(LocationsTable.COLUMN_COUNTY_NAME, location.getCountyName());
        values.put(LocationsTable.COLUMN_NAME_NOTIF_FAJR, location.getFajrNotification());
        values.put(LocationsTable.COLUMN_NAME_NOTIF_SUNRISE, location.getSunriseNotiication());
        values.put(LocationsTable.COLUMN_NAME_NOTIF_DHUHR, location.getDhuhrNotification());
        values.put(LocationsTable.COLUMN_NAME_NOTIF_ASR, location.getAsrNotification());
        values.put(LocationsTable.COLUMN_NAME_NOTIF_MAGHRIB, location.getMaghribNotification());
        values.put(LocationsTable.COLUMN_NAME_NOTIF_ISHA, location.getIshaNotification());

        return sDb.insert(LocationsTable.TABLE_NAME, LocationsTable.COLUMN_NULLABLE, values);
    }

    public static int updateLocation(Location location) {
        ContentValues values = new ContentValues();
        values.put(LocationsTable.COLUMN_COUNTRY_ID, location.getCountryId());
        values.put(LocationsTable.COLUMN_COUNTRY_NAME, location.getCountryName());
        values.put(LocationsTable.COLUMN_CITY_ID, location.getCityId());
        values.put(LocationsTable.COLUMN_CITY_NAME, location.getCityName());
        values.put(LocationsTable.COLUMN_COUNTY_ID, location.getCountyId());
        values.put(LocationsTable.COLUMN_COUNTY_NAME, location.getCountyName());
        values.put(LocationsTable.COLUMN_NAME_NOTIF_FAJR, location.getFajrNotification());
        values.put(LocationsTable.COLUMN_NAME_NOTIF_SUNRISE, location.getSunriseNotiication());
        values.put(LocationsTable.COLUMN_NAME_NOTIF_DHUHR, location.getDhuhrNotification());
        values.put(LocationsTable.COLUMN_NAME_NOTIF_ASR, location.getAsrNotification());
        values.put(LocationsTable.COLUMN_NAME_NOTIF_MAGHRIB, location.getMaghribNotification());
        values.put(LocationsTable.COLUMN_NAME_NOTIF_ISHA, location.getIshaNotification());

        String where = LocationsTable.COLUMN_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(location.getDatabaseId())};

        return sDb.update(LocationsTable.TABLE_NAME, values, where, whereArgs);
    }

    public static void removeLocation(int locationId) {
        String selection = LocationsTable.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(locationId)};

        sDb.delete(LocationsTable.TABLE_NAME, selection, selectionArgs);

        selection = PrayerTimesTable.COLUMN_NAME_LOCATION + " = ?";

        sDb.delete(PrayerTimesTable.TABLE_NAME, selection, selectionArgs);
    }

    public static List<Location> getLocations() {
        List<Location> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + LocationsTable.TABLE_NAME;

        Cursor c = sDb.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {
            do {
                int databaseId = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_ID));
                String countryId = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTRY_ID));
                String countryName = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTRY_NAME));
                String cityId = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_CITY_ID));
                String cityName = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_CITY_NAME));
                String countyId = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTY_ID));
                String countyName = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTY_NAME));
                int fajrNotif = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_NAME_NOTIF_FAJR));
                int sunriseNotif = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_NAME_NOTIF_SUNRISE));
                int dhuhrNotif = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_NAME_NOTIF_DHUHR));
                int asrNotif = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_NAME_NOTIF_ASR));
                int maghribNotif = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_NAME_NOTIF_MAGHRIB));
                int ishaNotif = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_NAME_NOTIF_ISHA));

                Location location = new Location(
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
                );
                list.add(location);
            } while (c.moveToNext());
        } else {
            Log.e(TAG, "No locations found; returning empty list.");
        }

        return list;
    }

    public static PrayerTimes getPrayerTimes(int locationId, long date) {

        String sortOrder =
                PrayerTimesTable.COLUMN_NAME_DATE + SORT_ASCN;

        String selection = PrayerTimesTable.COLUMN_NAME_LOCATION + " = ?" + " AND "
                    + PrayerTimesTable.COLUMN_NAME_DATE + " = ?";
        String[] selectionArgs = new String[]{
                String.valueOf(locationId),
                String.valueOf(date)
        };

        Cursor c = sDb.query(
                PrayerTimesTable.TABLE_NAME,
                prayerTimesProjection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        c.moveToFirst();
        try {
            return new PrayerTimes(
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_DATE)),
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_FAJR)),
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_SUNRISE)),
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_DHUHR)),
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_ASR)),
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_MAGHRIB)),
                    c.getLong(c.getColumnIndexOrThrow(PrayerTimesTable.COLUMN_NAME_ISHA))
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addPrayerTimes(List<PrayerTimes> list, int locationId) {
        for (PrayerTimes prayerTimes : list) {
            ContentValues values = new ContentValues();
            values.put(PrayerTimesTable.COLUMN_NAME_LOCATION, locationId);
            values.put(PrayerTimesTable.COLUMN_NAME_DATE, prayerTimes.getDate());
            values.put(PrayerTimesTable.COLUMN_NAME_FAJR, prayerTimes.getFajr().getTime());
            values.put(PrayerTimesTable.COLUMN_NAME_SUNRISE, prayerTimes.getSunrise().getTime());
            values.put(PrayerTimesTable.COLUMN_NAME_DHUHR, prayerTimes.getDhuhr().getTime());
            values.put(PrayerTimesTable.COLUMN_NAME_ASR, prayerTimes.getAsr().getTime());
            values.put(PrayerTimesTable.COLUMN_NAME_MAGHRIB, prayerTimes.getMaghrib().getTime());
            values.put(PrayerTimesTable.COLUMN_NAME_ISHA, prayerTimes.getIsha().getTime());

            sDb.insert(PrayerTimesTable.TABLE_NAME, PrayerTimesTable.COLUMN_NULLABLE, values);
        }
    }
}
