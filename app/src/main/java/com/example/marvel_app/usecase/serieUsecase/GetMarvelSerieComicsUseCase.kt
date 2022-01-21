package com.example.marvel_app.usecase.serieUsecase

import com.example.marvel_app.api.ApiClient
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelComic.MarvelComic
import com.example.marvel_app.usecase.UseCase
import retrofit2.Response

class GetMarvelSerieComicsUseCase(private val serieId: Int, private val offset: Int) :
    UseCase<JsonResponse<MarvelComic>?> {
    override suspend fun execute(): Result<JsonResponse<MarvelComic>?> {
        return try {
            val response: Response<JsonResponse<MarvelComic>> =
                ApiClient.service.getSerieComics(serieId, offset)

            if (response.isSuccessful) {
                Result.success(response.body())
            } else throw IllegalStateException("${response.code()}")

        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}