package tr.xip.prayertimes.ext

import tr.xip.prayertimes.app.NamazVakitleriApplication

/**
 * Calculates and returns the pixel value for a dp value
 */
fun Int.toPx(): Int {
    return (this * NamazVakitleriApplication.context!!.resources.displayMetrics.density).toInt()
}

/**
 * Calculates and returns the dp value for an amount of pixels
 */
fun Int.toDp(): Int {
    return (this / NamazVakitleriApplication.context!!.resources.displayMetrics.density).toInt()
}

