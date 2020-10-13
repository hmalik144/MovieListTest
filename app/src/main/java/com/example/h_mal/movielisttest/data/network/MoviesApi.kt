package com.example.h_mal.movielisttest.data.network

import com.example.h_mal.movielisttest.data.network.interceptors.NetworkConnectionInterceptor
import com.example.h_mal.movielisttest.data.network.interceptors.QueryParamsInterceptor
import com.example.h_mal.movielisttest.data.network.response.MoviesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApi {

    // https://api.themoviedb.org/3/movie/popular?api_key=<api_key>&language=en-US&page=1
    @GET("movie/popular?")
    suspend fun getFromApi(
        @Query("page") pageNumber: Int
    ): Response<MoviesResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor,
            queryParamsInterceptor: QueryParamsInterceptor
        ) : MoviesApi {

            val baseUrl = "https://api.themoviedb.org/3/"

            // okHttpClient
            val okkHttpclient = OkHttpClient.Builder()
                .addNetworkInterceptor(networkConnectionInterceptor)
                .addInterceptor(queryParamsInterceptor)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesApi::class.java)
        }
    }
}

