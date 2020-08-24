package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class WeatherCord (

    @field:SerializedName("lon")
    @field:Expose
    var lon: Double,

    @field:SerializedName("lat")
    @field:Expose
    var lat: Double

)