package com.guido.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.guido.weatherapp.databinding.PlaceItemLayoutBinding
import com.guido.weatherapp.models.AutoSuggest
import java.util.*
import kotlin.collections.ArrayList

class SearchAdapter(private var listener:OnItemClickListener) : RecyclerView.Adapter<SearchViewHolder>() {

    private var suggestsList: ArrayList<AutoSuggest> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(PlaceItemLayoutBinding.inflate(inflater,parent,false),listener)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(suggestsList[position])
    }

    override fun getItemCount(): Int {
        return suggestsList.size
    }

    fun set(suggests:ArrayList<AutoSuggest>){
        suggestsList = suggests
        notifyDataSetChanged()
    }

    fun deleteAll(){
        suggestsList.clear()
        notifyDataSetChanged()
    }

    fun setLoading(suggest:AutoSuggest){
        suggestsList.clear()
        suggestsList.add(suggest)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(suggest: AutoSuggest, position: Int)
    }

}
