package cr.ac.utn.appmovil.containers

import util.util
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface marco_AuthService {

    @GET("technicians")
    fun getTechnicians(): Call<marco_TechniciansResponse>

    @POST("users/auth")
    fun login(@Body body: marco_LoginRequest): Call<marco_LoginResponse>
}

object marco_AuthApiClient {

    val service: marco_AuthService by lazy {
        Retrofit.Builder()
            .baseUrl("${util.apiURL}/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(marco_AuthService::class.java)
    }
}