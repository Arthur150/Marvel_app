package com.example.marvel_app.model.QRCode

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class QRCodeData(
    @SerializedName("type") val type: String,
    @SerializedName("id") val id: Int,
) : Serializable
