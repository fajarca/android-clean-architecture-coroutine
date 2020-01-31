package io.fajarca.presentation.extension

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

infix fun Fragment.navigateTo(destination : String) {
    val uri = Uri.parse(destination)
    findNavController().navigate(uri)
}

fun String.toLocalTime(): String {
    if (this.isNotEmpty()) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val date = dateFormat.parse(this)
            val formattedDate = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(date ?: Date())
            formattedDate
        } catch (e: ParseException) {
            throw IllegalArgumentException("Not a valid datetime format")
        }
    }

    return ""
}