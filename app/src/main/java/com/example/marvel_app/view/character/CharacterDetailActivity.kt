package com.example.marvel_app.view.character

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.R
import com.example.marvel_app.adapter.ComicAdapter
import com.example.marvel_app.adapter.SerieAdapter
import com.example.marvel_app.model.MarvelCharacter.CharacterDetailViewModel
import com.example.marvel_app.model.MarvelCharacter.MarvelCharacter
import com.example.marvel_app.model.QRCode.QRCodeData
import com.example.marvel_app.view.MainActivity
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.squareup.picasso.Picasso

class CharacterDetailActivity : AppCompatActivity() {

    private var comicAdapter: ComicAdapter? = null

    private var serieAdapter: SerieAdapter? = null

    private var qrCode = false

    private var favorite = false

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
        val favoriteButton = findViewById<ImageButton>(R.id.characterDetailFavorite)
        val qrCodeButton = findViewById<ImageButton>(R.id.characterDetailQRCode)

        val id = getSharedPreferences(getString(R.string.favoriteCharacters), Context.MODE_PRIVATE)
            .getInt(model.character.name, -1)

        if (id != -1){
            favorite = true
        }

        if (id == model.character.id) {
            favoriteButton.setImageDrawable(getDrawable(R.drawable.ic_star))
        }

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

        favoriteButton.setOnClickListener {
            when (favorite) {
                true -> {
                    favoriteButton.setImageDrawable(getDrawable(R.drawable.ic_star_border))
                    getSharedPreferences(
                        getString(R.string.favoriteCharacters),
                        Context.MODE_PRIVATE
                    ).edit()
                        .remove(model.character.name)
                        .apply()
                    favorite = false
                }
                false -> {
                    favoriteButton.setImageDrawable(getDrawable(R.drawable.ic_star))
                    getSharedPreferences(
                        getString(R.string.favoriteCharacters),
                        Context.MODE_PRIVATE
                    ).edit()
                        .putInt(model.character.name, model.character.id)
                        .apply()
                    favorite = true
                }
            }
        }

        val data = QRCodeData("character", model.character.id)
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
                        .load("${model.character.thumbnail.path}.${model.character.thumbnail.extension}")
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