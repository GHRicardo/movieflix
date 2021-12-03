package com.example.movieflix.presentation.di.home

import com.example.movieflix.domain.usecase.GetSomeRandomMovieUseCase
import com.example.movieflix.presentation.ui.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @HomeScope
    @Provides
    fun provideHomeViewModelFactory(
        getSomeRandomMovieUseCase: GetSomeRandomMovieUseCase
    ): HomeViewModelFactory {
        return HomeViewModelFactory(getSomeRandomMovieUseCase)
    }
}