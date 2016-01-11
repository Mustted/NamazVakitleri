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
import tr.xip.prayertimes.ui.adapter.StatesAdapter
import java.util.*

class StateChooserActivity : AppCompatActivity(), Callback<List<State>> {
    private var statesAdapter: StatesAdapter? = null

    private var statesList: List<State>? = ArrayList()

    private var country: Country? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.nothing)
        setContentView(R.layout.activity_list_chooser)

        country = intent.getSerializableExtra(ARG_COUNTRY) as Country

        chooserTitle.text = getString(R.string.choose_your_city)

        previous.setOnClickListener { goBack() }
        next.setOnClickListener {
            if (statesAdapter != null) {
                val city = statesAdapter!!.selectedState
                val intent = Intent(this@StateChooserActivity, CityChooserActivity::class.java)
                intent.putExtra(CityChooserActivity.ARG_COUNTRY, country)
                intent.putExtra(CityChooserActivity.ARG_CITY, city)
                startActivity(intent)
                finish()
            }
        }

        if (country != null && country!!.id != null) {
            DiyanetClient.getStatesForCountry(country!!.id!!).enqueue(this)
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
        startActivity(Intent(this@StateChooserActivity, CountryChooserActivity::class.java))
        finish()
    }

    override fun onResponse(response: Response<List<State>>?) {
        statesList = response?.body()
        if (statesList != null) {
            statesAdapter = StatesAdapter(this@StateChooserActivity, R.layout.item_radio, statesList!!)

            list.adapter = statesAdapter
            list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l -> statesAdapter!!.selectItem(i) }
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
