package com.denisbeck.weather.extensions

import android.content.Context
import android.location.Geocoder
import android.location.Location
import java.util.*

fun Location.toCity(context: Context?): String = Geocoder(context, Locale.getDefault())
    .getFromLocation(this.latitude, this.longitude, 1)[0]
    .locality