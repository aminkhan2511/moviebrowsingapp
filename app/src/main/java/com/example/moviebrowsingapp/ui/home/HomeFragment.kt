package com.example.moviebrowsingapp.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviebrowsingapp.R
import com.example.moviebrowsingapp.data.remote.model.TrendingMovie
import com.example.moviebrowsingapp.ui.nowplaying.NowPlayingStateFragmentAdapter
import com.example.moviebrowsingapp.util.Resource
import com.example.moviebrowsingapp.ui.main.MainActivity
import com.example.moviebrowsingapp.ui.moviedetails.MovieDetailsActivity
import com.example.moviebrowsingapp.ui.main.MainViewModel
import com.example.moviebrowsingapp.ui.trending.TrendingMovieAdapter
import com.example.moviebrowsingapp.util.Constant.Companion.MOVIE_ID_KEY
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {
lateinit var mainViewModelHome:MainViewModel
lateinit var trendingMovieAdapter: TrendingMovieAdapter
lateinit var recyHome:RecyclerView
lateinit var linlayHome:LinearLayout
lateinit var tabLayoutHome:TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModelHome = (activity as MainActivity).mainViewModel
       linlayHome= view.findViewById<LinearLayout>(R.id.linlayHome)
        tabLayoutHome= view.findViewById<TabLayout>(R.id.tabLayoutHome)
        setupRecyclerView(view)
        setTrendingMovieData()
//        mainViewModelHome.hideLayoutFunction = { hideLayout() }
//        mainViewModelHome.showLayoutFunction = { showLayout() }



        val tabLayout: TabLayout = view.findViewById(R.id.tabLayoutHome)
        val viewPager: ViewPager2 = view.findViewById(R.id.viewPagerHome)

        // Set up the adapter for ViewPager2

        // Set up the adapter for ViewPager2
        val adapter = NowPlayingStateFragmentAdapter(this)
        viewPager.setAdapter(adapter)

        // Link TabLayout and ViewPager2

        // Link TabLayout and ViewPager2
        TabLayoutMediator(
            tabLayout, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.setText("Now Playing")
                1 -> tab.setText("Upcoming")
                2 -> tab.setText("Top Rated")
                3 -> tab.setText("Popular")
            }
        }.attach()
    }

    private fun setTrendingMovieData() {

        mainViewModelHome.trendingMoview.observe(viewLifecycleOwner, Observer {response ->


            when(response) {
                is Resource.Success -> {
                    response.data?.let { newsResponse ->
                       println("fjhfjfhfjj${newsResponse.results.size}")
                        trendingMovieAdapter.differ.submitList(newsResponse.results)
//                        trendingMovieAdapter.differ.submitList(newsResponse.results.take(5))
                    }
                }

                is Resource.Error ->  Toast.makeText(requireContext(),"Error", Toast.LENGTH_LONG).show()
                is Resource.Loading ->  Toast.makeText(activity,"Loading data...", Toast.LENGTH_SHORT).show()
                null -> TODO()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupRecyclerView(view: View) {
        trendingMovieAdapter = TrendingMovieAdapter(::movieData, requireContext())
        recyHome = view.findViewById<RecyclerView>(R.id.recyHome)

        recyHome.apply {
            adapter=trendingMovieAdapter
            layoutManager= LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
//            layoutManager=  GridLayoutManager(activity, 2)

        }


    }
    fun movieData(movie: TrendingMovie)
    {


        val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_ID_KEY,movie.id)
        startActivity(intent)
    }

}