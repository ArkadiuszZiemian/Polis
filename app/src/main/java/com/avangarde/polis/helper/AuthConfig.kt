package com.avangarde.polis.helper

import com.avangarde.polis.configuration.Idp
import com.avangarde.polis.di.component.AppComponent
import com.avangarde.polis.di.component.DaggerAuthComponent
import javax.inject.Inject
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration

class AuthConfig(idpConfig: Idp, appComponent: AppComponent) {
    @Inject
    lateinit var authService: AuthorizationService

    @Inject
    lateinit var authRequest: AuthorizationRequest

    @Inject
    lateinit var authServiceConf: AuthorizationServiceConfiguration

    init {
        val authComponent = DaggerAuthComponent.factory().create(idpConfig, appComponent)
        authComponent.inject(this)
    }
}
