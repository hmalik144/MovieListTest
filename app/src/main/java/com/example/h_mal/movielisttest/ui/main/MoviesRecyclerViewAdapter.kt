package com.example.h_mal.movielisttest.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.h_mal.movielisttest.R
import com.example.h_mal.movielisttest.data.models.Movie
import com.example.h_mal.movielisttest.utils.loadImage
import kotlinx.android.synthetic.main.item_layout.view.*

/**
 * Recycler view adapter to bind movies to a recycler view with
 */
class MoviesRecyclerViewAdapter(
    val favouriteClickListener: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list = mutableListOf<Movie>()

    fun updateList(movies: List<Movie>) {
        list.clear()
        list.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemTwo =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemOne(itemTwo)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        list[position].id?.let {
            return it.toLong()
        }
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemOne) {
            holder.populateMovie(list[position])
            holder.favourite.setOnClickListener {
                list[position].id?.let { id -> favouriteClickListener(id) }
            }
        }
    }

    internal inner class ItemOne(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title_tv
        val description: TextView = itemView.desc_tv
        val dateTv: TextView = itemView.date_tv
        val favourite: ImageView = itemView.fav_btn
        val cellImageView: ImageView = itemView.movie_iv
        val voteTextView: TextView = itemView.vote_average_tv

        fun populateMovie(movie: Movie) {
            title.text = movie.title
            description.text = movie.overview
            dateTv.text = movie.releaseDate
            movie.favourites?.let { setFavourite(it) }
            voteTextView.text = movie.voteAverage.toString()

            cellImageView.loadImage(movie.posterPath)
        }

        private fun setFavourite(fav: Boolean) {
            if (fav) {
                favourite.setImageResource(android.R.drawable.btn_star_big_on)
            } else {
                favourite.setImageResource(android.R.drawable.btn_star_big_off)
            }
        }
    }
}