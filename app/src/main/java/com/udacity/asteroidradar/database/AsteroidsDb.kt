package com.udacity.asteroidradar.database

import android.content.Context
import android.util.Log
import androidx.room.*
import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.pojo.Asteroid

@Database(entities = [Asteroid::class], version = 1)
abstract class AsteroidsDb : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

    companion object{

        private lateinit var INSTANCE: AsteroidsDb

        fun getDb(context: Context): AsteroidsDb {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidsDb::class.java,
                        "AsteroidsTable"
                    )
                        .build()
                }

            return INSTANCE
        }
    }

}
