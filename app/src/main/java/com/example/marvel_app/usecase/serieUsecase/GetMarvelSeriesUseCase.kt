package com.example.marvel_app.usecase.serieUsecase

import android.util.Log
import com.example.marvel_app.api.ApiClient
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelSerie.MarvelSerie
import com.example.marvel_app.usecase.UseCase
import retrofit2.Response

class GetMarvelSeriesUseCase(private val offset: Int) : UseCase<JsonResponse<MarvelSerie>?> {
    override suspend fun execute(): Result<JsonResponse<MarvelSerie>?> {
        return try {
            val response: Response<JsonResponse<MarvelSerie>> = ApiClient.service.getSeries(offset)

            if (response.isSuccessful) {
                Result.success(response.body())
            } else throw IllegalStateException("${response.code()}")

        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}