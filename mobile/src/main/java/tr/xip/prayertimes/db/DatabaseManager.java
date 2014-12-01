package tr.xip.prayertimes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tr.xip.prayertimes.api.objects.Location;
import tr.xip.prayertimes.db.table.LocationsTable;

/**
 * Created by ix on 12/1/14.
 */
public class DatabaseManager {
    Context context;

    SQLiteDatabase db;

    public static final String SORT_ASCN = " ASC";
    public static final String SORT_DESC = " DSC";

    private final String TAG = "Database Manager";

    public DatabaseManager(Context context) {
        this.context = context;
        this.db = new DatabaseHelper(context).getWritableDatabase();
    }

    public long addLocation(Location location) {
        ContentValues values = new ContentValues();
        values.put(LocationsTable.COLUMN_COUNTRY_ID, location.getCountryId());
        values.put(LocationsTable.COLUMN_CITY_ID, location.getCityId());
        values.put(LocationsTable.COLUMN_COUNTY_ID, location.getCountyId());

        return db.insert(LocationsTable.TABLE_NAME, LocationsTable.COLUMN_NULLABLE, values);
    }

    public List<Location> getLocations() {
        List<Location> list = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + LocationsTable.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {
            do {
                int databaseId = c.getInt(c.getColumnIndexOrThrow(LocationsTable.COLUMN_ID));
                String countryId = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTRY_ID));
                String cityId = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_CITY_ID));
                String countyId = c.getString(c.getColumnIndexOrThrow(LocationsTable.COLUMN_COUNTY_ID));

                Location location = new Location(databaseId, countryId, cityId, countyId);
                list.add(location);
            } while (c.moveToNext());
        } else {
            Log.e(TAG, "No locations found; returning empty list.");
        }

        return list;
    }
}
