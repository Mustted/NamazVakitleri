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
import tr.xip.prayertimes.model.City
import tr.xip.prayertimes.model.Country
import tr.xip.prayertimes.ui.adapter.CitiesAdapter
import java.util.*

class CityChooserActivity : AppCompatActivity(), Callback<List<City>> {
    private var citiesAdapter: CitiesAdapter? = null

    private var citiesList: List<City> = ArrayList()

    private var country: Country? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.nothing)
        setContentView(R.layout.activity_list_chooser)

        country = intent.getSerializableExtra(ARG_COUNTRY) as Country

        chooserTitle.text = getString(R.string.choose_your_city)

        previous.setOnClickListener { goBack() }
        next.setOnClickListener {
            if (citiesAdapter != null) {
                val city = citiesAdapter!!.selectedCity
                val intent = Intent(this@CityChooserActivity, CountyChooserActivity::class.java)
                intent.putExtra(CountyChooserActivity.ARG_COUNTRY, country)
                intent.putExtra(CountyChooserActivity.ARG_CITY, city)
                startActivity(intent)
                finish()
            }
        }


        if (country != null && country!!.id != null) {
            DiyanetClient.getCitiesForCountry(country!!.id!!).enqueue(this)
        }
    }

    override fun onBackPressed() {
        goBack()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.nothing, R.anim.fade_out)
    }

    private fun goBack() {
        startActivity(Intent(this@CityChooserActivity, CountryChooserActivity::class.java))
        finish()
    }

    override fun onResponse(response: Response<List<City>>?) {
        if (response?.body() != null) {
            citiesAdapter = CitiesAdapter(this@CityChooserActivity,
                    R.layout.item_radio, citiesList)

            list.adapter = citiesAdapter
            list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l -> citiesAdapter!!.selectItem(i) }
        } else {
            fail(null)
        }

        end()
    }

    override fun onFailure(t: Throwable) {
        fail(null)
    }

    private fun fail(response: Response<Any>?) {
        // TODO
        end()
    }

    private fun end() {
        progressBar.visibility = View.INVISIBLE
    }

    companion object {
        val ARG_COUNTRY = "arg_country"
    }
}
