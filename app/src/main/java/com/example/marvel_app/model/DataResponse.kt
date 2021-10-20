package com.example.marvel_app.model

import com.google.gson.annotations.SerializedName

data class DataResponse<T>(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("results") val results: List<T>
)
