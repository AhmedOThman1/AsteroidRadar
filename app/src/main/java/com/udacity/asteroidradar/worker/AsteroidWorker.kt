package com.udacity.asteroidradar.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidsDb
import com.udacity.asteroidradar.repository.AsteroidsRepository
import retrofit2.HttpException

class AsteroidWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "AsteroidWorker"
    }

    //I didn't understand what are you meaning about
    //      "use a coroutine for downloading today's asteroid list."
    //Does this mean that to download it from the beginning of tomorrow's date and there will be another function for today?
    override suspend fun doWork(): Result {
        val database = AsteroidsDb.getDb(applicationContext)
        val asteroidsRepository = AsteroidsRepository(database)
        return try {
            asteroidsRepository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}