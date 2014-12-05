package tr.xip.prayertimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tr.xip.prayertimes.apdater.CountriesAdapter;
import tr.xip.prayertimes.api.DiyanetApi;
import tr.xip.prayertimes.api.objects.Country;

/**
 * Created by ix on 11/30/14.
 */
public class CountryChooserActivity extends ActionBarActivity {
    private DiyanetApi api;

    private List<Country> mCountriesList = new ArrayList<>();

    private ListView mCountriesListView;
    private CountriesAdapter mCountriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.nothing);
        setContentView(R.layout.activity_list_chooser);
        api = new DiyanetApi();

        TextView mChooserTitle = (TextView) findViewById(R.id.chooser_title);
        mChooserTitle.setText(getString(R.string.choose_your_country));

        mCountriesList = api.getCountriesList();

        mCountriesAdapter = new CountriesAdapter(this,
                R.layout.item_radio, mCountriesList);

        mCountriesListView = (ListView) findViewById(R.id.chooser_list);
        mCountriesListView.setAdapter(mCountriesAdapter);
        mCountriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCountriesAdapter.selectItem(position);
            }
        });

        ((ImageView) findViewById(R.id.chooser_previous_icon))
                .setImageResource(R.drawable.ic_close_grey600_18dp);
        ((TextView) findViewById(R.id.chooser_previous_text)).setText(R.string.cancel);

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
                Country country = mCountriesAdapter.getSelectedCountry();
                Intent intent = new Intent(CountryChooserActivity.this, CityChooserActivity.class);
                intent.putExtra(CityChooserActivity.ARG_COUNTRY, country);
                startActivity(intent);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nothing, R.anim.fade_out);
        startActivity(new Intent(this, MainActivity.class));
    }
}
