package tr.xip.prayertimes.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner

import kotlinx.android.synthetic.activity_main.*

import tr.xip.prayertimes.R
import tr.xip.prayertimes.Utils
import tr.xip.prayertimes.api.objects.Location
import tr.xip.prayertimes.db.DatabaseManager
import tr.xip.prayertimes.ui.activty.CountryChooserActivity
import tr.xip.prayertimes.ui.apdater.LocationsSpinnerAdapter
import tr.xip.prayertimes.ui.fragment.PrayerTimesFragment
import tr.xip.prayertimes.ui.widget.OnItemSelectedListenerAdapter
import tr.xip.prayertimes.util.LocationsList

class MainActivity : AppCompatActivity() {
    var sharedPrefs: SharedPreferences? = null

    var locations = LocationsList()

    var locationsSpinner: Spinner? = null
    var adapter: LocationsSpinnerAdapter? = null

    var removeItem: MenuItem? = null

    var fragment: PrayerTimesFragment? = null

    var currentLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        toolbar.setContentInsetsAbsolute(Utils.dpToPx(8f), toolbar.contentInsetRight)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        locations = DatabaseManager.getLocations()

        if (locations.size > 0) {
            setUpLocationsSpinner()
            setContent(locations.findById(sharedPrefs!!.getInt(PREF_LAST_LOCATION, -1)))
        }

        notifyChange()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        removeItem = menu.findItem(R.id.action_remove_location)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_new_location -> {
                startActivity(Intent(this, CountryChooserActivity::class.java))
                finish()
            }
            R.id.action_remove_location -> {
                DatabaseManager.removeLocation(currentLocation!!.databaseId)
                adapter!!.remove(currentLocation)
                /*if (mCurrentItemPosition > 0) {
                    mLocationsSpinner.setSelection(mCurrentItemPosition - 1);
                }*/
                notifyChange()
            }
            R.id.action_settings -> {
                //...
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setContent(location: Location) {
        supportFragmentManager.beginTransaction().replace(R.id.container, PrayerTimesFragment.newInstance(location)).commit()
    }

    private fun notifyChange() {
        flipper.displayedChild = if (locations.size > 0) FLIPPER_CONTENT else FLIPPER_NO_LOCATIONS
    }

    private fun setUpLocationsSpinner() {
        adapter = LocationsSpinnerAdapter(this,
                R.layout.item_spinner_toolbar_selected,
                locations)
        adapter?.setDropDownViewResource(R.layout.item_spinner_toolbar)

        locationsSpinner = LayoutInflater.from(this).inflate(R.layout.view_toolbar_spinner,
                toolbar,
                false) as Spinner
        val params = ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        toolbar.addView(locationsSpinner, params)

        locationsSpinner?.adapter = adapter

        locationsSpinner?.onItemSelectedListener = OnLocationSelectedListener()
    }

    private inner class OnLocationSelectedListener : OnItemSelectedListenerAdapter() {

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            currentLocation = adapter?.getItem(position)
            setContent(currentLocation!!)

            sharedPrefs?.edit()?.putInt(PREF_LAST_LOCATION, currentLocation!!.databaseId)?.commit()

            notifyChange()
        }
    }

    companion object {
        private val PREF_LAST_LOCATION = "pref_last_location"
        private val FLIPPER_NO_LOCATIONS = 1
        private val FLIPPER_CONTENT = 0
    }
}
