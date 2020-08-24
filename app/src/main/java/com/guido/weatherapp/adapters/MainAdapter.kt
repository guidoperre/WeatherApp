package com.guido.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guido.weatherapp.databinding.PlaceItemLayoutBinding
import com.guido.weatherapp.models.AutoSuggest
import com.guido.weatherapp.models.Place

class MainAdapter(private var listener:OnItemClickListener) : RecyclerView.Adapter<MainViewHolder>() {

    private var placesList: ArrayList<Place> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainViewHolder(PlaceItemLayoutBinding.inflate(inflater,parent,false),listener)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(placesList[position])
    }

    override fun getItemCount(): Int {
        return placesList.size
    }

    fun set(places:ArrayList<Place>){
        placesList = places
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(place: Place, position: Int)
    }

}
