package com.example.marvel_app.model.MarvelComic

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicDate(
    @SerializedName("type") val type: String,
    @SerializedName("date") val date: String,

    ) : Serializable
