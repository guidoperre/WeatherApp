package com.guido.weatherapp.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.guido.weatherapp.databinding.PlaceItemLayoutBinding
import com.guido.weatherapp.models.Place
import com.guido.weatherapp.repository.PlaceRepository

class MainViewHolder (private var dataBinding: PlaceItemLayoutBinding, private var listener: MainAdapter.OnItemClickListener) : RecyclerView.ViewHolder(dataBinding.root) {

    private lateinit var place: Place

    private var view: View = dataBinding.root

    fun bind(place: Place){
        this.place = place

        setVisibility()
        setLabel()
        setDeleteClickListener()
        setItemClickListener(view,listener,adapterPosition)
    }

    private fun setVisibility(){
        dataBinding.address.visibility = View.INVISIBLE
        dataBinding.country.visibility = View.INVISIBLE
        dataBinding.state.visibility = View.INVISIBLE
        dataBinding.placeImage.visibility = View.INVISIBLE
        dataBinding.loadingGif.visibility = View.INVISIBLE
        dataBinding.deleteItem.visibility = View.VISIBLE
    }

    private fun setLabel(){
        val placeData = place.label.split(", ")
        when {
            placeData.size != 1 -> placeLargeLayoutConfiguration(placeData)
            else -> placeShortLayoutConfiguration()
        }
    }

    private fun placeLargeLayoutConfiguration(placeData:List<String>){
        dataBinding.placeImage.visibility = View.VISIBLE
        dataBinding.address.visibility = View.VISIBLE
        dataBinding.state.visibility = View.VISIBLE
        dataBinding.address.text = placeData[0]
        dataBinding.state.text = setState(placeData)
    }

    private fun placeShortLayoutConfiguration(){
        dataBinding.placeImage.visibility = View.VISIBLE
        dataBinding.country.visibility = View.VISIBLE
        dataBinding.country.text = place.label
    }

    private fun setState(codeData: List<String>): String{
        return if (codeData.size == 2)
            codeData[1]
         else
            codeData[codeData.size-1] + ", " + codeData[codeData.size-2]
    }

    private fun setDeleteClickListener(){
        dataBinding.deleteItem.setOnClickListener {
            PlaceRepository().delete(place.id)
        }
    }

    private fun setItemClickListener(itemView: View, listener: MainAdapter.OnItemClickListener, position: Int) {
        itemView.setOnClickListener { listener.onItemClick(place, position) }
    }
}