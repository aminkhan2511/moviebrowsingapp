package com.example.moviebrowsingapp.ui.videoplayer


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

class VideoTrailerModel(
    val mainRepository: MainRepository, application: Application
) : AndroidViewModel(application) {


    var movieTrailerVideo: MutableLiveData<Resource<VideoModelResponse>?> = MutableLiveData()

    fun getVideoTrailer(movieId:Int) {

        viewModelScope.launch {
            videoTrailer(movieId)
        }
    }

    private suspend fun videoTrailer(movieId: Int) {
        movieTrailerVideo.postValue(Resource.Loading())
        if (hasInternetConnection()) {

            mainRepository.getVideoTrailer(movieId)
                .enqueue(object : retrofit2.Callback<VideoModelResponse> {

                    override fun onResponse(
                        p0: Call<VideoModelResponse>,
                        response: Response<VideoModelResponse>
                    ) {
                        movieTrailerVideo.postValue(handleMovieVideoResponseModel(response))
                    }

                    override fun onFailure(call: Call<VideoModelResponse>, t: Throwable) {
                        movieTrailerVideo.postValue(Resource.Error("Network Failure"))

                    }
                })
        } else {
            movieTrailerVideo.postValue(Resource.Error("No internet connection"))
        }
    }

    private fun handleMovieVideoResponseModel(response: Response<VideoModelResponse>): Resource<VideoModelResponse>? {
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

