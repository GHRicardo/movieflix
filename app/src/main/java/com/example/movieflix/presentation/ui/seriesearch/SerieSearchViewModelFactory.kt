package com.example.movieflix.presentation.ui.seriesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieflix.domain.usecase.SearchSerieByNameUseCase
import java.lang.IllegalArgumentException
import javax.inject.Inject

class SerieSearchViewModelFactory(
    private val searchSerieByNameUseCase: SearchSerieByNameUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SerieSearchViewModel::class.java)){
            return SerieSearchViewModel(searchSerieByNameUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}