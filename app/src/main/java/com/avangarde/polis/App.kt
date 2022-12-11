package com.avangarde.polis

import android.app.Application
import com.avangarde.polis.BuildConfig.GOOGLE_PLACES_API_KEY
import com.avangarde.polis.di.component.AppComponent
import com.avangarde.polis.di.component.DaggerAppComponent
import com.avangarde.polis.di.component.DaggerDataStoreComponent
import com.avangarde.polis.di.component.DataStoreComponent
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.libraries.places.api.Places
import timber.log.Timber

const val DATASTORE_FILE_NAME = "AUTH_SETTINGS"

class App : Application() {
    private lateinit var appComponent: AppComponent
    private lateinit var authDataStoreComponent: DataStoreComponent
    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, GOOGLE_PLACES_API_KEY)
        Fresco.initialize(this)
        Timber.plant(Timber.DebugTree())
        appComponent = DaggerAppComponent.factory().create(this)
        authDataStoreComponent =
            DaggerDataStoreComponent.factory().create(DATASTORE_FILE_NAME, appComponent)
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }

    fun getAuthDataStoreComponent(): DataStoreComponent {
        return authDataStoreComponent
    }
}
