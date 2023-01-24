package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.utils.Constants
import com.udacity.asteroidradar.pojo.PictureOfDay
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create())
    .build();

interface NasaApiService {

    @GET("planetary/apod")
    fun getPictureOfDay(@Query("api_key") apiKey: String): Call<PictureOfDay>

    @GET("neo/rest/v1/feed")
    fun getAsteroids(
//        @Query("start_date") startDate: String,
//        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String,
    ): Call<String>

}

object ApiClient {
    val retrofitService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }

}