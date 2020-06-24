package com.denisbeck.weather.screens.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.denisbeck.weather.R
import com.denisbeck.weather.extensions.insertDrawable
import com.denisbeck.weather.extensions.time
import com.denisbeck.weather.extensions.shortDate
import com.denisbeck.weather.models.DayWeather
import com.denisbeck.weather.models.ForecastData
import com.denisbeck.weather.utils.weatherIconLowQ
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastAdapter(
    private val forecast: ForecastData,
    val onClickListener: (DayWeather) -> Unit
) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_forecast, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = forecast.list[position]
        holder.view.run {
            item_forecast_date.text = (weather.dt + forecast.city.timezone).shortDate()
            item_forecast_time.text = (weather.dt + forecast.city.timezone).time()
            item_forecast_icon.insertDrawable(weatherIconLowQ(weather.weather.first().icon))
            item_forecast_temp.text = context.getString(R.string.temp, weather.main.tempInt)
            setOnClickListener { onClickListener(weather) }
        }
    }

    override fun getItemCount(): Int = forecast.list.size

}