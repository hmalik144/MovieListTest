package com.example.h_mal.movielisttest.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.h_mal.movielisttest.data.repository.Repository

/**
 * Viewmodel factory for [MainViewModel]
 * @repository injected into MainViewModel
 */
class MainViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return (MainViewModel(repository)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}