package tr.xip.prayertimes.ui.widget

import android.view.View
import android.widget.AdapterView

abstract class OnItemSelectedListenerAdapter : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {}

    override fun onNothingSelected(parent: AdapterView<*>) {}
}
