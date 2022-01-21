package com.example.marvel_app.view.serie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.R
import com.example.marvel_app.adapter.CharacterAdapter
import com.example.marvel_app.adapter.ComicAdapter
import com.example.marvel_app.model.MarvelSerie.MarvelSerie
import com.example.marvel_app.model.MarvelSerie.SerieDetailViewModel
import com.example.marvel_app.model.QRCode.QRCodeData
import com.example.marvel_app.view.MainActivity
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.squareup.picasso.Picasso

class SerieDetailActivity : AppCompatActivity() {

    private var comicAdapter: ComicAdapter? = null

    private var characterAdapter: CharacterAdapter? = null

    private var qrCode = false

    private var favorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_detail)

        val model = SerieDetailViewModel(intent.getSerializableExtra("MarvelSerie") as MarvelSerie)

        val imageView = findViewById<ImageView>(R.id.serieDetailImage)
        val titleView = findViewById<TextView>(R.id.serieDetailTitle)
        val descriptionView = findViewById<TextView>(R.id.serieDetailDescription)
        val recyclerView = findViewById<RecyclerView>(R.id.serieDetailRecyclerView)
        val button = findViewById<Button>(R.id.serieDetailButtonSwitchList)
        val favoriteButton = findViewById<ImageButton>(R.id.serieDetailFavorite)
        val qrCodeButton = findViewById<ImageButton>(R.id.serieDetailQRCode)

        val id = getSharedPreferences(getString(R.string.favoriteSeries), Context.MODE_PRIVATE)
            .getInt(model.serie.title, -1)

        if (id != -1){
            favorite = true
        }

        if (id == model.serie.id) {
            favoriteButton.setImageDrawable(getDrawable(R.drawable.ic_star))
        }

        Picasso.with(this)
            .load("${model.serie.thumbnail.path}.${model.serie.thumbnail.extension}")
            .placeholder(R.drawable.ic_avengers)
            .error(R.drawable.ic_avengers)
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
            .observe(this, { characters ->
                characterAdapter?.updateValue(characters)
            })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    if (recyclerView.adapter?.javaClass == ComicAdapter::class.java) {
                        model.loadComics()
                    } else if (recyclerView.adapter?.javaClass == CharacterAdapter::class.java) {
                        model.loadCharacters()
                    }
                }
            }
        })

        button.setOnClickListener {
            when (recyclerView.adapter?.javaClass) {
                ComicAdapter::class.java -> {
                    recyclerView.adapter = characterAdapter
                    button.setBackgroundColor(getColor(R.color.marvel_blue))
                    button.setTextColor(getColor(R.color.marvel_red))
                    button.setText(R.string.characters)
                }
                CharacterAdapter::class.java -> {
                    recyclerView.adapter = comicAdapter
                    button.setBackgroundColor(getColor(R.color.marvel_red))
                    button.setTextColor(getColor(R.color.marvel_blue))
                    button.setText(R.string.comics)
                }
            }
        }

        favoriteButton.setOnClickListener {
            when (favorite) {
                true -> {
                    favoriteButton.setImageDrawable(getDrawable(R.drawable.ic_star_border))
                    getSharedPreferences(
                        getString(R.string.favoriteSeries),
                        Context.MODE_PRIVATE
                    ).edit()
                        .remove(model.serie.title)
                        .apply()
                    favorite = false
                }
                false -> {
                    favoriteButton.setImageDrawable(getDrawable(R.drawable.ic_star))
                    getSharedPreferences(
                        getString(R.string.favoriteSeries),
                        Context.MODE_PRIVATE
                    ).edit()
                        .putInt(model.serie.title, model.serie.id)
                        .apply()
                    favorite = true
                }
            }
        }

        val data = QRCodeData("serie", model.serie.id)
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(
            Gson().toJson(data),
            BarcodeFormat.QR_CODE, 400, 400
        )

        qrCodeButton.setOnClickListener {
            qrCode = when (qrCode) {
                false -> {
                    imageView.setImageBitmap(bitmap)
                    true
                }
                true -> {
                    Picasso.with(this)
                        .load("${model.serie.thumbnail.path}.${model.serie.thumbnail.extension}")
                        .placeholder(R.drawable.ic_iron_man)
                        .error(R.drawable.ic_iron_man)
                        .into(imageView)
                    false
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}