package com.guido.weatherapp.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.guido.weatherapp.models.Place
import com.guido.weatherapp.repository.PlaceRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var dataList: LiveData<List<Place>>

    init {
        val placeRepository = PlaceRepository(application)
        dataList = placeRepository.get()
    }

    fun get():LiveData<List<Place>>{
        return dataList
    }
}