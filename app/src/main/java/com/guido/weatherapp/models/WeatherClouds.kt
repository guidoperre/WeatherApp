package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherClouds (
    @field:SerializedName("all")
    @field:Expose
    var all: Int
)