package com.example.movieflix.presentation.ui.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieflix.domain.usecase.GetVideosFromMovieUseCase
import java.lang.IllegalArgumentException

class MovieDetailViewModelFactory(
    private val getVideosFromMovieUseCase: GetVideosFromMovieUseCase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieDetailViewModel::class.java)){
            return MovieDetailViewModel(getVideosFromMovieUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}