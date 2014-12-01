package tr.xip.prayertimes.apdater;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tr.xip.prayertimes.R;
import tr.xip.prayertimes.widget.RelativeTimeTextView;

/**
 * Created by ix on 12/1/14.
 */
public class PrayerTimesAdapter extends RecyclerView.Adapter<PrayerTimesAdapter.ViewHolder> {

    Context context;

    ArrayList<Long> mDataset = new ArrayList<>();

    Map<Integer, Integer> mTitles = new HashMap<>();

    public PrayerTimesAdapter(Context context, ArrayList<Long> dataset) {
        this.context = context;
        mDataset = dataset;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        long timestamp = mDataset.get(position);

        Date prayerTime = new Date(timestamp);
        Date now = new Date(System.currentTimeMillis());

        holder.mName.setText(mTitles.get(position));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String timeString = sdf.format(timestamp);

        if (now.after(prayerTime))
            holder.mTime.setText(timeString);
        else {
            holder.mTime.setPrefix(timeString + " ‒ ");
            holder.mTime.setReferenceTime(timestamp);
        }

        Resources res = context.getResources();
        if (position == mDataset.size() - 1) {
            if (now.after(prayerTime))
                holder.mName.setTextColor(res.getColor(R.color.apptheme_primary));
        } else {
            Date nextPrayerTime = new Date(mDataset.get(position + 1));

            if (now.after(prayerTime) && now.before(nextPrayerTime))
                holder.mName.setTextColor(res.getColor(R.color.apptheme_primary));
        }

        holder.mNotificationToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public FrameLayout mNotificationToggle;
        public ImageView mNotificationIndicator;
        public TextView mName;
        public RelativeTimeTextView mTime;

        public ViewHolder(View v) {
            super(v);
            mNotificationToggle = (FrameLayout) v.findViewById(R.id.item_prayer_notifications_toggle);
            mNotificationIndicator = (ImageView) v.findViewById(R.id.item_prayer_notifications_indicator);
            mName = (TextView) v.findViewById(R.id.item_prayer_name);
            mTime = (RelativeTimeTextView) v.findViewById(R.id.item_prayer_time);
        }
    }
}
