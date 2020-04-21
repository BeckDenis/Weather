package com.denisbeck.weather.screens.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.denisbeck.weather.R
import com.denisbeck.weather.extensions.fullDate
import com.denisbeck.weather.extensions.isDay
import com.denisbeck.weather.models.DayWeather
import com.denisbeck.weather.models.ForecastData
import com.denisbeck.weather.models.WeatherData
import com.denisbeck.weather.networking.*
import com.denisbeck.weather.repository.getForecastRepository
import com.denisbeck.weather.repository.getWeatherRepository
import com.denisbeck.weather.utils.setColorText
import com.denisbeck.weather.utils.weatherIconHighQ
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        private val TAG = MainFragment::class.java.simpleName
    }

    private val viewModel by activityViewModels<MainViewModel> {
        MainViewModelFactory(getWeatherRepository(), getForecastRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkSharedPref()
        viewModel.updateLastLocation("pavlodar")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.weather.observe(viewLifecycleOwner, Observer { statusWeatherActions(it) })
        viewModel.forecast.observe(viewLifecycleOwner, Observer { statusForecastActions(it) })
    }

    private fun checkSharedPref() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val lastSearchedLocation =
            sharedPref.getString(getString(R.string.last_location_searched_key), "")
        if (!lastSearchedLocation.isNullOrBlank()) lastLocation(lastSearchedLocation)
    }

    private fun lastLocation(location: String) = viewModel.updateLastLocation(location)

    private fun statusWeatherActions(it: Resource<WeatherData>) {
        when (it.status) {
            Status.SUCCESS -> {
                main_weather_progress_bar.visibility = View.GONE
                it.data?.let {
                    setStyle((it.dt + it.timezone).isDay())
                    updateViews(it)
                    saveLocation(it.name)
                }
            }
            Status.ERROR -> {
                main_weather_progress_bar.visibility = View.GONE
                showError(it.message!!)
            }
            Status.LOADING -> showWeatherLoading()
        }
    }

    private fun statusForecastActions(it: Resource<ForecastData>) {
        when (it.status) {
            Status.SUCCESS -> {
                updateRecycler(it.data)
                main_recycler_progress_bar.visibility = View.GONE
                Log.d(TAG, "statusForecastActions: success")
            }
            Status.ERROR -> {
                showError(it.message!!)
                main_recycler_progress_bar.visibility = View.GONE
                Log.d(TAG, "statusForecastActions: ${it.message}")
            }
            Status.LOADING -> showForecastLoading()
        }
    }

    private fun updateRecycler(forecast: ForecastData?) {
        forecast?.let {
            main_recycler.adapter = ForecastAdapter(it) { weather ->
                val action = MainFragmentDirections
                    .actionMainFragmentToDetailFragment(weather, it.city, it.city.name)
                findNavController().navigate(action)
            }
        }
    }

    private fun saveLocation(location: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        sharedPref.edit().run {
            putString(getString(R.string.last_location_searched_key), location)
            commit()
        }
    }

    private fun showWeatherLoading() {
        main_weather_progress_bar.visibility = View.VISIBLE
    }

    private fun showForecastLoading() {
        main_recycler_progress_bar.visibility = View.VISIBLE
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun updateViews(weatherData: WeatherData) {
        main_name.text = weatherData.name
        main_date.text = weatherData.date
        main_icon.setImageDrawable(context?.getDrawable(weatherIconHighQ(weatherData.weather.first().icon)))
        main_temp.text = getString(R.string.temp, weatherData.main.temp)
        main_description.text = weatherData.weather.first().description
        main_humidity.text = getString(R.string.humidity, weatherData.main.humidity)
        main_wind.text = getString(R.string.wind, weatherData.wind.speed)
        main_clouds.text = getString(R.string.clouds, weatherData.clouds.all)
    }


    private fun setStyle(isDay: Boolean) {
        context?.let {
            val color: Int
            val colorSecondary: Int
            val background: Drawable?

            if (isDay) {
                color = ContextCompat.getColor(it, R.color.text)
                colorSecondary = ContextCompat.getColor(it, R.color.text_secondary)
                background = it.getDrawable(R.drawable.app_background)
            } else {
                color = ContextCompat.getColor(it, R.color.text_night)
                colorSecondary = ContextCompat.getColor(it, R.color.text_secondary_night)
                background = it.getDrawable(R.drawable.app_background_night)
            }

            setColorText(
                color,
                main_name,
                main_temp,
                main_humidity_label,
                main_wind_label,
                main_clouds_label
            )

            setColorText(
                colorSecondary,
                main_date,
                main_description,
                main_humidity,
                main_wind,
                main_clouds
            )

            main_container.background = background
        }
    }


}

