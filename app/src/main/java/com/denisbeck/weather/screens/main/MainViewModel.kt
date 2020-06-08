package com.denisbeck.weather.screens.main

import androidx.lifecycle.*
import com.denisbeck.weather.networking.Resource
import com.denisbeck.weather.repository.ForecastRepository
import com.denisbeck.weather.repository.WeatherRepository

class MainViewModel(private val weatherRepo: WeatherRepository, private val forecastRepo: ForecastRepository) : ViewModel() {

    var lastUserLocation: String? = null

    private val location = MutableLiveData<String>()

    fun updateLastLocation(input: String) {
        location.value = input
    }

    var weather = location.switchMap { location ->
        liveData {
            emit(Resource.loading(null))
            emit(weatherRepo.getWeather(location))
        }
    }

    var forecast = location.switchMap { location ->
        liveData {
            emit(Resource.loading(null))
            emit(forecastRepo.getForecast(location))
        }
    }
}