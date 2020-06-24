package com.denisbeck.weather.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.insertDrawable(ref: Int) {
    Glide.with(context).load(ref).into(this)
}