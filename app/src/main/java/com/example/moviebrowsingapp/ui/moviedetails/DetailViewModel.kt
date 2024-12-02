package com.example.moviebrowsingapp.ui.moviedetails


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
import com.example.moviebrowsingapp.data.remote.model.VideoModelResponse
import com.example.moviebrowsingapp.data.repository.MainRepository
import com.example.moviebrowsingapp.util.MovieBrowsingApplication
import com.example.moviebrowsingapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class DetailViewModel(
    val mainRepository: MainRepository, application: Application
) : AndroidViewModel(application) {




    var movieDetails: MutableLiveData<Resource<MovieDetailsModelResponse>?> = MutableLiveData()
    var movieCast: MutableLiveData<Resource<MovieCastModelResponse>?> = MutableLiveData()
    var movieReview: MutableLiveData<Resource<MovieReviewResponseModel>?> = MutableLiveData()






    fun getMovieCast(movieId: Int) {

        viewModelScope.launch {
            movieCast(movieId)
        }
    }

    private suspend fun movieCast(movieId: Int) {
        movieCast.postValue(Resource.Loading())
        if (hasInternetConnection()) {

            mainRepository.getMovieCast(movieId)
                .enqueue(object : retrofit2.Callback<MovieCastModelResponse> {

                    override fun onResponse(
                        p0: Call<MovieCastModelResponse>,
                        response: Response<MovieCastModelResponse>
                    ) {
                        movieCast.postValue(handleMovieCastResponse(response))
                    }

                    override fun onFailure(call: Call<MovieCastModelResponse>, t: Throwable) {
                        movieCast.postValue(Resource.Error("Network Failure"))

                    }
                })
        } else {
            movieCast.postValue(Resource.Error("No internet connection"))
        }
    }

    private fun handleMovieCastResponse(response: Response<MovieCastModelResponse>): Resource<MovieCastModelResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)

            }
        }

        return Resource.Error(response.message())
    }


    fun getMovieDetails(movieId: Int?) {

        viewModelScope.launch {
            movieDetailsFun(movieId)
        }
    }

    private suspend fun movieDetailsFun(movieId:Int?) {
        movieDetails.postValue(Resource.Loading())
        if (hasInternetConnection()) {

            mainRepository.getMovieDetails(movieId)
                .enqueue(object : retrofit2.Callback<MovieDetailsModelResponse> {

                    override fun onResponse(
                        p0: Call<MovieDetailsModelResponse>,
                        response: Response<MovieDetailsModelResponse>
                    ) {
                        movieDetails.postValue(handleMovieDetailsResponse(response))
                    }

                    override fun onFailure(call: Call<MovieDetailsModelResponse>, t: Throwable) {
                        movieDetails.postValue(Resource.Error("Network Failure"))

                    }
                })
        } else {
            movieDetails.postValue(Resource.Error("No internet connection"))
        }
    }

    private fun handleMovieDetailsResponse(response: Response<MovieDetailsModelResponse>): Resource<MovieDetailsModelResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)

            }
        }

        return Resource.Error(response.message())
    }

    private fun handleMovieVideoResponseModel(response: Response<VideoModelResponse>): Resource<VideoModelResponse>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)

            }
        }

        return Resource.Error(response.message())
    }


    fun getMovieReview(movieId: Int) {

        viewModelScope.launch {
            movieReviewFun(movieId)
        }
    }

    private suspend fun movieReviewFun(movieId: Int) {
        movieReview.postValue(Resource.Loading())
        if (hasInternetConnection()) {

            mainRepository.getMovieReview(movieId)
                .enqueue(object : retrofit2.Callback<MovieReviewResponseModel> {

                    override fun onResponse(
                        p0: Call<MovieReviewResponseModel>,
                        response: Response<MovieReviewResponseModel>
                    ) {
                        movieReview.postValue(handleMovieReviewResponse(response))
                    }

                    override fun onFailure(call: Call<MovieReviewResponseModel>, t: Throwable) {
                        movieReview.postValue(Resource.Error("Network Failure"))

                    }
                })
        } else {
            movieReview.postValue(Resource.Error("No internet connection"))
        }
    }

    private fun handleMovieReviewResponse(response: Response<MovieReviewResponseModel>): Resource<MovieReviewResponseModel>? {
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

