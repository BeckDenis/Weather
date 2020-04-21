package com.denisbeck.weather.repository

import android.util.Log
import com.denisbeck.weather.models.ForecastData
import com.denisbeck.weather.models.WeatherData
import com.denisbeck.weather.networking.*
import retrofit2.Retrofit


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

open class ForecastRepository(
    private val forecastApi: ForecastApi,
    private val responseHandler: ResponseHandler
) {
    companion object {
        private val TAG = ForecastRepository::class.java.simpleName
    }

    suspend fun getForecast(location: String): Resource<ForecastData> {
        return try {
            val response = forecastApi.getForecast(location, "metric")
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            Log.e(TAG, "getForecast: ${e.message}")
            responseHandler.handleException(e)
        }
    }
}

fun getWeatherRepository() = WeatherRepository(
    provideWeatherApi(settings()),
    ResponseHandler()
)

fun getForecastRepository() = ForecastRepository(
    provideForecastApi(settings()),
    ResponseHandler()
)

private fun settings(): Retrofit {
    return provideRetrofit(
            provideOkHttpClient(
                AuthInterceptor(),
                provideLoggingInterceptor()
            )
        )
}