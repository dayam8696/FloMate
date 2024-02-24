package com.example.flomate

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {

    companion object {
        var instance: ApplicationClass? = null
    }

    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Disable dark mode
        instance = this

        super.onCreate()
    }

}