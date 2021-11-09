package com.example.marvel_app.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.R
import com.example.marvel_app.model.MarvelCharacter
import com.squareup.picasso.Picasso

class CharacterAdapter(private val context: Context, private val characterList: List<MarvelCharacter>) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView
        val name: TextView

        init {
            image = view.findViewById(R.id.characterItemImage)
            name = view.findViewById(R.id.characterItemName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.character_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = characterList[position].name

        Picasso.with(context)
            .load(characterList[position].thumbnail.path + characterList[position].thumbnail.extension)
            .into(holder.image)
    }

    override fun getItemCount() = characterList.size
}