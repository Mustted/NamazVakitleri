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
import tr.xip.prayertimes.apdater.CitiesAdapter;
import tr.xip.prayertimes.api.DiyanetApi;
import tr.xip.prayertimes.api.objects.City;
import tr.xip.prayertimes.api.objects.Country;
import tr.xip.prayertimes.db.DatabaseManager;

/**
 * Created by ix on 11/30/14.
 */
public class CityChooserActivity extends ActionBarActivity {
    public static final String ARG_COUNTRY = "arg_country";

    private DiyanetApi api;
    private DatabaseManager dbMan;

    private List<City> mCitiesList = new ArrayList<>();

    private ListView mCitiesListView;
    private CitiesAdapter mCitiesAdapter;

    private SmoothProgressBar mProgressBar;

    private Country mCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.nothing);
        setContentView(R.layout.activity_list_chooser);
        api = new DiyanetApi();
        dbMan = new DatabaseManager(this);

        mCountry = (Country) getIntent().getSerializableExtra(ARG_COUNTRY);

        TextView mChooserTitle = (TextView) findViewById(R.id.chooser_title);
        mChooserTitle.setText(getString(R.string.choose_your_city));

        LinearLayout mPreviousButton = (LinearLayout) findViewById(R.id.chooser_previous);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        LinearLayout mNextButton = (LinearLayout) findViewById(R.id.chooser_next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCitiesAdapter != null) {
                    City city = mCitiesAdapter.getSelectedCity();
                    Intent intent = new Intent(CityChooserActivity.this, CountyChooserActivity.class);
                    intent.putExtra(CountyChooserActivity.ARG_COUNTRY, mCountry);
                    intent.putExtra(CountyChooserActivity.ARG_CITY, city);
                    startActivity(intent);
                    finish();
                }
            }
        });

        mProgressBar = (SmoothProgressBar) findViewById(R.id.chooser_progress_bar);

        new FetchCitiesTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nothing, R.anim.fade_out);
    }

    private void goBack() {
        startActivity(new Intent(CityChooserActivity.this, CountryChooserActivity.class));
        finish();
    }

    private class FetchCitiesTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mProgressBar != null)
                mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (mCountry != null && mCountry.getId() != null) {
                try {
                    mCitiesList = api.getCitiesForCountry(mCountry.getId());
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

            if (success) {
                mCitiesAdapter = new CitiesAdapter(CityChooserActivity.this,
                        R.layout.item_radio, mCitiesList);

                mCitiesListView = (ListView) findViewById(R.id.chooser_list);
                mCitiesListView.setAdapter(mCitiesAdapter);
                mCitiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mCitiesAdapter.selectItem(position);
                    }
                });
            } else {
                // TODO: handle failure
            }

            if (mProgressBar != null)
                mProgressBar.setVisibility(View.GONE);
        }
    }
}
