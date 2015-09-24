package tr.xip.prayertimes.api.objects;

import android.graphics.drawable.Drawable;

import tr.xip.prayertimes.R;
import tr.xip.prayertimes.Utils;

import static tr.xip.prayertimes.ui.app.NamazVakitleriApplication.getContext;

public class PrayerTime {
    private long time;
    private Type type;

    public PrayerTime(Type type, long time) {
        this.time = time;
        this.type = type;
    }

    public PrayerTime(Type type, String date, String time) {
        this.time = Utils.getTimestampFromDateTime(date, time);
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public String getTitle() {
        switch (type) {
            case FAJR:
                return getContext().getString(R.string.prayer_time_fajr);
            case SUNRISE:
                return getContext().getString(R.string.prayer_time_sunrise);
            case DHUHR:
                return getContext().getString(R.string.prayer_time_dhuhr);
            case ASR:
                return getContext().getString(R.string.prayer_time_asr);
            case MAGHRIB:
                return getContext().getString(R.string.prayer_time_maghrib);
            case ISHA:
                return getContext().getString(R.string.prayer_time_isha);
            default:
                return "?";
        }
    }

    public Drawable getSmallImage() {
        switch (type) {
            case FAJR:
                return getContext().getResources().getDrawable(R.drawable.prayer_time_fajr_small);
            case SUNRISE:
                return getContext().getResources().getDrawable(R.drawable.prayer_time_sunrise_small);
            case DHUHR:
                return getContext().getResources().getDrawable(R.drawable.prayer_time_dhuhr_small);
            case ASR:
                return getContext().getResources().getDrawable(R.drawable.prayer_time_asr_small);
            case MAGHRIB:
                return getContext().getResources().getDrawable(R.drawable.prayer_time_maghrib_small);
            case ISHA:
                return getContext().getResources().getDrawable(R.drawable.prayer_time_isha_small);
            default:
                return null;
        }
    }

    public Drawable getLargeImage() {
        switch (type) {
            case FAJR:
                return getContext().getResources().getDrawable(R.drawable.prayer_time_fajr_large);
            case SUNRISE:
                return getContext().getResources().getDrawable(R.drawable.prayer_time_sunrise_large);
            case DHUHR:
                return getContext().getResources().getDrawable(R.drawable.prayer_time_dhuhr_large);
            case ASR:
                return getContext().getResources().getDrawable(R.drawable.prayer_time_asr_large);
            case MAGHRIB:
                return getContext().getResources().getDrawable(R.drawable.prayer_time_maghrib_large);
            case ISHA:
                return getContext().getResources().getDrawable(R.drawable.prayer_time_isha_large);
            default:
                return null;
        }
    }

    public enum Type {
        FAJR, SUNRISE, DHUHR, ASR, MAGHRIB, ISHA
    }
}
