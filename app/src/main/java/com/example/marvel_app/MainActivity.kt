package com.example.marvel_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelCharacter
import com.example.marvel_app.usecase.GetMarvelCharacterUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runBlocking {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelCharacter>>(
                GetMarvelCharacterUseCase(0).execute().getOrNull(),
                object : TypeToken<JsonResponse<MarvelCharacter>>() {}.type
            )
            Log.d(TAG, "onCreate: $jsonResponse")

        }
    }
}