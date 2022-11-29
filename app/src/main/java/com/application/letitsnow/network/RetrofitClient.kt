package com.application.letitsnow.network

import com.application.letitsnow.utils.BASE_URL
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        var api: ApiService? = null
        private val json = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }

        @OptIn(ExperimentalSerializationApi::class)
        fun getInstance(): ApiService {
            if (api == null) {
                val contentType = "application/json".toMediaType()

                val interceptor =
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

                val client: OkHttpClient =
                    OkHttpClient.Builder().addInterceptor(interceptor).build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                api = retrofit.create(ApiService::class.java)
            }
            return api!!
        }
    }
}