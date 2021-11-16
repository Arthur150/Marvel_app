package com.example.marvel_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.R
import com.example.marvel_app.model.MarvelSerie
import com.squareup.picasso.Picasso

class SerieAdapter(
    private val context: Context,
    private var series: List<MarvelSerie>
) :
    RecyclerView.Adapter<SerieAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView
        val title: TextView

        init {
            image = view.findViewById(R.id.serieItemImage)
            title = view.findViewById(R.id.serieItemTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.serie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = series[position].title

        Picasso.with(context)
            .load("${series[position].thumbnail.path}.${series[position].thumbnail.extension}")
            .placeholder(R.drawable.ic_avengers)
            .error(R.drawable.ic_captain_america)
            .into(holder.image)
    }

    override fun getItemCount() = series.size

    fun updateValue(list: List<MarvelSerie>) {
        series = list
        notifyDataSetChanged()
    }
}