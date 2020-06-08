package com.denisbeck.weather.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.isDay() = Calendar.getInstance().apply {
    timeZone = TimeZone.getTimeZone("GMT")
    time = Date(this@isDay * 1000)
}.get(Calendar.HOUR_OF_DAY) in 7..21

fun Long.fullDate(): String = DateFormat.getDateInstance(DateFormat.FULL).format(this * 1000)

fun Long.shortDate(): String = SimpleDateFormat("EEE, MMM d", Locale.getDefault()).apply {
    timeZone = TimeZone.getTimeZone("GMT")
}.format(this * 1000)

fun Long.time(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
    timeZone = TimeZone.getTimeZone("GMT")
}.format(this * 1000)

fun Long.verticalTime() = this.time().replace(":", "\n")
