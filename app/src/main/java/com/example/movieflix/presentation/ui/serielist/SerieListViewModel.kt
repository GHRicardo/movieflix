package com.example.movieflix.presentation.ui.serielist

import androidx.lifecycle.*
import com.example.movieflix.data.model.series.Serie
import com.example.movieflix.domain.usecase.GetPopularSeriesUseCase
import com.example.movieflix.domain.usecase.GetTopRatedSeriesUseCase
import com.example.movieflix.other.Resource
import kotlinx.coroutines.launch

class SerieListViewModel(
    private val getPopularSeriesUseCase: GetPopularSeriesUseCase,
    private val getTopRatedSeriesUseCase: GetTopRatedSeriesUseCase
) : ViewModel() {

    private val _series = MutableLiveData<Resource<List<Serie>>>()
    val seriesLiveData : LiveData<Resource<List<Serie>>> = _series

    fun getPopularSeries() = viewModelScope.launch {
        _series.postValue(Resource.loading())
        val resourcePopularSeries = getPopularSeriesUseCase.invoke()
        _series.postValue(resourcePopularSeries)
    }

    fun getTopRatedSeries() = viewModelScope.launch {
        _series.postValue(Resource.loading())
        val resourceTopRatedSeries = getTopRatedSeriesUseCase.invoke()
        _series.postValue(resourceTopRatedSeries)
    }
}