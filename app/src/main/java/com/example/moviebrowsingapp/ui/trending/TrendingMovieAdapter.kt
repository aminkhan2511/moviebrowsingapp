package com.example.moviebrowsingapp.ui.trending

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
import com.example.moviebrowsingapp.data.remote.model.TrendingMovie
import com.example.moviebrowsingapp.util.Constant.Companion.POST_BASE_URL


class TrendingMovieAdapter(private val moviePoster: ((TrendingMovie) -> Unit)? = null,
                           private val contex:Context) :
    RecyclerView.Adapter<TrendingMovieAdapter.ViewHolder>() {




    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var moviePosterTrending:ImageView

        init {
            moviePosterTrending=view.findViewById(R.id.moviePosterTrending)
        }
    }




    val diffUtil = object : DiffUtil.ItemCallback<TrendingMovie>() {
        override fun areItemsTheSame(oldItem: TrendingMovie, newItem: TrendingMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TrendingMovie, newItem: TrendingMovie): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.trending_movie_item_list, viewGroup, false)

        return ViewHolder(view)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movie = differ.currentList[position]
        val displayMetrics = holder.itemView.context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val itemWidth = screenWidth / 2

        holder.itemView.layoutParams = RecyclerView.LayoutParams(itemWidth, RecyclerView.LayoutParams.WRAP_CONTENT)
        holder.apply {
            Glide.with(itemView.context).load(POST_BASE_URL+movie.poster_path).into(moviePosterTrending)

        }


        holder.itemView.apply {
            setOnClickListener {

                moviePoster?.let { it1 -> it1(movie) }


            }

        }

    }


    override fun getItemCount(): Int {
        return differ.currentList.size

    }

}
