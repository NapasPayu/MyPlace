package com.napas.myplace.ui.places

import com.napas.myplace.base.SchedulersFacade
import com.napas.myplace.ui.places.usecase.*
import dagger.Module
import dagger.Provides

@Module
class PlacesModule {
    @Provides
    fun providePlacesViewModelFactory(
        loadNearbyPlacesUseCase: LoadNearbyPlacesUseCase,
        loadFavoritePlacesUseCase: LoadFavoritePlacesUseCase,
        updateFavoritePlaceUseCase: UpdateFavoritePlaceUseCase,
        schedulersFacade: SchedulersFacade
    ) = PlacesViewModelFactory(
        loadNearbyPlacesUseCase,
        loadFavoritePlacesUseCase,
        updateFavoritePlaceUseCase,
        schedulersFacade
    )
}