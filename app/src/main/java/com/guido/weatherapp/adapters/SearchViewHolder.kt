package com.guido.weatherapp.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.guido.weatherapp.databinding.PlaceItemLayoutBinding
import com.guido.weatherapp.models.AutoSuggest

class SearchViewHolder(private var dataBinding: PlaceItemLayoutBinding, private var listener: SearchAdapter.OnItemClickListener) : RecyclerView.ViewHolder(dataBinding.root) {

    private lateinit var suggest:AutoSuggest

    private var view:View = dataBinding.root

    fun bind(suggest: AutoSuggest){
        this.suggest = suggest

        setVisibility()
        setLabel()
        setItemClickListener(view,listener,adapterPosition)
    }

    private fun setVisibility(){
        dataBinding.address.visibility = View.INVISIBLE
        dataBinding.country.visibility = View.INVISIBLE
        dataBinding.state.visibility = View.INVISIBLE
        dataBinding.placeImage.visibility = View.INVISIBLE
        dataBinding.loadingGif.visibility = View.INVISIBLE
        dataBinding.deleteItem.visibility = View.INVISIBLE
    }

    private fun setLabel(){
        val placeData = suggest.label.split(", ")
        when {
            placeData.size != 1 -> placeLargeLayoutConfiguration(placeData)
            suggest.label == "loading" -> setLoadingGif()
            else -> placeShortLayoutConfiguration()
        }
    }

    private fun setLoadingGif(){
        dataBinding.loadingGif.visibility = View.VISIBLE
    }

    private fun placeLargeLayoutConfiguration(placeData:List<String>){
        dataBinding.placeImage.visibility = View.VISIBLE
        dataBinding.address.visibility = View.VISIBLE
        dataBinding.state.visibility = View.VISIBLE
        dataBinding.address.text = setAddress(placeData)
        dataBinding.state.text = setState(placeData)
    }

    private fun placeShortLayoutConfiguration(){
        dataBinding.placeImage.visibility = View.VISIBLE
        dataBinding.country.visibility = View.VISIBLE
        dataBinding.country.text = suggest.label
    }

    private fun setAddress(codeData: List<String>): String {
        var addressText = ""
        when (codeData.size) {
            1 -> addressText = codeData[0]
            2 -> addressText = codeData[1]
            3 -> addressText = codeData[2]
            4 -> addressText = codeData[3]
            5 -> addressText = codeData[4]
        }
        return addressText
    }

    private fun setState(codeData: List<String>): String{
        return if (codeData.size == 2) {
            codeData[0]
        } else {
            codeData[0] + ", " + codeData[1]
        }
    }

    private fun setItemClickListener(itemView: View, listener: SearchAdapter.OnItemClickListener, position: Int) {
        itemView.setOnClickListener { listener.onItemClick(suggest, position) }
    }
}