package Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.util

object kam_RetrofitClient {

    val apiService: kam_ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(util.apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(kam_ApiService::class.java)
    }
}