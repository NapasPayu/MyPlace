package com.napas.myplace.ui.places.model

import com.google.gson.annotations.SerializedName

data class PlaceResult(
    @field:[SerializedName("place_id")]
    val placeId: String = "",

    @field:[SerializedName("geometry")]
    val geometry: Geometry = Geometry(),

    @field:[SerializedName("icon")]
    val icon: String = "",

    @field:[SerializedName("name")]
    val name: String = "",

    @field:[SerializedName("vicinity")]
    val vicinity: String = "",

    @field:[SerializedName("website")]
    val website: String = "",

    @field:[SerializedName("rating")]
    val rating: Double = 0.0
)