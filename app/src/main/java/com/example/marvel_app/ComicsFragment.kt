package com.example.marvel_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.adapter.ComicAdapter
import com.example.marvel_app.model.MarvelCharacter.CharacterViewModel
import com.example.marvel_app.model.MarvelComic.ComicViewModel

class ComicsFragment : Fragment() {

    private var comicAdapter: ComicAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comics, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.comicList)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        comicAdapter = ComicAdapter(requireContext(), emptyList())
        recyclerView.adapter = comicAdapter

        val model = ViewModelProvider(this)[ComicViewModel::class.java]

        model.getComics()
            .observe(viewLifecycleOwner, { comics ->
                comicAdapter?.updateValue(comics)
            })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    model.loadComics()
                }
            }
        })

        return view
    }
}