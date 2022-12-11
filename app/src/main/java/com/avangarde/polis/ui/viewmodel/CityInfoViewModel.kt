package com.avangarde.polis.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avangarde.polis.data.local.datastore.AuthStateDataStore
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthState

class CityInfoViewModel @Inject constructor(
    private val datastore: AuthStateDataStore,
    private var authState: AuthState
) : ViewModel() {

    fun signOut() {
        viewModelScope.launch {
            authState = AuthState()
            withContext(Dispatchers.IO) { datastore.save(authState) }
        }
    }
}
