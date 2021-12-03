package com.example.movieflix.presentation.di.core

import com.example.movieflix.domain.repository.MovieRepository
import com.example.movieflix.domain.usecase.*
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetPopularMoviesUseCase(movieRepository: MovieRepository):GetPopularMoviesUseCase{
        return GetPopularMoviesUseCase(movieRepository)
    }

    @Provides
    fun provideGetTopRatedMoviesUseCase(movieRepository: MovieRepository):GetTopRatedMoviesUseCase{
        return GetTopRatedMoviesUseCase(movieRepository)
    }

    @Provides
    fun provideSearchMovieByNameUseCase(movieRepository: MovieRepository):SearchMovieByNameUseCase{
        return SearchMovieByNameUseCase(movieRepository)
    }

    @Provides
    fun provideGetSomeRandomMovieUseCase(movieRepository: MovieRepository):GetSomeRandomMovieUseCase{
        return GetSomeRandomMovieUseCase(movieRepository)
    }

    @Provides
    fun provideGetVideosFromMovieUseCase(movieRepository: MovieRepository):GetVideosFromMovieUseCase{
        return GetVideosFromMovieUseCase(movieRepository)
    }
}