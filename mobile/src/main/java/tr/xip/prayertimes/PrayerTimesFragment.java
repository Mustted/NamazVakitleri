package tr.xip.prayertimes;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import java.util.Calendar;
import java.util.List;

import tr.xip.prayertimes.apdater.PrayerTimesAdapter;
import tr.xip.prayertimes.api.DiyanetApi;
import tr.xip.prayertimes.api.objects.Location;
import tr.xip.prayertimes.api.objects.PrayerTimes;
import tr.xip.prayertimes.db.DatabaseManager;
import tr.xip.prayertimes.widget.DividerItemDecoration;

/**
 * Created by ix on 12/4/14.
 */
public class PrayerTimesFragment extends Fragment {
    public static final String ARG_LOCATION = "arg_location";

    private static final String STATE_PRAYER_TIMES = "state_prayer_times";

    private static final int FLIPPER_PROGRESS_BAR = 1;
    private static final int FLIPPER_CONTENT = 0;

    private Context context;

    private DatabaseManager dbMan;
    private DiyanetApi api;

    private View rootView;

    private Location mLocation;

    private PrayerTimes mPrayerTimes;

    private ViewFlipper mFlipper;

    private RecyclerView mRecyclerView;
    private PrayerTimesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        this.dbMan = new DatabaseManager(context);
        this.api = new DiyanetApi();

        if (savedInstanceState != null)
            mPrayerTimes = (PrayerTimes) savedInstanceState.get(STATE_PRAYER_TIMES);

        Bundle bundle = getArguments();
        if (bundle != null)
            mLocation = (Location) bundle.getSerializable(ARG_LOCATION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_prayer_times, null);

        mFlipper = (ViewFlipper) rootView.findViewById(R.id.prayer_times_flipper);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.prayer_times_recycler);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL_LIST));

        if (mPrayerTimes != null)
            displayPrayerTimes();
        else
            new LoadPrayerTimesForTodayTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_PRAYER_TIMES, mPrayerTimes);
    }

    private boolean locationHasCountyValue() {
        return mLocation.getCountyId() != null;
    }

    private void displayPrayerTimes() {
        if (mPrayerTimes != null) {
            mAdapter = new PrayerTimesAdapter(context, mPrayerTimes.getPrayerTimesArrayList());
            if (mRecyclerView != null)
                mRecyclerView.setAdapter(mAdapter);
        }
    }

    private class LoadPrayerTimesForTodayTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mFlipper != null)
                mFlipper.setDisplayedChild(FLIPPER_PROGRESS_BAR);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            long now = cal.getTimeInMillis();

            mPrayerTimes = dbMan.getPrayerTimes(mLocation.getDatabaseId(), now);

            return mPrayerTimes != null;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            if (success)
                displayPrayerTimes();
            else
                new FetchPrayerTimesTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            if (mFlipper != null)
                mFlipper.setDisplayedChild(FLIPPER_CONTENT);
        }
    }

    private class FetchPrayerTimesTask extends AsyncTask<Void, Void, Boolean> {
        List<PrayerTimes> mPrayerTimesList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mFlipper != null)
                mFlipper.setDisplayedChild(FLIPPER_PROGRESS_BAR);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if (locationHasCountyValue()) {
                    mPrayerTimesList = api.getPrayerTimesForCounty(
                            mLocation.getCountryId(),
                            mLocation.getCityId(),
                            mLocation.getCountyId()
                    );
                } else {
                    mPrayerTimesList = api.getPrayerTimesForCity(
                            mLocation.getCountryId(),
                            mLocation.getCityId()
                    );
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
                dbMan.addPrayerTimes(mPrayerTimesList, mLocation.getDatabaseId());

                new LoadPrayerTimesForTodayTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                // TODO: indicate failure
            }

            if (mFlipper != null)
                mFlipper.setDisplayedChild(FLIPPER_CONTENT);
        }
    }
}
