package com.example.marvel_app.api

import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelCharacter.MarvelCharacter
import com.example.marvel_app.model.MarvelComic.MarvelComic
import com.example.marvel_app.model.MarvelSerie.MarvelSerie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("offset") offset: Int
    ): Response<JsonResponse<MarvelCharacter>>

    @GET("/v1/public/series")
    suspend fun getSeries(
        @Query("offset") offset: Int
    ): Response<JsonResponse<MarvelSerie>>

    @GET("/v1/public/comics")
    suspend fun getComics(
        @Query("offset") offset: Int
    ): Response<JsonResponse<MarvelComic>>

    @GET("/v1/public/characters/{id}/comics")
    suspend fun getCharacterComics(
        @Path("id") characterId: Int,
        @Query("offset") offset: Int
    ): Response<JsonResponse<MarvelComic>>

    @GET("/v1/public/characters/{id}/series")
    suspend fun getCharacterSeries(
        @Path("id") characterId: Int,
        @Query("offset") offset: Int
    ): Response<JsonResponse<MarvelSerie>>

    @GET("/v1/public/series/{id}/comics")
    suspend fun getSerieComics(
        @Path("id") serieId: Int,
        @Query("offset") offset: Int
    ): Response<JsonResponse<MarvelComic>>

    @GET("/v1/public/series/{id}/characters")
    suspend fun getSerieCharacters(
        @Path("id") serieId: Int,
        @Query("offset") offset: Int
    ): Response<JsonResponse<MarvelCharacter>>
}