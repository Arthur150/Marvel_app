package com.example.marvel_app.model.MarvelSerie

import com.example.marvel_app.model.Item
import com.example.marvel_app.model.Sample
import com.example.marvel_app.model.Thumbnail
import com.example.marvel_app.model.Url
import com.google.gson.annotations.SerializedName

data class MarvelSerie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("urls") val urls: List<Url>,
    @SerializedName("startYear") val startYear: String,
    @SerializedName("endYear") val endYear: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("modified") val modified: String,
    @SerializedName("thumbnail") val thumbnail: Thumbnail,
    @SerializedName("comics") val comics: Sample,
    @SerializedName("stories") val stories: Sample,
    @SerializedName("events") val events: Sample,
    @SerializedName("characters") val characters: Sample,
    @SerializedName("creators") val creators: Sample,
    @SerializedName("next") val next: Item,
    @SerializedName("previous") val previous: Item,
)
