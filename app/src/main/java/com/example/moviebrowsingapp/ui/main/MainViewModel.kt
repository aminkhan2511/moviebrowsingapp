package com.example.moviebrowsingapp.ui.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviebrowsingapp.data.remote.model.MovieCastModelResponse
import com.example.moviebrowsingapp.data.remote.model.MovieDetailsModelResponse
import com.example.moviebrowsingapp.data.remote.model.MovieReviewResponseModel
import com.example.moviebrowsingapp.data.remote.model.NowPlayingModelResponse
import com.example.moviebrowsingapp.data.remote.model.PopularModelResponse
import com.example.moviebrowsingapp.data.remote.model.TopRatedModelResponse
import com.example.moviebrowsingapp.data.remote.model.TrendingMovieModelResponse
import com.example.moviebrowsingapp.data.remote.model.UpcomingModelResponse
import com.example.moviebrowsingapp.data.remote.model.VideoModelResponse
import com.example.moviebrowsingapp.data.repository.MainRepository
import com.example.moviebrowsingapp.util.MovieBrowsingApplication
import com.example.moviebrowsingapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainViewModel(
    val mainRepository: MainRepository, application: Application
) : AndroidViewModel(application) {


    var trendingMoview: MutableLiveData<Resource<TrendingMovieModelResponse>?> = MutableLiveData()
    var nowPlayingMovie: MutableLiveData<Resource<NowPlayingModelResponse>?> = MutableLiveData()
    var upComingMovie: MutableLiveData<Resource<UpcomingModelResponse>?> = MutableLiveData()
    var topRatedMovie: MutableLiveData<Resource<TopRatedModelResponse>?> = MutableLiveData()
    var popularMovie: MutableLiveData<Resource<PopularModelResponse>?> = MutableLiveData()




    init {
        getTrendingMovie()
        getNowPlayingMovie()
        getUpComingMovieFunction()
        getTopRatedMovie()
        getPopularMovie()
    }






    private fun getPopularMovie() {

        viewModelScope.launch {
            popularMovie()
        }
    }

    private suspend fun popularMovie() {
        popularMovie.postValue(Resource.Loading())
        if (hasInternetConnection()) {

            mainRepository.getPopularMovie()
                .enqueue(object : retrofit2.Callback<PopularModelResponse> {

                    override fun onResponse(
                        p0: Call<PopularModelResponse>,
                        response: Response<PopularModelResponse>
                    ) {
                        popularMovie.postValue(handleResponsePopularMovie(response))
                    }

                    override fun onFailure(call: Call<PopularModelResponse>, t: Throwable) {
                        popularMovie.postValue(Resource.Error("Network Failure"))

                    }
                })
        } else {
            popularMovie.postValue(Resource.Error("No internet connection"))
        }

    }

    private fun handleResponsePopularMovie(response: Response<PopularModelResponse>): Resource<PopularModelResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)

            }
        }

        return Resource.Error(response.message())
    }

    private fun getTopRatedMovie() {
        viewModelScope.launch {
            topRatedMovieFunction()
        }
    }

    private suspend fun topRatedMovieFunction() {
        topRatedMovie.postValue(Resource.Loading())
        if (hasInternetConnection()) {

            mainRepository.getTopRatedMovie()
                .enqueue(object : retrofit2.Callback<TopRatedModelResponse> {

                    override fun onResponse(
                        p0: Call<TopRatedModelResponse>,
                        response: Response<TopRatedModelResponse>
                    ) {
                        topRatedMovie.postValue(handleResponseTopRatedMovie(response))
                    }

                    override fun onFailure(call: Call<TopRatedModelResponse>, t: Throwable) {
                        topRatedMovie.postValue(Resource.Error("Network Failure"))

                    }
                })
        } else {
            topRatedMovie.postValue(Resource.Error("No internet connection"))
        }
    }

    private fun handleResponseTopRatedMovie(response: Response<TopRatedModelResponse>): Resource<TopRatedModelResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)

            }
        }

        return Resource.Error(response.message())
    }

    private fun getUpComingMovieFunction() {
        viewModelScope.launch {
            upComingMovieFunction()
        }
    }

    private fun getNowPlayingMovie() {
        viewModelScope.launch {
            nowPlayingMovie()
        }
    }


    private suspend fun upComingMovieFunction() {
        upComingMovie.postValue(Resource.Loading())
        if (hasInternetConnection()) {

            mainRepository.getUpComingMovie()
                .enqueue(object : retrofit2.Callback<UpcomingModelResponse> {

                    override fun onResponse(
                        call: Call<UpcomingModelResponse>,
                        response: retrofit2.Response<UpcomingModelResponse>
                    ) {
                        upComingMovie.postValue(handleResponseUpcomingMovie(response))
                    }

                    override fun onFailure(call: Call<UpcomingModelResponse>, t: Throwable) {
                        upComingMovie.postValue(Resource.Error("Network Failure"))

                    }
                })
        } else {
            upComingMovie.postValue(Resource.Error("No internet connection"))
        }
    }

    private fun handleResponseUpcomingMovie(response: Response<UpcomingModelResponse>): Resource<UpcomingModelResponse>? {

        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)

            }
        }

        return Resource.Error(response.message())
    }


    private suspend fun nowPlayingMovie() {
        nowPlayingMovie.postValue(Resource.Loading())
        if (hasInternetConnection()) {

            mainRepository.getNowPlayingMovie()
                .enqueue(object : retrofit2.Callback<NowPlayingModelResponse> {
                    override fun onResponse(
                        call: Call<NowPlayingModelResponse>,
                        response: retrofit2.Response<NowPlayingModelResponse>
                    ) {
                        nowPlayingMovie.postValue(handleNowPlayingMovie(response))
                    }

                    override fun onFailure(call: Call<NowPlayingModelResponse>, t: Throwable) {
                        nowPlayingMovie.postValue(Resource.Error("Network Failure"))

                    }
                })
        } else {
            nowPlayingMovie.postValue(Resource.Error("No internet connection"))
        }
    }

    private fun handleNowPlayingMovie(response: Response<NowPlayingModelResponse>): Resource<NowPlayingModelResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                            return Resource.Success(resultResponse)

            }
        }

        return Resource.Error(response.message())
    }


    fun getTrendingMovie() =
        viewModelScope.launch {
            trendingMovies()
        }


    private suspend fun trendingMovies() {
        trendingMoview.postValue(Resource.Loading())
        if (hasInternetConnection()) {

            mainRepository.getTrendingMovie()
                .enqueue(object : retrofit2.Callback<TrendingMovieModelResponse> {
                    override fun onResponse(
                        call: Call<TrendingMovieModelResponse>,
                        response: retrofit2.Response<TrendingMovieModelResponse>
                    ) {
                        trendingMoview.postValue(handleTrendingMoviesResponse(response))
                    }

                    override fun onFailure(call: Call<TrendingMovieModelResponse>, t: Throwable) {
                        trendingMoview.postValue(Resource.Error("Network Failure"))
                    }
                })
        } else {
            trendingMoview.postValue(Resource.Error("No internet connection"))
        }
    }

    private fun handleTrendingMoviesResponse(response: Response<TrendingMovieModelResponse>): Resource<TrendingMovieModelResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)

            }
        }

        return Resource.Error(response.message())

    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<MovieBrowsingApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}

