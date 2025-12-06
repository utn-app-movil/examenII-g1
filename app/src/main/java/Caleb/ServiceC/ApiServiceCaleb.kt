package Caleb.ServiceC

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceCaleb {
    private const val BASE_URL = "https://apicontainers.azurewebsites.net"

    val api: IAPIServiceC by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IAPIServiceC::class.java)
    }
}