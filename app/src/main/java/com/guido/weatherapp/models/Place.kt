package com.guido.weatherapp.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place")
class Place (
    @field:PrimaryKey(autoGenerate = true)
    var id: Long,
    @field:ColumnInfo(name = "label")
    var label: String,
    @Embedded
    var location: GeoCode
)