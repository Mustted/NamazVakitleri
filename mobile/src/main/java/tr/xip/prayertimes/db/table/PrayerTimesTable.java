package tr.xip.prayertimes.db.table;

import android.provider.BaseColumns;

public class PrayerTimesTable implements BaseColumns {
    public static final String TABLE_NAME = "prayer_times";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_LOCATION = "location";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_FAJR = "fajr";
    public static final String COLUMN_NAME_SUNRISE = "sunrise";
    public static final String COLUMN_NAME_DHUHR = "dhuhr";
    public static final String COLUMN_NAME_ASR = "asr";
    public static final String COLUMN_NAME_MAGHRIB = "maghrib";
    public static final String COLUMN_NAME_ISHA = "isha";
    public static final String COLUMN_NULLABLE = "nullable";
}
