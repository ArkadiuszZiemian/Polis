package com.avangarde.polis.di.module

import android.content.Context
import com.avangarde.polis.data.remote.GooglePlaces.GooglePlacesEndpoint
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import net.openid.appauth.AuthState
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
abstract class AppModule {

    companion object {

        @Singleton
        @Provides
        fun provideAuthState() = AuthState()

        @Singleton
        @Provides
        fun provideGooglePlacesSdk(appContext: Context): PlacesClient =
            Places.createClient(appContext)

        @Singleton
        @Provides
        fun provideGooglePlacesEndpoint(): GooglePlacesEndpoint {
            val baseUrl = "https://maps.googleapis.com/maps/api/place/"
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(GooglePlacesEndpoint::class.java)
        }

        @Singleton
        @Provides
        fun provideMoshi(): Moshi = Moshi.Builder().build()
    }
}
