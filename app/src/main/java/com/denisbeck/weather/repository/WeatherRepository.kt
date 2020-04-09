package com.denisbeck.weather.repository

import com.denisbeck.weather.models.WeatherData
import com.denisbeck.weather.networking.Resource
import com.denisbeck.weather.networking.ResponseHandler
import com.denisbeck.weather.networking.WeatherApi


open class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val responseHandler: ResponseHandler
) {

    suspend fun getWeather(location: String): Resource<WeatherData> {
        return try {
            val response = weatherApi.getForecast(location, "metric")
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}