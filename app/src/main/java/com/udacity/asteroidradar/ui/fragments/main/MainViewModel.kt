package com.udacity.asteroidradar.ui.fragments.main

import android.app.Application
import android.util.Log.w
import androidx.lifecycle.*
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidsDb.Companion.getDb
import com.udacity.asteroidradar.network.ApiClient
import com.udacity.asteroidradar.pojo.Asteroid
import com.udacity.asteroidradar.pojo.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.await

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val database = getDb(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    init {
        try {
            getPictureOfDayFromNetwork()
//            viewModelScope.launch {
//                getAsteroidsFromNetwork()
//            }
        } catch (ignore: Exception) {

        }

    }

    fun getTodayAsteroidsFromRoom(): LiveData<List<Asteroid>> =
        asteroidsRepository.getTodayAsteroids()

    fun getWeekAsteroidsFromRoom(): LiveData<List<Asteroid>> =
        asteroidsRepository.getWeekAsteroids()

    fun getSavedAsteroidsFromRoom(): LiveData<List<Asteroid>> =
        asteroidsRepository.getSavedAsteroids()

    fun getStarterAsteroidsFromRoom(): LiveData<List<Asteroid>> =
        asteroidsRepository.getWeekAsteroids()

    suspend fun getAsteroidsFromNetwork() =
        asteroidsRepository.refreshAsteroids()

    private fun getPictureOfDayFromNetwork() = viewModelScope.launch {
        try {
            _pictureOfDay.value =
                ApiClient.retrofitService.getPictureOfDay(BuildConfig.NASA_API_KEY).await()
        } catch (ignore: Exception) {

        }
    }

//    private fun getDataFromNetwork() = viewModelScope.launch {
//        val asteroids = ApiClient.retrofitService.getAsteroids(
//            BuildConfig.NASA_API_KEY
//        ).await()
//        _response.value = parseAsteroidsJsonResult(JSONObject(asteroids))
//    }

//    private fun getDataFromNet3ork() {
//        val arr = getNextSevenDaysFormattedDates()
//        val startDate: String = arr[0]
//        val endDate: String = arr[arr.lastIndex]
//        ApiClient.retrofitService.getAsteroids(
//            startDate, endDate, BuildConfig.NASA_API_KEY
//        ).enqueue(object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Log.w("Failed2", "" + t.message)
//            }
//
//        })
//    }

    //    private fun getPictureOfDay() =viewModelScope.launch {
//        _pictureOfDay.value = response.body()
//    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }


}