package com.example.marvel_app.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Sample(
    @SerializedName("available") val available: Int,
    @SerializedName("collectionURI") val collectionURI: String,
    @SerializedName("items") val items: List<Item>,
    @SerializedName("returned") val returned: Int?,
    @SerializedName("role") val role: String?,
) : Serializable