package com.example.moviebrowsingapp.ui.nowplaying

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviebrowsingapp.R
import com.example.moviebrowsingapp.data.remote.model.NowPlaying
import com.example.moviebrowsingapp.util.Constant.Companion.POST_BASE_URL


class NowPlayingMovieAdapter(
    private val nowPlayingMoviePoster: ((NowPlaying) -> Unit)? = null,
    private val contex: Context
) :
    RecyclerView.Adapter<NowPlayingMovieAdapter.ViewHolder>() {



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageViewPoster:ImageView

        init {
            imageViewPoster=view.findViewById(R.id.imageViewPoster)
        }
    }




    val diffUtil = object : DiffUtil.ItemCallback<NowPlaying>() {
        override fun areItemsTheSame(oldItem: NowPlaying, newItem: NowPlaying): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NowPlaying, newItem: NowPlaying): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list_popular_movie, viewGroup, false)

        return ViewHolder(view)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movie = differ.currentList[position]

        holder.apply {
            Glide.with(itemView.context).load(POST_BASE_URL+movie.poster_path).into(imageViewPoster)

        }


        holder.itemView.apply {
            setOnClickListener {

                nowPlayingMoviePoster?.let { it1 -> it1(movie) }

            }

        }

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}
