package com.example.marvel_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.ComicDetailActivity
import com.example.marvel_app.R
import com.example.marvel_app.SerieDetailActivity
import com.example.marvel_app.model.MarvelComic.MarvelComic
import com.squareup.picasso.Picasso

class ComicAdapter(
    private val context: Context,
    private var comics: List<MarvelComic>
) :
    RecyclerView.Adapter<ComicAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.comicItemImage)
        val title: TextView = view.findViewById(R.id.comicItemTitle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.comic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = comics[position].title

        Picasso.with(context)
            .load("${comics[position].thumbnail.path}.${comics[position].thumbnail.extension}")
            .placeholder(R.drawable.ic_captain_america)
            .error(R.drawable.ic_captain_america)
            .into(holder.image)

        holder.itemView.setOnClickListener {

            val intent = Intent(context, ComicDetailActivity::class.java).apply {
                putExtra("MarvelComic", comics[position])
            }

            context.startActivity(intent)
        }
    }

    override fun getItemCount() = comics.size

    fun updateValue(list: List<MarvelComic>) {
        comics = list
        notifyDataSetChanged()
    }
}