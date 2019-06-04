package com.napas.myplace.base.service

import com.napas.myplace.ui.places.model.PlaceResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesService {
    @GET("nearbysearch/json")
    fun nearby(
        @Query("location") location: String,
        @Query("radius") radius: String,
        @Query("key") apiKey: String
    ): Single<PlaceResponse>
}