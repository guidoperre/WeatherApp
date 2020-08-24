package com.guido.weatherapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guido.weatherapp.R
import com.guido.weatherapp.adapters.MainAdapter
import com.guido.weatherapp.databinding.ActivityMainBinding
import com.guido.weatherapp.models.Place
import com.guido.weatherapp.ui.weather.WeatherActivity
import com.guido.weatherapp.ui.search.SearchActivity

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val clickHandler = ClickHandler(this, binding.searchLayout)
        binding.clickHandler = clickHandler

        initializeViewModel()
        instanceRecyclerView()
    }

    private fun initializeViewModel(){
        val model = MainViewModel(application)
        model.get().observe(this, Observer {
            adapter.set(it as ArrayList<Place>)
        })
    }

    private fun instanceRecyclerView() {
        val recyclerView = binding.placesList
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        adapterClickListener()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    private fun adapterClickListener() {
        adapter = MainAdapter(object : MainAdapter.OnItemClickListener{
            override fun onItemClick(place: Place, position: Int) {
                //Go to weather activity
                val intent = Intent(applicationContext, WeatherActivity::class.java)

                intent.putExtra("address",place.label)
                intent.putExtra("latitude",place.location.latitude)
                intent.putExtra("longitude",place.location.longitude)

                startActivity(intent)
                overridePendingTransition(R.transition.fade_in, R.transition.fade_out)
            }
        })
    }

    class ClickHandler(){

        private lateinit var searchLayout: CardView
        private lateinit var activity: MainActivity

        constructor(activity: MainActivity, searchLayout:CardView):this(){
            this.activity = activity
            this.searchLayout = searchLayout
        }

        fun goToSearch(){
            val intent = Intent(activity, SearchActivity::class.java)
            activity.startActivity(intent)
            activity.overridePendingTransition(R.transition.fade_in, R.transition.fade_out)
        }

    }
}