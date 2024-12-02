package com.example.moviebrowsingapp.data.remote.model

data class NowPlayingModelResponse(
    val dates: Dates,
    val page: Int,
    val results: List<NowPlaying>,
    val total_pages: Int,
    val total_results: Int
)