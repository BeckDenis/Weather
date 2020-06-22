package com.denisbeck.weather.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.denisbeck.weather.R

class Style(isDay: Boolean, private val context: Context) {
    private fun getColor(link: Int): Int = ContextCompat.getColor(context, link)
    private fun getDrawable(link: Int): Drawable? = ContextCompat.getDrawable(context, link)

    val colorPrimary = if (isDay) getColor(R.color.text) else getColor(R.color.text_night)
    val colorSecondary = if (isDay) {
        getColor(R.color.text_secondary)
    } else {
        getColor(R.color.text_secondary_night)
    }
    val background: Drawable? = if (isDay) {
            getDrawable(R.drawable.app_background_day)
        } else {
            getDrawable(R.drawable.app_background_night)
        }

}

fun setColorText(color: Int, vararg textViews: TextView) {
    textViews.forEach {
        it.setTextColor(color)
    }
}