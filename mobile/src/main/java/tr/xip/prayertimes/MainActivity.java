package tr.xip.prayertimes;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import tr.xip.prayertimes.apdater.PrayerTimesAdapter;
import tr.xip.prayertimes.api.DiyanetApi;
import tr.xip.prayertimes.api.objects.Location;
import tr.xip.prayertimes.api.objects.PrayerTimes;
import tr.xip.prayertimes.db.DatabaseManager;
import tr.xip.prayertimes.widget.DividerItemDecoration;

public class MainActivity extends ActionBarActivity {
    private static final String STATE_PRAYER_TIMES = "state_prayer_times";

    private static final int FLIPPER_PROGRESS_BAR = 1;
    private static final int FLIPPER_CONTENT = 0;

    private DatabaseManager dbMan;
    private DiyanetApi api;

    private List<Location> mLocations = new ArrayList<>();
    private PrayerTimes mPrayerTimes;

    private ViewFlipper mFlipper;

    private RecyclerView mRecyclerView;
    private PrayerTimesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbMan = new DatabaseManager(this);
        api = new DiyanetApi();

        mLocations = dbMan.getLocations();

        if (savedInstanceState != null)
            mPrayerTimes = (PrayerTimes) savedInstanceState.get(STATE_PRAYER_TIMES);

        if (mLocations.size() == 0) {
            startActivity(new Intent(this, CountryChooserActivity.class));
            finish();
        } else {
            mFlipper = (ViewFlipper) findViewById(R.id.activity_main_flipper);

            mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));

            if (mPrayerTimes != null)
                displayPrayerTimes();
            else
                new FetchPrayerTimesTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                // ...
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_PRAYER_TIMES, mPrayerTimes);
    }

    private void displayPrayerTimes() {
        mAdapter = new PrayerTimesAdapter(this, mPrayerTimes.getPrayerTimesArrayList());
        if (mRecyclerView != null)
            mRecyclerView.setAdapter(mAdapter);
    }

    private class FetchPrayerTimesTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mFlipper != null)
                mFlipper.setDisplayedChild(FLIPPER_PROGRESS_BAR);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Location mLocation = mLocations.get(0);
            try {
                if (mLocation.getCountyId() != null) {
                    mPrayerTimes = api.getPrayerTimesForCounty(
                            mLocation.getCountryId(),
                            mLocation.getCityId(),
                            mLocation.getCountyId()
                    ).get(0);
                } else {
                    mPrayerTimes = api.getPrayerTimesForCity(
                            mLocation.getCountryId(),
                            mLocation.getCityId()
                    ).get(0);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            if (success) {
                displayPrayerTimes();
            } else {
                // TODO: indicate failure
            }

            if (mFlipper != null)
                mFlipper.setDisplayedChild(FLIPPER_CONTENT);
        }
    }
}
