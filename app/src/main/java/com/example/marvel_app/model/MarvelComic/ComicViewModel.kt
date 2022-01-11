package com.example.marvel_app.model.MarvelComic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.usecase.comicUsecase.GetMarvelComicsUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class ComicViewModel : ViewModel() {
    private val comics: MutableLiveData<List<MarvelComic>> by lazy {
        MutableLiveData<List<MarvelComic>>().also {
            loadComics()
        }
    }

    private val comicList = ArrayList<MarvelComic>()

    private var offset = 0

    fun getComics(): LiveData<List<MarvelComic>> {
        return comics
    }

    fun loadComics() {
        viewModelScope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelComic>>(
                Gson().toJson(GetMarvelComicsUseCase(offset).execute().getOrNull()),
                object : TypeToken<JsonResponse<MarvelComic>>() {}.type
            )

            offset = jsonResponse.data.offset + jsonResponse.data.count

            comicList.addAll(jsonResponse.data.results)

            val tempList = List(comicList.size) {
                comicList[it]
            }

            comics.postValue(tempList)
        }
    }
}