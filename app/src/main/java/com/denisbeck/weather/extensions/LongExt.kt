package com.denisbeck.weather.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.isDay() = Calendar.getInstance().apply {
        time = Date(this@isDay * 1000)
    }.get(Calendar.HOUR_OF_DAY) in 7..21

fun Long.fullDate(): String = DateFormat.getDateInstance(DateFormat.FULL).format(this * 1000)

private var shortDateFormat = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
fun Long.shortDate(): String = shortDateFormat.format(this * 1000)

private var hoursDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
fun Long.time(): String = hoursDateFormat.format(this * 1000)

fun Long.verticalTime() = this.time().replace(":", "\n")
