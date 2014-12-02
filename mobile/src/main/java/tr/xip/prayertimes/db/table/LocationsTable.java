package tr.xip.prayertimes.db.table;

import android.provider.BaseColumns;

/**
 * Created by ix on 12/1/14.
 */
public class LocationsTable implements BaseColumns {
    public static final String TABLE_NAME = "locations";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COUNTRY_ID = "country";
    public static final String COLUMN_CITY_ID = "city";
    public static final String COLUMN_COUNTY_ID = "county";
    public static final String COLUMN_NULLABLE = "nullable";
}
