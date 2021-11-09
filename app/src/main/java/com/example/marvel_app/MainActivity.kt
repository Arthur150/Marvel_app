package com.example.marvel_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.adapter.CharacterAdapter
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

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private var bottomNavigationView: BottomNavigationView? = null
    private var recyclerView: RecyclerView? = null

    private var characterList: List<MarvelCharacter> = ArrayList()

    private var characterAdapter: CharacterAdapter = CharacterAdapter(this, characterList)

    private var characterOffset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavBar)
        initNav()

        recyclerView = findViewById(R.id.characterList)
        recyclerView?.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = characterAdapter
        }


        callCharacters()

    }

    private fun initNav() {
        bottomNavigationView?.itemIconTintList = null
        bottomNavigationView?.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_SELECTED
        bottomNavigationView?.selectedItemId = R.id.characters
    }

    private fun callCharacters(){
        scope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelCharacter>>(
                Gson().toJson(GetMarvelCharacterUseCase(characterOffset).execute().getOrNull()),
                object : TypeToken<JsonResponse<MarvelCharacter>>() {}.type
            )
            Log.d("Main", "onCreate: $jsonResponse")

            characterOffset = jsonResponse.data.offset + jsonResponse.data.count
            characterList = jsonResponse.data.results
            characterAdapter.notifyDataSetChanged()
        }
    }
}