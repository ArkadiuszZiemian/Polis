package com.avangarde.polis

import android.app.Application
import com.avangarde.polis.BuildConfig.GOOGLE_PLACES_API_KEY
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, GOOGLE_PLACES_API_KEY)
        Fresco.initialize(this)
        Timber.plant(Timber.DebugTree())
    }
}
