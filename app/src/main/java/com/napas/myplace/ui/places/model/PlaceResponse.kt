package com.napas.myplace.ui.places.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @field:[SerializedName("status")]
    val status: String = "",

    @field:[SerializedName("results")]
    val results: List<PlaceResult> = emptyList()
)