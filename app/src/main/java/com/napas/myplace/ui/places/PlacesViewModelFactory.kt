package com.napas.myplace.ui.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.napas.myplace.base.SchedulersFacade
import com.napas.myplace.ui.places.usecase.*

class PlacesViewModelFactory(
    private val loadNearbyPlacesUseCase: LoadNearbyPlacesUseCase,
    private val loadFavoritePlacesUseCase: LoadFavoritePlacesUseCase,
    private val updateFavoritePlaceUseCase: UpdateFavoritePlaceUseCase,
    private val schedulersFacade: SchedulersFacade
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlacesViewModel::class.java)) {
            return PlacesViewModel(
                loadNearbyPlacesUseCase,
                loadFavoritePlacesUseCase,
                updateFavoritePlaceUseCase,
                schedulersFacade
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}