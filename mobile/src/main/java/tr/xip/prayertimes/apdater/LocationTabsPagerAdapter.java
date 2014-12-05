package tr.xip.prayertimes.apdater;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import tr.xip.prayertimes.PrayerTimesFragment;
import tr.xip.prayertimes.api.objects.Location;

/**
 * Created by ix on 12/4/14.
 */
public class LocationTabsPagerAdapter extends FragmentStatePagerAdapter {

    List<Location> mLocations = new ArrayList<>();

    int selectedPosition;

    public LocationTabsPagerAdapter(FragmentManager fm, List<Location> list) {
        super(fm);
        mLocations = list;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PrayerTimesFragment.ARG_LOCATION, mLocations.get(position));
        PrayerTimesFragment fragment = new PrayerTimesFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mLocations.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Location currentLocation = mLocations.get(position);
        return currentLocation.getCountyName() != null ?
                currentLocation.getCountyName() : currentLocation.getCityName();
    }

    public int getDatabaseIdByPosition(int position) {
        return mLocations.get(position).getDatabaseId();
    }

    public void removeLocation(int position) {
        mLocations.remove(position);
        notifyDataSetChanged();
    }
}
