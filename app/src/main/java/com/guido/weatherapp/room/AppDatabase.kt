package com.guido.weatherapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.guido.weatherapp.models.Place
import com.guido.weatherapp.room.dao.PlaceDAO

@Database(entities = [Place::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    //Dao
    abstract fun placeDao(): PlaceDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context?): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = context?.applicationContext?.let {
                            Room.databaseBuilder(
                                it,
                                AppDatabase::class.java, "weather_app_database")
                                .fallbackToDestructiveMigration()
                                .build()
                        }
                    }
                }
            }
            return INSTANCE
        }
    }
}