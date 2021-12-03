package com.example.movieflix.presentation.ui.moviesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieflix.domain.usecase.SearchMovieByNameUseCase
import java.lang.IllegalArgumentException

class MovieSearchViewModelFactory(
    private val searchMovieByNameUseCase: SearchMovieByNameUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieSearchViewModel::class.java)){
            return MovieSearchViewModel(searchMovieByNameUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}