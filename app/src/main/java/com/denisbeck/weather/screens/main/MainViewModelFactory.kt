package com.denisbeck.weather.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisbeck.weather.repository.ForecastRepository
import com.denisbeck.weather.repository.WeatherRepository

class MainViewModelFactory(private val weatherRepo: WeatherRepository, private val forecastRepo: ForecastRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(weatherRepo, forecastRepo) as T
}