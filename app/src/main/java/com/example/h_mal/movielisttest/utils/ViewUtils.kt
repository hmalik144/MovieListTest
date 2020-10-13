package com.example.h_mal.movielisttest.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.h_mal.movielisttest.R
import com.squareup.picasso.Picasso

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Context.displayToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


fun ImageView.loadImage(url: String?){
    Picasso.get()
        .load(url)
        .fit()
        .centerCrop()
        .into(this)
}

fun RecyclerView.scrollBottomReachedListener(bottomReached: () -> Unit){
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                bottomReached()
            }
        }
    })
}