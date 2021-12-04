package com.example.movieflix.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieflix.data.model.series.Serie

@Dao
interface SerieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: List<Serie>)

    @Query("DELETE FROM series WHERE type=:type")
    suspend fun deleteSeriesByType(type:String)

    @Query("SELECT * FROM series WHERE type=:type")
    suspend fun getSeriesByType(type:String):List<Serie>

    @Query("SELECT * FROM series")
    suspend fun getSeries():List<Serie>
}