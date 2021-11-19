package com.example.marvel_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.marvel_app.model.MarvelCharacter.MarvelCharacter
import com.squareup.picasso.Picasso

class CharacterDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        val character : MarvelCharacter = intent.getSerializableExtra("MarvelCharacter") as MarvelCharacter

        val imageView = findViewById<ImageView>(R.id.characterDetailImage)
        val nameView = findViewById<TextView>(R.id.characterDetailName)
        val descriptionView = findViewById<TextView>(R.id.characterDetailDescription)

        Picasso.with(this)
            .load("${character.thumbnail.path}.${character.thumbnail.extension}")
            .placeholder(R.drawable.ic_iron_man)
            .error(R.drawable.ic_iron_man)
            .into(imageView)

        nameView.text = character.name
        descriptionView.text = character.description
    }


}