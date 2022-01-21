package com.example.marvel_app.model.QRCode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelCharacter.MarvelCharacter
import com.example.marvel_app.model.MarvelComic.MarvelComic
import com.example.marvel_app.model.MarvelSerie.MarvelSerie
import com.example.marvel_app.usecase.characterUsecase.GetMarvelCharacterUseCase
import com.example.marvel_app.usecase.comicUsecase.GetMarvelComicUseCase
import com.example.marvel_app.usecase.serieUsecase.GetMarvelSerieUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class ScanQRCodeViewModel : ViewModel() {

    private val character = MutableLiveData<MarvelCharacter>()

    private val comic = MutableLiveData<MarvelComic>()

    private val serie = MutableLiveData<MarvelSerie>()

    fun getCharacter(): LiveData<MarvelCharacter> {
        return character
    }

    fun getComic(): LiveData<MarvelComic> {
        return comic
    }

    fun getSerie(): LiveData<MarvelSerie> {
        return serie
    }

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelCharacter>>(
                Gson().toJson(
                    GetMarvelCharacterUseCase(
                        id
                    ).execute().getOrNull()
                ),
                object : TypeToken<JsonResponse<MarvelCharacter>>() {}.type
            )

            character.postValue(jsonResponse.data.results.first())
        }
    }

    fun loadComic(id: Int) {
        viewModelScope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelComic>>(
                Gson().toJson(
                    GetMarvelComicUseCase(
                        id
                    ).execute().getOrNull()
                ),
                object : TypeToken<JsonResponse<MarvelComic>>() {}.type
            )

            comic.postValue(jsonResponse.data.results.first())
        }
    }

    fun loadSerie(id: Int) {
        viewModelScope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelSerie>>(
                Gson().toJson(
                    GetMarvelSerieUseCase(
                        id
                    ).execute().getOrNull()
                ),
                object : TypeToken<JsonResponse<MarvelSerie>>() {}.type
            )

            serie.postValue(jsonResponse.data.results.first())
        }
    }
}