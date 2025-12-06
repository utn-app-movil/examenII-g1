package Service

import model.pablo_LoginRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import util.util.Companion.apiURL

object pablo_RetrofitClient {
     val instance = Retrofit.Builder()
        .baseUrl(apiURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(pablo_ApiContainers::class.java)

}