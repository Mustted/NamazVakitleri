package tr.xip.prayertimes.api.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ix on 11/30/14.
 */
public class PrayerTimes implements Serializable {

    @SerializedName("Tarih")
    private String date;

    @SerializedName("Imsak")
    private String fajr;

    @SerializedName("Gunes")
    private String sunrise;

    @SerializedName("Ogle")
    private String dhuhr;

    @SerializedName("Ikindi")
    private String asr;

    @SerializedName("Aksam")
    private String maghrib;

    @SerializedName("Yatsi")
    private String isha;

    @SerializedName("Kible")
    private String qibla;

    private long dateTimestamp;
    private long fajrTimestamp;
    private long sunriseTimestamp;
    private long dhuhrTimestamp;
    private long asrTimestamp;
    private long maghribTimestamp;
    private long ishaTimestamp;

    public PrayerTimes(long date, long fajr, long sunrise, long dhuhr, long asr, long maghrib, long isha) {
        this.dateTimestamp = date;
        this.fajrTimestamp = fajr;
        this.sunriseTimestamp = sunrise;
        this.dhuhrTimestamp = dhuhr;
        this.asrTimestamp = asr;
        this.maghribTimestamp = maghrib;
        this.ishaTimestamp = isha;
    }

    public long getDate() {
        return dateTimestamp != 0 ? dateTimestamp : getTimestampFromDate(date);
    }

    public long getFajr() {
        return fajrTimestamp != 0 ? fajrTimestamp : getTimestampFromDateTime(date, fajr);
    }

    public long getSunrise() {
        return sunriseTimestamp != 0 ? sunriseTimestamp : getTimestampFromDateTime(date, sunrise);
    }

    public long getDhuhr() {
        return dhuhrTimestamp != 0 ? dhuhrTimestamp : getTimestampFromDateTime(date, dhuhr);
    }

    public long getAsr() {
        return asrTimestamp != 0 ? asrTimestamp : getTimestampFromDateTime(date, asr);
    }

    public long getMaghrib() {
        return maghribTimestamp != 0 ? maghribTimestamp : getTimestampFromDateTime(date, maghrib);
    }

    public long getIsha() {
        return ishaTimestamp != 0 ? ishaTimestamp : getTimestampFromDateTime(date, isha);
    }

    public ArrayList<Long> getPrayerTimesArrayList() {
        ArrayList<Long> list = new ArrayList<>();
        list.add(getFajr());
        list.add(getSunrise());
        list.add(getDhuhr());
        list.add(getAsr());
        list.add(getMaghrib());
        list.add(getIsha());
        return list;
    }

    public String getQibla() {
        return qibla;
        // TODO: figure out what to do with this
    }

    private long getTimestampFromDateTime(String date, String time) {
        String[] dateParts = date.split("\\.");
        String[] timeParts = time.split(":");

        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTimeInMillis();
    }

    private long getTimestampFromDate(String date) {
        String[] dateParts = date.split("\\.");

        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTimeInMillis();
    }
}
