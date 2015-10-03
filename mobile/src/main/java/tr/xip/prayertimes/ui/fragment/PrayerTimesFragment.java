package tr.xip.prayertimes.ui.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import tr.xip.prayertimes.R;
import tr.xip.prayertimes.ui.apdater.PrayerTimesAdapter;
import tr.xip.prayertimes.api.DiyanetApi;
import tr.xip.prayertimes.api.objects.Location;
import tr.xip.prayertimes.api.objects.PrayerTimes;
import tr.xip.prayertimes.db.DatabaseManager;
import tr.xip.prayertimes.util.RemainingTimeCounter;

public class PrayerTimesFragment extends Fragment {
    public static final String ARG_LOCATION = "arg_location";

    private static final String STATE_PRAYER_TIMES = "state_prayer_times";

    private static final int FLIPPER_PROGRESS_BAR = 1;
    private static final int FLIPPER_CONTENT = 0;

    private Context context;

    private View rootView;

    private Location mLocation;

    private PrayerTimes mPrayerTimes;

    private ViewFlipper mFlipper;

    private RecyclerView mRecyclerView;
    private PrayerTimesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static PrayerTimesFragment newInstance(Location location) {
        PrayerTimesFragment fragment = new PrayerTimesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCATION, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();

        if (savedInstanceState != null) {
            mPrayerTimes = (PrayerTimes) savedInstanceState.get(STATE_PRAYER_TIMES);
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            mLocation = (Location) bundle.getSerializable(ARG_LOCATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_prayer_times, null);

        mFlipper = (ViewFlipper) rootView.findViewById(R.id.prayer_times_flipper);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.prayer_times_recycler);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (mPrayerTimes != null) {
            displayPrayerTimes();
        } else {
            new LoadPrayerTimesForTodayTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        RemainingTimeCounter.cancelIfInstanceExists();
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
            mAdapter = new PrayerTimesAdapter(mPrayerTimes.getPrayerTimesList());
            if (mRecyclerView != null) {
                mRecyclerView.setAdapter(mAdapter);
                // TODO: Implementation
                // mRecyclerView.smoothScrollToPosition(mAdapter.getCurrentPrayerTimePosition());
            }
        }
    }

    private class LoadPrayerTimesForTodayTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mFlipper != null) {
                mFlipper.setDisplayedChild(FLIPPER_PROGRESS_BAR);
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            long now = cal.getTimeInMillis();

            mPrayerTimes = DatabaseManager.getPrayerTimes(mLocation.getDatabaseId(), now);

            return mPrayerTimes != null;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            if (success) {
                displayPrayerTimes();
            } else {
                if (mFlipper != null) {
                    mFlipper.setDisplayedChild(FLIPPER_PROGRESS_BAR);
                }

                if (locationHasCountyValue()) {
                    DiyanetApi.getPrayerTimesForCounty(
                            mLocation.getCountryId(),
                            mLocation.getCityId(),
                            mLocation.getCountyId()
                    ).enqueue(new PrayerTimesListCallback());
                } else {
                    DiyanetApi.getPrayerTimesForCity(
                            mLocation.getCountryId(),
                            mLocation.getCityId()
                    ).enqueue(new PrayerTimesListCallback());
                }
            }

            if (mFlipper != null) {
                mFlipper.setDisplayedChild(FLIPPER_CONTENT);
            }
        }

        private class PrayerTimesListCallback implements Callback<List<PrayerTimes>> {

            @Override
            public void onResponse(Response<List<PrayerTimes>> response) {
                List<PrayerTimes> mPrayerTimesList = response.body();

                if (mPrayerTimesList != null) {
                    DatabaseManager.addPrayerTimes(mPrayerTimesList, mLocation.getDatabaseId());

                    new LoadPrayerTimesForTodayTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    fail(response);
                }

                if (mFlipper != null) {
                    mFlipper.setDisplayedChild(FLIPPER_CONTENT);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                fail(null);
                end();
            }

            private void fail(Response response) {
                // TODO
            }

            private void end() {
                if (mFlipper != null) {
                    mFlipper.setDisplayedChild(FLIPPER_CONTENT);
                }
            }
        }
    }
}
