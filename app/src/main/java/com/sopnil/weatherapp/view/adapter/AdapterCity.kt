package com.sopnil.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sopnil.weatherapp.R

class AdapterCity(
        private val cityList : List<String>
) : RecyclerView.Adapter<AdapterCity.ViewHolder>() {

    var onListItemClick: ((String , Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_city, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val cityName = cityList[position]
        holder.bind(cityName)
        holder.name.setOnClickListener {
            onListItemClick?.invoke(cityName , holder.name.id)
        }
        holder.image.setOnClickListener{
            onListItemClick?.invoke(cityName , holder.image.id)
        }
    }

    override fun getItemCount(): Int {
        return cityList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val name = itemView.findViewById<TextView>(R.id.txt_city_name)
         val image = itemView.findViewById<ImageView>(R.id.img_delete_city)
        fun bind(cityName: String) {
            name.text = cityName
        }
    }
}