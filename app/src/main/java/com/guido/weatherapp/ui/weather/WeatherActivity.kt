package com.guido.weatherapp.ui.weather

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.guido.weatherapp.R
import com.guido.weatherapp.databinding.ActivityWeatherBinding
import com.guido.weatherapp.models.Place
import com.guido.weatherapp.models.WeatherResponse
import com.guido.weatherapp.ui.main.MainActivity
import com.guido.weatherapp.ui.main.MainViewModel
import java.util.*

class WeatherActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var binding: ActivityWeatherBinding

    private lateinit var model:WeatherViewModel

    private lateinit var waitingDialog: Dialog

    private var latitude:Double = 0.0
    private var longitude:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        val clickHandler = ClickHandler(this, binding.goBack)
        binding.clickHandler = clickHandler

        onReady()
    }

    private fun onReady(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Set fullscreen
        val w: Window = window
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        initializeViewModel()

        //Open wait dialog
        openDialog()
    }

    private fun initializeViewModel(){
        model = WeatherViewModel()
        model.getWeather().observe(this, androidx.lifecycle.Observer {
            //Set all the weather params in the layout
            setWeatherParams(it)
            waitingDialog.dismiss()
        })
    }

    private fun setWeatherParams(weatherResponse: WeatherResponse){
        val temperature = weatherResponse.main.temp.toInt().toString() + "°"
        val maxTemperature = weatherResponse.main.tempMax.toString() + "°"
        val minTemperature = weatherResponse.main.tempMin.toString() + "°"
        val feelsLike = "Feels like " + weatherResponse.main.feelsLike.toInt().toString() + "°"
        val pressure = weatherResponse.main.pressure.toString()
        val humidity = weatherResponse.main.humidity.toString() + "%"
        val dayStatus = weatherResponse.weather[0].main

        //Check if is hot or cold and set the right background
        isHotOrCold(weatherResponse.main.temp)

        //Check day status and set the right icon
        setDayStatusIcon(dayStatus)

        binding.temperature.text = temperature
        binding.maxTemp.text = maxTemperature
        binding.minTemp.text = minTemperature
        binding.feelsLike.text = feelsLike
        binding.pressure.text = pressure
        binding.humidity.text = humidity

        binding.dayStatus.text = dayStatus
    }

    private fun isHotOrCold(temp:Double){
        if (temp > 20)
            binding.cardBackground.setImageResource(R.drawable.hot)
        else
            binding.cardBackground.setImageResource(R.drawable.cold)
    }

    private fun setDayStatusIcon(temp:String){
        when(temp){
            "Thunderstorm" -> {
                binding.dayStatusIcon.setImageResource(R.mipmap.ic_rain_foreground)
            }
            "Drizzle" -> {
                binding.dayStatusIcon.setImageResource(R.mipmap.ic_rain_foreground)
            }
            "Rain" -> {
                binding.dayStatusIcon.setImageResource(R.mipmap.ic_rain_foreground)
            }
            "Snow" -> {
                binding.dayStatusIcon.setImageResource(R.mipmap.ic_snow)
            }
            "Clear" -> {
                binding.dayStatusIcon.setImageResource(R.mipmap.ic_sunny_foreground)
            }
            else -> {
                binding.dayStatusIcon.setImageResource(R.mipmap.ic_cloud_foreground)
            }
        }
    }

    private fun makeCall(){
        model.callWeather(latitude,longitude)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setMapGestureConfiguration()
        getExtras()
    }

    private fun setMapGestureConfiguration() {
        val uiSettings = mMap.uiSettings
        uiSettings.isTiltGesturesEnabled = false
        uiSettings.isMapToolbarEnabled = false
        uiSettings.isCompassEnabled = false
        uiSettings.isMyLocationButtonEnabled = false
    }

    private fun getExtras() {
        if (intent.extras!!.getDouble("latitude") != 0.0 && intent.extras!!.getDouble("longitude") != 0.0) {
            latitude = intent.extras!!.getDouble("latitude")
            longitude = intent.extras!!.getDouble("longitude")
            val address = intent.extras!!.getString("address")
            if (address != null) {
                val parts = address.split(",".toRegex()).toTypedArray()
                binding.city.text = parts[0]
                val countryText = ", "+parts[parts.size-1]
                binding.country.text = countryText
            }
            moveCameraToSelectedPosition(LatLng(latitude, longitude))
        }
        makeCall()
    }

    private fun moveCameraToSelectedPosition(position: LatLng?) {
        val cameraOfMyPosition = CameraPosition.Builder()
            .target(position)
            .zoom(12f)
            .bearing(360f)
            .build()
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraOfMyPosition))
    }

    private fun openDialog(){
        waitingDialog = Dialog(this)
        waitingDialog.setContentView(R.layout.waiting_dialog)
        waitingDialog.setCanceledOnTouchOutside(false)
        waitingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        waitingDialog.show()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out)
    }

    class ClickHandler(){
        private lateinit var goBack: ImageView
        private lateinit var activity: WeatherActivity

        constructor(activity: WeatherActivity, goBack: ImageView):this(){
            this.activity = activity
            this.goBack = goBack
        }

        fun goToMain(){
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
            activity.overridePendingTransition(R.transition.fade_in, R.transition.fade_out)
        }
    }
}