package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeoCodeResult (
    @field:SerializedName("Location")
    @field:Expose
    var location: GeoCodeLocation
)