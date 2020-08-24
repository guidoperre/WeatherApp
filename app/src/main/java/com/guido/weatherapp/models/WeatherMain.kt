package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class WeatherMain (

    @field:SerializedName("temp")
    @field:Expose
    var temp: Double,

    @field:SerializedName("feels_like")
    @field:Expose
    var feelsLike: Double,

    @field:SerializedName("temp_min")
    @field:Expose
    var tempMin: Double,

    @field:SerializedName("temp_max")
    @field:Expose
    var tempMax: Double,

    @field:SerializedName("pressure")
    @field:Expose
    var pressure: Int,

    @field:SerializedName("humidity")
    @field:Expose
    var humidity: Int

)