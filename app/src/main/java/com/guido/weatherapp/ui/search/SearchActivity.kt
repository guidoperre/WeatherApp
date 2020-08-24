package com.guido.weatherapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guido.weatherapp.R
import com.guido.weatherapp.adapters.SearchAdapter
import com.guido.weatherapp.databinding.ActivitySearchBinding
import com.guido.weatherapp.models.AutoSuggest
import com.guido.weatherapp.models.Place
import com.guido.weatherapp.repository.PlaceRepository
import com.guido.weatherapp.ui.main.MainActivity
import com.guido.weatherapp.ui.weather.WeatherActivity

class SearchActivity : AppCompatActivity() {

    private lateinit var adapter: SearchAdapter
    private lateinit var model: SearchViewModel

    private lateinit var binding:ActivitySearchBinding

    private val handler = Handler()
    private var runnable: Runnable? = null

    private var isTextEmptyFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SearchTheme)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search)
        onTextViewChange()
        initializeViewModel()
        instanceRecyclerView()
    }

    private fun onTextViewChange() {
        val searchPlace = binding.searchText
        searchPlace.requestFocus()
        searchPlace.setSelection(0)
        searchPlace.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }
            override fun afterTextChanged(editable: Editable) {
                afterTextChangedRunnable(editable.toString())
            }
        })
    }

    private fun afterTextChangedRunnable(query: String) {
        if (runnable != null)
            handler.removeCallbacks(runnable!!)
        deleteSuggestions()
        if (query != "") {
            adapter.setLoading(AutoSuggest("loading",""))
            isTextEmptyFlag = false
            runnable = Runnable { model.callSuggest(query) }
            handler.postDelayed(runnable!!, 500)
        } else
            isTextEmptyFlag = true
    }

    private fun initializeViewModel() {
        model = SearchViewModel()
        model.getSuggest().observe(this, androidx.lifecycle.Observer {
          updateSuggestions(it)
        })
        model.getGeocode().observe(this,androidx.lifecycle.Observer {
            //Add to database
            PlaceRepository(application).insert(Place(0L,it.address.label,it.geocode))

            //Go to weather activity
            val intent = Intent(this, WeatherActivity::class.java)

            intent.putExtra("address",it.address.label)
            intent.putExtra("latitude",it.geocode.latitude)
            intent.putExtra("longitude",it.geocode.longitude)

            startActivity(intent)
            overridePendingTransition(R.transition.fade_in, R.transition.fade_out)
        })
    }

    private fun instanceRecyclerView() {
        val recyclerView = binding.placesRecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        adapterClickListener()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    private fun adapterClickListener() {
        adapter = SearchAdapter(object : SearchAdapter.OnItemClickListener{
            override fun onItemClick(suggest: AutoSuggest, position: Int) {
                model.cancelAsyncTasks()
                //Call geocode api
                model.callGeocode(suggest.locationId)
            }
        })
    }

    private fun updateSuggestions(autoSuggests: List<AutoSuggest>) {
        deleteSuggestions()
        if (!isTextEmptyFlag)
            adapter.set(autoSuggests as ArrayList<AutoSuggest>)
    }

    private fun deleteSuggestions(){
        adapter.deleteAll()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out)
    }
}