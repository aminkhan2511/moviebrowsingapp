package com.example.moviebrowsingapp.data.remote.model

data class TopRatedModelResponse(
    val page: Int,
    val results: List<TopRated>,
    val total_pages: Int,
    val total_results: Int
)