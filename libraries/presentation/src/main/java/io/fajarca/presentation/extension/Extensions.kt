package io.fajarca.presentation.extension

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.toLocalTime(locale: Locale): String {
    if (this.isNotEmpty()) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val date = dateFormat.parse(this)
            val formattedDate = DateFormat.getTimeInstance(DateFormat.SHORT, locale).format(date ?: Date())
            formattedDate
        } catch (e: ParseException) {
            throw IllegalArgumentException("Not a valid datetime format")
        }
    }

    return ""
}