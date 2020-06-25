package com.denisbeck.weather.utils

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.denisbeck.weather.R

class Style(isDay: Boolean, private val context: Context) {
    private fun getColor(link: Int): Int = ContextCompat.getColor(context, link)

    val colorPrimary = if (isDay) getColor(R.color.text_day) else getColor(R.color.text_night)

    val colorSecondary =
        if (isDay) getColor(R.color.text_secondary_day) else getColor(R.color.text_secondary_night)

    val background: Int =
        if (isDay) R.drawable.app_background_day else R.drawable.app_background_night


    val addIcon = if (isDay) R.drawable.ic_add_day else R.drawable.ic_add_night
    val backIcon = if (isDay) R.drawable.ic_back_day else R.drawable.ic_back_night

}

fun setColorText(color: Int, vararg textViews: TextView) {
    textViews.forEach {
        it.setTextColor(color)
    }
}