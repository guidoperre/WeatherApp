package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class WeatherResponse (

    @field:SerializedName("coord")
    @field:Expose
    var coord: WeatherCord,

    @field:SerializedName("weather")
    @field:Expose
    var weather: List<Weather>,

    @field:SerializedName("base")
    @field:Expose
    var base: String,

    @field:SerializedName("main")
    @field:Expose
    var main: WeatherMain,

    @field:SerializedName("visibility")
    @field:Expose
    var visibility: Int,

    @field:SerializedName("wind")
    @field:Expose
    var weatherWind: WeatherWind,

    @field:SerializedName("clouds")
    @field:Expose
    var clouds: WeatherClouds,

    @field:SerializedName("dt")
    @field:Expose
    var dt: Int,

    @field:SerializedName("sys")
    @field:Expose
    var sys: WeatherSys,

    @field:SerializedName("timezone")
    @field:Expose
    var timezone: Int,

    @field:SerializedName("id")
    @field:Expose
    var id: Int,

    @field:SerializedName("name")
    @field:Expose
    var name: String,

    @field:SerializedName("cod")
    @field:Expose
    var cod: Int

)