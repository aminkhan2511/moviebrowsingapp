package com.example.moviebrowsingapp.data.remote.model

data class PopularModelResponse(
    val page: Int,
    val results: List<Popular>,
    val total_pages: Int,
    val total_results: Int
)