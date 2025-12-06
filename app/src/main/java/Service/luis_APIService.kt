package Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.util

object luis_APIService {

    val apiPeople: luis_IAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(util.Companion.apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(luis_IAPIService::class.java)
    }

}