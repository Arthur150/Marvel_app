package com.example.marvel_app.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Item(
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String?,
    @SerializedName("role") val role: String?,
) : Serializable
