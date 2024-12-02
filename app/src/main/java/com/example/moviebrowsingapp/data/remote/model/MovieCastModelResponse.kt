package com.example.moviebrowsingapp.data.remote.model

data class MovieCastModelResponse(
    val cast: List<MovieCast>,
    val crew: List<Crew>,
    val id: Int
)