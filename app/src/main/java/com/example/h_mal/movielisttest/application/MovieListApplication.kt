package com.example.h_mal.movielisttest.application

import android.app.Application
import com.example.h_mal.movielisttest.data.network.MoviesApi
import com.example.h_mal.movielisttest.data.network.interceptors.NetworkConnectionInterceptor
import com.example.h_mal.movielisttest.data.network.interceptors.QueryParamsInterceptor
import com.example.h_mal.movielisttest.data.prefs.PreferenceProvider
import com.example.h_mal.movielisttest.data.repository.RepositoryImpl
import com.example.h_mal.movielisttest.data.room.MoviesRoomDatabase
import com.example.h_mal.movielisttest.ui.main.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MovieListApplication : Application(), KodeinAware{

    // Kodein creation of modules to be retrieve within the app
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MovieListApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { QueryParamsInterceptor() }
        bind() from singleton { MoviesApi(instance(), instance())}
        bind() from singleton { MoviesRoomDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { RepositoryImpl(instance(), instance(), instance()) }
        bind() from provider { MainViewModelFactory(instance()) }
    }
}