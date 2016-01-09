package tr.xip.prayertimes.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.widget_notification_toggle.view.*

import tr.xip.prayertimes.R

class NotificationsToggle(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var listener: OnNotificationStateChangedListener? = null

    private var state = NOTIFICATIONS_OFF

    init {
        LayoutInflater.from(context).inflate(R.layout.widget_notification_toggle, this, true)

        setOnClickListener {
            when (state) {
                NOTIFICATIONS_OFF -> setNotificationsState(NOTIFICATIONS_ON)
                NOTIFICATIONS_ON -> setNotificationsState(NOTIFICATIONS_RING)
                NOTIFICATIONS_RING -> setNotificationsState(NOTIFICATIONS_OFF)
            }
        }
    }

    fun setNotificationsState(notificationsState: Int) {
        when (notificationsState) {
            NOTIFICATIONS_OFF -> {
                state = NOTIFICATIONS_OFF
                indicator.setImageResource(R.drawable.ic_notifications_off_grey600_24dp)
                indicator.setBackgroundResource(R.drawable.oval_gray_light)
            }
            NOTIFICATIONS_ON -> {
                state = NOTIFICATIONS_ON
                indicator.setImageResource(R.drawable.ic_notifications_white_24dp)
                indicator.setBackgroundResource(R.drawable.oval_apptheme_primary)
            }
            NOTIFICATIONS_RING -> {
                state = NOTIFICATIONS_RING
                indicator.setImageResource(R.drawable.ic_notifications_on_white_24dp)
                indicator.setBackgroundResource(R.drawable.oval_apptheme_primary)
            }
        }

        listener?.onNotificationsStateChanged(notificationsState)
    }

    fun setOnNotificationStateChangedListener(listener: OnNotificationStateChangedListener) {
        this.listener = listener
    }

    interface OnNotificationStateChangedListener {
        fun onNotificationsStateChanged(notificationsState: Int)
    }

    companion object {
        private val NOTIFICATIONS_OFF = 0
        private val NOTIFICATIONS_ON = 1
        private val NOTIFICATIONS_RING = 2
    }
}
