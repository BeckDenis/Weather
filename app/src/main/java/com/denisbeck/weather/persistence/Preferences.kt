package com.denisbeck.weather.persistence

import android.content.Context
import android.content.SharedPreferences
import com.denisbeck.weather.utils.lastLocationKey

class Preferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun storeLastLocation(location: String) {
        preferences.edit().putString(lastLocationKey, location).apply()
    }

    fun getLastLocation(): String? {
        return preferences.getString(lastLocationKey, "")
    }

}