package com.denisbeck.weather.utils

import com.denisbeck.weather.R

fun weatherIconHighQ(iconId: String) = when (iconId) {
    "01d" -> R.drawable.i01d_high
    "01n" -> R.drawable.i01n_high
    "02d" -> R.drawable.i02d_high
    "02n" -> R.drawable.i02n_high
    "03d", "03n", "04d", "04n" -> R.drawable.i03_high
    "09d", "09n" -> R.drawable.i09_high
    "10d" -> R.drawable.i10d_high
    "10n" -> R.drawable.i10n_high
    "11d" -> R.drawable.i11d_high
    "11n" -> R.drawable.i11n_high
    "13d", "13n" -> R.drawable.i13_high
    else -> R.drawable.i01d_high
}

fun weatherIconLowQ(iconId: String) = when (iconId) {
    "01d" -> R.drawable.i01d_low
    "01n" -> R.drawable.i01n_low
    "02d" -> R.drawable.i02d_low
    "02n" -> R.drawable.i02n_low
    "03d", "03n", "04d", "04n" -> R.drawable.i03_low
    "09d", "09n" -> R.drawable.i09_low
    "10d" -> R.drawable.i10d_low
    "10n" -> R.drawable.i10n_low
    "11d" -> R.drawable.i11d_low
    "11n" -> R.drawable.i11n_low
    "13d", "13n" -> R.drawable.i13_low
    else -> R.drawable.i01d_low
}