package com.example.marvel_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.adapter.ComicAdapter
import com.example.marvel_app.model.MarvelComic.ComicViewModel

class ComicsFragment : Fragment() {

    private var comicAdapter: ComicAdapter? = null

    private val model = ComicViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comics, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.comicList)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        comicAdapter = ComicAdapter(requireContext(), emptyList())
        recyclerView.adapter = comicAdapter

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