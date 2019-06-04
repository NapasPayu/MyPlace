package com.napas.myplace.ui.places.model

data class PlaceItem(
    val id: String = "",
    val icon: String = "",
    val name: String = "",
    val website: String = "",
    val isFavorite: Boolean = false
)