package com.napas.myplace.ui.places.usecase

import com.napas.myplace.base.provider.PlacesServiceProvider
import com.napas.myplace.base.database.dao.PlacesDao
import com.napas.myplace.ui.places.model.PlaceItem
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import javax.inject.Inject

class LoadNearbyPlacesUseCase @Inject constructor(
    private val placesServiceProvider: PlacesServiceProvider,
    private val placesDao: PlacesDao
) {
    fun execute(latitude: Double, longitude: Double): Single<List<PlaceItem>> {
        return Singles.zip(
            placesServiceProvider.findNearbyPlaces(latitude, longitude),
            placesDao.getPlaces()
        ) { nearbyPlaceResponse, favoritePlaceEntities ->
            val favoritePlaceIds = favoritePlaceEntities.map { it.id }
            nearbyPlaceResponse.results.map {
                PlaceItem(
                    id = it.placeId,
                    icon = it.icon,
                    name = it.name,
                    website = it.website,
                    isFavorite = favoritePlaceIds.contains(it.placeId)
                )
            }
        }
    }
}