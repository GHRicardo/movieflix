package com.example.movieflix.presentation.ui.movielist

import androidx.lifecycle.*
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.domain.usecase.GetPopularMoviesUseCase
import com.example.movieflix.domain.usecase.GetTopRatedMoviesUseCase
import com.example.movieflix.other.Resource
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movie>>>()
    val moviesLiveData : LiveData<Resource<List<Movie>>> = _movies

    fun getPopularMovies() = viewModelScope.launch {
        _movies.postValue(Resource.loading())
        val resourcePopularMovies = getPopularMoviesUseCase.invoke()
        _movies.postValue(resourcePopularMovies)
    }

    fun getTopRatedMovies() = viewModelScope.launch {
        _movies.postValue(Resource.loading())
        val resourceTopRatedMovies = getTopRatedMoviesUseCase.invoke()
        _movies.postValue(resourceTopRatedMovies)
    }
}