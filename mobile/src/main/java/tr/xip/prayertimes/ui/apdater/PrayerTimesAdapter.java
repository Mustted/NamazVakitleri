package tr.xip.prayertimes.ui.apdater;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tr.xip.prayertimes.R;
import tr.xip.prayertimes.api.objects.PrayerTime;
import tr.xip.prayertimes.util.RemainingTimeCounter;

public class PrayerTimesAdapter extends RecyclerView.Adapter<PrayerTimesAdapter.ViewHolder> {
    private ArrayList<PrayerTime> mDataset = new ArrayList<>();

    public PrayerTimesAdapter(ArrayList<PrayerTime> dataset) {
        this.mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (Type.fromInt(viewType) == Type.CURRENT) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_prayer_current, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_prayer, parent, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        PrayerTime item = mDataset.get(position);

        Date prayerTime = new Date(item.getTime());
        Date now = new Date(System.currentTimeMillis());

        /* image */
        if (getItemViewType(position) == Type.CURRENT.toInt()) {
            holder.image.setImageDrawable(item.getLargeImage());
        } else {
            holder.image.setImageDrawable(item.getSmallImage());
        }

        /* title */
        holder.title.setText(item.getTitle());

        /* time */
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String timeString = sdf.format(item.getTime());
        holder.time.setText(timeString);

        /* remaining time / countdown */
        if (position == getCurrentPrayerTimePosition() + 1 && now.before(prayerTime)) {
            // One item after the current one and now is before this item. Show countdown here.
            holder.remainingTime.setVisibility(View.VISIBLE);
            RemainingTimeCounter.newInstance(
                    this, holder.remainingTime, prayerTime.getTime()
            ).start();
        } else if (holder.remainingTime != null) {
            holder.remainingTime.setVisibility(View.GONE);
        }
    }

    private boolean prayerTimeIsNow(int position) {
        Date prayerTime = new Date(mDataset.get(position).getTime());
        if (position == (mDataset.size() - 1) && new Date().after(prayerTime)) {
            // This is the last item and there's no need to check for the next prayer time
            return true;
        } else if (position != (mDataset.size() - 1)) {
            Date nextPrayerTime = new Date(mDataset.get(position + 1).getTime());
            return new Date().after(prayerTime) && new Date().before(nextPrayerTime);
        } else return false;
    }

    @Override
    public int getItemViewType(int position) {
        return prayerTimeIsNow(position) ? Type.CURRENT.toInt() : Type.NORMAL.toInt();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public int getCurrentPrayerTimePosition() {
        int position = -1;
        for (int i = 0; i < mDataset.size(); i++) {
            if (prayerTimeIsNow(i)) {
                position = i;
                break;
            }
        }
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView time;
        public TextView remainingTime;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.item_prayer_image);
            title = (TextView) v.findViewById(R.id.item_prayer_title);
            time = (TextView) v.findViewById(R.id.item_prayer_time);
            remainingTime = (TextView) v.findViewById(R.id.item_prayer_remaining_time);
        }
    }

    private enum Type {
        NORMAL(0), CURRENT(1);

        private int value;

        Type(int value) {
            this.value = value;
        }

        public int toInt() {
            return value;
        }

        public static Type fromInt(int type) {
            return type == 1 ? CURRENT : NORMAL;
        }
    }
}
