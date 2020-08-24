package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeoCodeView (
    @field:SerializedName("Result")
    @field:Expose
    var result: List<GeoCodeResult>
)