package tr.xip.prayertimes.ui.fragment

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_prayer_times.view.*

import java.util.Calendar

import retrofit.Callback
import retrofit.Response
import tr.xip.prayertimes.R
import tr.xip.prayertimes.ui.apdater.PrayerTimesAdapter
import tr.xip.prayertimes.client.DiyanetClient
import tr.xip.prayertimes.model.Location
import tr.xip.prayertimes.model.PrayerTimes
import tr.xip.prayertimes.db.DatabaseManager
import tr.xip.prayertimes.util.RemainingTimeCounter

class PrayerTimesFragment : Fragment() {
    private var adapter: PrayerTimesAdapter? = null

    private var prayerTimes: PrayerTimes? = null
    private var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prayerTimes = savedInstanceState?.get(STATE_PRAYER_TIMES) as PrayerTimes?
        location = arguments?.getSerializable(ARG_LOCATION) as Location?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_prayer_times, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.recycler.layoutManager = LinearLayoutManager(context)

        if (prayerTimes != null) {
            displayPrayerTimes()
        } else {
            LoadPrayerTimesForTodayTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        RemainingTimeCounter.cancelIfInstanceExists()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(STATE_PRAYER_TIMES, prayerTimes)
    }

    private fun displayPrayerTimes() {
        if (prayerTimes != null) {
            adapter = PrayerTimesAdapter(prayerTimes!!.prayerTimesList)
            view.recycler.adapter = adapter
            // TODO: Implementation
            // recycler.smoothScrollToPosition(adapter.currentPrayerTimePosition)
        }
    }

    private inner class LoadPrayerTimesForTodayTask : AsyncTask<Void, Void, Boolean>() {

        override fun onPreExecute() {
            super.onPreExecute()
            view.flipper.displayedChild = FLIPPER_PROGRESS_BAR
        }

        override fun doInBackground(vararg params: Void): Boolean? {
            // TODO: Extension method
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)
            val now = cal.timeInMillis

            if (location != null) {
                prayerTimes = DatabaseManager.getPrayerTimes(location!!.databaseId, now)
            }

            return prayerTimes != null
        }

        override fun onPostExecute(success: Boolean) {
            super.onPostExecute(success)

            if (success) {
                displayPrayerTimes()
            } else {
                view.flipper.displayedChild = FLIPPER_PROGRESS_BAR

                DiyanetClient.getPrayerTimesForMonth(
                        location!!.countryId!!,
                        location!!.cityId!!,
                        location!!.countyId
                ).enqueue(PrayerTimesListCallback())
            }

            view.flipper.displayedChild = FLIPPER_CONTENT
        }

        private inner class PrayerTimesListCallback : Callback<List<PrayerTimes>> {

            override fun onResponse(response: Response<List<PrayerTimes>>) {
                val prayerTimesList = response.body()

                if (prayerTimesList != null) {
                    DatabaseManager.addPrayerTimes(prayerTimesList, location!!.databaseId)
                    LoadPrayerTimesForTodayTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                } else {
                    fail(response)
                }

                view.flipper.displayedChild = FLIPPER_CONTENT
            }

            override fun onFailure(t: Throwable) {
                fail(null)
                end()
            }

            private fun fail(response: Response<List<PrayerTimes>>?) {
                // TODO
            }

            private fun end() {
                view.flipper.displayedChild = FLIPPER_CONTENT
            }
        }
    }

    companion object {
        val ARG_LOCATION = "arg_location"

        private val STATE_PRAYER_TIMES = "state_prayer_times"

        private val FLIPPER_PROGRESS_BAR = 1
        private val FLIPPER_CONTENT = 0

        fun newInstance(location: Location): PrayerTimesFragment {
            val fragment = PrayerTimesFragment()
            val args = Bundle()
            args.putSerializable(ARG_LOCATION, location)
            fragment.arguments = args
            return fragment
        }
    }
}
