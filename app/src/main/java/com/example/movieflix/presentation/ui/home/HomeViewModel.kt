package com.example.movieflix.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.domain.usecase.GetSomeRandomMovieUseCase
import com.example.movieflix.other.Resource
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getSomeRandomMovieUseCase: GetSomeRandomMovieUseCase
    ):ViewModel() {


    private val _randomMovie = MutableLiveData<Resource<Movie>>()
    val randomMovieLiveData : LiveData<Resource<Movie>> = _randomMovie

    fun getSomeRandomMovie() = viewModelScope.launch {
        val resourceRandomMovie = getSomeRandomMovieUseCase.invoke()
        _randomMovie.postValue(resourceRandomMovie)
    }
}