package com.avangarde.polis.di

import android.accounts.AccountManager
import android.content.Context
import com.avangarde.polis.data.remote.api.GooglePlaces
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


const val PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place/"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAccountManager(@ApplicationContext appContext: Context): AccountManager =
        AccountManager.get(appContext)

    @Provides
    @Singleton
    fun provideGooglePlacesSdk(@ApplicationContext appContext: Context): PlacesClient =
        Places.createClient(appContext)

    @Provides
    @Singleton
    fun provideGooglePlacesApi(): GooglePlaces {
        return Retrofit.Builder()
            .baseUrl(PLACES_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GooglePlaces::class.java)
    }

}