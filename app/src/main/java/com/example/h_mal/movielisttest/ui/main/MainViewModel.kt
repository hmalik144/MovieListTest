package com.example.h_mal.movielisttest.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.h_mal.movielisttest.data.models.Movie
import com.example.h_mal.movielisttest.data.repository.Repository
import com.example.h_mal.movielisttest.data.room.MovieEntity
import com.example.h_mal.movielisttest.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    val moviesLiveData = MutableLiveData<List<Movie>>()

    val operationState = MutableLiveData<Event<Boolean>>()
    val operationError = MutableLiveData<Event<String>>()

    init {
        val observer = Observer<List<MovieEntity>> {
            val list = it.map {entity -> Movie(entity) }
            moviesLiveData.postValue(list)
        }
        repository.getMoviesFromDatabase().observeForever (observer)

        loadMovies()
    }

    fun loadMovies(){
        CoroutineScope(Dispatchers.IO).launch {
            operationState.postValue(Event(true))
            try {
                val response = repository.getMoviesFromApi(1)
                // null check response exists and contains list of users
                response?.results?.let {
                    // save users to database
                    repository.saveMoviesToDatabase(it)
                }
            }catch (e: IOException){
                operationError.postValue(Event(e.message!!))
            }finally {
                operationState.postValue(Event(false))
            }
        }
    }

    fun loadMoreMovies(){
        CoroutineScope(Dispatchers.IO).launch {
            operationState.postValue(Event(true))
            try {
                val page = repository.getCurrentPage()

                val response = repository.getMoviesFromApi(page)
                // null check response exists and contains list of users
                response?.results?.let {
                    // save users to database
                    repository.saveMoviesToDatabase(it)
                    // update current page
                    repository.updateCurrentPage()
                }
            }catch (e: IOException){
                operationError.postValue(Event(e.message!!))
            }finally {
                operationState.postValue(Event(false))
            }
        }
    }

    fun setFavourite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            operationState.postValue(Event(true))
            try {
                // Set favourite
                repository.setFavourite(id)
            }catch (e: IOException){
                operationError.postValue(Event(e.message!!))
            }finally {
                operationState.postValue(Event(false))
            }
        }
    }
}