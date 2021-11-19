package com.example.marvel_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.adapter.ComicAdapter
import com.example.marvel_app.adapter.SerieAdapter
import com.example.marvel_app.model.MarvelCharacter.CharacterDetailViewModel
import com.example.marvel_app.model.MarvelCharacter.MarvelCharacter
import com.squareup.picasso.Picasso

class CharacterDetailActivity : AppCompatActivity() {

    private var comicAdapter: ComicAdapter? = null

    private var serieAdapter: SerieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        val model =
            CharacterDetailViewModel(intent.getSerializableExtra("MarvelCharacter") as MarvelCharacter)

        val imageView = findViewById<ImageView>(R.id.characterDetailImage)
        val nameView = findViewById<TextView>(R.id.characterDetailName)
        val descriptionView = findViewById<TextView>(R.id.characterDetailDescription)
        val recyclerView = findViewById<RecyclerView>(R.id.characterDetailRecyclerView)
        val button = findViewById<Button>(R.id.characterDetailButtonSwitchList)

        Picasso.with(this)
            .load("${model.character.thumbnail.path}.${model.character.thumbnail.extension}")
            .placeholder(R.drawable.ic_iron_man)
            .error(R.drawable.ic_iron_man)
            .into(imageView)

        nameView.text = model.character.name
        descriptionView.text = model.character.description

        recyclerView.layoutManager = LinearLayoutManager(this)
        comicAdapter = ComicAdapter(this, emptyList())
        serieAdapter = SerieAdapter(this, emptyList())
        recyclerView.adapter = comicAdapter

        model.getComics()
            .observe(this, { comics ->
                comicAdapter?.updateValue(comics)
            })

        model.getSeries()
            .observe(this, { series ->
                serieAdapter?.updateValue(series)
            })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    if (recyclerView.adapter?.javaClass == ComicAdapter::class.java) {
                        model.loadComics()
                    } else if (recyclerView.adapter?.javaClass == SerieAdapter::class.java) {
                        model.loadSeries()
                    }
                }
            }
        })

        button.setOnClickListener {
            when (recyclerView.adapter?.javaClass) {
                ComicAdapter::class.java -> {
                    recyclerView.adapter = serieAdapter
                    button.setBackgroundColor(getColor(R.color.marvel_red))
                    button.setTextColor(getColor(R.color.marvel_blue))
                    button.setText(R.string.series)
                }
                SerieAdapter::class.java -> {
                    recyclerView.adapter = comicAdapter
                    button.setBackgroundColor(getColor(R.color.marvel_blue))
                    button.setTextColor(getColor(R.color.marvel_red))
                    button.setText(R.string.comics)
                }
            }
        }


    }


}