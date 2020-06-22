package com.denisbeck.weather

import android.app.Application
import com.denisbeck.weather.di.networkModule
import com.denisbeck.weather.di.preferencesModule
import com.denisbeck.weather.di.viewModelsModule
import com.denisbeck.weather.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(networkModule, viewModelsModule, weatherModule, preferencesModule)
        }
    }

}