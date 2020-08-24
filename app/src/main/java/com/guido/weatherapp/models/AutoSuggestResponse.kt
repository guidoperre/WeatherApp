package com.guido.weatherapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AutoSuggestResponse (
    @field:SerializedName("suggestions")
    @field:Expose
    var response: List<AutoSuggest>
)