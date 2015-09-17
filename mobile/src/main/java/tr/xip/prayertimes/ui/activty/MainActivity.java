package tr.xip.prayertimes.ui.activty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import tr.xip.prayertimes.R;
import tr.xip.prayertimes.ui.apdater.LocationTabsPagerAdapter;
import tr.xip.prayertimes.api.objects.Location;
import tr.xip.prayertimes.db.DatabaseManager;

public class MainActivity extends ActionBarActivity {
    private static final String PREF_LAST_LOCATION = "pref_last_location";

    private static final int FLIPPER_NO_LOCATIONS = 1;
    private static final int FLIPPER_CONTENT = 0;

    private Toolbar mToolbar;

    private DatabaseManager dbMan;
    private SharedPreferences mSharedPrefs;

    private List<Location> mLocations = new ArrayList<>();

    private ViewPager mViewPager;
    private LocationTabsPagerAdapter mAdapter;
    private PagerSlidingTabStrip mTabs;

    private ViewFlipper mFlipper;

    private MenuItem mRemoveItem;

    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbMan = new DatabaseManager(this);
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mFlipper = (ViewFlipper) findViewById(R.id.activity_main_flipper);

        mLocations = dbMan.getLocations();

        if (mLocations.size() > 0) {
            mAdapter = new LocationTabsPagerAdapter(
                    getSupportFragmentManager(), mLocations);
            mViewPager = (ViewPager) findViewById(R.id.activity_main_view_pager);
            mViewPager.setAdapter(mAdapter);

            mTabs = (PagerSlidingTabStrip) findViewById(R.id.activity_main_pager_tabs);
            mTabs.setViewPager(mViewPager);
            mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                    mSharedPrefs.edit().putInt(PREF_LAST_LOCATION, position).commit();
                }

                @Override
                public void onPageScrollStateChanged(int position) {

                }
            });

            mViewPager.setCurrentItem(mSharedPrefs.getInt(PREF_LAST_LOCATION, 0));
        } else
            mFlipper.setDisplayedChild(FLIPPER_NO_LOCATIONS);
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
                int itemToRemovePos = currentPage;
                if (currentPage > 0)
                    mViewPager.setCurrentItem(currentPage - 1);
                dbMan.removeLocation(mAdapter.getDatabaseIdByPosition(itemToRemovePos));
                mAdapter.removeLocation(itemToRemovePos);
                onTabsChanged();
                break;
            case R.id.action_settings:
                // ...
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onTabsChanged() {
        mTabs.notifyDataSetChanged();

        if (mAdapter.getCount() == 0)
            mFlipper.setDisplayedChild(FLIPPER_NO_LOCATIONS);

        if (mRemoveItem != null)
            if (mLocations.size() == 0)
                mRemoveItem.setVisible(false);
            else
                mRemoveItem.setVisible(true);
    }
}
