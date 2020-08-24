package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeoCodeAddress (
    @field:SerializedName("Label")
    @field:Expose
    var label: String
)