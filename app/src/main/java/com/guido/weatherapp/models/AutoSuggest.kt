package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AutoSuggest (
    @field:SerializedName("label")
    @field:Expose
    var label: String,
    @field:SerializedName("locationId")
    @field:Expose
    var locationId: String
)