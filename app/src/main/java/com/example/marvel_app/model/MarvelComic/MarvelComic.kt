package com.example.marvel_app.model.MarvelComic

import com.example.marvel_app.model.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MarvelComic(
    @SerializedName("id") val id: Int,
    @SerializedName("digitalId") val digitalId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("issueNumber") val issueNumber: Double,
    @SerializedName("variantDescription") val variantDescription: String,
    @SerializedName("description") val description: String,
    @SerializedName("modified") val modified: String,
    @SerializedName("isbn") val isbn: String,
    @SerializedName("upc") val upc: String,
    @SerializedName("diamondCode") val diamondCode: String,
    @SerializedName("ean") val ean: String,
    @SerializedName("issn") val issn: String,
    @SerializedName("format") val format: String,
    @SerializedName("pageCount") val pageCount: Int,
    @SerializedName("textObjects") val textObjects: List<TextObject>,
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("urls") val urls: List<Url>,
    @SerializedName("series") val series: Item,
    @SerializedName("variants") val variants: List<Item>,
    @SerializedName("collections") val collections: List<Item>,
    @SerializedName("collectedIssues") val collectedIssues: List<Item>,
    @SerializedName("dates") val dates: List<ComicDate>,
    @SerializedName("prices") val prices: List<ComicPrice>,
    @SerializedName("thumbnail") val thumbnail: Thumbnail,
    @SerializedName("images") val images: List<Thumbnail>,
    @SerializedName("creators") val creators: Sample,
    @SerializedName("stories") val stories: Sample,
    @SerializedName("events") val events: Sample,
) : Serializable
