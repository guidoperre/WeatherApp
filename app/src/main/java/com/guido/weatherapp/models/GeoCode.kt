package com.guido.weatherapp.models

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeoCode(
    @field:Expose
    @field:SerializedName("latitude")
    var latitude: Double,
    @field:Expose
    @field:SerializedName("longitude")
    var longitude: Double
)