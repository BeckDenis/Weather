package com.denisbeck.weather.models

data class WeatherData(
    val weather: List<Weather>,
    val main: Main,
    val name: String,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double
)

data class Clouds(
    val all: Int
)

data class Sys(
    val sunrise: Long,
    val sunset: Long
)

data class Main(
    val temp: Double,
    val humidity: Int
)
