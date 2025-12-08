package cr.ac.utn.appmovil.containers.mjose_network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object mjose_RetrofitClient {

    private const val BASE_URL = "https://apicontainers.azurewebsites.net/api/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}