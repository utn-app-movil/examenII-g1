package Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object sam_APIService {

    private const val BASE_URL = "https://apicontainers.azurewebsites.net/"

    val api: sam_IAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(sam_IAPIService::class.java)
    }
}
