package com.example.moviebrowsingapp.ui.moviedetails.aboutmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.moviebrowsingapp.R
import com.example.moviebrowsingapp.ui.moviedetails.MovieDetailsActivity
import com.example.moviebrowsingapp.ui.moviedetails.DetailViewModel
import com.example.moviebrowsingapp.util.Resource


class AboutMovieFragment() : Fragment() {
    lateinit var detailViewModel: DetailViewModel

lateinit var descritption:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel = (activity as MovieDetailsActivity).detailViewModel
        descritption=view.findViewById<TextView>(R.id.textViewMovieDescription)
        setMovieDetailsDataOnViews()
    }





    private fun setMovieDetailsDataOnViews() {

        detailViewModel.movieDetails.observe(viewLifecycleOwner, Observer {response ->


            when(response) {
                is Resource.Success -> {
                    response.data?.let { movieDetailsResponse ->
                        descritption.text=movieDetailsResponse?.overview

                    }
                }

                is Resource.Error ->  Toast.makeText(requireContext(),"Error", Toast.LENGTH_LONG).show()
                is Resource.Loading ->  Toast.makeText(activity,"Loading data...", Toast.LENGTH_SHORT).show()
                null -> TODO()
            }
        })
    }



}