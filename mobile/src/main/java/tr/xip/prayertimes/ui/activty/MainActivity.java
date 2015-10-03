package tr.xip.prayertimes.ui.activty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import tr.xip.prayertimes.R;
import tr.xip.prayertimes.Utils;
import tr.xip.prayertimes.api.objects.Location;
import tr.xip.prayertimes.db.DatabaseManager;
import tr.xip.prayertimes.ui.apdater.LocationsSpinnerAdapter;
import tr.xip.prayertimes.ui.fragment.PrayerTimesFragment;
import tr.xip.prayertimes.ui.widget.OnItemSelectedListenerAdapter;
import tr.xip.prayertimes.util.LocationsList;

public class MainActivity extends ActionBarActivity {
    private static final String PREF_LAST_LOCATION = "pref_last_location";

    private static final int FLIPPER_NO_LOCATIONS = 1;
    private static final int FLIPPER_CONTENT = 0;

    private Toolbar mToolbar;

    private SharedPreferences mSharedPrefs;

    private LocationsList mLocations = new LocationsList();

    private Spinner mLocationsSpinner;
    private LocationsSpinnerAdapter mAdapter;

    private ViewFlipper mFlipper;

    private MenuItem mRemoveItem;

    private PrayerTimesFragment mFragment;

    private Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setContentInsetsAbsolute(Utils.dpToPx(8), mToolbar.getContentInsetRight());
        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mFlipper = (ViewFlipper) findViewById(R.id.activity_main_flipper);

        mLocations = DatabaseManager.getLocations();

        if (mLocations.size() > 0) {
            setUpLocationsSpinner();
            setContent(mLocations.findById(mSharedPrefs.getInt(PREF_LAST_LOCATION, -1)));
        }

        notifyChange();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mRemoveItem = menu.findItem(R.id.action_remove_location);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_new_location:
                startActivity(new Intent(this, CountryChooserActivity.class));
                finish();
                break;
            case R.id.action_remove_location:
                DatabaseManager.removeLocation(mCurrentLocation.getDatabaseId());
                mAdapter.remove(mCurrentLocation);
                /*if (mCurrentItemPosition > 0) {
                    mLocationsSpinner.setSelection(mCurrentItemPosition - 1);
                }*/
                notifyChange();
                break;
            case R.id.action_settings:
                // ...
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setContent(Location location) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PrayerTimesFragment.newInstance(location))
                .commit();
    }

    private void notifyChange() {
        if (mLocations.size() > 0) {
            mFlipper.setDisplayedChild(FLIPPER_CONTENT);
        } else {
            mFlipper.setDisplayedChild(FLIPPER_NO_LOCATIONS);
        }
    }

    private void setUpLocationsSpinner() {
        //noinspection ConstantConditions
        mAdapter = new LocationsSpinnerAdapter(this,
                R.layout.item_spinner_toolbar_selected,
                mLocations
        );
        mAdapter.setDropDownViewResource(R.layout.item_spinner_toolbar);

        mLocationsSpinner = (Spinner) LayoutInflater.from(this).inflate(
                R.layout.view_toolbar_spinner,
                mToolbar,
                false
        );
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mToolbar.addView(mLocationsSpinner, params);

        mLocationsSpinner.setAdapter(mAdapter);

        mLocationsSpinner.setOnItemSelectedListener(new OnLocationSelectedListener());
    }

    private class OnLocationSelectedListener extends OnItemSelectedListenerAdapter {

        @SuppressLint("CommitPrefEdits")
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mCurrentLocation = mAdapter.getItem(position);
            setContent(mCurrentLocation);

            mSharedPrefs.edit().putInt(PREF_LAST_LOCATION, mCurrentLocation.getDatabaseId()).commit();

            notifyChange();
        }
    }
}
