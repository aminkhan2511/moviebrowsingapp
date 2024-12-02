package com.example.moviebrowsingapp.ui.moviedetails

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.moviebrowsingapp.R
import com.example.moviebrowsingapp.data.remote.model.MovieDetailsModelResponse
import com.example.moviebrowsingapp.data.repository.MainRepository
import com.example.moviebrowsingapp.ui.main.MainViewModelProviderFactory
import com.example.moviebrowsingapp.ui.moviedetails.aboutmovie.AboutMovieAndCastFragmentStateAdapter
import com.example.moviebrowsingapp.ui.videoplayer.VideoPlayerActivity
import com.example.moviebrowsingapp.util.Constant
import com.example.moviebrowsingapp.util.Constant.Companion.MOVIE_ID_KEY
import com.example.moviebrowsingapp.util.Resource
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MovieDetailsActivity : AppCompatActivity() {
    lateinit var titleMovie: TextView
    lateinit var yearTxt: TextView
    lateinit var dateTxt: TextView
    lateinit var durationTxt: TextView
    lateinit var actionTxt: TextView
    lateinit var imageViewBannerPorster: ImageView
    lateinit var imageViewMovieShortPorster: ImageView
    lateinit var minuteTxt: TextView
    lateinit var genresTxt: TextView
    var movieId:Int = 0

    lateinit var detailViewModel:DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Movie Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mainRepository = MainRepository()
        val viewModelProviderFactory = MainViewModelProviderFactory(mainRepository, application)
        detailViewModel = ViewModelProvider(this, viewModelProviderFactory)[DetailViewModel::class.java]

        initViews()
        clickEvent()
        setTabAndViewPager()
         movieId = intent?.getIntExtra(MOVIE_ID_KEY, 0) ?: 0
        setMovieDetailsDataOnViews(movieId)
        setMovieDetailsDataOnViews(movieId)

    }

    private fun clickEvent() {
        actionTxt.setOnClickListener {
            intent= Intent(this,VideoPlayerActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY,movieId)
            startActivity(intent)
        }
    }


    private fun setMovieDetailsDataOnViews(movieId: Int) {
        detailViewModel.getMovieDetails(movieId)
        detailViewModel.getMovieCast(intent.getIntExtra(MOVIE_ID_KEY, 0))


        detailViewModel.movieDetails.observe(this, Observer {response ->


            when(response) {
                is Resource.Success -> {
                    response.data?.let { movieDetailsResponse ->
                        setDataOnViews(movieDetailsResponse,MainActivity2@this)

                    }
                }

                is Resource.Error ->  Toast.makeText(MainActivity2@this,"Error", Toast.LENGTH_LONG).show()
                is Resource.Loading ->  Toast.makeText(MainActivity2@this,"Loading data...", Toast.LENGTH_SHORT).show()
                null -> TODO()
            }
        })
    }
    private fun setDataOnViews(
        movieDetailsResponse: MovieDetailsModelResponse,
        mainActivity2: MovieDetailsActivity
    ) {
        movieDetailsResponse?.apply {
            Glide.with(mainActivity2).load(Constant.POST_BASE_URL +poster_path).into(imageViewMovieShortPorster)
            Glide.with(mainActivity2).load(Constant.POST_BASE_URL +backdrop_path).into(imageViewBannerPorster)
            titleMovie.text=title
            dateTxt.text=release_date
            yearTxt.text=release_date
//            yearTxt.text=gen
        }


    }

    private fun initViews() {
        actionTxt= findViewById(R.id.actionTxt)
        dateTxt= findViewById(R.id.dateTxt)
        minuteTxt=  findViewById(R.id.minuteTxt)
        imageViewMovieShortPorster=findViewById(R.id.imageViewMovieShortPorster)
        imageViewBannerPorster=  findViewById(R.id.imageViewBannerPorster)
        titleMovie= findViewById(R.id.titleMovie)
        genresTxt=findViewById(R.id.genresTxt)
        durationTxt= findViewById(R.id.durationTxt)
        yearTxt= findViewById(R.id.yearTxt)

    }

    private fun setTabAndViewPager() {
        val tabLayout: TabLayout = findViewById(com.example.moviebrowsingapp.R.id.tabLayout)
        val viewPager: ViewPager2 = findViewById(com.example.moviebrowsingapp.R.id.viewPager)


        val adapter = AboutMovieAndCastFragmentStateAdapter(this)
        viewPager.setAdapter(adapter)


        TabLayoutMediator(
            tabLayout, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.setText("About Movie")
                1 -> tab.setText("Cast")
            }
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}