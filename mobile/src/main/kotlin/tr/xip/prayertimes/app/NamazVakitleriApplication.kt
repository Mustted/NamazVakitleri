package tr.xip.prayertimes.app

import android.app.Application
import android.content.Context

import tr.xip.prayertimes.client.DiyanetClient
import tr.xip.prayertimes.db.DatabaseManager

class NamazVakitleriApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        DiyanetClient.init()
        DatabaseManager.init()
    }

    companion object {
        var context: Context? = null
            private set
    }
}