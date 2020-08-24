package com.guido.weatherapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guido.weatherapp.models.*
import com.guido.weatherapp.retrofit.HTTPRequest
import com.guido.weatherapp.retrofit.RetrofitNewInstance
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

internal class SearchViewModel : ViewModel() {

    private val dataList: MutableLiveData<List<AutoSuggest>> = MutableLiveData()
    private val locationData:  MutableLiveData<GeoCodeLocation> = MutableLiveData()

    companion object{
        private var callSuggest: Call<AutoSuggestResponse>? = null
        private var callGeocode: Call<GeoCodeResponse>? = null
    }

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val appID = "YOUR_APP_ID"
    private val appCode = "YOUR_APP_CODE"
    private val suggestApiURL = "https://autocomplete.geocoder.api.here.com/6.2/"
    private val geocodeApiURL = "https://geocoder.api.here.com/6.2/"

    fun getSuggest():LiveData<List<AutoSuggest>>{
        return dataList
    }

    fun getGeocode():LiveData<GeoCodeLocation>{
        return locationData
    }

    fun callSuggest(query:String){
        uiScope.launch {
            val retrofit = RetrofitNewInstance()
            val service: HTTPRequest = retrofit.newInstance(suggestApiURL)

            callSuggest = service.suggestAPI(appID, appCode, query, "ARG")

            val response = withContext(Dispatchers.IO) {
                val response: Response<AutoSuggestResponse>? = try {
                    callSuggest?.execute()
                } catch (t: IllegalArgumentException) {
                    null
                } catch (t: IOException) {
                    null
                }
                response
            }

            if (response != null && response.isSuccessful) {
                val suggests = response.body()
                if (suggests != null)
                    dataList.value = suggests.response
            }
        }
    }

    fun callGeocode(locationID:String){
        uiScope.launch {
            val retrofit = RetrofitNewInstance()
            val service: HTTPRequest = retrofit.newInstance(geocodeApiURL)

            callGeocode = service.geocodeAPI(appID, appCode, locationID)

            val response = withContext(Dispatchers.IO) {
                val response: Response<GeoCodeResponse>? = try {
                    callGeocode?.execute()
                } catch (t: IllegalArgumentException) {
                    null
                } catch (t: IOException) {
                    null
                }
                response
            }

            if (response != null && response.isSuccessful) {
                val geocode = response.body()
                if (geocode != null)
                    locationData.value = geocode.response.view[0].result[0].location
            }
        }
    }

    fun cancelAsyncTasks() {
        callSuggest?.cancel()
    }
}
