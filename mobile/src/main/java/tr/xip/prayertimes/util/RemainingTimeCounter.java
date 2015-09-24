package tr.xip.prayertimes.util;

import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import tr.xip.prayertimes.ui.apdater.PrayerTimesAdapter;

public class RemainingTimeCounter extends CountDownTimer {
    private static RemainingTimeCounter sInstance;

    private PrayerTimesAdapter mAdapter;
    private TextView mTextView;

    public static RemainingTimeCounter newInstance(PrayerTimesAdapter adapter, TextView textView, long endTime) {
        cancelIfInstanceExists();
        sInstance = new RemainingTimeCounter(adapter, textView, endTime);
        return sInstance;
    }

    public static RemainingTimeCounter getInstance() {
        return sInstance;
    }

    private RemainingTimeCounter(PrayerTimesAdapter adapter, TextView textView, long endTime) {
        super(endTime - System.currentTimeMillis(), 1000);
        mAdapter = adapter;
        mTextView = textView;
    }

    public static void cancelIfInstanceExists() {
        if (sInstance != null) {
            sInstance.cancel();
        }
    }

    @Override
    public void onTick(long millis/*UntilFinished*/) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.HOURS.toSeconds(hours)
                - TimeUnit.MINUTES.toSeconds(minutes);
        mTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    @Override
    public void onFinish() {
        mTextView.setText("00:00:00");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }
}