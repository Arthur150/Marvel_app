package com.example.marvel_app.api

import com.example.marvel_app.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val gson = GsonBuilder().setLenient().create()

    private val client = OkHttpClient.Builder()
        .addInterceptor(MarvelInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.WSUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val service: ApiInterface = retrofit.create(ApiInterface::class.java)

}