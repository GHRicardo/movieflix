package com.example.movieflix.presentation.di.core

import com.example.movieflix.data.repository.movie.MovieRepositoryImpl
import com.example.movieflix.data.repository.movie.datasource.MovieLocalDataSource
import com.example.movieflix.data.repository.movie.datasource.MovieRemoteDataSource
import com.example.movieflix.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource
    ):MovieRepository{
        return MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource)
    }
}