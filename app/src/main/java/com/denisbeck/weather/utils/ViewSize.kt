package com.denisbeck.weather.utils

import android.view.View

fun changeMeasureSize(measureSpec: Int, divider: Double) = View.MeasureSpec.makeMeasureSpec(
(View.MeasureSpec.getSize(measureSpec) * divider).toInt(), View.MeasureSpec.EXACTLY
)