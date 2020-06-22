package com.denisbeck.weather.screens.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs

import com.denisbeck.weather.R
import com.denisbeck.weather.extensions.isDay
import com.denisbeck.weather.extensions.verticalTime
import com.denisbeck.weather.utils.setColorText
import com.denisbeck.weather.utils.weatherIconHighQ
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showWeather()
    }

    private fun showWeather() {
        setStyle((args.weather.dt + args.city.timezone).isDay())
        updateViews()
    }


    private fun updateViews() {
        detail_icon.setImageDrawable(context?.getDrawable(weatherIconHighQ(args.weather.weather.first().icon)))
        detail_temp.text = getString(R.string.temp, args.weather.main.temp)
        detail_description.text = args.weather.weather.first().description
        detail_humidity.text = getString(R.string.humidity_detail, args.weather.main.humidity)
        detail_wind.text = getString(R.string.wind_detail, args.weather.wind.speed)
        detail_clouds.text = getString(R.string.clouds_detail, args.weather.clouds.all)
        detail_time.text = (args.weather.dt + args.city.timezone).verticalTime()
    }

    private fun setStyle(isDay: Boolean) {
        context?.let {
            val color: Int
            val background: Drawable?

            if (isDay) {
                color = ContextCompat.getColor(it, R.color.text)
                background = it.getDrawable(R.drawable.app_background_day)
            } else {
                color = ContextCompat.getColor(it, R.color.text_night)
                background = it.getDrawable(R.drawable.app_background_night)
            }

            setColorText(
                color,
                detail_temp,
                detail_time,
                detail_description,
                detail_humidity,
                detail_wind,
                detail_clouds
            )

            detail_container.background = background
        }
    }
}
