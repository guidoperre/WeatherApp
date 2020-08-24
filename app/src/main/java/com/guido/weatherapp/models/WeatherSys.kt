package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherSys (
    @field:SerializedName("type")
    @field:Expose
    var type: Int,

    @field:SerializedName("id")
    @field:Expose
    var id: Int,

    @field:SerializedName("country")
    @field:Expose
    var country: String,

    @field:SerializedName("sunrise")
    @field:Expose
    var sunrise: Int,

    @field:SerializedName("sunset")
    @field:Expose
    var sunset: Int

)