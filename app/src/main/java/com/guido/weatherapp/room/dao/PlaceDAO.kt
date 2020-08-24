package com.guido.weatherapp.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.guido.weatherapp.models.Place

@Dao
interface PlaceDAO {
    @Query("SELECT * FROM place")
    fun getAll(): LiveData<List<Place>>

    @Query("SELECT * FROM place")
    fun get(): List<Place>

    @Insert
    fun insert(place: Place): Long

    @Query("DELETE FROM place WHERE id=:id")
    fun deleteById(id: Long)
}