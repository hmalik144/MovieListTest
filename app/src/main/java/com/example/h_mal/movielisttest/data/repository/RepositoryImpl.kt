package com.example.h_mal.movielisttest.data.repository

import com.example.h_mal.movielisttest.data.network.MoviesApi
import com.example.h_mal.movielisttest.data.network.networkUtils.SafeApiCall
import com.example.h_mal.movielisttest.data.network.response.MoviesResponse
import com.example.h_mal.movielisttest.data.network.response.ResultsItem
import com.example.h_mal.movielisttest.data.prefs.PreferenceProvider
import com.example.h_mal.movielisttest.data.room.MovieEntity
import com.example.h_mal.movielisttest.data.room.MoviesRoomDatabase

class RepositoryImpl(
    private val api: MoviesApi,
    private val database: MoviesRoomDatabase,
    private val preferences: PreferenceProvider
) : Repository, SafeApiCall() {


    override suspend fun getMoviesFromApi(pageNumber: Int): MoviesResponse? {
        return responseUnwrap { api.getFromApi(pageNumber) }
    }

    override fun getMoviesFromDatabase() = database.getSimpleDao().getAllItems()

    override suspend fun saveMoviesToDatabase(resultsItems: List<ResultsItem>){
        val userList = resultsItems.map { MovieEntity(it) }
        database.getSimpleDao().saveAllItems(userList)
    }

    override suspend fun setFavourite(id: Int) = database.getSimpleDao().updateFavourite(id)

    override fun getCurrentPage(): Int = preferences.getPageNumber()

    override fun updateCurrentPage(){
        preferences.savePageNumber()
    }
}