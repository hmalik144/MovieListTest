package com.example.h_mal.movielisttest.data.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class MoviesRoomDatabaseTest{
    private lateinit var simpleDao: SimpleDao
    private lateinit var db: MoviesRoomDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MoviesRoomDatabase::class.java).build()
        simpleDao = db.getSimpleDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeEntryAndReadResponse() = runBlocking{
        // Given
        val entity = MovieEntity(123)
        // When
        simpleDao.saveAllItems(listOf(entity))
        // Then
        val retrieved = simpleDao.getItem(123)
        assertThat(retrieved, equalTo(entity))
    }

    @Test
    @Throws(Exception::class)
    fun changeFavouriteAndRead() = runBlocking{
        // Given
        val entity = MovieEntity(123)
        simpleDao.saveAllItems(listOf(entity))
        val retrieved = simpleDao.getItem(123)

        // When
        simpleDao.updateFavourite(123)

        // Then
        val favourite = retrieved.favourites
        val retrieveAgain = simpleDao.getItem(123)
        assertThat(retrieveAgain.favourites, equalTo(!favourite))
    }
}