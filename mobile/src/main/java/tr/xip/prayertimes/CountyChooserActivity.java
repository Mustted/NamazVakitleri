package tr.xip.prayertimes;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import tr.xip.prayertimes.apdater.CountiesAdapter;
import tr.xip.prayertimes.api.DiyanetApi;
import tr.xip.prayertimes.api.objects.City;
import tr.xip.prayertimes.api.objects.Country;
import tr.xip.prayertimes.api.objects.County;
import tr.xip.prayertimes.api.objects.Location;
import tr.xip.prayertimes.db.DatabaseManager;

/**
 * Created by ix on 11/30/14.
 */
public class CountyChooserActivity extends ActionBarActivity {
    public static final String ARG_COUNTRY = "arg_country";
    public static final String ARG_CITY = "arg_city";

    private DiyanetApi api;
    private DatabaseManager dbMan;

    private List<County> mCountiesList = new ArrayList<>();

    private ListView mCountiesListView;
    private CountiesAdapter mCountiesAdapter;

    private SmoothProgressBar mProgressBar;

    private Country mCountry;
    private City mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.nothing);
        setContentView(R.layout.activity_list_chooser);
        api = new DiyanetApi();
        dbMan = new DatabaseManager(this);

        Intent intent = getIntent();
        mCountry = (Country) intent.getSerializableExtra(ARG_COUNTRY);
        mCity = (City) intent.getSerializableExtra(ARG_CITY);

        TextView mChooserTitle = (TextView) findViewById(R.id.chooser_title);
        mChooserTitle.setText(getString(R.string.choose_your_county));

        LinearLayout mPreviousButton = (LinearLayout) findViewById(R.id.chooser_previous);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout mNextButton = (LinearLayout) findViewById(R.id.chooser_next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCountiesAdapter != null)
                    saveValuesAndExit(mCountiesAdapter.getSelectedCounty());
            }
        });

        mProgressBar = (SmoothProgressBar) findViewById(R.id.chooser_progress_bar);

        new FetchCountiesTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void saveValuesAndExit(County county) {
        dbMan.addLocation(new Location(
                mCountry.getId(),
                mCity.getId(),
                county != null ? county.getId() : null
        ));
        startActivity(new Intent(CountyChooserActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nothing, R.anim.fade_out);
    }

    private class FetchCountiesTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mProgressBar != null)
                mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (mCity != null && mCity.getId() != null) {
                try {
                    mCountiesList = api.getCountiesForCity(mCity.getId());
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            boolean countiesExist = false;

            if (success)
                countiesExist = !(mCountiesList.size() <= 1 && mCountiesList.get(0).getError() != null);

            if (countiesExist) {
                mCountiesAdapter = new CountiesAdapter(CountyChooserActivity.this,
                        R.layout.item_radio, mCountiesList);

                mCountiesListView = (ListView) findViewById(R.id.chooser_list);
                mCountiesListView.setAdapter(mCountiesAdapter);
                mCountiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mCountiesAdapter.selectItem(position);
                    }
                });
            } else {
                saveValuesAndExit(null);
            }

            if (mProgressBar != null)
                mProgressBar.setVisibility(View.GONE);
        }
    }
}
