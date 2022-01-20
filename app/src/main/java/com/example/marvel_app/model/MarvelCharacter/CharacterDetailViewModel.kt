package com.example.marvel_app.model.MarvelCharacter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelComic.MarvelComic
import com.example.marvel_app.model.MarvelSerie.MarvelSerie
import com.example.marvel_app.usecase.characterUsecase.GetMarvelCharacterComicsUseCase
import com.example.marvel_app.usecase.characterUsecase.GetMarvelCharacterSeriesUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class CharacterDetailViewModel(val character: MarvelCharacter) : ViewModel() {

    private val comics: MutableLiveData<List<MarvelComic>> by lazy {
        MutableLiveData<List<MarvelComic>>().also {
            loadComics()
        }
    }

    private val series: MutableLiveData<List<MarvelSerie>> by lazy {
        MutableLiveData<List<MarvelSerie>>().also {
            loadSeries()
        }
    }

    private val comicList = ArrayList<MarvelComic>()

    private var offsetComics = 0

    private val serieList = ArrayList<MarvelSerie>()

    private var offsetSeries = 0

    fun getComics(): LiveData<List<MarvelComic>> {
        return comics
    }

    fun getSeries(): LiveData<List<MarvelSerie>> {
        return series
    }

    fun loadComics() {
        viewModelScope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelComic>>(
                Gson().toJson(
                    GetMarvelCharacterComicsUseCase(character.id, offsetComics).execute()
                        .getOrNull()
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

    fun loadSeries() {
        viewModelScope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelSerie>>(
                Gson().toJson(
                    GetMarvelCharacterSeriesUseCase(character.id, offsetSeries).execute()
                        .getOrNull()
                ),
                object : TypeToken<JsonResponse<MarvelSerie>>() {}.type
            )

            offsetSeries = jsonResponse.data.offset + jsonResponse.data.count

            serieList.addAll(jsonResponse.data.results)

            val tempList = List(serieList.size) {
                serieList[it]
            }

            series.postValue(tempList)
        }
    }
}