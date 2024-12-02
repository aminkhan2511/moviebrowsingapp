package com.example.moviebrowsingapp.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviebrowsingapp.data.repository.MainRepository
import com.example.moviebrowsingapp.ui.moviedetails.DetailViewModel
import com.example.moviebrowsingapp.ui.videoplayer.VideoTrailerModel

//class mainViewModelProviderFactory(val mainRepository: MainRepository, val application: Application): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return MainViewModel(mainRepository,application) as T
//    }
//}

class MainViewModelProviderFactory(
    private val mainRepository: MainRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(mainRepository, application) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mainRepository,application) as T
            }
            modelClass.isAssignableFrom(VideoTrailerModel::class.java) -> {
                VideoTrailerModel(mainRepository,application) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
