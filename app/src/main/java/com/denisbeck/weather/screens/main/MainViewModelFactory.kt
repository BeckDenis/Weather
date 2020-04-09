package com.denisbeck.weather.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisbeck.weather.repository.WeatherRepository

class MainViewModelFactory(private val weatherRepo: WeatherRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(weatherRepo) as T
}