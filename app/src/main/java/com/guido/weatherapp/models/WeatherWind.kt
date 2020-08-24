package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherWind (

    @field:SerializedName("speed")
    @field:Expose
    var speed: Double,

    @field:SerializedName("deg")
    @field:Expose
    var deg: Int

)