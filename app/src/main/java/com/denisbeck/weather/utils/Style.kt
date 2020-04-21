package com.denisbeck.weather.utils

import android.widget.TextView

fun setColorText(color: Int, vararg textViews: TextView) {
    textViews.forEach {
        it.setTextColor(color)
    }
}