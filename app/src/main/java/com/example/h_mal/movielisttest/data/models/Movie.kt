package com.example.h_mal.movielisttest.data.models

import com.example.h_mal.movielisttest.data.room.MovieEntity

data class Movie(
    val id: Int? = null,
    val overview: String? = null,
    var favourites: Boolean? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val popularity: Double? = null,
    val voteAverage: Double? = null
){

    constructor(movieEntity: MovieEntity): this(
        movieEntity.id,
        movieEntity.overview,
        movieEntity.favourites,
        movieEntity.title,
        movieEntity.posterPath,
        movieEntity.releaseDate,
        movieEntity.popularity,
        movieEntity.voteAverage
    )
}
