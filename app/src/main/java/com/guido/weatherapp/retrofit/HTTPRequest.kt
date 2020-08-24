package com.guido.weatherapp.retrofit

import com.guido.weatherapp.models.AutoSuggestResponse
import com.guido.weatherapp.models.GeoCode
import com.guido.weatherapp.models.GeoCodeResponse
import com.guido.weatherapp.models.WeatherResponse


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HTTPRequest {

    @GET("suggest.json")
    fun suggestAPI(@Query("app_id") appID: String, @Query("app_code") appCode: String, @Query("query") query: String, @Query("country") countryCode: String): Call<AutoSuggestResponse>

    @GET("geocode.json")
    fun geocodeAPI(@Query("app_id") appID: String, @Query("app_code") appCode: String, @Query("locationid") locationID: String): Call<GeoCodeResponse>

    @GET("weather")
    fun weatherAPI(@Query("appid") appID: String, @Query("lat") lat: Double, @Query("lon") lon: Double,  @Query("units") units: String): Call<WeatherResponse>

}