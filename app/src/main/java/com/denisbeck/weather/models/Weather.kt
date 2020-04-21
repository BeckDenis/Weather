package com.denisbeck.weather.models

import android.os.Parcelable
import com.denisbeck.weather.extensions.fullDate
import kotlinx.android.parcel.Parcelize

data class WeatherData(
    val weather: List<Weather>,
    val main: Main,
    val name: String,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys,
    val dt: Long,
    val timezone: Int
) {
    val date: String
        get() = (dt + timezone).fullDate()
}

data class ForecastData(val list: List<DayWeather>, val city: City)

@Parcelize
data class DayWeather(val main: Main, val weather: List<Weather>, val dt: Long, val clouds: Clouds, val wind: Wind) : Parcelable

@Parcelize
data class Weather(
    val main: String, val description: String, val icon: String
) : Parcelable

@Parcelize
data class Wind(val speed: Double) : Parcelable

@Parcelize
data class Clouds(val all: Int) : Parcelable

data class Sys(val sunrise: Long, val sunset: Long)

@Parcelize
data class City(
    val name: String, val timezone: Int, val sunrise: Long, val sunset: Long
) : Parcelable

@Parcelize
data class Main(val temp: Double, val humidity: Int) : Parcelable
