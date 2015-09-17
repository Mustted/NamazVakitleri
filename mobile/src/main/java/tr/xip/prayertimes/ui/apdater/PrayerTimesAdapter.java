package tr.xip.prayertimes.ui.apdater;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tr.xip.prayertimes.R;
import tr.xip.prayertimes.api.objects.Location;
import tr.xip.prayertimes.db.DatabaseManager;
import tr.xip.prayertimes.ui.widget.NotificationsToggle;
import tr.xip.prayertimes.ui.widget.RelativeTimeTextView;

public class PrayerTimesAdapter extends RecyclerView.Adapter<PrayerTimesAdapter.ViewHolder> {
    private Context mContext;

    private ArrayList<Long> mDataset = new ArrayList<>();

    private Map<Integer, Integer> mTitles = new HashMap<>();

    private Location mLocation;

    public PrayerTimesAdapter(Context context, ArrayList<Long> dataset, Location location) {
        this.mContext = context;
        this.mDataset = dataset;
        this.mLocation = location;

        mTitles.put(0, R.string.prayer_time_fajr);
        mTitles.put(1, R.string.prayer_time_sunrise);
        mTitles.put(2, R.string.prayer_time_dhuhr);
        mTitles.put(3, R.string.prayer_time_asr);
        mTitles.put(4, R.string.prayer_time_maghrib);
        mTitles.put(5, R.string.prayer_time_isha);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_prayer, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        long timestamp = mDataset.get(position);

        Date prayerTime = new Date(timestamp);
        Date now = new Date(System.currentTimeMillis());

        holder.mName.setText(mTitles.get(position));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String timeString = sdf.format(timestamp);

        if (now.after(prayerTime)) {
            holder.mTime.setText(timeString);
        } else {
            holder.mTime.setPrefix(timeString + " ‒ ");
            holder.mTime.setReferenceTime(timestamp);
        }

        Resources res = mContext.getResources();
        if (position == mDataset.size() - 1) {
            if (now.after(prayerTime)) {
                holder.mName.setTextColor(res.getColor(R.color.apptheme_primary));
            }
        } else {
            Date nextPrayerTime = new Date(mDataset.get(position + 1));

            if (now.after(prayerTime) && now.before(nextPrayerTime)) {
                holder.mName.setTextColor(res.getColor(R.color.apptheme_primary));
            }
        }

        loadNotificationsState(holder, position);

        holder.mNotificationsToggle.setOnNotificationStateChangedListener(
                new NotificationsToggle.OnNotificationStateChangedListener() {
                    @Override
                    public void onNotificationsStateChanged(int notificationsState) {
                        saveNotificationsState(notificationsState, position);
                    }
                });

    }

    private void loadNotificationsState(ViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.mNotificationsToggle.setNotificationsState(mLocation.getFajrNotification());
                break;
            case 1:
                holder.mNotificationsToggle.setNotificationsState(mLocation.getSunriseNotiication());
                break;
            case 2:
                holder.mNotificationsToggle.setNotificationsState(mLocation.getDhuhrNotification());
                break;
            case 3:
                holder.mNotificationsToggle.setNotificationsState(mLocation.getAsrNotification());
                break;
            case 4:
                holder.mNotificationsToggle.setNotificationsState(mLocation.getMaghribNotification());
                break;
            case 5:
                holder.mNotificationsToggle.setNotificationsState(mLocation.getIshaNotification());
                break;
        }
    }

    private void saveNotificationsState(int notificationsState, int position) {
        switch (position) {
            case 0:
                mLocation.setFajrNotification(notificationsState);
                break;
            case 1:
                mLocation.setSunriseNotiication(notificationsState);
                break;
            case 2:
                mLocation.setDhuhrNotification(notificationsState);
                break;
            case 3:
                mLocation.setAsrNotification(notificationsState);
                break;
            case 4:
                mLocation.setMaghribNotification(notificationsState);
                break;
            case 5:
                mLocation.setIshaNotification(notificationsState);
                break;
        }

        DatabaseManager.updateLocation(mLocation);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public NotificationsToggle mNotificationsToggle;
        public TextView mName;
        public RelativeTimeTextView mTime;

        public ViewHolder(View v) {
            super(v);
            mNotificationsToggle = (NotificationsToggle) v.findViewById(R.id.item_prayer_notifications_toggle);
            mName = (TextView) v.findViewById(R.id.item_prayer_name);
            mTime = (RelativeTimeTextView) v.findViewById(R.id.item_prayer_time);
        }
    }
}
