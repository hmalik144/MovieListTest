package com.example.h_mal.movielisttest.data.network.networkUtils

import com.example.h_mal.movielisttest.data.network.interceptors.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

fun okHttpClient(
    networkConnectionInterceptor: NetworkConnectionInterceptor

): OkHttpClient {
    val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    return OkHttpClient.Builder()
        .addNetworkInterceptor(networkConnectionInterceptor)
        .addInterceptor(logging)
        .readTimeout(5 * 60, TimeUnit.SECONDS)
        .build()
}