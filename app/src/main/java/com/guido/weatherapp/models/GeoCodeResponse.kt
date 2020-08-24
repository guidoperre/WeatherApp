package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeoCodeResponse (
    @field:SerializedName("Response")
    @field:Expose
    var response: GeoCodeResponseInfo
)