package tr.xip.prayertimes.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_spinner_toolbar_selected.view.*
import tr.xip.prayertimes.model.Location
import tr.xip.prayertimes.util.LocationsList

class LocationsSpinnerAdapter(ctx: Context, resource: Int, val items: LocationsList) : ArrayAdapter<Location>(ctx, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = super.getView(position, convertView, parent)
            view!!
        }

        view.text.text = items[position].name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = super.getDropDownView(position, convertView, parent)
            view!!
        }

        view.text.text = items[position].name
        return view
    }
}
