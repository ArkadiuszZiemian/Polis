package com.avangarde.polis.di.component

import com.avangarde.polis.configuration.Idp
import com.avangarde.polis.di.module.AuthModule
import com.avangarde.polis.di.scope.ScreenScoped
import dagger.BindsInstance
import dagger.Component
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration

@ScreenScoped
@Component(dependencies = [AppComponent::class], modules = [AuthModule::class])
interface AuthComponent {

    fun getAuthServConf(): AuthorizationServiceConfiguration
    fun getAuthReq(): AuthorizationRequest
    fun getAuthServ(): AuthorizationService

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance idpConfig: Idp,
            appComponent: AppComponent
        ): AuthComponent
    }
}
