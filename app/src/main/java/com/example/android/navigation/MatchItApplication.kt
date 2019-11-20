package com.example.android.navigation

import android.app.Application
import timber.log.Timber

class MatchItApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}