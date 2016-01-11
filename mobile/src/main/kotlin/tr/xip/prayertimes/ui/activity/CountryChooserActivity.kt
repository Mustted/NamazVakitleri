package tr.xip.prayertimes.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_list_chooser.*
import tr.xip.prayertimes.R
import tr.xip.prayertimes.client.DiyanetClient
import tr.xip.prayertimes.model.Country
import tr.xip.prayertimes.ui.adapter.CountriesAdapter
import java.util.*

class CountryChooserActivity : AppCompatActivity() {
    private var countriesList: List<Country> = ArrayList()

    private var countriesAdapter: CountriesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.nothing)
        setContentView(R.layout.activity_list_chooser)

        chooserTitle.text = getString(R.string.choose_your_country)

        countriesList = DiyanetClient.countriesList

        countriesAdapter = CountriesAdapter(this,
                R.layout.item_radio, countriesList)

        list.adapter = countriesAdapter
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l -> countriesAdapter!!.selectItem(i) }

        previousIcon.setImageResource(R.drawable.ic_close_grey600_18dp)
        previousText.setText(R.string.cancel)

        previous.setOnClickListener {
            startActivity(Intent(this@CountryChooserActivity, MainActivity::class.java))
            finish()
        }

        next.setOnClickListener {
            val country = countriesAdapter!!.selectedCountry
            val intent = Intent(this@CountryChooserActivity, StateChooserActivity::class.java)
            intent.putExtra(StateChooserActivity.ARG_COUNTRY, country)
            startActivity(intent)
            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.nothing, R.anim.fade_out)
    }
}
