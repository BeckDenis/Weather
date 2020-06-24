package com.denisbeck.weather.screens.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.denisbeck.weather.models.WeatherData
import com.denisbeck.weather.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers

class SearchViewModel(private val weatherRepository: WeatherRepository): ViewModel() {

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }

    private val _weather = MutableLiveData<MutableSet<WeatherData>>(mutableSetOf())
    val weather: LiveData<MutableSet<WeatherData>>
        get() = _weather

    fun updateWeather(value: WeatherData) {
        Log.d(TAG, "updateWeather: $value")
        _weather.value?.add(value)
        _weather.notifyObserver()
    }

    fun getWeather(location: String) = liveData(Dispatchers.IO) {
        emit(weatherRepository.getCurrentWeather(location))
    }

    fun deleteWeather(value: WeatherData) {
        _weather.value?.remove(value)
        _weather.notifyObserver()
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}