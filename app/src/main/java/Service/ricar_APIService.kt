package Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ricar_APIService {
    private const val BASE_URL = "https://apicontainers.azurewebsites.net"

    val api: ricar_IAPIService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ricar_IAPIService::class.java)
    }
}