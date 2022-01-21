package com.example.marvel_app.view.serie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.R
import com.example.marvel_app.adapter.SerieAdapter
import com.example.marvel_app.model.MarvelSerie.SerieViewModel

class SeriesFragment : Fragment() {

    private var serieAdapter: SerieAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_series, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.serieList)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        serieAdapter = SerieAdapter(requireContext(), emptyList())
        recyclerView.adapter = serieAdapter

        val model = ViewModelProvider(this)[SerieViewModel::class.java]

        model.getSeries()
            .observe(viewLifecycleOwner, { series ->
                serieAdapter?.updateValue(series)
            })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    model.loadSeries()
                }
            }
        })

        return view
    }
}