package tr.xip.prayertimes.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_radio.view.*
import tr.xip.prayertimes.R
import tr.xip.prayertimes.model.Country

class CountriesAdapter(ctx: Context, resource: Int, val items: List<Country>) : ArrayAdapter<Country>(ctx, resource, items) {
    private var selectedPosition = 0
    val selectedCountry: Country
        get() = items[selectedPosition]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_radio, parent, false)
            view!!
        }

        val item = items[position]

        view.title.text = item.name

        view.radio.isChecked = position == selectedPosition
        view.radio.setOnClickListener { selectItem(position) }

        return view
    }

    fun selectItem(position: Int) {
        selectedPosition = position
        notifyDataSetInvalidated()
    }
}
