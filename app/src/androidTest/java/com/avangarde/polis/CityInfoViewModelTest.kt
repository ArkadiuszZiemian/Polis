package com.avangarde.polis

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.avangarde.polis.data.local.datastore.AuthStateDataStore
import com.avangarde.polis.ui.viewmodel.CityInfoViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import net.openid.appauth.AuthState
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class CityInfoViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val testContext = ApplicationProvider.getApplicationContext<Context>()
    private val prefsName = DATASTORE_FILE_NAME
    private val datastore = PreferenceDataStoreFactory.create(
        scope = testScope,
        produceFile = { testContext.preferencesDataStoreFile(prefsName) }
    )
    private val authDataStore = AuthStateDataStore(datastore)
    private val depAuthState = AuthState()
    private val viewModel = CityInfoViewModel(authDataStore, depAuthState)

    @Test
    fun isAuthStateCleared_true() {
        var readState: AuthState? = null
        testScope.runTest {
            viewModel.signOut()
            readState = authDataStore.read()
        }
        val authState = object : AuthState() {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is AuthState) return false
                if (
                    accessToken == other.accessToken &&
                    accessTokenExpirationTime == other.accessTokenExpirationTime &&
                    authorizationServiceConfiguration == other.authorizationServiceConfiguration &&
                    idToken == other.idToken &&
                    lastAuthorizationResponse == other.lastAuthorizationResponse &&
                    lastTokenResponse == other.lastTokenResponse &&
                    needsTokenRefresh == other.needsTokenRefresh &&
                    refreshToken == other.refreshToken &&
                    scope == other.scope &&
                    scopeSet == other.scopeSet
                ) return true
                return false
            }
        }
        assertThat(authState).isEqualTo(readState)
    }
}
