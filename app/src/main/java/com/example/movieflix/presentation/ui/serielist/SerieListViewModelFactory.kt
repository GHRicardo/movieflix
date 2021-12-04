package com.example.movieflix.presentation.ui.serielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieflix.domain.usecase.GetPopularSeriesUseCase
import com.example.movieflix.domain.usecase.GetTopRatedSeriesUseCase
import java.lang.IllegalArgumentException

class SerieListViewModelFactory(
    private val getPopularSeriesUseCase: GetPopularSeriesUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SerieListViewModel::class.java)){
            return SerieListViewModel(
                getPopularSeriesUseCase,
                getTopRatedSeriesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}