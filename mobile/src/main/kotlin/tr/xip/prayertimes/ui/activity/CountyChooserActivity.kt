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
import tr.xip.prayertimes.model.County
import tr.xip.prayertimes.model.Location
import tr.xip.prayertimes.db.DatabaseManager
import tr.xip.prayertimes.ui.adapter.CountiesAdapter
import java.util.*

class CountyChooserActivity : AppCompatActivity(), Callback<List<County>> {
    private var countiesAdapter: CountiesAdapter? = null

    private var countiesList: List<County>? = ArrayList()

    private var country: Country? = null
    private var city: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.nothing)
        setContentView(R.layout.activity_list_chooser)

        val intent = intent
        country = intent.getSerializableExtra(ARG_COUNTRY) as Country
        city = intent.getSerializableExtra(ARG_CITY) as City

        chooserTitle.text = getString(R.string.choose_your_county)

        previous.setOnClickListener { goBack() }

        next.setOnClickListener {
            if (countiesAdapter != null) {
                saveValuesAndExit(countiesAdapter!!.selectedCounty)
            }
        }

        if (city != null && city!!.id != null) {
            DiyanetClient.getCountiesForCity(city!!.id!!).enqueue(this)
        }
    }

    private fun saveValuesAndExit(county: County?) {
        DatabaseManager.addLocation(Location(
                country?.id,
                country?.name,
                city?.id,
                city?.name,
                county?.id,
                county?.name))
        startActivity(Intent(this@CountyChooserActivity, MainActivity::class.java))
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
        val intent = Intent(this@CountyChooserActivity, CityChooserActivity::class.java)
        intent.putExtra(CityChooserActivity.ARG_COUNTRY, country)
        startActivity(intent)
        finish()
    }

    override fun onResponse(response: Response<List<County>>) {
        countiesList = response.body()

        var countiesExist = false

        if (countiesList != null) {
            countiesExist = !(countiesList!!.size <= 1 && countiesList!![0].error != null)
        }

        if (countiesExist) {
            countiesAdapter = CountiesAdapter(this@CountyChooserActivity,
                    R.layout.item_radio, countiesList as List<County>)

            list.adapter = countiesAdapter
            list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> countiesAdapter!!.selectItem(position) }
        } else {
            saveValuesAndExit(null)
        }

        end()
    }

    override fun onFailure(t: Throwable) {
        // TODO
        end()
    }

    private fun end() {
        list.visibility = View.INVISIBLE
    }

    companion object {
        val ARG_COUNTRY = "arg_country"
        val ARG_CITY = "arg_city"
    }
}
