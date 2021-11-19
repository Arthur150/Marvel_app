package com.example.marvel_app.model.MarvelComic

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicPrice(
    @SerializedName("type") val type: String,
    @SerializedName("price") val price: Float,
) : Serializable
