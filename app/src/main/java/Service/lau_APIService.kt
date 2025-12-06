package Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object lau_APIService {
    private var URL = util.util.apiURL

    val techniciansService: lau_ITechniciansAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(lau_ITechniciansAPIService::class.java)
    }

    val containersService: lau_IContainersAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(lau_IContainersAPIService::class.java)
    }

    val authService: lau_IAuthAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(lau_IAuthAPIService::class.java)
    }
}