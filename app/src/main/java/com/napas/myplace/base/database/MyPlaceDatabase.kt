package com.napas.myplace.base.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.napas.myplace.base.database.dao.PlacesDao
import com.napas.myplace.base.database.entity.PlaceEntity

@Database(
    entities = [
        PlaceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MyPlaceDatabase : RoomDatabase() {
    abstract fun placesDao(): PlacesDao
}