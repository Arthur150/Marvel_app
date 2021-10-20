package com.example.marvel_app.usecase

import android.util.Log
import com.example.marvel_app.api.ApiClient
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelCharacter
import retrofit2.Response

class GetMarvelCharacterUseCase(private val offset: Int) : UseCase<String> {
    override suspend fun execute(): Result<String> {
        return try {
            val response: Response<JsonResponse<MarvelCharacter>> = ApiClient.service.getCharacters(offset)

            if (response.isSuccessful) {
                Log.d("MainActivity", "execute: nique ta mère ")
                Result.success(response.body().toString())
            } else throw IllegalStateException("${response.code()}")

        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}