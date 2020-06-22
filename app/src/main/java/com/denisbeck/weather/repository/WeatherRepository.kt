package com.denisbeck.weather.repository

import com.denisbeck.weather.models.ForecastData
import com.denisbeck.weather.models.WeatherData
import com.denisbeck.weather.networking.*


class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val forecastApi: ForecastApi,
    private val responseHandler: ResponseHandler
) {

    suspend fun getCurrentWeather(location: String): Resource<WeatherData> {
        return try {
            val response = weatherApi.getCurrentWeather(location, "metric")
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getForecast(location: String): Resource<ForecastData> {
        return try {
            val response = forecastApi.getForecast(location, "metric")
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}