package com.example.movieflix.presentation

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.movieflix.TestMovieFlixApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MovieFlixTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestMovieFlixApplication::class.java.name, context)
    }
}