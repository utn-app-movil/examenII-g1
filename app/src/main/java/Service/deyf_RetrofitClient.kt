package Service

import interfaces.deyf_APIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object deyf_RetrofitClient {
    private const val BASE_URL = "https://apicontainers.azurewebsites.net/"

    val deyf_apiService: deyf_APIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(deyf_APIService::class.java)
    }
}