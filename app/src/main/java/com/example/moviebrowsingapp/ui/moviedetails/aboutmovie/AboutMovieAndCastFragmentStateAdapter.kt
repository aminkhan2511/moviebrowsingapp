package com.example.moviebrowsingapp.ui.moviedetails.aboutmovie

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviebrowsingapp.ui.moviedetails.aboutcast.CastFragment




class AboutMovieAndCastFragmentStateAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Number of pages
    override fun getItemCount(): Int = 2

    // Create the fragment for each page
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AboutMovieFragment()  // Fragment for "About Movie"
            1 -> CastFragment()        // Fragment for "Cast"
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
