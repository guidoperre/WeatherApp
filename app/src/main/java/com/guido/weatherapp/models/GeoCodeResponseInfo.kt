package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeoCodeResponseInfo (
    @field:SerializedName("View")
    @field:Expose
    var view: List<GeoCodeView>
)