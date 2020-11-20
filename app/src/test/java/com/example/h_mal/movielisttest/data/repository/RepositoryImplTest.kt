package com.example.h_mal.movielisttest.data.repository

import com.example.h_mal.movielisttest.data.network.MoviesApi
import com.example.h_mal.movielisttest.data.network.response.MoviesResponse
import com.example.h_mal.movielisttest.data.prefs.PreferenceProvider
import com.example.h_mal.movielisttest.data.room.MoviesRoomDatabase
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertFailsWith

class RepositoryTest {

    lateinit var repository: Repository

    @Mock
    lateinit var api: MoviesApi
    @Mock
    lateinit var db: MoviesRoomDatabase
    @Mock
    lateinit var prefs: PreferenceProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = RepositoryImpl(api, db, prefs)
    }

    @Test
    fun fetchUserFromApi_positiveResponse() = runBlocking {
        // GIVEN
        val input = 1
        val mockApiResponse = Mockito.mock(MoviesResponse::class.java)
        val mockResponse = Response.success(mockApiResponse)

        // WHEN
        Mockito.`when`(api.getFromApi(input)).thenReturn(
            mockResponse
        )

        // THEN
        val getUser = repository.getMoviesFromApi(input)
        assertNotNull(getUser)
        assertEquals(mockApiResponse, getUser)
    }

    @Test
    fun fetchUserFromApi_negativeResponse() = runBlocking {
        //GIVEN
        //mock retrofit error response
        val mockBody = Mockito.mock(ResponseBody::class.java)
        val mockRaw = Mockito.mock(okhttp3.Response::class.java)
        val re = Response.error<String>(mockBody, mockRaw)

        //WHEN
        Mockito.`when`(api.getFromApi(10)).then { re }

        //THEN - assert exception is not null
        val ioExceptionReturned = assertFailsWith<IOException> {
            repository.getMoviesFromApi(10)
        }
        assertNotNull(ioExceptionReturned)
        assertNotNull(ioExceptionReturned.message)
    }

}