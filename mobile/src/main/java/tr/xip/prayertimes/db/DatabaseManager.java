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

/**
 * Created by ix on 12/1/14.
 */
public class DatabaseManager {
    Context context;

    SQLiteDatabase db;

    public static final String SORT_ASCN = " ASC";
    public static final String SORT_DESC = " DESC";

    private final String TAG = "Database Manager";

    String[] prayerTimesProjection = {
            PrayerTimesTable.COLUMN_NAME_ID,
            PrayerTimesTable.COLUMN_NAME_CITY,
            PrayerTimesTable.COLUMN_NAME_COUNTY,
            PrayerTimesTable.COLUMN_NAME_DATE,
            PrayerTimesTable.COLUMN_NAME_FAJR,
            PrayerTimesTable.COLUMN_NAME_SUNRISE,
            PrayerTimesTable.COLUMN_NAME_DHUHR,
            PrayerTimesTable.COLUMN_NAME_ASR,
            PrayerTimesTable.COLUMN_NAME_MAGHRIB,
            PrayerTimesTable.COLUMN_NAME_ISHA
    };

    public DatabaseManager(Context context) {
        this.context = context;
        this.db = new DatabaseHelper(context).getWritableDatabase();
    }

    public long addLocation(Location location) {
        ContentValues values = new ContentValues();
        values.put(LocationsTable.COLUMN_COUNTRY_ID, location.getCountryId());
        values.put(LocationsTable.COLUMN_COUNTRY_NAME, location.getcountryNama());
        values.put(LocationsTable.COLUMN_CITY_ID, location.getCityId());
        values.put(LocationsTable.COLUMN_CITY_NAME, location.getCityName());
        values.put(LocationsTable.COLUMN_COUNTY_ID, location.getCountyId());
        values.put(LocationsTable.COLUMN_COUNTY_NAME, location.getCountyName());

        return db.insert(LocationsTable.TABLE_NAME, LocationsTable.COLUMN_NULLABLE, values);
    }

    public int removeLocation(int locationId) {
        String selection = LocationsTable.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(locationId)};

        return db.delete(LocationsTable.TABLE_NAME, selection, selectionArgs);
    }

    public List<Location> getLocations() {
        List<Location> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + LocationsTable.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {
            do {
                int databaseId = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_ID));
                String countryId = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTRY_ID));
                String countryName = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTRY_NAME));
                String cityId = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_CITY_ID));
                String cityName = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_CITY_NAME));
                String countyId = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTY_ID));
                String countyName = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTY_NAME));

                Location location = new Location(
                        databaseId,
                        countryId,
                        countryName,
                        cityId,
                        cityName,
                        countyId,
                        countyName
                );
                list.add(location);
            } while (c.moveToNext());
        } else {
            Log.e(TAG, "No locations found; returning empty list.");
        }

        return list;
    }

    public PrayerTimes getPrayerTimes(String countryId, String cityId, String countyId, long date) {

        String sortOrder =
                PrayerTimesTable.COLUMN_NAME_DATE + SORT_ASCN;

        String selection;
        String[] selectionArgs;

        if (countyId == null) {
            selection = PrayerTimesTable.COLUMN_NAME_COUNTRY + " = ?" + " AND "
                    + PrayerTimesTable.COLUMN_NAME_CITY + " = ?" + " AND "
                    + PrayerTimesTable.COLUMN_NAME_DATE + " = ?";
            selectionArgs = new String[]{
                    countryId,
                    cityId,
                    String.valueOf(date)
            };
        } else {
            selection = PrayerTimesTable.COLUMN_NAME_COUNTRY + " = ?" + " AND "
                    + PrayerTimesTable.COLUMN_NAME_CITY + " = ?" + " AND "
                    + PrayerTimesTable.COLUMN_NAME_COUNTY + " = ?" + " AND "
                    + PrayerTimesTable.COLUMN_NAME_DATE + " = ?";
            selectionArgs = new String[]{
                    countryId,
                    cityId,
                    countyId,
                    String.valueOf(date)
            };
        }

        Cursor c = db.query(
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

    public void addPrayerTimesByCityId(List<PrayerTimes> list, String countryId, String cityId) {
        for (PrayerTimes prayerTimes : list) {
            ContentValues values = new ContentValues();
            values.put(PrayerTimesTable.COLUMN_NAME_COUNTRY, countryId);
            values.put(PrayerTimesTable.COLUMN_NAME_CITY, cityId);
            values.put(PrayerTimesTable.COLUMN_NAME_COUNTY, "null");
            values.put(PrayerTimesTable.COLUMN_NAME_DATE, prayerTimes.getDate());
            values.put(PrayerTimesTable.COLUMN_NAME_FAJR, prayerTimes.getFajr());
            values.put(PrayerTimesTable.COLUMN_NAME_SUNRISE, prayerTimes.getSunrise());
            values.put(PrayerTimesTable.COLUMN_NAME_DHUHR, prayerTimes.getDhuhr());
            values.put(PrayerTimesTable.COLUMN_NAME_ASR, prayerTimes.getAsr());
            values.put(PrayerTimesTable.COLUMN_NAME_MAGHRIB, prayerTimes.getMaghrib());
            values.put(PrayerTimesTable.COLUMN_NAME_ISHA, prayerTimes.getIsha());

            db.insert(PrayerTimesTable.TABLE_NAME, PrayerTimesTable.COLUMN_NULLABLE, values);
        }
    }

    public void addPrayerTimesByCountyId(List<PrayerTimes> list, String countryId, String cityId, String countyId) {
        for (PrayerTimes prayerTimes : list) {
            ContentValues values = new ContentValues();
            values.put(PrayerTimesTable.COLUMN_NAME_COUNTRY, countryId);
            values.put(PrayerTimesTable.COLUMN_NAME_CITY, cityId);
            values.put(PrayerTimesTable.COLUMN_NAME_COUNTY, countyId);
            values.put(PrayerTimesTable.COLUMN_NAME_DATE, prayerTimes.getDate());
            values.put(PrayerTimesTable.COLUMN_NAME_FAJR, prayerTimes.getFajr());
            values.put(PrayerTimesTable.COLUMN_NAME_SUNRISE, prayerTimes.getSunrise());
            values.put(PrayerTimesTable.COLUMN_NAME_DHUHR, prayerTimes.getDhuhr());
            values.put(PrayerTimesTable.COLUMN_NAME_ASR, prayerTimes.getAsr());
            values.put(PrayerTimesTable.COLUMN_NAME_MAGHRIB, prayerTimes.getMaghrib());
            values.put(PrayerTimesTable.COLUMN_NAME_ISHA, prayerTimes.getIsha());

            db.insert(PrayerTimesTable.TABLE_NAME, PrayerTimesTable.COLUMN_NULLABLE, values);
        }
    }
}
