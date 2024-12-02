package com.example.moviebrowsingapp.data.remote.model

data class TrendingMovieModelResponse(
    val page: Int,
    val results: List<TrendingMovie>,
    val total_pages: Int,
    val total_results: Int
)