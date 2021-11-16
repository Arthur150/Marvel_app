package com.example.marvel_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_app.adapter.SerieAdapter
import com.example.marvel_app.model.SerieViewModel

class SeriesFragment : Fragment() {

    private var serieAdapter: SerieAdapter? = null

    private val model = SerieViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_series, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.serieList)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        serieAdapter = SerieAdapter(requireContext(), emptyList())
        recyclerView.adapter = serieAdapter

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