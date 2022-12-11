package com.avangarde.polis.di.component

import com.avangarde.polis.di.scope.ScreenScoped
import com.avangarde.polis.ui.viewmodel.CityInfoViewModel
import com.avangarde.polis.ui.viewmodel.StarterViewModel
import dagger.Component

@ScreenScoped
@Component(dependencies = [AppComponent::class, DataStoreComponent::class])
interface ScreenComponent {

    fun getStarterViewModel(): StarterViewModel.Factory
    fun getCityInfoViewModel(): CityInfoViewModel

    @Component.Factory
    interface Factory {
        fun create(
            appComponent: AppComponent,
            dataStoreComponent: DataStoreComponent
        ): ScreenComponent
    }
}
