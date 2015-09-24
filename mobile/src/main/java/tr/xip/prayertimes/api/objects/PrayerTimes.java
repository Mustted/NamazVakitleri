package tr.xip.prayertimes.api.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import tr.xip.prayertimes.Utils;

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
    private PrayerTime fajrPrayerTime;
    private PrayerTime sunrisePrayerTime;
    private PrayerTime dhuhrPrayerTime;
    private PrayerTime asrPrayerTime;
    private PrayerTime maghribPrayerTime;
    private PrayerTime ishaPrayerTime;

    public PrayerTimes(long date, long fajr, long sunrise, long dhuhr, long asr, long maghrib, long isha) {
        this.dateTimestamp = date;
        this.fajrPrayerTime = new PrayerTime(PrayerTime.Type.FAJR, fajr);
        this.sunrisePrayerTime = new PrayerTime(PrayerTime.Type.SUNRISE, sunrise);
        this.dhuhrPrayerTime = new PrayerTime(PrayerTime.Type.DHUHR, dhuhr);
        this.asrPrayerTime = new PrayerTime(PrayerTime.Type.ASR, asr);
        this.maghribPrayerTime = new PrayerTime(PrayerTime.Type.MAGHRIB, maghrib);
        this.ishaPrayerTime = new PrayerTime(PrayerTime.Type.ISHA, isha);
    }

    public long getDate() {
        return dateTimestamp != 0 ? dateTimestamp : Utils.getTimestampFromDate(date);
    }

    public PrayerTime getFajr() {
        return fajrPrayerTime != null ? fajrPrayerTime : new PrayerTime(PrayerTime.Type.FAJR, date, fajr);
    }

    public PrayerTime getSunrise() {
        return sunrisePrayerTime != null ? sunrisePrayerTime : new PrayerTime(PrayerTime.Type.SUNRISE, date, sunrise);
    }

    public PrayerTime getDhuhr() {
        return dhuhrPrayerTime != null ? dhuhrPrayerTime : new PrayerTime(PrayerTime.Type.DHUHR, date, dhuhr);
    }

    public PrayerTime getAsr() {
        return asrPrayerTime != null ? asrPrayerTime : new PrayerTime(PrayerTime.Type.ASR, date, asr);
    }

    public PrayerTime getMaghrib() {
        return maghribPrayerTime != null ? maghribPrayerTime : new PrayerTime(PrayerTime.Type.MAGHRIB, date, maghrib);
    }

    public PrayerTime getIsha() {
        return ishaPrayerTime != null ? ishaPrayerTime : new PrayerTime(PrayerTime.Type.ISHA, date, isha);
    }

    public ArrayList<PrayerTime> getPrayerTimesList() {
        ArrayList<PrayerTime> list = new ArrayList<>();
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
}
