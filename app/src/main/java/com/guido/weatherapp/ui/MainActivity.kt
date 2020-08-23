package com.guido.weatherapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import com.guido.weatherapp.R
import com.guido.weatherapp.databinding.ActivityMainBinding
import com.guido.weatherapp.ui.search.SearchActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val clickHandler = ClickHandler(this,binding.searchLayout)
        binding.clickHandler = clickHandler
    }

    class ClickHandler(){

        private lateinit var searchLayout: CardView
        private lateinit var activity: MainActivity

        constructor(activity:MainActivity, searchLayout:CardView):this(){
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