package com.example.h_mal.movielisttest.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.espresso.idling.CountingIdlingResource
import com.example.h_mal.movielisttest.application.MovieListApplication.Companion.idlingResources
import com.example.h_mal.movielisttest.data.network.response.MoviesResponse
import com.example.h_mal.movielisttest.data.repository.Repository
import com.example.h_mal.movielisttest.data.room.MovieEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException
import javax.annotation.meta.When

class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel: MainViewModel

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var observer: Observer<List<MovieEntity>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val mockLiveData = object: LiveData<List<MovieEntity>>(){}
        Mockito.`when`(repository.getMoviesFromDatabase()).thenReturn(mockLiveData)

        viewModel = MainViewModel(repository)
    }

    @Test
    fun getApiFromRepository_SuccessfulReturn() = runBlocking{
        //GIVEN
        val mockApiResponse = Mockito.mock(MoviesResponse::class.java)

        //WHEN
        Mockito.`when`(repository.getMoviesFromApi(1)).thenReturn(mockApiResponse)

        //THEN
        viewModel.loadMovies()
        delay(200)
        viewModel.operationState.observeForever{
            it.getContentIfNotHandled()?.let {result ->
                kotlin.test.assertFalse { result }
            }
        }
    }

    @Test
    fun getFromRepository_unsuccessfulReturn() = runBlocking{
        // WHEN
        Mockito.`when`(repository.getMoviesFromApi(1)).thenAnswer{ throw IOException("throwed") }

        // THEN
        viewModel.loadMovies()
        viewModel.operationError.observeForever{
            it.getContentIfNotHandled()?.let {result ->
                assertEquals(result, "throwed")
            }
        }
    }

    @Test
    fun getMoreFromRepository_SuccessfulReturn() = runBlocking{
        //GIVEN
        val mockApiResponse = Mockito.mock(MoviesResponse::class.java)

        //WHEN
        Mockito.`when`(repository.getMoviesFromApi(2)).thenReturn(mockApiResponse)
        Mockito.`when`(repository.getCurrentPage()).thenReturn(2)

        //THEN
        viewModel.loadMovies()
        delay(200)
        viewModel.operationState.observeForever{
            it.getContentIfNotHandled()?.let {result ->
                kotlin.test.assertFalse { result }
            }
        }
    }

    @Test
    fun getMoreFromRepository_unsuccessfulReturn() = runBlocking{
        // WHEN
        Mockito.`when`(repository.getMoviesFromApi(2)).thenAnswer{ throw IOException("throwed") }

        // THEN
        viewModel.loadMovies()
        viewModel.operationError.observeForever{
            it.getContentIfNotHandled()?.let {result ->
                assertEquals(result, "throwed")
            }
        }
    }
}