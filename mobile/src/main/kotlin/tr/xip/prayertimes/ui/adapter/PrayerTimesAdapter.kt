package tr.xip.prayertimes.ui.apdater

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_prayer.view.*
//import kotlinx.android.synthetic.main.item_prayer_current.view.*
import tr.xip.prayertimes.R
import tr.xip.prayertimes.model.PrayerTime
import tr.xip.prayertimes.util.RemainingTimeCounter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.indices

class PrayerTimesAdapter(val items: List<PrayerTime>) : RecyclerView.Adapter<PrayerTimesAdapter.ViewHolder>() {
    val currentPrayerTimePosition: Int
        get() {
            var position = -1
            for (i in items.indices) {
                if (prayerTimeIsNow(i)) {
                    position = i
                    break
                }
            }
            return position
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        if (Type.fromInt(viewType) == Type.CURRENT) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_prayer_current, parent, false)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_prayer, parent, false)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        val prayerTime = Date(item.time)
        val now = Date(System.currentTimeMillis())

        /* image */
        if (getItemViewType(position) == Type.CURRENT.toInt()) {
            holder.view.image.setImageDrawable(item.largeImage)
        } else {
            holder.view.image.setImageDrawable(item.smallImage)
        }

        /* title */
        holder.view.title.text = item.title

        /* time */
        @SuppressLint("SimpleDateFormat")
        val sdf = SimpleDateFormat("HH:mm")
        val timeString = sdf.format(item.time)
        holder.view.time.text = timeString

        /* remaining time / countdown */
        if (position == currentPrayerTimePosition + 1 && now.before(prayerTime)) {
            // One item after the current one and now is before this item. Show countdown here.
            holder.view.remainingTime.visibility = View.VISIBLE
            RemainingTimeCounter.newInstance(
                    this, holder.view.remainingTime, prayerTime.time).start()
        } else if (holder.view.remainingTime != null) {
            holder.view.remainingTime.visibility = View.GONE
        }
    }

    private fun prayerTimeIsNow(position: Int): Boolean {
        val prayerTime = Date(items[position].time)
        if (position == (items.size - 1) && Date().after(prayerTime)) {
            // This is the last item and there's no need to check for the next prayer time
            return true
        } else if (position != (items.size - 1)) {
            val nextPrayerTime = Date(items[position + 1].time)
            return Date().after(prayerTime) && Date().before(nextPrayerTime)
        } else {
            return false
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (prayerTimeIsNow(position)) Type.CURRENT.toInt() else Type.NORMAL.toInt()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    private enum class Type private constructor(private val value: Int) {
        NORMAL(0), CURRENT(1);

        fun toInt(): Int = value

        companion object {

            fun fromInt(type: Int): Type {
                return if (type == 1) CURRENT else NORMAL
            }
        }
    }
}
