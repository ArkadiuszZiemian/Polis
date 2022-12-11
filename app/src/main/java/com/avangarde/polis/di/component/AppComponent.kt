package com.avangarde.polis.di.component

import android.app.Application
import com.avangarde.polis.di.module.AppModule
import com.avangarde.polis.ui.fragment.onboarding.StarterFragment
import com.squareup.moshi.Moshi
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import net.openid.appauth.AuthState

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun getAppContext(): Application
    fun getAuthState(): AuthState
    fun getMoshi(): Moshi

    fun inject(fragment: StarterFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appContext: Application
        ): AppComponent
    }
}
