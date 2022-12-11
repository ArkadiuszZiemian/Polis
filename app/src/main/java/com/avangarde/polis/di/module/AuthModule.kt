package com.avangarde.polis.di.module

import android.app.Application
import android.net.Uri
import com.avangarde.polis.configuration.Idp
import com.avangarde.polis.configuration.IdpWithPkce
import dagger.Module
import dagger.Provides
import net.openid.appauth.*

@Module
abstract class AuthModule {

    companion object {
        @Provides
        fun provideAuthorizationServiceConfiguration(idpConfig: Idp) =
            AuthorizationServiceConfiguration(
                Uri.parse(idpConfig.authorizationUri),
                Uri.parse(idpConfig.tokenUri)
            )

        @Provides
        fun provideAuthorizationRequest(
            idpConfig: Idp,
            serviceConfig: AuthorizationServiceConfiguration,
        ): AuthorizationRequest {
            val codeVerifier: String
            val authRequest = AuthorizationRequest.Builder(
                serviceConfig,
                idpConfig.clientId,
                ResponseTypeValues.CODE,
                Uri.parse(idpConfig.redirectUri)
            )
            if (idpConfig is IdpWithPkce) {
                codeVerifier = idpConfig.getCodeVerifier()
                authRequest.setCodeVerifier(
                    codeVerifier,
                    idpConfig.getCodeChallenge(codeVerifier),
                    idpConfig.codeChallengeMethod
                )
            }
            return authRequest
                .setScope(idpConfig.permissionScope)
                .build()
        }

        @Provides
        fun provideAuthService(appContext: Application) = AuthorizationService(
            appContext,
            AppAuthConfiguration.DEFAULT
        )
    }
}
