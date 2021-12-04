package com.example.movieflix.presentation.di

import com.example.movieflix.presentation.di.home.HomeSubComponent
import com.example.movieflix.presentation.di.movie.MovieSubcomponent
import com.example.movieflix.presentation.di.serie.SerieSubcomponent

interface Injector {
    fun createMovieSubcomponent():MovieSubcomponent
    fun createHomeSubComponent():HomeSubComponent
    fun createSerieSubComponent():SerieSubcomponent
}