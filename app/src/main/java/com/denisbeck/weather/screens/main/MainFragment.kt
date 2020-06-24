package com.denisbeck.weather.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.denisbeck.weather.R
import com.denisbeck.weather.extensions.*
import com.denisbeck.weather.models.ForecastData
import com.denisbeck.weather.models.WeatherData
import com.denisbeck.weather.networking.*
import com.denisbeck.weather.persistence.Preferences
import com.denisbeck.weather.utils.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private val preferences: Preferences by inject()
    private val viewModel: MainViewModel by viewModel()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main_search.setOnClickListener { goToSearchFragment() }
        getLocation()
        viewModel.run {
            weather.observe(viewLifecycleOwner, Observer { statusWeatherActions(it) })
            forecast.observe(viewLifecycleOwner, Observer { statusForecastActions(it) })
        }
    }

    private fun goToSearchFragment() {
        val action = MainFragmentDirections.actionMainFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun statusWeatherActions(it: Resource<WeatherData>) {
        when (it.status) {
            Status.SUCCESS -> showWeather(it)
            Status.ERROR -> showErrorAndHideProgressBar(main_weather_progress_bar, it.message)
            Status.LOADING -> main_weather_progress_bar.show()
        }
    }

    private fun showWeather(it: Resource<WeatherData>) {
        main_weather_progress_bar.visibility = View.GONE
        it.data?.let {weatherData ->
            setStyle((weatherData.dt + weatherData.timezone).isDay())
            updateViews(weatherData)
            updatePreferences(weatherData.name)
        }
    }

    private fun updatePreferences(location: String) {
        preferences.storeSelectedLocation(location)
        preferences.storeSavedLocations(location)
    }

    private fun statusForecastActions(it: Resource<ForecastData>) {
        when (it.status) {
            Status.SUCCESS -> updateRecyclerAndHideProgressBar(it)
            Status.ERROR -> showErrorAndHideProgressBar(main_recycler_progress_bar, it.message)
            Status.LOADING -> main_recycler_progress_bar.show()
        }
    }

    private fun updateRecyclerAndHideProgressBar(it: Resource<ForecastData>) {
        updateRecycler(it.data)
        main_recycler_progress_bar.hide()
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

    private fun updateViews(weatherData: WeatherData) {
        main_name.text = weatherData.name
        main_date.text = weatherData.date
        main_icon.setImageDrawable(context?.getDrawable(weatherIconHighQ(weatherData.weather.first().icon)))
        main_temp.text = getString(R.string.temp, weatherData.main.tempInt)
        main_description.text = weatherData.weather.first().description
        main_humidity.text = getString(R.string.humidity, weatherData.main.humidity)
        main_wind.text = getString(R.string.wind, weatherData.wind.speed)
        main_clouds.text = getString(R.string.clouds, weatherData.clouds.all)
    }


    private fun setStyle(isDay: Boolean) {
        val style = Style(isDay, requireContext())
        setColorText(
            style.colorPrimary,
            main_name, main_temp, main_humidity_label, main_wind_label, main_clouds_label
        )
        setColorText(
            style.colorSecondary,
            main_date, main_description, main_humidity, main_wind, main_clouds
        )
        main_container.setBackgroundResource(style.background)
        main_search.insertDrawable(style.addIcon)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode && allPermissionsGranted(grantResults)) {
            getLocation()
        }
    }


    private fun getLocation() {
        if (preferences.getAutoLocate()) {
            getLastLocation()
        } else {
            viewModel.updateLocation(preferences.getSelectedLocation())
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        checkPermissions(requireContext()) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        viewModel.updateLocation(location.toCity(context))
                    } ?: offGps()
                }
                .addOnFailureListener {
                    viewModel.updateLocation(preferences.getSelectedLocation())
                    showToast(it.localizedMessage)
                }
        }
    }

    private fun offGps() {
        showToast(R.string.off_gps_message)
        viewModel.updateLocation(preferences.getSelectedLocation())
    }

}

