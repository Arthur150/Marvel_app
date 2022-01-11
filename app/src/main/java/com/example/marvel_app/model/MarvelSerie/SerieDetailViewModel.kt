package com.example.marvel_app.model.MarvelSerie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelCharacter.MarvelCharacter
import com.example.marvel_app.model.MarvelComic.MarvelComic
import com.example.marvel_app.usecase.serieUsecase.GetMarvelSerieCharactersUseCase
import com.example.marvel_app.usecase.serieUsecase.GetMarvelSerieComicsUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class SerieDetailViewModel(val serie: MarvelSerie) : ViewModel() {

    private val comics: MutableLiveData<List<MarvelComic>> by lazy {
        MutableLiveData<List<MarvelComic>>().also {
            loadComics()
        }
    }

    private val characters: MutableLiveData<List<MarvelCharacter>> by lazy {
        MutableLiveData<List<MarvelCharacter>>().also {
            loadCharacters()
        }
    }

    private val comicList = ArrayList<MarvelComic>()

    private var offsetComics = 0

    private val characterList = ArrayList<MarvelCharacter>()

    private var offset = 0

    fun getComics(): LiveData<List<MarvelComic>> {
        return comics
    }

    fun getCharacters(): LiveData<List<MarvelCharacter>> {
        return characters
    }

    fun loadComics() {
        viewModelScope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelComic>>(
                Gson().toJson(
                    GetMarvelSerieComicsUseCase(serie.id, offsetComics).execute().getOrNull()
                ),
                object : TypeToken<JsonResponse<MarvelComic>>() {}.type
            )

            offsetComics = jsonResponse.data.offset + jsonResponse.data.count

            comicList.addAll(jsonResponse.data.results)

            val tempList = List(comicList.size) {
                comicList[it]
            }

            comics.postValue(tempList)
        }
    }

    fun loadCharacters() {
        viewModelScope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelCharacter>>(
                Gson().toJson(
                    GetMarvelSerieCharactersUseCase(serie.id, offset).execute().getOrNull()
                ),
                object : TypeToken<JsonResponse<MarvelCharacter>>() {}.type
            )

            offset = jsonResponse.data.offset + jsonResponse.data.count

            characterList.addAll(jsonResponse.data.results)

            val tempList = List(characterList.size) {
                characterList[it]
            }

            characters.postValue(tempList)
        }
    }
}