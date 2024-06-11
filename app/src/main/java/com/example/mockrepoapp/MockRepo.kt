package com.example.mockrepoapp

import android.app.Application
import com.example.mockrepoapp.utils.Constant.appContext
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MockRepo: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}