package com.denisbeck.weather.screens.main

import androidx.lifecycle.*
import com.denisbeck.weather.networking.Resource
import com.denisbeck.weather.repository.ForecastRepository
import com.denisbeck.weather.repository.WeatherRepository
import java.util.*

class MainViewModel(private val weatherRepo: WeatherRepository, private val forecastRepo: ForecastRepository) : ViewModel() {

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