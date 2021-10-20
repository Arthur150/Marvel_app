package com.example.marvel_app.api

import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int
    ): Response<JsonResponse<MarvelCharacter>>
}