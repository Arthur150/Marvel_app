package com.example.marvel_app.model

import com.google.gson.annotations.SerializedName

data class MarvelCharacter (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("modified") val modified: String,
    @SerializedName("thumbnail") val thumbnail: Thumbnail,
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("comics") val comics: Sample,
    @SerializedName("series") val series: Sample,
    @SerializedName("stories") val stories: Sample,
    @SerializedName("events") val events: Sample,
)
