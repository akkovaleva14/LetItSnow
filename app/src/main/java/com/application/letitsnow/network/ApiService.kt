package com.application.letitsnow.network

import com.application.letitsnow.data.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY: String = "599041b0cd314490ac1170219221111"

interface ApiService {

    @GET("v1/current.json")
    suspend fun getTown(
        @Query("key") key : String = API_KEY,
        @Query("q") town : String = "Saint Petersburg",
        @Query("aqi") aqi : String = "no",
    ) : Response<List<Weather>>

}