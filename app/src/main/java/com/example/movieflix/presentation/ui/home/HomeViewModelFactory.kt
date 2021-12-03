package com.example.movieflix.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieflix.domain.usecase.GetSomeRandomMovieUseCase
import java.lang.IllegalArgumentException

class HomeViewModelFactory(
    private val getSomeRandomMovieUseCase: GetSomeRandomMovieUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(getSomeRandomMovieUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}