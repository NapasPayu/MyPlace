package com.napas.myplace.base.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.napas.myplace.ui.places.model.PlaceItem

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey
    val id: String = "",

    @ColumnInfo(name = "icon")
    val icon: String = "",

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "website")
    val website: String = ""
) {
    val toPlaceItem
        get() = PlaceItem(
            id = id,
            icon = icon,
            name = name,
            website = website,
            isFavorite = true
        )
}