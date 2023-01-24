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

    override suspend fun doWork(): Result {
        Log.w("Worker", "HERE1")
        val database = AsteroidsDb.getDb(applicationContext)
        Log.w("Worker", "HERE2")
        val asteroidsRepository = AsteroidsRepository(database)
        Log.w("Worker", "HERE3")
        return try {
            Log.w("Worker", "HERE4")
            asteroidsRepository.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}