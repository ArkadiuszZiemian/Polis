package com.avangarde.polis.ui.viewmodel

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.avangarde.polis.configuration.Idp
import com.avangarde.polis.data.local.datastore.AuthStateDataStore
import com.avangarde.polis.di.component.AppComponent
import com.avangarde.polis.helper.AuthConfig
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.ClientSecretBasic
import timber.log.Timber

class StarterViewModel @AssistedInject constructor(
    private var authState: AuthState,
    private val dataStore: AuthStateDataStore,
    @Assisted private val appComponent: AppComponent
) : ViewModel() {
    lateinit var secret: String
    private lateinit var authConfig: AuthConfig
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun executeAuthLauncher(
        authLauncher: ActivityResultLauncher<Intent>,
        packageManager: PackageManager,
        idpConfig: Idp
    ) {
        authConfig = AuthConfig(idpConfig, appComponent)
        authState = AuthState(authConfig.authServiceConf)
        val authIntent =
            authConfig.authService.getAuthorizationRequestIntent(authConfig.authRequest)
        authIntent.resolveActivity(packageManager)?.let { authLauncher.launch(authIntent) }
            ?: throw IllegalStateException("No Intent available to handle the code retrieval")
    }

    fun launchAuthRequest(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK)
            when (val e = AuthorizationException.fromIntent(result.data)) {
                null -> {
                    val authCode = AuthorizationResponse.fromIntent(result.data!!)
                    authState.update(authCode, null)
                    obtainAccessToken(authCode)
                }
                else -> throw e
            }
        else
            Timber.e("Unsuccessful request: ${result.resultCode}")
    }

    private fun obtainAccessToken(authCode: AuthorizationResponse?) {
        if (authCode == null)
            throw IllegalStateException("Authorization code wasn't retrieved successfully")
        else {
            authConfig.authService.performTokenRequest(
                authCode.createTokenExchangeRequest(), ClientSecretBasic(secret)
            ) { tokenResponse, e ->
                if (e != null) throw e
                if (tokenResponse != null) {
                    authState.update(tokenResponse, null)
                    viewModelScope.launch { dataStore.save(authState) }
                    _isLoggedIn.value = true
                } else throw IllegalStateException("Response token is empty")
            }
        }
    }

    fun getLoginEmail(): String? {
        var email: String? = null
        runBlocking {
            val authState = dataStore.read()
            val idToken = authState?.idToken
            idToken?.let {
                val jwt = JWT(it)
                email = jwt.getClaim("email").asString()
            } ?: return@runBlocking
        }
        return email
    }

    @AssistedFactory
    interface Factory {
        fun create(appComponent: AppComponent): StarterViewModel
    }
}