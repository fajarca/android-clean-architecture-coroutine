package io.fajarca.presentation.extension

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

infix fun Fragment.navigateTo(destination : String) {
    val uri = Uri.parse(destination)
    findNavController().navigate(uri)
}