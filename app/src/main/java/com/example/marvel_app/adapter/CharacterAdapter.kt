package com.example.marvel_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.CharacterDetailActivity
import com.example.marvel_app.R
import com.example.marvel_app.model.MarvelCharacter.MarvelCharacter
import com.squareup.picasso.Picasso

class CharacterAdapter(
    private val context: Context,
    private var characterList: List<MarvelCharacter>
) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.characterItemImage)
        val name: TextView = view.findViewById(R.id.characterItemName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.character_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = characterList[position].name

        Picasso.with(context)
            .load("${characterList[position].thumbnail.path}.${characterList[position].thumbnail.extension}")
            .placeholder(R.drawable.ic_iron_man)
            .error(R.drawable.ic_iron_man)
            .into(holder.image)

        holder.itemView.setOnClickListener {

            val intent = Intent(context, CharacterDetailActivity::class.java).apply {
                putExtra("MarvelCharacter", characterList[position])
            }

            context.startActivity(intent)
        }
    }

    override fun getItemCount() = characterList.size

    fun updateValue(list: List<MarvelCharacter>) {
        characterList = list
        notifyDataSetChanged()
    }
}