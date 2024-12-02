package com.example.moviebrowsingapp.data.repository

import com.example.moviebrowsingapp.data.remote.RetrofitInstance
import com.example.moviebrowsingapp.data.remote.model.MovieCast
import com.example.moviebrowsingapp.data.remote.model.MovieCastModelResponse
import com.example.moviebrowsingapp.data.remote.model.MovieDetailsModelResponse
import com.example.moviebrowsingapp.data.remote.model.MovieReviewResponseModel
import com.example.moviebrowsingapp.data.remote.model.NowPlayingModelResponse
import com.example.moviebrowsingapp.data.remote.model.PopularModelResponse
import com.example.moviebrowsingapp.data.remote.model.TopRatedModelResponse
import com.example.moviebrowsingapp.data.remote.model.TrendingMovieModelResponse
import com.example.moviebrowsingapp.data.remote.model.UpcomingModelResponse
import com.example.moviebrowsingapp.data.remote.model.VideoModelResponse
import retrofit2.Call

class MainRepository {

    fun getTrendingMovie(): Call<TrendingMovieModelResponse> {
        return RetrofitInstance.movieApi.getTrendingMovies()
    }

    fun getNowPlayingMovie(): Call<NowPlayingModelResponse> {
        return RetrofitInstance.movieApi.playingNow()
    }
    fun getUpComingMovie(): Call<UpcomingModelResponse> {
        return RetrofitInstance.movieApi.upcomingMovie()
    }

    fun getTopRatedMovie(): Call<TopRatedModelResponse> {
        return RetrofitInstance.movieApi.topRatedMovie()
    }
    fun getPopularMovie(): Call<PopularModelResponse> {
        return RetrofitInstance.movieApi.popularMovie()
    }


    fun getMovieCast(movieId:Int): Call<MovieCastModelResponse> {
        return RetrofitInstance.movieApi.castOfMovie(movieId)
    }

    fun getMovieDetails(movieId: Int?): Call<MovieDetailsModelResponse> {
        return RetrofitInstance.movieApi.movieDetailsApi(movieId)
    }

    fun getVideoTrailer(movieId: Int): Call<VideoModelResponse> {
        return RetrofitInstance.movieApi.videoTrailer(movieId)
    }

    fun getMovieReview(movieId: Int): Call<MovieReviewResponseModel> {
        return RetrofitInstance.movieApi.movieReview(movieId)
    }
}