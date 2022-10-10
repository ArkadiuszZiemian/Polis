package com.avangarde.polis.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CityInfoViewModel @Inject constructor (private val placesClient: PlacesClient) : ViewModel() {

}