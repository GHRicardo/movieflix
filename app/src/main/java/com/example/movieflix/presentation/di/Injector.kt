package com.example.movieflix.presentation.di

import com.example.movieflix.presentation.di.home.HomeSubComponent
import com.example.movieflix.presentation.di.movie.MovieSubcomponent

interface Injector {
    fun createMovieSubcomponent():MovieSubcomponent
    fun createHomeSubComponent():HomeSubComponent
}