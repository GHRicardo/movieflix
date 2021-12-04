package com.example.movieflix.presentation.ui.seriedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieflix.domain.usecase.GetVideosFromMovieUseCase
import com.example.movieflix.domain.usecase.GetVideosFromSerieUseCase
import java.lang.IllegalArgumentException

class SerieDetailViewModelFactory(
    private val getVideosFromSerieUseCase: GetVideosFromSerieUseCase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SerieDetailViewModel::class.java)){
            return SerieDetailViewModel(getVideosFromSerieUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}