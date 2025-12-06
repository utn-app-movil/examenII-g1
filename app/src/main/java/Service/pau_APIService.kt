package Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.util

object pau_APIService {

    val pau_IAPIService: pau_IAPIService by lazy { Retrofit.Builder()
        .baseUrl(util.Companion.apiURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(pau_IAPIService::class.java)
    }
}