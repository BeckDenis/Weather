package com.denisbeck.weather.extensions

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.denisbeck.weather.R

fun Fragment.showToast(message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(message: Int) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showErrorAndHideProgressBar(progressBar: View, message: String?) {
    progressBar.hide()
    showToast(message)
}