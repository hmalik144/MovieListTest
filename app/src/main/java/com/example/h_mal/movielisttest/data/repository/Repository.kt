package com.example.h_mal.movielisttest.data.repository

import androidx.lifecycle.LiveData
import com.example.h_mal.movielisttest.data.network.response.MoviesResponse
import com.example.h_mal.movielisttest.data.network.response.ResultsItem
import com.example.h_mal.movielisttest.data.room.MovieEntity

interface Repository {
    suspend fun getMoviesFromApi(pageNumber: Int): MoviesResponse?
    fun getMoviesFromDatabase(): LiveData<List<MovieEntity>>
    suspend fun saveMoviesToDatabase(resultsItems: List<ResultsItem>)
    suspend fun setFavourite(id: Int)
    fun getCurrentPage(): Int
    fun updateCurrentPage()
}
