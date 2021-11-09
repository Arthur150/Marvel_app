package com.example.marvel_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.adapter.CharacterAdapter
import com.example.marvel_app.model.JsonResponse
import com.example.marvel_app.model.MarvelCharacter
import com.example.marvel_app.usecase.GetMarvelCharacterUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterListFragment : Fragment() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private var characterList: List<MarvelCharacter> = ArrayList()

    private var characterAdapter: CharacterAdapter? = null

    private var characterOffset = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.characterList)

        characterAdapter =  CharacterAdapter(requireContext(), characterList)

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerView.adapter = characterAdapter

        callCharacters()

        return view
    }

    private fun callCharacters() {
        scope.launch {
            val jsonResponse = Gson().fromJson<JsonResponse<MarvelCharacter>>(
                Gson().toJson(GetMarvelCharacterUseCase(characterOffset).execute().getOrNull()),
                object : TypeToken<JsonResponse<MarvelCharacter>>() {}.type
            )
            Log.d("Main", "onCreate: $jsonResponse")

            characterOffset = jsonResponse.data.offset + jsonResponse.data.count
            characterList = jsonResponse.data.results

            activity?.runOnUiThread {
                characterAdapter?.updateValue(jsonResponse.data.results)
            }

            Log.d("Main", "callCharacters: $characterList")
        }
    }

}