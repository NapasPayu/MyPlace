package com.napas.myplace.base.provider

import com.napas.myplace.base.service.PlacesService
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
data class PlacesServiceProvider @Inject constructor(
    @Named("API_KEY") private val apiKey: String,
    private val placesService: PlacesService
) {
    companion object {
        private const val DEFAULT_RADIUS_METERS = 500
    }

    fun findNearbyPlaces(latitude: Double, longitude: Double) = placesService.nearby(
        location = "$latitude,$longitude",
        radius = DEFAULT_RADIUS_METERS.toString(),
        apiKey = apiKey
    )
}