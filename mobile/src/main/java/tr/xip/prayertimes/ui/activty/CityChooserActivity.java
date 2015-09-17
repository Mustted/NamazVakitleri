package tr.xip.prayertimes.ui.activty;

import android.content.Intent;
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
import retrofit.Callback;
import retrofit.Response;
import tr.xip.prayertimes.R;
import tr.xip.prayertimes.ui.apdater.CitiesAdapter;
import tr.xip.prayertimes.api.DiyanetApi;
import tr.xip.prayertimes.api.objects.City;
import tr.xip.prayertimes.api.objects.Country;
import tr.xip.prayertimes.db.DatabaseManager;

/**
 * Created by ix on 11/30/14.
 */
public class CityChooserActivity extends ActionBarActivity implements Callback<List<City>> {
    public static final String ARG_COUNTRY = "arg_country";

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

        if (mCountry != null && mCountry.getId() != null) {
            DiyanetApi.getCitiesForCountry(mCountry.getId()).enqueue(this);
        }
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

    @Override
    public void onResponse(Response<List<City>> response) {
        if ((mCitiesList = response.body()) != null) {
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
                fail(null);
            }

        end();
        }

    @Override
    public void onFailure(Throwable t) {
        fail(null);
    }

    private void fail(Response response) {
        // TODO
        end();
    }

    private void end() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
