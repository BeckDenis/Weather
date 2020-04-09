package com.denisbeck.weather.screens.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.denisbeck.weather.R
import com.denisbeck.weather.models.WeatherData
import com.denisbeck.weather.networking.*
import com.denisbeck.weather.repository.WeatherRepository
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by activityViewModels<MainViewModel> {
        MainViewModelFactory(
            WeatherRepository(
                provideForecastApi(
                    provideRetrofit(
                        provideOkHttpClient(AuthInterceptor(), provideLoggingInterceptor())
                    )
                ),
                ResponseHandler()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkSharedPref()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.weather.observe(viewLifecycleOwner, Observer { statusActions(it) })

    }

    private fun checkSharedPref() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val lastSearchedLocation =
            sharedPref.getString(getString(R.string.last_location_searched_key), "")
        if (!lastSearchedLocation.isNullOrBlank()) getWeather(lastSearchedLocation)
    }

    private fun getWeather(location: String) = viewModel.getWeather(location)

    private fun statusActions(it: Resource<WeatherData>) {
        when (it.status) {
            Status.SUCCESS -> {
                it.data?.let {
                    updateTemperatureText(it)
                    saveLocation(it.name)
                }
            }
            Status.ERROR -> showError(it.message!!)
            Status.LOADING -> showLoading()
        }
    }

    private fun saveLocation(location: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        sharedPref.edit().run {
            putString(getString(R.string.last_location_searched_key), location)
            commit()
        }
    }

    private fun showLoading() {

    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun updateTemperatureText(weatherData: WeatherData) {
        main_name.text = weatherData.name
        main_date.text = viewModel.date.toString()
        main_icon.setImageDrawable(weatherIcon(weatherData.weather.first().icon))
        main_temp.text = weatherData.main.temp.toString()
        main_description.text = weatherData.weather.first().description
        main_humidity.text = weatherData.main.humidity.toString()
        main_wind.text = weatherData.wind.speed.toString()
        main_clouds.text = weatherData.clouds.all.toString()
    }

    private fun weatherIcon(iconId: String) = when(iconId) {
        "01d" -> context?.getDrawable(R.drawable.i01d_high)
        "01n" -> context?.getDrawable(R.drawable.i01n_high)
        "02d" -> context?.getDrawable(R.drawable.i02d_high)
        "02n" -> context?.getDrawable(R.drawable.i02n_high)
        "03d", "03n", "04d", "04n" -> context?.getDrawable(R.drawable.i03_high)
        "09d", "09n" -> context?.getDrawable(R.drawable.i09_high)
        "10d" -> context?.getDrawable(R.drawable.i10d_high)
        "10n" -> context?.getDrawable(R.drawable.i10n_high)
        "11d" -> context?.getDrawable(R.drawable.i11d_high)
        "11n" -> context?.getDrawable(R.drawable.i11n_high)
        "13d", "13n" -> context?.getDrawable(R.drawable.i13_high)
        else -> context?.getDrawable(R.drawable.i01d_high)
    }
}

