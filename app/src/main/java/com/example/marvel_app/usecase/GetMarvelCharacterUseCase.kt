package com.example.marvel_app.usecase

import android.util.Log
import com.example.marvel_app.api.ApiClient
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelCharacter.MarvelCharacter
import retrofit2.Response

class GetMarvelCharacterUseCase(private val offset: Int) : UseCase<JsonResponse<MarvelCharacter>?> {
    override suspend fun execute(): Result<JsonResponse<MarvelCharacter>?> {
        return try {
            val response: Response<JsonResponse<MarvelCharacter>> = ApiClient.service.getCharacters(offset)

            if (response.isSuccessful) {
                Log.d("api", "execute: characters")
                Result.success(response.body())
            } else throw IllegalStateException("${response.code()}")

        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}