package cr.ac.utn.appmovil.containers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.util

object bran_RetrofitClient {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(util.apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: bran_ApiService by lazy {
        retrofit.create(bran_ApiService::class.java)
    }
}