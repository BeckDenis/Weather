package com.denisbeck.weather.screens.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.denisbeck.weather.networking.Resource
import com.denisbeck.weather.repository.WeatherRepository

class SearchViewModel(private val weatherRepo: WeatherRepository): ViewModel() {

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }
    
    private val location = MutableLiveData<String>()

    fun updateLastLocation(input: String?) {
        Log.d(TAG, "updateLastLocation: ")
        input?.let {
            location.value = it
        }
    }

    var weather = location.switchMap { location ->
        Log.d(TAG, "switchMap: ")
        liveData {
            emit(Resource.loading(null))
            emit(weatherRepo.getCurrentWeather(location))
        }
    }
}