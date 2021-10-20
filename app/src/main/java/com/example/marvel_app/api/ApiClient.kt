package com.example.marvel_app.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://gateway.marvel.com:443"

object ApiClient {

    private val gson = GsonBuilder().setLenient().create()

    private val client = OkHttpClient.Builder()
        .addInterceptor(MarvelInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val service: ApiInterface = retrofit.create(ApiInterface::class.java)

}