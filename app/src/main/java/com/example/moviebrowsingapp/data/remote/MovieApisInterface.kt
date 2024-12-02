package com.example.moviebrowsingapp.data.remote

import com.example.moviebrowsingapp.data.remote.model.MovieCastModelResponse
import com.example.moviebrowsingapp.data.remote.model.MovieDetailsModelResponse
import com.example.moviebrowsingapp.data.remote.model.MovieReviewResponseModel
import com.example.moviebrowsingapp.data.remote.model.NowPlayingModelResponse
import com.example.moviebrowsingapp.data.remote.model.PopularModelResponse
import com.example.moviebrowsingapp.data.remote.model.TopRatedModelResponse
import com.example.moviebrowsingapp.data.remote.model.TrendingMovieModelResponse
import com.example.moviebrowsingapp.data.remote.model.UpcomingModelResponse
import com.example.moviebrowsingapp.data.remote.model.VideoModelResponse
import com.example.moviebrowsingapp.util.Constant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApisInterface {

    // TRENDING
    @GET("trending/movie/{time_window}")
    fun getTrendingMovies(
        @Path("time_window") day: String = "day",
        @Query("api_key") apiKey: String = Constant.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Call<TrendingMovieModelResponse>


    //PLAYING NOW
    @GET("movie/now_playing")
    fun playingNow(
        @Query("api_key") apiKey: String = Constant.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Call<NowPlayingModelResponse>


    // POPULAR
    @GET("movie/popular")
    fun popularMovie(
        @Query("api_key") apiKey: String = Constant.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Call<PopularModelResponse>

    //TOP RATED
    @GET("movie/top_rated")
    fun topRatedMovie(
        @Query("api_key") apiKey: String = Constant.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Call<TopRatedModelResponse>


    //    UPCOMING
    @GET("movie/upcoming")
    fun upcomingMovie(
        @Query("api_key") apiKey: String = Constant.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Call<UpcomingModelResponse>


    //MOVIE DETAILS
    @GET("movie/{movie_id}")
    fun movieDetailsApi(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String = Constant.API_KEY
    ): Call<MovieDetailsModelResponse>


    //MOVIE CAST
    @GET("movie/{movie_id}/credits")
    fun castOfMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Constant.API_KEY
    ): Call<MovieCastModelResponse>


    //VIDEO TRAILER
    @GET("movie/{movie_id}/videos")
    fun videoTrailer(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Constant.API_KEY
    ): Call<VideoModelResponse>

    //VIDEO TRAILER
    @GET("movie/{movie_id}/reviews")
    fun movieReview(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Constant.API_KEY,
        @Query("language") language: String = "en-US",
         @Query("page") page: Int = 1
    ): Call<MovieReviewResponseModel>
}