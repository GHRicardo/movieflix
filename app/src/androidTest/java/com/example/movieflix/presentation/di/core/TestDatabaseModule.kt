package com.example.movieflix.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.example.movieflix.data.db.MovieDao
import com.example.movieflix.data.db.MovieDatabase
import com.example.movieflix.data.db.SerieDao
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class TestDatabaseModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryMovieDatabase(context: Context):MovieDatabase{
        return Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideInMemoryMovieDao(@Named("test_db") movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    @Provides
    fun provideInMemorySerieDao(@Named("test_db")movieDatabase: MovieDatabase): SerieDao {
        return movieDatabase.serieDao()
    }
}