package com.example.marvel_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.adapter.CharacterAdapter
import com.example.marvel_app.model.MarvelComic.ComicDetailViewModel
import com.example.marvel_app.model.MarvelComic.MarvelComic
import com.squareup.picasso.Picasso

class ComicDetailActivity : AppCompatActivity() {

    private var characterAdapter: CharacterAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_detail)

        val model = ComicDetailViewModel(intent.getSerializableExtra("MarvelComic") as MarvelComic)

        val imageView = findViewById<ImageView>(R.id.comicDetailImage)
        val titleView = findViewById<TextView>(R.id.comicDetailTitle)
        val descriptionView = findViewById<TextView>(R.id.comicDetailDescription)
        val recyclerView = findViewById<RecyclerView>(R.id.comicDetailRecyclerView)
        val button = findViewById<Button>(R.id.comicDetailButtonSwitchList)

        val serieItem = findViewById<ConstraintLayout>(R.id.comicDetailSerieItem)
        val imageSerie = serieItem.findViewById<ImageView>(R.id.ComicDetailSerieItemImage)
        val titleSerie = serieItem.findViewById<TextView>(R.id.ComicDetailSerieItemTitle)

        val serie = model.loadSerie()

        model.getSerie()
            .observe(this, { serie ->
                Picasso.with(this)
                    .load("${serie.thumbnail.path}.${serie.thumbnail.extension}")
                    .placeholder(R.drawable.ic_avengers)
                    .error(R.drawable.ic_avengers)
                    .into(imageSerie)

                titleSerie.text = serie.title

                serieItem.setOnClickListener {

                    val intent = Intent(this, SerieDetailActivity::class.java).apply {
                        putExtra("MarvelSerie", serie)
                    }

                    this.startActivity(intent)
                }
            })

        Picasso.with(this)
            .load("${model.comic.thumbnail.path}.${model.comic.thumbnail.extension}")
            .placeholder(R.drawable.ic_captain_america)
            .error(R.drawable.ic_captain_america)
            .into(imageView)

        titleView.text = model.comic.title
        descriptionView.text = model.comic.description

        recyclerView.layoutManager = LinearLayoutManager(this)
        characterAdapter = CharacterAdapter(this, emptyList())
        recyclerView.adapter = characterAdapter

        model.getCharacters()
            .observe(this, { characters ->
                characterAdapter?.updateValue(characters)
            })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    if (recyclerView.visibility != RecyclerView.GONE) {
                        model.loadCharacters()
                    }
                }
            }
        })

        button.setOnClickListener {
            if (recyclerView.visibility == RecyclerView.GONE) {
                recyclerView.visibility = RecyclerView.VISIBLE
                serieItem.visibility = ConstraintLayout.GONE
                recyclerView.adapter = characterAdapter
                button.setBackgroundColor(getColor(R.color.marvel_blue))
                button.setTextColor(getColor(R.color.marvel_red))
                button.setText(R.string.characters)
            } else if (serieItem.visibility == ConstraintLayout.GONE) {
                recyclerView.visibility = RecyclerView.GONE
                serieItem.visibility = ConstraintLayout.VISIBLE
                button.setBackgroundColor(getColor(R.color.marvel_red))
                button.setTextColor(getColor(R.color.marvel_blue))
                button.setText(R.string.series)
            }
        }
    }
}