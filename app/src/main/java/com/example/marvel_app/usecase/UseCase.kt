package com.example.marvel_app.usecase

interface UseCase<out T> {
    suspend fun execute(): Result<T>
}