package com.example.moviebrowsingapp.ui.nowplaying

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebrowsingapp.R
import com.example.moviebrowsingapp.data.remote.model.NowPlaying
import com.example.moviebrowsingapp.ui.main.MainActivity
import com.example.moviebrowsingapp.ui.moviedetails.MovieDetailsActivity
import com.example.moviebrowsingapp.ui.main.MainViewModel
import com.example.moviebrowsingapp.util.Constant.Companion.MOVIE_ID_KEY
import com.example.moviebrowsingapp.util.Resource


class NowPlayingFragment : Fragment() {



    lateinit var fragmentContainerNP: FrameLayout

    lateinit var nowPlayingMovieAdapter: NowPlayingMovieAdapter
    lateinit var mainViewModelHome: MainViewModel
    lateinit var recyNowPlaying: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.now_playing_fragment, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModelHome = (activity as MainActivity).mainViewModel
        setupRecyclerView(view)
        setNowPlayingData()

        fragmentContainerNP=view.findViewById<FrameLayout>(R.id.fragmentContainerNP)
    }

    private fun setNowPlayingData() {

        mainViewModelHome.nowPlayingMovie.observe(viewLifecycleOwner, Observer { response ->


            when (response) {
                is Resource.Success -> {
                    response.data?.let { nowPlayingResponse ->

                        nowPlayingMovieAdapter.differ.submitList(nowPlayingResponse.results)
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
        nowPlayingMovieAdapter = NowPlayingMovieAdapter(
            ::movieDataNowPlaying,
            requireContext()
        )
        recyNowPlaying = view.findViewById<RecyclerView>(R.id.recyNowPlaying)

        recyNowPlaying.apply {
            adapter = nowPlayingMovieAdapter
//            layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
            layoutManager = GridLayoutManager(activity, 2)

        }


    }

    fun movieDataNowPlaying(movie: NowPlaying) {


        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_ID_KEY,movie.id)
        startActivity(intent)

    }

}