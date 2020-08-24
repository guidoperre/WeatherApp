package com.guido.weatherapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.guido.weatherapp.models.Place
import com.guido.weatherapp.room.AppDatabase
import com.guido.weatherapp.room.dao.PlaceDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlaceRepository() {

    private val viewModelJob = Job()
    private val uiScope =  CoroutineScope(Dispatchers.IO + viewModelJob)

    private lateinit var dataList: LiveData<List<Place>>

    companion object {
        private var placeDAO: PlaceDAO? = null
    }

    constructor(application: Application):this(){
        val database: AppDatabase? = AppDatabase.getDatabase(application)
        if (database != null){
            placeDAO = database.placeDao()
            dataList = placeDAO!!.getAll()
        }

    }

    fun get():LiveData<List<Place>>{
        return dataList
    }

    fun insert(place:Place){
        uiScope.launch {
            val places = placeDAO?.get()
            if (places?.size!! > 4){
                var id = places[0].id
                for (placeID in places)
                    if (placeID.id < id)
                        id = placeID.id
                placeDAO?.deleteById(id)
            }

            placeDAO?.insert(place)
        }
    }

    fun delete(id:Long){
        uiScope.launch {
            placeDAO?.deleteById(id)
        }
    }

}