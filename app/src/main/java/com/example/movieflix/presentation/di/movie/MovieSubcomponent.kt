package com.example.movieflix.presentation.di.movie

import com.example.movieflix.presentation.ui.moviedetail.MovieDetailFragment
import com.example.movieflix.presentation.ui.movielist.MoviesListFragment
import com.example.movieflix.presentation.ui.moviesearch.MovieSearchFragment
import dagger.Subcomponent

@MovieScope
@Subcomponent(modules = [MovieModule::class])
interface MovieSubcomponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():MovieSubcomponent
    }

    fun inject(fragment: MoviesListFragment)
    fun inject(fragment: MovieSearchFragment)
    fun inject(fragment: MovieDetailFragment)
}