package com.example.marvel_app.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TextObject(
    @SerializedName("type") val type: String,
    @SerializedName("language") val language: String,
    @SerializedName("text") val text: String,
) : Serializable
