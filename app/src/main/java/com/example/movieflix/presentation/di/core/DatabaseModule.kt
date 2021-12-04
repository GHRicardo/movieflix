package com.example.movieflix.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.example.movieflix.data.db.MovieDao
import com.example.movieflix.data.db.MovieDatabase
import com.example.movieflix.data.db.SerieDao
import com.example.movieflix.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesMovieDatabase(context: Context):MovieDatabase{
        return Room.databaseBuilder(context, MovieDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase):MovieDao{
        return movieDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideSerieDao(movieDatabase: MovieDatabase):SerieDao{
        return movieDatabase.serieDao()
    }
}