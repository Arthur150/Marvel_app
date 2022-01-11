package com.example.marvel_app.model.MarvelComic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelCharacter.MarvelCharacter
import com.example.marvel_app.model.MarvelSerie.MarvelSerie
import com.example.marvel_app.usecase.comicUsecase.GetMarvelComicCharactersUseCase
import com.example.marvel_app.usecase.comicUsecase.GetMarvelComicSeriesUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class ComicDetailViewModel(val comic: MarvelComic) : ViewModel() {

    private val serie: MutableLiveData<MarvelSerie> by lazy {
        MutableLiveData<MarvelSerie>().also {
            loadSerie()
        }
    }

    private val characters: MutableLiveData<List<MarvelCharacter>> by lazy {
        MutableLiveData<List<MarvelCharacter>>().also {
            loadCharacters()
        }
    }

    private val characterList = ArrayList<MarvelCharacter>()

    private var offsetCharacter = 0

    fun getSerie(): LiveData<MarvelSerie> {
        return serie
    }

    fun getCharacters(): LiveData<List<MarvelCharacter>> {
        return characters
    }

    fun loadSerie() {
        viewModelScope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelSerie>>(
                Gson().toJson(
                    GetMarvelComicSeriesUseCase(
                        comic.series.resourceURI.split('/').last().toInt()
                    ).execute().getOrNull()
                ),
                object : TypeToken<JsonResponse<MarvelSerie>>() {}.type
            )

            serie.postValue(jsonResponse.data.results.first())
        }
    }

    fun loadCharacters() {
        viewModelScope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelCharacter>>(
                Gson().toJson(
                    GetMarvelComicCharactersUseCase(comic.id, offsetCharacter).execute().getOrNull()
                ),
                object : TypeToken<JsonResponse<MarvelCharacter>>() {}.type
            )

            offsetCharacter = jsonResponse.data.offset + jsonResponse.data.count

            characterList.addAll(jsonResponse.data.results)

            val tempList = List(characterList.size) {
                characterList[it]
            }

            characters.postValue(tempList)
        }
    }
}