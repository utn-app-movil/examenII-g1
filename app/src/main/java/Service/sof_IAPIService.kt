package cr.ac.utn.appmovil.containers.Service

import cr.ac.utn.appmovil.containers.model.ApiResponse
import cr.ac.utn.appmovil.containers.model.AssignRequest
import cr.ac.utn.appmovil.containers.model.Container
import cr.ac.utn.appmovil.containers.model.ContainerRequest
import cr.ac.utn.appmovil.containers.model.LoginRequest
import cr.ac.utn.appmovil.containers.model.ReleaseRequest
import cr.ac.utn.appmovil.containers.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface apiService {

    @POST("users/auth")
    fun authenticate(@Body request: LoginRequest): Call<ApiResponse<User>>

    @GET("technicians")
    fun getTechnicians(): Call<ApiResponse<List<User>>>

    @GET("containers")
    fun getContainers(): Call<ApiResponse<List<Container>>>

    @POST("containers")
    fun createContainer(@Body request: ContainerRequest): Call<ApiResponse<Container>>

    @PUT("containers/asign")
    fun assignContainer(@Body request: AssignRequest): Call<ApiResponse<Container>>

    @PUT("containers/releas")
    fun releaseContainer(@Body request: ReleaseRequest): Call<ApiResponse<Container>>
}

object sof_apiService {

    val sof_ApiService: apiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://apicontainers.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiService::class.java)
    }
}