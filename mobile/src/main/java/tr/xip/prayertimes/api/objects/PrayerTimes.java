package tr.xip.prayertimes.api.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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

    public String getDate() {
        return date;
        // TODO: return proper date format
    }

    public String getFajr() {
        return fajr;
        // TODO: return proper format
    }

    public String getSunrise() {
        return sunrise;
        // TODO: return proper format
    }

    public String getDhuhr() {
        return dhuhr;
        // TODO: return proper format
    }

    public String getAsr() {
        return asr;
        // TODO: return proper format
    }

    public String getMaghrib() {
        return maghrib;
        // TODO: return proper format
    }

    public String getIsha() {
        return isha;
        // TODO: return proper format
    }

    public String getQibla() {
        return qibla;
        // TODO: figure out what to do with this
    }
}
