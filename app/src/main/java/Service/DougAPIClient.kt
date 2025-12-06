package Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.util

object DougAPIClient {

    val apiService: DougAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(util.apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DougAPIService::class.java)
    }
}