package com.example.h_mal.movielisttest.data.network.networkUtils

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

/**
 * Abstract class for extracting <T> from Retrofit Response<T>
 * Or throw IOException if the API call fails
 */
abstract class SafeApiCall {

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun <T : Any> responseUnwrap(
        call: suspend () -> Response<T>
    ): T {

        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()

            val errorMessage = error?.let {
                try {
                    JSONObject(it).getString("status_message")
                } catch (e: JSONException) {
                    e.printStackTrace()
                    null
                }
            } ?: "Error Code: ${response.code()}"

            throw IOException(errorMessage)
        }
    }
}