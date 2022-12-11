package com.avangarde.polis.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.avangarde.polis.di.scope.DataStoreSingletonScoped
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import net.openid.appauth.AuthState

const val AUTH_KEY = "AUTH_STRING_KEY"
@DataStoreSingletonScoped
class AuthStateDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) :
    DataStoreIo<AuthState> {
    private val dataStoreKey = stringPreferencesKey(AUTH_KEY)
    override suspend fun read(): AuthState? {
        val preferences = dataStore.data.first()
        val prefStr = preferences[dataStoreKey]
        return prefStr?.let { AuthState.jsonDeserialize(it) }
    }

    override suspend fun save(value: AuthState) {
        dataStore.edit { preferences -> preferences[dataStoreKey] = value.jsonSerializeString() }
    }
}
