package com.denisbeck.weather.extensions

import android.content.Context
import androidx.fragment.app.Fragment
import com.denisbeck.weather.R

fun Fragment.saveLocation(location: String) {
    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
    sharedPref.edit().run {
        putString(getString(R.string.last_location_searched_key), location)
        commit()
    }
}