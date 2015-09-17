package tr.xip.prayertimes.ui.app;

import android.app.Application;
import android.content.Context;

import tr.xip.prayertimes.api.DiyanetApi;
import tr.xip.prayertimes.db.DatabaseManager;

public class NamazVakitleriApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        DiyanetApi.init();
        DatabaseManager.init();
    }

    public static Context getContext() {
        return sContext;
    }
}
