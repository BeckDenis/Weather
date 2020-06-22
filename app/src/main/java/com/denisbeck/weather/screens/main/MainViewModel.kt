package com.denisbeck.weather.screens.main

import android.util.Log
import androidx.lifecycle.*
import com.denisbeck.weather.networking.Resource
import com.denisbeck.weather.repository.WeatherRepository

class MainViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val location = MutableLiveData<String>()

    fun updateLastLocation(input: String?) {
        input?.let {
            Log.d(TAG, "updateLastLocation: $it")
            location.value = it
        }
    }

    var weather = location.switchMap { location ->
        liveData {
            emit(Resource.loading(null))
            emit(weatherRepository.getCurrentWeather(location))
        }
    }

    var forecast = location.switchMap { location ->
        liveData {
            emit(Resource.loading(null))
            emit(weatherRepository.getForecast(location))
        }
    }
}