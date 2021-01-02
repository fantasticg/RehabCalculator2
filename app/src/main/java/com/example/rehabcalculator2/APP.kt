package com.example.rehabcalculator2

import android.app.Application
import com.example.rehabcalculator2.utils.AppPreferences

class App : Application() {
    companion object {
        lateinit var  prefs : AppPreferences
    }

    override fun onCreate() {
        prefs = AppPreferences(applicationContext)
        super.onCreate()
    }
}