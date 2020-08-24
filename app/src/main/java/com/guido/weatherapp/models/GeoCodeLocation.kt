package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeoCodeLocation (
    @field:SerializedName("DisplayPosition")
    @field:Expose
    var geocode: GeoCode,
    @field:SerializedName("Address")
    @field:Expose
    var address: GeoCodeAddress
)