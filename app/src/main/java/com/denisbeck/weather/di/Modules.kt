package com.denisbeck.weather.di

import com.denisbeck.weather.networking.*
import com.denisbeck.weather.persistence.Preferences
import com.denisbeck.weather.repository.WeatherRepository
import com.denisbeck.weather.screens.main.MainViewModel
import com.denisbeck.weather.screens.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val networkModule = module {
    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get(), get()) }
    factory { provideCurrentWeatherApi(get()) }
    factory { provideLoggingInterceptor() }
    factory { provideForecastApi(get()) }
    single { provideRetrofit(get()) }
    factory { ResponseHandler() }
}

val viewModelsModule = module {
    viewModel{MainViewModel(get())}
    viewModel{SearchViewModel(get())}
}

val weatherModule = module {
    factory { WeatherRepository(get(), get(), get()) }
}

val preferencesModule = module {
    factory { Preferences(androidContext()) }
}