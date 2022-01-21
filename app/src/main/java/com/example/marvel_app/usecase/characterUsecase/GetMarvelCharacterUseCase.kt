package com.example.marvel_app.usecase.characterUsecase

import com.example.marvel_app.api.ApiClient
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelCharacter.MarvelCharacter
import com.example.marvel_app.usecase.UseCase
import retrofit2.Response

class GetMarvelCharacterUseCase(private val characterId: Int) :
    UseCase<JsonResponse<MarvelCharacter>?> {
    override suspend fun execute(): Result<JsonResponse<MarvelCharacter>?> {
        return try {
            val response: Response<JsonResponse<MarvelCharacter>> =
                ApiClient.service.getCharacter(characterId)

            if (response.isSuccessful) {
                Result.success(response.body())
            } else throw IllegalStateException("${response.code()}")

        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}