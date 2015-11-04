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
import retrofit.Retrofit;
import tr.xip.prayertimes.R;
import tr.xip.prayertimes.ui.apdater.CountiesAdapter;
import tr.xip.prayertimes.api.DiyanetApi;
import tr.xip.prayertimes.api.objects.City;
import tr.xip.prayertimes.api.objects.Country;
import tr.xip.prayertimes.api.objects.County;
import tr.xip.prayertimes.api.objects.Location;
import tr.xip.prayertimes.db.DatabaseManager;

public class CountyChooserActivity extends ActionBarActivity implements Callback<List<County>> {
    public static final String ARG_COUNTRY = "arg_country";
    public static final String ARG_CITY = "arg_city";

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

        Intent intent = getIntent();
        mCountry = (Country) intent.getSerializableExtra(ARG_COUNTRY);
        mCity = (City) intent.getSerializableExtra(ARG_CITY);

        TextView mChooserTitle = (TextView) findViewById(R.id.chooser_title);
        mChooserTitle.setText(getString(R.string.choose_your_county));

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
                if (mCountiesAdapter != null)
                    saveValuesAndExit(mCountiesAdapter.getSelectedCounty());
            }
        });

        mProgressBar = (SmoothProgressBar) findViewById(R.id.chooser_progress_bar);

        if (mCity != null && mCity.getId() != null) {
            DiyanetApi.getCountiesForCity(mCity.getId()).enqueue(this);
        }
    }

    private void saveValuesAndExit(County county) {
        DatabaseManager.addLocation(new Location(
                mCountry.getId(),
                mCountry.getName(),
                mCity.getId(),
                mCity.getName(),
                county != null ? county.getId() : null,
                county != null ? county.getName() : null
        ));
        startActivity(new Intent(CountyChooserActivity.this, MainActivity.class));
        finish();
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
        Intent intent = new Intent(CountyChooserActivity.this, CityChooserActivity.class);
        intent.putExtra(CityChooserActivity.ARG_COUNTRY, mCountry);
        startActivity(intent);
        finish();
    }

    @Override
    public void onResponse(Response<List<County>> response, Retrofit retrofit) {
        mCountiesList = response.body();

        boolean countiesExist = false;

        if (mCountiesList != null) {
            countiesExist = !(mCountiesList.size() <= 1 && mCountiesList.get(0).getError() != null);
        }

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

        end();
    }

    @Override
    public void onFailure(Throwable t) {
        // TODO
        end();
    }

    private void end() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
