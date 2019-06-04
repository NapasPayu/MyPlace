package com.napas.myplace.ui.places.usecase

import com.napas.myplace.base.database.dao.PlacesDao
import com.napas.myplace.base.database.entity.PlaceEntity
import com.napas.myplace.ui.places.model.PlaceItem
import io.reactivex.Completable
import javax.inject.Inject

class UpdateFavoritePlaceUseCase @Inject constructor(
    private val placesDao: PlacesDao
) {
    fun execute(placeItem: PlaceItem, isFavorite: Boolean): Completable {
        val placeEntity = PlaceEntity(
            id = placeItem.id,
            icon = placeItem.icon,
            name = placeItem.name,
            website = placeItem.website
        )
        return when (isFavorite) {
            true -> placesDao.insertPlace(placeEntity)
            else -> placesDao.deletePlace(placeEntity)
        }
    }
}