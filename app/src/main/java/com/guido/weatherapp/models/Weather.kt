package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Weather (
    @field:SerializedName("id")
    @field:Expose
    var id: Int,

    @field:SerializedName("main")
    @field:Expose
    var main: String,

    @field:SerializedName("description")
    @field:Expose
    var description: String,

    @field:SerializedName("icon")
    @field:Expose
    var icon: String

)