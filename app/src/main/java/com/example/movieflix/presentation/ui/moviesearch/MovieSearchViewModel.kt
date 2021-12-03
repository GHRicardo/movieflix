package com.example.movieflix.presentation.ui.moviesearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieflix.data.model.movie.Movie
import com.example.movieflix.domain.usecase.SearchMovieByNameUseCase
import com.example.movieflix.other.Resource
import kotlinx.coroutines.launch

class MovieSearchViewModel(
    private val searchMovieByNameUseCase: SearchMovieByNameUseCase
    ):ViewModel() {

    private val _searchedMovies = MutableLiveData<Resource<List<Movie>>>()
    val searchedMoviesLiveData : LiveData<Resource<List<Movie>>> = _searchedMovies

    fun searchMoviesByName(name:String) = viewModelScope.launch {
        _searchedMovies.postValue(Resource.loading())
        val resourceSearchedMovies = searchMovieByNameUseCase.invoke(name)
        _searchedMovies.postValue(resourceSearchedMovies)
    }
}