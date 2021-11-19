package com.example.marvel_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.adapter.CharacterAdapter
import com.example.marvel_app.adapter.ComicAdapter
import com.example.marvel_app.adapter.SerieAdapter
import com.example.marvel_app.model.MarvelSerie.MarvelSerie
import com.example.marvel_app.model.MarvelSerie.SerieDetailViewModel
import com.squareup.picasso.Picasso

class SerieDetailActivity : AppCompatActivity() {

    private var comicAdapter: ComicAdapter? = null

    private var characterAdapter: CharacterAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_detail)

        val model = SerieDetailViewModel(intent.getSerializableExtra("MarvelSerie") as MarvelSerie)

        val imageView = findViewById<ImageView>(R.id.serieDetailImage)
        val titleView = findViewById<TextView>(R.id.serieDetailTitle)
        val descriptionView = findViewById<TextView>(R.id.serieDetailDescription)
        val recyclerView = findViewById<RecyclerView>(R.id.serieDetailRecyclerView)
        val button = findViewById<Button>(R.id.serieDetailButtonSwitchList)

        Picasso.with(this)
            .load("${model.serie.thumbnail.path}.${model.serie.thumbnail.extension}")
            .placeholder(R.drawable.ic_iron_man)
            .error(R.drawable.ic_iron_man)
            .into(imageView)

        titleView.text = model.serie.title
        descriptionView.text = model.serie.description

        recyclerView.layoutManager = LinearLayoutManager(this)
        comicAdapter = ComicAdapter(this, emptyList())
        characterAdapter = CharacterAdapter(this, emptyList())
        recyclerView.adapter = characterAdapter

        model.getComics()
            .observe(this, { comics ->
                comicAdapter?.updateValue(comics)
            })

        model.getCharacters()
            .observe(this, { series ->
                characterAdapter?.updateValue(series)
            })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    if (recyclerView.adapter?.javaClass == ComicAdapter::class.java) {
                        model.loadComics()
                    }
                    else if (recyclerView.adapter?.javaClass == CharacterAdapter::class.java){
                        model.loadCharacters()
                    }
                }
            }
        })

        button.setOnClickListener {
            if (recyclerView.adapter?.javaClass == ComicAdapter::class.java) {
                recyclerView.adapter = characterAdapter
                button.setBackgroundColor(getColor(R.color.marvel_blue))
                button.setTextColor(getColor(R.color.marvel_red))
                button.setText(R.string.comics)
            }
            else if (recyclerView.adapter?.javaClass == CharacterAdapter::class.java){
                recyclerView.adapter = comicAdapter
                button.setBackgroundColor(getColor(R.color.marvel_red))
                button.setTextColor(getColor(R.color.marvel_blue))
                button.setText(R.string.series)
            }
        }
    }
}