package com.example.h_mal.movielisttest.data.network.networkUtils

import com.example.h_mal.movielisttest.data.network.response.MoviesResponse
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertFailsWith

class SafeApiCallTest: SafeApiCall(){

    @Test
    fun successfulResponse_SuccessfulOutput() = runBlocking{
        // GIVEN
        val mockApiResponse = mockk<MoviesResponse>()
        val mockResponse = Response.success(mockApiResponse)

        // WHEN
        val result = responseUnwrap { mockResponse }

        // THEN
        assertNotNull(result)
        assertEquals(mockApiResponse, result)
    }

    @Test
    fun unsuccessfulResponse_thrownOutput() = runBlocking{
        // GIVEN
        val errorMessage = "{\n" +
                "  \"status_code\": 7,\n" +
                "  \"status_message\": \"Invalid API key: You must be granted a valid key.\",\n" +
                "  \"success\": false\n" +
                "}"

        val errorResponseBody = errorMessage.toResponseBody("application/json".toMediaTypeOrNull())
        val mockResponse = Response.error<String>(404, errorResponseBody)

        //THEN - assert exception is not null
        val ioExceptionReturned = assertFailsWith<IOException> {
            responseUnwrap { mockResponse }!!
        }

        assertNotNull(ioExceptionReturned)
        assertEquals(ioExceptionReturned.message, "Invalid API key: You must be granted a valid key.")
    }

}