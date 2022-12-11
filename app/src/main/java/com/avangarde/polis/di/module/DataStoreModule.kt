package com.avangarde.polis.di.module

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
abstract class DataStoreModule {
    companion object {
        @Provides
        fun provideDataStore(appContext: Application, prefsName: String): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
                migrations = listOf(SharedPreferencesMigration(appContext, prefsName)),
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                produceFile = { appContext.preferencesDataStoreFile(prefsName) }
            )
        }
    }
}
