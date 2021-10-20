package com.example.marvel_app.model

import com.google.gson.annotations.SerializedName

data class Sample(
    @SerializedName("available") val available: Int,
    @SerializedName("collectionURI") val collectionURI: String,
    @SerializedName("items") val items: List<Item>,
    @SerializedName("returned") val returned: Int?
)