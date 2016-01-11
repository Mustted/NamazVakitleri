package tr.xip.prayertimes.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_list_chooser.*
import retrofit.Callback
import retrofit.Response
import tr.xip.prayertimes.R
import tr.xip.prayertimes.client.DiyanetClient
import tr.xip.prayertimes.model.State
import tr.xip.prayertimes.model.Country
import tr.xip.prayertimes.model.City
import tr.xip.prayertimes.model.Location
import tr.xip.prayertimes.db.DatabaseManager
import tr.xip.prayertimes.ui.adapter.CitiesAdapter
import java.util.*

class CityChooserActivity : AppCompatActivity(), Callback<List<City>> {
    private var citiesAdapter: CitiesAdapter? = null

    private var citiesList: List<City>? = ArrayList()

    private var country: Country? = null
    private var state: State? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.nothing)
        setContentView(R.layout.activity_list_chooser)

        val intent = intent
        country = intent.getSerializableExtra(ARG_COUNTRY) as Country
        state = intent.getSerializableExtra(ARG_CITY) as State

        chooserTitle.text = getString(R.string.choose_your_county)

        previous.setOnClickListener { goBack() }

        next.setOnClickListener {
            if (citiesAdapter != null) {
                saveValuesAndExit(citiesAdapter!!.selectedCity)
            }
        }

        if (state != null && state!!.id != null) {
            DiyanetClient.getCitiesForState(state!!.id!!).enqueue(this)
        }
    }

    private fun saveValuesAndExit(county: City? = null) {
        DatabaseManager.addLocation(Location(
                country?.id,
                country?.name,
                state?.id,
                state?.name,
                county?.id,
                county?.name))
        startActivity(Intent(this@CityChooserActivity, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        goBack()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.nothing, R.anim.fade_out)
    }

    private fun goBack() {
        val intent = Intent(this@CityChooserActivity, StateChooserActivity::class.java)
        intent.putExtra(StateChooserActivity.ARG_COUNTRY, country)
        startActivity(intent)
        finish()
    }

    override fun onResponse(response: Response<List<City>>) {
        citiesList = response.body()

        var countiesExist = false

        if (citiesList != null) {
            countiesExist = !(citiesList!!.size <= 1 && citiesList!![0].error != null)
        }

        if (countiesExist) {
            citiesAdapter = CitiesAdapter(this@CityChooserActivity,
                    R.layout.item_radio, citiesList as List<City>)

            list.adapter = citiesAdapter
            list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> citiesAdapter!!.selectItem(position) }
        } else {
            saveValuesAndExit()
        }

        end()
    }

    override fun onFailure(t: Throwable) {
        // TODO
        end()
    }

    private fun end() {
//        list.visibility = View.INVISIBLE
    }

    companion object {
        val ARG_COUNTRY = "arg_country"
        val ARG_CITY = "arg_city"
    }
}
