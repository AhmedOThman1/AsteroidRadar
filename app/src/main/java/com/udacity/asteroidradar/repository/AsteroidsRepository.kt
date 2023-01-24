package com.udacity.asteroidradar.repository

import android.util.Log.w
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidsDb
import com.udacity.asteroidradar.network.ApiClient
import com.udacity.asteroidradar.pojo.Asteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await

class AsteroidsRepository(private val asteroidsDb: AsteroidsDb) {

    fun getTodayAsteroids(): LiveData<List<Asteroid>> {
        return asteroidsDb.asteroidDao.getTodayAsteroids()
    }

    fun getWeekAsteroids(): LiveData<List<Asteroid>> {
        return asteroidsDb.asteroidDao.getWeekAsteroids()
    }

    fun getSavedAsteroids(): LiveData<List<Asteroid>> {
        return asteroidsDb.asteroidDao.getSavedAsteroids()
    }

    suspend fun refreshAsteroids() {
        w("refreshAsteroids", "HERE1")
        withContext(Dispatchers.IO) {
            try {
                val asteroids = ApiClient.retrofitService.getAsteroids(
                    BuildConfig.NASA_API_KEY
                ).await()
                val list = parseAsteroidsJsonResult(JSONObject(asteroids))
                for (asteroid in list)
                    asteroidsDb.asteroidDao.insertAsteroidToDatabase(asteroid)
            } catch (ignore: Exception) {
                w("refreshAsteroids", "HERE7, " + ignore.message)
                if (ignore.message.equals("timeout"))
                    try {
                        refreshAsteroids()
                    } catch (ignore: Exception) {

                    }
                else {
                    //DO NOTHING
                }
            }
        }
    }
}