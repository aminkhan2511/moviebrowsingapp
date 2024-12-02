package com.example.moviebrowsingapp.data.remote.model

data class MovieReviewResponseModel(
    val id: Int,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)