package com.example.moviebrowsingapp.ui.moviedetails.aboutcast

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
import com.example.moviebrowsingapp.ui.moviedetails.MovieDetailsActivity
import com.example.moviebrowsingapp.ui.moviedetails.DetailViewModel
import com.example.moviebrowsingapp.util.Resource


class CastFragment : Fragment() {

    lateinit var detailViewModel: DetailViewModel
    lateinit var recyCast: RecyclerView
    lateinit var aboutCastAdapter: AboutCastAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cast, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel = (activity as MovieDetailsActivity).detailViewModel
        recyCast = view.findViewById<RecyclerView>(R.id.recyCast)
        movieDetails()
        setupRecyclerView(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupRecyclerView(view: View) {
        aboutCastAdapter = AboutCastAdapter()
        recyCast = view.findViewById<RecyclerView>(R.id.recyCast)

        recyCast.apply {
            adapter = aboutCastAdapter
//            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            layoutManager=  GridLayoutManager(activity, 2)

        }


    }

    private fun movieDetails() {

        detailViewModel.movieDetails.observe(viewLifecycleOwner, Observer { response ->


            when (response) {
                is Resource.Success -> {
                    response.data?.let { movieDetailsResponse ->

                        movieCast(movieDetailsResponse.id)
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

    private fun movieCast(id: Int) {
        detailViewModel.movieCast.observe(viewLifecycleOwner, Observer { response ->


            when (response) {
                is Resource.Success -> {
                    response.data?.let { movieCast ->

                        aboutCastAdapter.differ.submitList(movieCast.cast)
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


}