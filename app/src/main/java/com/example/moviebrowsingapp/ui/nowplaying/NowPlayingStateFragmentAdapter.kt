package com.example.moviebrowsingapp.ui.nowplaying

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviebrowsingapp.ui.popular.PopularFragment
import com.example.moviebrowsingapp.ui.toprated.TopRatedFragment
import com.example.moviebrowsingapp.ui.upcoming.UpcomingFragment

class NowPlayingStateFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NowPlayingFragment()
            1 -> UpcomingFragment()
            2 -> TopRatedFragment()
            3 -> PopularFragment()
            else -> NowPlayingFragment()
        }
    }



}