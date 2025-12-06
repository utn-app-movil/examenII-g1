package cr.ac.utn.appmovil.containers

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface marco_ContainerService {

    @GET("containers")
    fun getContainers(): Call<marco_ContainerListResponse>

    @POST("containers")
    fun createContainer(
        @Body body: marco_CreateContainerRequest
    ): Call<marco_ContainerResponse>

    @PUT("containers/asign")
    fun assignContainer(
        @Body body: marco_AssignContainerRequest
    ): Call<marco_ContainerResponse>

    @PUT("containers/release")
    fun releaseContainer(
        @Body body: marco_ReleaseContainerRequest
    ): Call<marco_ContainerResponse>
}

object marco_ContainerApiClient {

    private const val BASE_URL = "https://apicontainers.azurewebsites.net/"

    val service: marco_ContainerService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(marco_ContainerService::class.java)
    }
}