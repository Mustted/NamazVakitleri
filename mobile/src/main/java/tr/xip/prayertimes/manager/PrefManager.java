package tr.xip.prayertimes.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ix on 11/30/14.
 */
public class PrefManager {
    private static final String PREF_SETUP_COMPLETED = "setup_completed";

    private Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        this.context = context;
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = prefs.edit();
    }

    public void setSetupCompleted(boolean value) {
        editor.putBoolean(PREF_SETUP_COMPLETED, value).commit();
    }

    public boolean isSetupCompleted() {
        return prefs.getBoolean(PREF_SETUP_COMPLETED, false);
    }
}
