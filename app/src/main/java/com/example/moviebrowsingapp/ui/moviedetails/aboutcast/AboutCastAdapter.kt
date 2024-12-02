package com.example.moviebrowsingapp.ui.moviedetails.aboutcast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviebrowsingapp.R
import com.example.moviebrowsingapp.data.remote.model.MovieCast
import com.example.moviebrowsingapp.data.remote.model.NowPlaying
import com.example.moviebrowsingapp.util.Constant.Companion.POST_BASE_URL
import de.hdodenhof.circleimageview.CircleImageView


class AboutCastAdapter() :
    RecyclerView.Adapter<AboutCastAdapter.ViewHolder>() {

 

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var castName:TextView
        init {
            castName = view.findViewById<TextView>(R.id.castNameTxt)
        }
    }




    val diffUtil = object : DiffUtil.ItemCallback<MovieCast>() {
        override fun areItemsTheSame(oldItem: MovieCast, newItem: MovieCast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieCast, newItem: MovieCast): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_cast_list, viewGroup, false)

        return ViewHolder(view)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cast = differ.currentList[position]

        holder.apply {
            castName.text=cast.name

        }




    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return differ.currentList.size
//        return 5
    }





}
