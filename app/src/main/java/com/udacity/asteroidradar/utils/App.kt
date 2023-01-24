package com.udacity.asteroidradar.utils

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.work.*
import com.udacity.asteroidradar.worker.AsteroidWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class App : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Log.w("Application", "HERE1")
        applicationScope.launch {
            Log.w("Application", "HERE2")
            setupRecurringWork()
            Log.w("Application", "HERE_LAST")
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()
        Log.w("Application", "HERE3")

        val repeatingRequest = PeriodicWorkRequestBuilder<AsteroidWorker>(
            1,
            TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .build()
        Log.w("Application", "HERE4")

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            AsteroidWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )

        Log.w("Application", "HERE5")
    }
}