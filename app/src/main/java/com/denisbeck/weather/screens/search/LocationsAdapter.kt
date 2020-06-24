package com.denisbeck.weather.screens.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.denisbeck.weather.R
import com.denisbeck.weather.extensions.insertDrawable
import com.denisbeck.weather.models.WeatherData
import com.denisbeck.weather.utils.weatherIconLowQ
import kotlinx.android.synthetic.main.item_search.view.*

class LocationsAdapter(
    val selectClickListener: (String) -> Unit,
    val deleteClickListener: (WeatherData) -> Unit
) : ListAdapter<WeatherData, LocationsAdapter.ViewHolder>(WeatherDiffCallback()) {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    companion object {
        private val TAG = LocationsAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.run {
            item_search_name.text = item.name
            item_search_image.insertDrawable(weatherIconLowQ(item.weather.first().icon))
            item_search_weather.text = context.getString(
                R.string.search_weather,
                item.weather.first().main,
                item.main.tempInt
            )
            item_search_delete.setOnClickListener {
                deleteClickListener(item)
            }
            setOnClickListener { selectClickListener(item.name) }
        }
    }
}

class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherData>() {
    override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
        return oldItem == newItem
    }
}