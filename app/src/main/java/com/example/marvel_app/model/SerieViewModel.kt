package com.example.marvel_app.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel_app.usecase.GetMarvelCharacterUseCase
import com.example.marvel_app.usecase.GetMarvelSeriesUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class SerieViewModel: ViewModel() {
    private val series: MutableLiveData<List<MarvelSerie>> by lazy {
        MutableLiveData<List<MarvelSerie>>().also {
            loadSeries()
        }
    }

    private val serieList = ArrayList<MarvelSerie>()

    private var offset = 0

    fun getSeries(): LiveData<List<MarvelSerie>> {
        return series
    }

    fun loadSeries() {
        viewModelScope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelSerie>>(
                Gson().toJson(GetMarvelSeriesUseCase(offset).execute().getOrNull()),
                object : TypeToken<JsonResponse<MarvelSerie>>() {}.type
            )
            Log.d("ViewModel", "loadSeries: $jsonResponse")

            offset = jsonResponse.data.offset + jsonResponse.data.count

            serieList.addAll(jsonResponse.data.results)

            val tempList = List(serieList.size) {
                serieList[it]
            }

            series.postValue(tempList)
        }
    }
}