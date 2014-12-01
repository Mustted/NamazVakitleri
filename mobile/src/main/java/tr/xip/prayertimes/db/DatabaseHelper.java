package tr.xip.prayertimes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import tr.xip.prayertimes.db.table.LocationsTable;

/**
 * Created by ix on 12/1/14.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database.db";

    private final String TAG = "Database Helper";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String INTEGER_PRIMARY_KEY_AUTOINCREMENT = " INTEGER PRIMARY KEY AUTOINCREMENT";
    private static final String COMMA_SEP = ", ";

    private static final String SQL_CREATE_LOCATIONS_TABLE = "CREATE TABLE "
            + LocationsTable.TABLE_NAME + " ("
            + LocationsTable.COLUMN_ID + INTEGER_PRIMARY_KEY_AUTOINCREMENT + COMMA_SEP
            + LocationsTable.COLUMN_COUNTRY_ID + TEXT_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_CITY_ID + TEXT_TYPE + COMMA_SEP
            + LocationsTable.COLUMN_COUNTY_ID + TEXT_TYPE + " )";

    private static final String SQL_DELETE_LOCATIONS_TABLE = "DROP TABLE IF EXISTS "
            + LocationsTable.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_LOCATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_LOCATIONS_TABLE);
        Log.d(TAG, "Cleared database!");
        onCreate(db);
    }
}
