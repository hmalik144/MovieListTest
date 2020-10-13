package com.example.h_mal.movielisttest.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.h_mal.movielisttest.data.network.response.ResultsItem

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val overview: String? = null,
    var favourites: Boolean = false,
    val title: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val popularity: Double? = null,
    val voteAverage: Double? = null
){
    constructor(resultsItem: ResultsItem): this(
        resultsItem.id!!,
        resultsItem.overview,
        false,
        resultsItem.title,
        "https://image.tmdb.org/t/p/w500${resultsItem.posterPath}",
        resultsItem.releaseDate,
        resultsItem.popularity,
        resultsItem.voteAverage
    )
}