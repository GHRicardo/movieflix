package com.example.movieflix.presentation.ui.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieflix.domain.usecase.GetPopularMoviesUseCase
import com.example.movieflix.domain.usecase.GetTopRatedMoviesUseCase
import java.lang.IllegalArgumentException

class MovieListViewModelFactory(private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
                                private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieListViewModel::class.java)){
            return MovieListViewModel(
                getPopularMoviesUseCase,
                getTopRatedMoviesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}