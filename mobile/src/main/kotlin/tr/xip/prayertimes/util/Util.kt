package tr.xip.prayertimes.util

import tr.xip.prayertimes.app.NamazVakitleriApplication
import java.util.*
import kotlin.collections.dropLastWhile
import kotlin.collections.toTypedArray
import kotlin.text.isEmpty
import kotlin.text.split
import kotlin.text.toRegex

fun getContext() = NamazVakitleriApplication.context!!

fun getTimestampFromDateTime(date: String, time: String): Long {
    val dateParts = date.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
    val timeParts = time.split(":".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

    val day = Integer.parseInt(dateParts[0])
    val month = Integer.parseInt(dateParts[1])
    val year = Integer.parseInt(dateParts[2])
    val hour = Integer.parseInt(timeParts[0])
    val minute = Integer.parseInt(timeParts[1])

    val cal = Calendar.getInstance()
    cal.set(Calendar.YEAR, year)
    cal.set(Calendar.MONTH, month - 1)
    cal.set(Calendar.DAY_OF_MONTH, day)
    cal.set(Calendar.HOUR_OF_DAY, hour)
    cal.set(Calendar.MINUTE, minute)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)

    return cal.timeInMillis
}

fun getTimestampFromDate(date: String): Long {
    val dateParts = date.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

    val day = Integer.parseInt(dateParts[0])
    val month = Integer.parseInt(dateParts[1])
    val year = Integer.parseInt(dateParts[2])

    val cal = Calendar.getInstance()
    cal.set(Calendar.YEAR, year)
    cal.set(Calendar.MONTH, month - 1)
    cal.set(Calendar.DAY_OF_MONTH, day)
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)

    return cal.timeInMillis
}