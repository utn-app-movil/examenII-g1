package util

import interfaces.dyl_ITechAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object dyl_RetrofitClient {

    private val BASE_URL= util.apiURL

    val apiAUTH: dyl_ITechAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(dyl_ITechAPIService::class.java)
    }
}