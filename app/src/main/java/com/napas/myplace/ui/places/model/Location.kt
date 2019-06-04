package com.napas.myplace.ui.places.model

import com.google.gson.annotations.SerializedName

data class Location(
    @field:[SerializedName("lat")]
    val lat: Double = 0.0,

    @field:[SerializedName("lng")]
    val lng: Double = 0.0
)