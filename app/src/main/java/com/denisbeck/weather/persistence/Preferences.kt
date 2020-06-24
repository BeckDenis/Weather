package com.denisbeck.weather.persistence

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.denisbeck.weather.utils.autoLocationKey
import com.denisbeck.weather.utils.savedLocationsKey
import com.denisbeck.weather.utils.selectedLocationKey
import java.util.*

class Preferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    companion object {
        private val TAG = Preferences::class.java.simpleName
    }

    fun storeAutoLocate(value: Boolean) {
        preferences.edit().putBoolean(autoLocationKey, value).apply()
    }

    fun getAutoLocate(): Boolean {
        return preferences.getBoolean(autoLocationKey, true)
    }

    fun deleteSavedLocations(location: String) {
        val locations = getSavedLocations()?.toMutableSet() ?: mutableSetOf()
        Log.d(TAG, "deleteSavedLocations: $locations")
        locations.remove(location.toLowerCase(Locale.getDefault()))
        Log.d(TAG, "deleteSavedLocations: $locations")
        preferences.edit().putStringSet(savedLocationsKey, locations).apply()
    }

    fun storeSavedLocations(location: String) {
        val locations = getSavedLocations()?.toMutableSet() ?: mutableSetOf()
        locations.add(location.toLowerCase(Locale.getDefault()))
        preferences.edit().putStringSet(savedLocationsKey, locations).apply()
    }

    fun getSavedLocations(): MutableSet<String>? {
        return preferences.getStringSet(savedLocationsKey, emptySet())
    }

    fun storeSelectedLocation(location: String) {
        Log.d(TAG, "storeSelectedLocation: $location")
        preferences.edit().putString(selectedLocationKey, location).apply()
    }

    fun getSelectedLocation(): String? {
        Log.d(TAG, "getSelectedLocation: ${preferences.getString(selectedLocationKey, "Moscow")}")
        return preferences.getString(selectedLocationKey, "Moscow")
    }

}