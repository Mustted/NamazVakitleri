package tr.xip.prayertimes.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import tr.xip.prayertimes.R;

public class NotificationsToggle extends FrameLayout {
    private static final int NOTIFICATIONS_OFF = 0;
    private static final int NOTIFICATIONS_ON = 1;
    private static final int NOTIFICATIONS_RING = 2;

    private OnNotificationStateChangedListener mListener;

    private int mNotificationsState = NOTIFICATIONS_OFF;

    private ImageView mNotificationsIndicator;

    public NotificationsToggle(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_notification_toggle, this, true);

        mNotificationsIndicator = (ImageView) findViewById(R.id.widget_notifications_indicator);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mNotificationsState) {
                    case NOTIFICATIONS_OFF:
                        setNotificationsState(NOTIFICATIONS_ON);
                        break;
                    case NOTIFICATIONS_ON:
                        setNotificationsState(NOTIFICATIONS_RING);
                        break;
                    case NOTIFICATIONS_RING:
                        setNotificationsState(NOTIFICATIONS_OFF);
                        break;
                }
            }
        });
    }

    public void setNotificationsState(int notificationsState) {
        switch (notificationsState) {
            case NOTIFICATIONS_OFF:
                mNotificationsState = NOTIFICATIONS_OFF;
                mNotificationsIndicator.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
                mNotificationsIndicator.setBackgroundResource(R.drawable.oval_gray_light);
                break;
            case NOTIFICATIONS_ON:
                mNotificationsState = NOTIFICATIONS_ON;
                mNotificationsIndicator.setImageResource(R.drawable.ic_notifications_white_24dp);
                mNotificationsIndicator.setBackgroundResource(R.drawable.oval_apptheme_primary);
                break;
            case NOTIFICATIONS_RING:
                mNotificationsState = NOTIFICATIONS_RING;
                mNotificationsIndicator.setImageResource(R.drawable.ic_notifications_on_white_24dp);
                mNotificationsIndicator.setBackgroundResource(R.drawable.oval_apptheme_primary);
                break;
        }

        if (mListener != null)
            mListener.onNotificationsStateChanged(mNotificationsState);
    }

    public void setOnNotificationStateChangedListener(OnNotificationStateChangedListener listener) {
        mListener = listener;
    }

    public interface OnNotificationStateChangedListener {
        public void onNotificationsStateChanged(int notificationsState);
    }
}
