package com.example.h_mal.movielisttest.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * Room database class for caching movies locally.
 */
@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MoviesRoomDatabase : RoomDatabase() {

    abstract fun getSimpleDao(): SimpleDao

    companion object {

        @Volatile
        private var instance: MoviesRoomDatabase? = null
        private val LOCK = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MoviesRoomDatabase::class.java,
                "MyDatabase.db"
            ).build()
    }
}