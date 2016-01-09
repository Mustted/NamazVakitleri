package tr.xip.prayertimes.util

import android.os.CountDownTimer
import android.os.Handler
import android.widget.TextView

import java.util.concurrent.TimeUnit

import tr.xip.prayertimes.ui.apdater.PrayerTimesAdapter
import kotlin.text.format

class RemainingTimeCounter private constructor(val adapter: PrayerTimesAdapter, val textView: TextView, endTime: Long) : CountDownTimer(endTime - System.currentTimeMillis(), 1000) {

    override fun onTick(millis/*UntilFinished*/: Long) {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.MINUTES.toSeconds(minutes)
        textView.setText("%02d:%02d:%02d".format(hours, minutes, seconds))
    }

    override fun onFinish() {
        textView.text = "00:00:00"
        Handler().postDelayed({ adapter.notifyDataSetChanged() }, 1000)
    }

    companion object {
        var instance: RemainingTimeCounter? = null
            private set

        fun newInstance(adapter: PrayerTimesAdapter, textView: TextView, endTime: Long): RemainingTimeCounter {
            cancelIfInstanceExists()
            instance = RemainingTimeCounter(adapter, textView, endTime)
            return instance as RemainingTimeCounter
        }

        fun cancelIfInstanceExists() {
            instance?.cancel()
        }
    }
}