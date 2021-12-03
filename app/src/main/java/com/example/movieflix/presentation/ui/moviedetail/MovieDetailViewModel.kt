package com.example.movieflix.presentation.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieflix.data.model.video.Video
import com.example.movieflix.domain.usecase.GetVideosFromMovieUseCase
import com.example.movieflix.other.Resource
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getVideosFromMovieUseCase: GetVideosFromMovieUseCase
): ViewModel() {

    private val _videosFromMovie = MutableLiveData<Resource<List<Video>>>()
    val videosFromMovieLiveData : LiveData<Resource<List<Video>>> = _videosFromMovie

    fun getVideosFromMovie(movieId:Int) = viewModelScope.launch {
        _videosFromMovie.postValue(Resource.loading())
        val result = getVideosFromMovieUseCase.invoke(movieId)
        _videosFromMovie.postValue(result)
    }
}