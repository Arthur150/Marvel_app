package com.example.marvel_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelCharacter
import com.example.marvel_app.usecase.GetMarvelCharacterUseCase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.IO)

    var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNav()

        scope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelCharacter>>(
                Gson().toJson(GetMarvelCharacterUseCase(0).execute().getOrNull()),
                object : TypeToken<JsonResponse<MarvelCharacter>>() {}.type
            )
            Log.d("Main", "onCreate: $jsonResponse")

        }
    }

    private fun initNav() {
        Log.d("Main", "initNav: start")
        bottomNavigationView = findViewById(R.id.bottomNavBar)
        bottomNavigationView?.itemIconTintList = null
        bottomNavigationView?.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
        bottomNavigationView?.selectedItemId = R.id.characters
        Log.d("Main", "initNav: end")
    }
}