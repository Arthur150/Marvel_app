package com.example.marvel_app.model.MarvelCharacter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.usecase.characterUsecase.GetMarvelCharacterUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {
    private val characters: MutableLiveData<List<MarvelCharacter>> by lazy {
        MutableLiveData<List<MarvelCharacter>>().also {
            loadCharacters()
        }
    }

    private val characterList = ArrayList<MarvelCharacter>()

    private var offset = 0

    fun getCharacters(): LiveData<List<MarvelCharacter>> {
        return characters
    }

    fun loadCharacters() {
        viewModelScope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelCharacter>>(
                Gson().toJson(GetMarvelCharacterUseCase(offset).execute().getOrNull()),
                object : TypeToken<JsonResponse<MarvelCharacter>>() {}.type
            )
            Log.d("ViewModel", "loadCharacters: $jsonResponse")

            offset = jsonResponse.data.offset + jsonResponse.data.count

            characterList.addAll(jsonResponse.data.results)

            val tempList = List(characterList.size) {
                characterList[it]
            }

            characters.postValue(tempList)
        }
    }
}