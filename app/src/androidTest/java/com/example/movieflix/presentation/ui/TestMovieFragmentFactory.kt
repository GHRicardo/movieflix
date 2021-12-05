package com.example.movieflix.presentation.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.movieflix.presentation.ui.movielist.MovieListViewModel
import com.example.movieflix.presentation.ui.movielist.MoviesListFragment
import com.example.movieflix.presentation.ui.moviesearch.MovieSearchFragment
import com.example.movieflix.presentation.ui.moviesearch.MovieSearchViewModel

class TestMovieFragmentFactory(
    private val movieListViewModel: MovieListViewModel? = null,
    private val movieSearchViewModel: MovieSearchViewModel? = null
):FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            MoviesListFragment::class.java.name -> MoviesListFragment(movieListViewModel)
            MovieSearchFragment::class.java.name -> MovieSearchFragment(movieSearchViewModel)
            else -> super.instantiate(classLoader, className)
        }
    }
}
