package com.guido.weatherapp.models

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeoCode(
    @field:ColumnInfo(name = "latitude")
    @field:Expose
    @field:SerializedName("Latitude")
    var latitude: Double,
    @field:ColumnInfo(name = "longitude")
    @field:Expose
    @field:SerializedName("Longitude")
    var longitude: Double
)