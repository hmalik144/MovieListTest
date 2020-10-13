package com.example.h_mal.movielisttest.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SimpleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAllItems(items: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity")
    fun getAllItems(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getItem(id: Int): MovieEntity

    @Query("UPDATE MovieEntity SET favourites = :favourite WHERE id = :id")
    fun setFavourite(id: Int, favourite: Boolean)

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteEntries()

    @Transaction
    suspend fun updateFavourite(id: Int){
        val fav = getItem(id).favourites
        setFavourite(id, !fav)
    }

    @Delete
    fun deleteEntry(movie: MovieEntity)
}