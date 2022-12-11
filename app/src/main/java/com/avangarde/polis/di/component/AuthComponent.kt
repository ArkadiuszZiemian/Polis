package com.avangarde.polis.di.component

import com.avangarde.polis.configuration.Idp
import com.avangarde.polis.di.module.AuthModule
import com.avangarde.polis.di.scope.ScreenScoped
import com.avangarde.polis.helper.AuthConfig
import dagger.BindsInstance
import dagger.Component

@ScreenScoped
@Component(dependencies = [AppComponent::class], modules = [AuthModule::class])
interface AuthComponent {

    fun inject(authConfig: AuthConfig)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance idpConfig: Idp,
            appComponent: AppComponent
        ): AuthComponent
    }
}
