package com.example.movieflix.presentation.ui.seriesearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.domain.usecase.SearchSerieByNameUseCase
import com.example.movieflix.other.Resource
import kotlinx.coroutines.launch

class SerieSearchViewModel(
    private val searchSerieByNameUseCase: SearchSerieByNameUseCase
    ):ViewModel() {

    private val _searchedSeries = MutableLiveData<Resource<List<Serie>>>()
    val searchedSeriesLiveData : LiveData<Resource<List<Serie>>> = _searchedSeries

    fun searchSeriesByName(name:String) = viewModelScope.launch {
        _searchedSeries.postValue(Resource.loading())
        val resourceSearchedMovies = searchSerieByNameUseCase.invoke(name)
        _searchedSeries.postValue(resourceSearchedMovies)
    }
}