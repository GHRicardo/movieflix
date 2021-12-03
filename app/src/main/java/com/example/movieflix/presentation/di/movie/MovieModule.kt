package com.example.movieflix.presentation.di.movie

import com.example.movieflix.domain.usecase.GetPopularMoviesUseCase
import com.example.movieflix.domain.usecase.GetTopRatedMoviesUseCase
import com.example.movieflix.domain.usecase.GetVideosFromMovieUseCase
import com.example.movieflix.domain.usecase.SearchMovieByNameUseCase
import com.example.movieflix.presentation.ui.moviedetail.MovieDetailViewModelFactory
import com.example.movieflix.presentation.ui.movielist.MovieListViewModelFactory
import com.example.movieflix.presentation.ui.moviesearch.MovieSearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MovieModule {

    @MovieScope
    @Provides
    fun provideMovieListViewModelFactory(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
    ):MovieListViewModelFactory{
        return MovieListViewModelFactory(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase)
    }

    @MovieScope
    @Provides
    fun provideMovieSearchViewModelFactory(
        searchMovieByNameUseCase: SearchMovieByNameUseCase
    ): MovieSearchViewModelFactory {
        return MovieSearchViewModelFactory(
            searchMovieByNameUseCase
        )
    }

    @MovieScope
    @Provides
    fun provideMovieDetailViewModelFactory(
        getVideosFromMovieUseCase: GetVideosFromMovieUseCase
    ): MovieDetailViewModelFactory {
        return MovieDetailViewModelFactory(
            getVideosFromMovieUseCase
        )
    }
}