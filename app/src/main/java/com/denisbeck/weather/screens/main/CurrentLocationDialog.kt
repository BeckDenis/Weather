package com.denisbeck.weather.screens.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.denisbeck.weather.repository.getForecastRepository
import com.denisbeck.weather.repository.getWeatherRepository

class CurrentLocationDialog : DialogFragment() {

    companion object {
        private val TAG = CurrentLocationDialog::class.java.simpleName
    }

    private val viewModel by activityViewModels<MainViewModel> {
        MainViewModelFactory(getWeatherRepository(), getForecastRepository())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(context)
            .setTitle("Are you here now?")
            .setMessage(viewModel.lastUserLocation)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                viewModel.lastUserLocation?.let {
                    viewModel.updateLastLocation(it)
                }
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->

            }
            .create()
    }
}