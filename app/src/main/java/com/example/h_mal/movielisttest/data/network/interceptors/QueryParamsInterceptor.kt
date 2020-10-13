package com.example.h_mal.movielisttest.data.network.interceptors

import com.example.h_mal.movielisttest.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Inject query parameters into the API calls.
 * For uniform constraints (eg. Language, sort order, page size ect)
 * Also for injecting an API key to all api calls
 */
class QueryParamsInterceptor : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("language", "en-UK")
            .addQueryParameter("api_key", BuildConfig.ParamOne)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}