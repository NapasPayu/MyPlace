package com.napas.myplace.ui.places.model

import com.google.gson.annotations.SerializedName

data class Geometry(
    @field:[SerializedName("location")]
    val location: Location = Location()
)