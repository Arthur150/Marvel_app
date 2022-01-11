package com.example.marvel_app.usecase.comicUsecase

import android.util.Log
import com.example.marvel_app.api.ApiClient
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelComic.MarvelComic
import com.example.marvel_app.model.MarvelSerie.MarvelSerie
import com.example.marvel_app.usecase.UseCase
import retrofit2.Response

class GetMarvelComicUseCase(private val comicId: Int) :
    UseCase<JsonResponse<MarvelComic>?> {
    override suspend fun execute(): Result<JsonResponse<MarvelComic>?> {
        return try {
            val response: Response<JsonResponse<MarvelComic>> =
                ApiClient.service.getComic(comicId)

            if (response.isSuccessful) {
                Result.success(response.body())
            } else throw IllegalStateException("${response.code()}")

        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}