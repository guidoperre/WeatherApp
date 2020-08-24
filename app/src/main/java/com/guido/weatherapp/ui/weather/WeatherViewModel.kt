package com.guido.weatherapp.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guido.weatherapp.models.WeatherResponse
import com.guido.weatherapp.retrofit.HTTPRequest
import com.guido.weatherapp.retrofit.RetrofitNewInstance
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException

class WeatherViewModel : ViewModel() {

    private val dataList: MutableLiveData<WeatherResponse> = MutableLiveData()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val apiKey = "YOUR_API_KEY"
    private val weatherApiURL = "https://api.openweathermap.org/data/2.5/"

    fun getWeather(): LiveData<WeatherResponse> {
        return dataList
    }

    fun callWeather(latitude:Double, longitude:Double){
        uiScope.launch {
            val retrofit = RetrofitNewInstance()
            val service: HTTPRequest = retrofit.newInstance(weatherApiURL)

            val call = service.weatherAPI(apiKey, latitude, longitude, "metric")

            val response = withContext(Dispatchers.IO) {
                val response: Response<WeatherResponse>? = try {
                    call.execute()
                } catch (t: IllegalArgumentException) {
                    null
                } catch (t: IOException) {
                    null
                }
                response
            }

            if (response != null && response.isSuccessful) {
                val weather = response.body()
                if (weather != null)
                    dataList.value = weather
            }
        }
    }
}