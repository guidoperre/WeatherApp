package com.guido.weatherapp.application

import android.app.Application
import com.facebook.stetho.Stetho

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}