package com.avangarde.polis.di.component

import com.avangarde.polis.data.local.datastore.AuthStateDataStore
import com.avangarde.polis.di.module.DataStoreModule
import com.avangarde.polis.di.scope.DataStoreSingletonScoped
import com.avangarde.polis.ui.fragment.onboarding.StarterFragment
import dagger.BindsInstance
import dagger.Component

@DataStoreSingletonScoped
@Component(dependencies = [AppComponent::class], modules = [DataStoreModule::class])
interface DataStoreComponent {

    fun getAuthStateDataStore(): AuthStateDataStore

    fun inject(fragment: StarterFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance fileName: String,
            appComponent: AppComponent
        ): DataStoreComponent
    }
}
