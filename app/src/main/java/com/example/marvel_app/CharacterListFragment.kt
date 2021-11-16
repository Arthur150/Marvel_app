package com.example.marvel_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.marvel_app.adapter.CharacterAdapter
import com.example.marvel_app.model.CharacterViewModel

class CharacterListFragment : Fragment() {

    private var characterAdapter: CharacterAdapter? = null

    private val model = CharacterViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.characterList)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        characterAdapter = CharacterAdapter(requireContext(), emptyList())
        recyclerView.adapter = characterAdapter

        model.getCharacters()
            .observe(viewLifecycleOwner, { characters ->
                characterAdapter?.updateValue(characters)
            })

        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    model.loadCharacters()
                }
            }
        })

        return view
    }

}