package com.example.moviebrowsingapp.ui.videoplayer

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviebrowsingapp.R
import com.example.moviebrowsingapp.data.remote.model.Video
import com.example.moviebrowsingapp.data.repository.MainRepository
import com.example.moviebrowsingapp.ui.main.MainViewModelProviderFactory
import com.example.moviebrowsingapp.util.Constant.Companion.MOVIE_ID_KEY
import com.example.moviebrowsingapp.util.Resource
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class VideoPlayerActivity : AppCompatActivity() {
    lateinit var videoTrailerModel: VideoTrailerModel
    lateinit var youTubePlayerView:YouTubePlayerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_video_player)

        val mainRepository = MainRepository()
        val viewModelProviderFactory = MainViewModelProviderFactory(mainRepository, application)
        videoTrailerModel =  ViewModelProvider(this, viewModelProviderFactory)[VideoTrailerModel::class.java]
        val movieId = intent?.getIntExtra(MOVIE_ID_KEY, 0) ?: 0
        youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtube_player_view)

        videoTrailerModel.getVideoTrailer(movieId)
        getMovieTrailerUrl()


    }

    private fun getMovieTrailerUrl() {

        videoTrailerModel.movieTrailerVideo.observe(this, Observer { response ->


            when (response) {
                is Resource.Success -> {
                    response.data?.let { videoTrailerRes ->

                        playVideInYoutubePlayrView(videoTrailerRes.results,this)
                    }
                }

                is Resource.Error -> Toast.makeText(
                    VideoPlayerActivity@ this,
                    "Error",
                    Toast.LENGTH_LONG
                ).show()

                is Resource.Loading -> Toast.makeText(
                    VideoPlayerActivity@ this,
                    "Loading data...",
                    Toast.LENGTH_SHORT
                ).show()

                null -> TODO()
            }
        })
    }

    private fun playVideInYoutubePlayrView(
        results: List<Video>,
        videoPlayerActivity: VideoPlayerActivity
    ) {
       try {
           if(results[0].key.isBlank())
           {
               return
           }

           else {

               lifecycle.addObserver(youTubePlayerView)

               youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                   override fun onReady(youTubePlayer: YouTubePlayer) {
                       val videoId = results[0].key
                       youTubePlayer.loadVideo(videoId, 0f)
                       requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE; // Set to landscape
                   }
               })
           }
       }
       catch (e:Exception){
           e.printStackTrace()
       }


    }



    override fun onDestroy() {
        super.onDestroy()
        if (youTubePlayerView!=null) // Release the player when done
        {
            youTubePlayerView.release()
        }
    }
    public override fun onUserLeaveHint() {
        if (youTubePlayerView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enterPictureInPictureMode();
            }

        }
    }
}