package com.napas.myplace.ui.places.usecase

import com.napas.myplace.base.database.dao.PlacesDao
import com.napas.myplace.ui.places.model.PlaceItem
import io.reactivex.Single
import javax.inject.Inject

class LoadFavoritePlacesUseCase @Inject constructor(
    private val placesDao: PlacesDao
) {
    fun execute(): Single<List<PlaceItem>> {
        return placesDao.getPlaces()
            .map {
                it.map { placeEntity ->
                    placeEntity.toPlaceItem
                }
            }
    }
}