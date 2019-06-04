package com.napas.myplace.base.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.napas.myplace.base.database.entity.PlaceEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class PlacesDao {
    @Query("SELECT * FROM places")
    abstract fun getPlaces(): Single<List<PlaceEntity>>

    @Insert(onConflict = REPLACE)
    abstract fun insertPlace(placeEntity: PlaceEntity): Completable

    @Delete
    abstract fun deletePlace(placeEntity: PlaceEntity): Completable
}