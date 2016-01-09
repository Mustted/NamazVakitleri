package tr.xip.prayertimes.ui.widget

import android.content.Context
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

import tr.xip.prayertimes.R

/**
 * A `TextView` that, given a reference time, renders that time as a time period relative to the current time.
 * @author Kiran Rao
 * *
 * @see .setReferenceTime
 */
class RelativeTimeTextView : TextView {

    private var mReferenceTime: Long = 0
    private var mText: String? = null
    /**
     * Returns prefix
     * @return
     */
    /**
     * String to be attached before the reference time
     * @param prefix
     * *
     * * Example:
     * * [prefix] in XX minutes
     */
    var prefix: String? = null
        set(prefix) {
            this.prefix = prefix
            updateTextDisplay()
        }
    /**
     * Returns suffix
     * @return
     */
    /**
     * String to be attached after the reference time
     * @param suffix
     * *
     * * Example:
     * * in XX minutes [suffix]
     */
    var suffix: String? = null
        set(suffix) {
            this.suffix = suffix
            updateTextDisplay()
        }
    private val mHandler = Handler()

    /*private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long difference = Math.abs(System.currentTimeMillis() - mReferenceTime);
            long interval = DateUtils.MINUTE_IN_MILLIS;
            if (difference > DateUtils.WEEK_IN_MILLIS) {
                interval = DateUtils.WEEK_IN_MILLIS;
            } else if (difference > DateUtils.DAY_IN_MILLIS) {
                interval = DateUtils.DAY_IN_MILLIS;
            } else if (difference > DateUtils.HOUR_IN_MILLIS) {
                interval = DateUtils.HOUR_IN_MILLIS;
            }
            updateTextDisplay();
            mHandler.postDelayed(this, interval);
        }
    };*/

    private var mUpdateTimeTask: UpdateTimeRunnable? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(attrs,
                R.styleable.RelativeTimeTextView, 0, 0)
        try {
            mText = a.getString(R.styleable.RelativeTimeTextView_reference_time)
            prefix = a.getString(R.styleable.RelativeTimeTextView_relative_time_prefix)
            suffix = a.getString(R.styleable.RelativeTimeTextView_relative_time_suffix)

            prefix = if (prefix == null) "" else prefix
            suffix = if (suffix == null) "" else suffix
        } finally {
            a.recycle()
        }

        try {
            mReferenceTime = java.lang.Long.valueOf(mText)!!
        } catch (nfe: NumberFormatException) {
            /*
        	 * TODO: Better exception handling
        	 */
            mReferenceTime = -1L
        }


    }

    /**
     * Sets the reference time for this view. At any moment, the view will render a relative time period relative to the time set here.
     *
     *
     * This value can also be set with the XML attribute `reference_time`
     * @param referenceTime The timestamp (in milliseconds since epoch) that will be the reference point for this view.
     */
    fun setReferenceTime(referenceTime: Long) {
        this.mReferenceTime = referenceTime

        /*
         * Note that this method could be called when a row in a ListView is recycled.
         * Hence, we need to first stop any currently running schedules (for example from the recycled view.
         */
        stopTaskForPeriodicallyUpdatingRelativeTime()

        /*
         * Instantiate a new runnable with the new reference time
         */
        mUpdateTimeTask = UpdateTimeRunnable(mReferenceTime)

        /*
         * Start a new schedule.
         */
        startTaskForPeriodicallyUpdatingRelativeTime()

        /*
         * Finally, update the text display.
         */
        updateTextDisplay()
    }

    private fun updateTextDisplay() {
        /*
         * TODO: Validation, Better handling of negative cases
         */
        if (this.mReferenceTime == -1L)
            return
        text = prefix + relativeTimeDisplayString + suffix
    }

    private val relativeTimeDisplayString: CharSequence
        get() {
            val now = System.currentTimeMillis()
            val difference = now - mReferenceTime
            return if ((difference >= 0 && difference <= DateUtils.MINUTE_IN_MILLIS))
                resources.getString(R.string.just_now)
            else
                DateUtils.getRelativeTimeSpanString(
                        mReferenceTime,
                        now,
                        DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_RELATIVE)
        }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startTaskForPeriodicallyUpdatingRelativeTime()

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopTaskForPeriodicallyUpdatingRelativeTime()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            stopTaskForPeriodicallyUpdatingRelativeTime()
        } else {
            startTaskForPeriodicallyUpdatingRelativeTime()
        }
    }

    private fun startTaskForPeriodicallyUpdatingRelativeTime() {
        mHandler.post(mUpdateTimeTask)
    }

    private fun stopTaskForPeriodicallyUpdatingRelativeTime() {
        mHandler.removeCallbacks(mUpdateTimeTask)
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.refTime = mReferenceTime
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        mReferenceTime = state.refTime
        super.onRestoreInstanceState(state.superState)
    }

    class SavedState : View.BaseSavedState {
        var refTime: Long = 0

        constructor(superState: Parcelable) : super(superState)

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeLong(refTime)
        }

        private constructor(`in`: Parcel) : super(`in`) {
            refTime = `in`.readLong()
        }

        companion object {

            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    private inner class UpdateTimeRunnable internal constructor(private val mRefTime: Long) : Runnable {

        override fun run() {
            val difference = Math.abs(System.currentTimeMillis() - mRefTime)
            var interval = DateUtils.MINUTE_IN_MILLIS
            if (difference > DateUtils.WEEK_IN_MILLIS) {
                interval = DateUtils.WEEK_IN_MILLIS
            } else if (difference > DateUtils.DAY_IN_MILLIS) {
                interval = DateUtils.DAY_IN_MILLIS
            } else if (difference > DateUtils.HOUR_IN_MILLIS) {
                interval = DateUtils.HOUR_IN_MILLIS
            }
            updateTextDisplay()
            mHandler.postDelayed(this, interval)
        }
    }
}