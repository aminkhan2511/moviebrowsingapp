package com.example.moviebrowsingapp.ui.toprated

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebrowsingapp.R
import com.example.moviebrowsingapp.data.remote.model.TopRated
import com.example.moviebrowsingapp.ui.main.MainActivity
import com.example.moviebrowsingapp.ui.moviedetails.MovieDetailsActivity
import com.example.moviebrowsingapp.ui.main.MainViewModel
import com.example.moviebrowsingapp.util.Constant.Companion.MOVIE_ID_KEY
import com.example.moviebrowsingapp.util.Resource


class TopRatedFragment : Fragment() {

    lateinit var topRatedMovieAdapter: TopRatedMovieAdapter
    lateinit var mainViewModelHome: MainViewModel
    lateinit var recyTopRated: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.top_rated_fragment, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModelHome = (activity as MainActivity).mainViewModel
        setupRecyclerView(view)
        setNowPlayingData()
    }

    private fun setNowPlayingData() {

        mainViewModelHome.topRatedMovie.observe(viewLifecycleOwner, Observer { response ->


            when (response) {
                is Resource.Success -> {
                    response.data?.let { nowPlayingResponse ->

                        topRatedMovieAdapter.differ.submitList(nowPlayingResponse.results)
                    }
                }

                is Resource.Error -> Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG)
                    .show()

                is Resource.Loading -> Toast.makeText(
                    activity,
                    "Loading data...",
                    Toast.LENGTH_SHORT
                ).show()

                null -> TODO()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupRecyclerView(view: View) {
        topRatedMovieAdapter = TopRatedMovieAdapter(::topRatedMoviePoster, requireContext())
        recyTopRated = view.findViewById<RecyclerView>(R.id.recyTopRated)

        recyTopRated.apply {
            adapter = topRatedMovieAdapter
            layoutManager = GridLayoutManager(activity, 2)

        }


    }

    fun topRatedMoviePoster(movie: TopRated) {

        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_ID_KEY,movie.id)
        startActivity(intent)

    }
}