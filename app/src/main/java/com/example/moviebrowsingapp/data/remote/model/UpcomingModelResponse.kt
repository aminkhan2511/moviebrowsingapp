package com.example.moviebrowsingapp.data.remote.model

data class UpcomingModelResponse(
    val dates: DatesX,
    val page: Int,
    val results: List<Upcoming>,
    val total_pages: Int,
    val total_results: Int
)