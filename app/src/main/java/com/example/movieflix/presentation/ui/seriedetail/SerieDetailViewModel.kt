package com.example.movieflix.presentation.ui.seriedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.domain.usecase.GetVideosFromSerieUseCase
import com.example.movieflix.other.Resource
import kotlinx.coroutines.launch

class SerieDetailViewModel(
    private val getVideosFromSerieUseCase: GetVideosFromSerieUseCase
): ViewModel() {

    private val _videosFromSerie = MutableLiveData<Resource<List<Video>>>()
    val videosFromSerieLiveData : LiveData<Resource<List<Video>>> = _videosFromSerie

    fun getVideosFromSerie(movieId:Int) = viewModelScope.launch {
        _videosFromSerie.postValue(Resource.loading())
        val result = getVideosFromSerieUseCase.invoke(movieId)
        _videosFromSerie.postValue(result)
    }
}