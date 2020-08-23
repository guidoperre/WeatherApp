package com.guido.weatherapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guido.weatherapp.models.AutoSuggest
import com.guido.weatherapp.models.AutoSuggestResponse
import com.guido.weatherapp.retrofit.HTTPRequest
import com.guido.weatherapp.retrofit.RetrofitNewInstance
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

internal class SearchViewModel : ViewModel() {

    private val dataList: MutableLiveData<List<AutoSuggest>> = MutableLiveData()

    companion object{
        private var call: Call<AutoSuggestResponse>? = null
    }

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val appID = "YOUR_APP_ID"
    private val appCode = "YOUR_APP_CODE"
    private val apiURL = "https://autocomplete.geocoder.api.here.com/6.2/"

    fun getSuggest():LiveData<List<AutoSuggest>>{
        return dataList
    }

    fun get(query:String){
        uiScope.launch {
            val retrofit = RetrofitNewInstance()
            val service: HTTPRequest = retrofit.newInstance(apiURL)

            call = service.suggestAPI(appID, appCode, query, "ARG")

            val response = withContext(Dispatchers.IO) {
                val response: Response<AutoSuggestResponse>? = try {
                    call?.execute()
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

    fun cancelAsyncTasks() {
        call?.cancel()
    }
}
