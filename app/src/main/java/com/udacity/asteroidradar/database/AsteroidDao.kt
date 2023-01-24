package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.pojo.Asteroid

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroidToDatabase(vararg asteroid:Asteroid)


    @Query("select * from AsteroidsTable where ((select date())<= closeApproachDate) AND ((select date('now','+7 days'))>=closeApproachDate) order by closeApproachDate ASC")
    fun getSavedAsteroids(): LiveData<List<Asteroid>>

    //    @Query("select * from AsteroidsTable where closeApproachDate between ( (select date()) AND select dateadd(day,7,date()))")
//    @Query("select * from AsteroidsTable where closeApproachDate between ( (select date()) AND (select date('now','+7 days')))")
    @Query("select * from AsteroidsTable where ((select date('now','+1 day'))<= closeApproachDate) AND ((select date('now','+7 days'))>=closeApproachDate) order by closeApproachDate ASC")
    fun getWeekAsteroids(): LiveData<List<Asteroid>>

    @Query("select * from AsteroidsTable where closeApproachDate = (select date()) ")
    fun getTodayAsteroids(): LiveData<List<Asteroid>>


}